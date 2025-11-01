/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.registrooperadoreffront.service;

import java.lang.reflect.UndeclaredThrowableException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.RenapoBDTUException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.renapo.model.RenapoCurpOut;
import mx.gob.imss.dpes.registrooperadoreffront.exception.CurpInvalidStatusException;
import mx.gob.imss.dpes.registrooperadoreffront.exception.CurpNotBelongsToNSSException;
import mx.gob.imss.dpes.registrooperadoreffront.exception.RenapoBDTUInfoNotMacthingException;
import mx.gob.imss.dpes.common.exception.UnknowException;
import mx.gob.imss.dpes.registrooperadoreffront.model.BDTUPersonaRenapoResponse;
import mx.gob.imss.dpes.registrooperadoreffront.model.ValidarCandidatoOperadorRequest;
import mx.gob.imss.dpes.registrooperadoreffront.restclient.BDTUPersonaClient;
import mx.gob.imss.dpes.support.util.ExceptionUtils;
import mx.gob.imss.dpes.support.util.ServiceLogger;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author eduardo.montesh
 */
@Provider
public class ComparaBDTU extends ServiceDefinition<ValidarCandidatoOperadorRequest, ValidarCandidatoOperadorRequest> {

    @Inject
    @RestClient
    BDTUPersonaClient bdtuPersonaClient;
    @Inject
    CreateBitacoraService createBitacoraService;

    final static String DATE_FORMAT = "dd/MM/yyyy";
    final static DateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);

    @Override
    public Message<ValidarCandidatoOperadorRequest> execute(Message<ValidarCandidatoOperadorRequest> request) throws BusinessException {
  
        log.log(Level.INFO, ">>>>>>>>>  Antes de ejecutar el servicio bdtu " + request.getPayload().getCurp() +" - "+request.getPayload().getNss());
        Response response = null;
        try {
            response = bdtuPersonaClient.load(request.getPayload().getCurp(),request.getPayload().getNss());
            log.log(Level.INFO, ">>>>>>>>>  Despues de ejecutar el load de bdtu");
        } 
        catch (UndeclaredThrowableException ex) {
            ex.printStackTrace();
            createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(request.getPayload(),"Servicio BDTU no disponible o inalcanzable",true)));
            ExceptionUtils.throwServiceException("BDTU");
        }
        catch (Exception e) {
            String msg = e.getMessage();
            log.log(Level.SEVERE, ">>>>>>>ComparaBDTU  !!!--- ERROR: INIT Exception message={0}", msg);
            e.printStackTrace();
            createBitacoraService.createInterfaz(new Message<>(ServiceLogger.log(request.getPayload(),e.getMessage(),true)));
            if (msg!=null && (msg.contains("El NSS no fue localizado en BDTU") || msg.contains("Los datos estadisticos no coinciden entre RENAPO y BDTU"))) {
               throw new RenapoBDTUException(RenapoBDTUException.INVALID_NSS);
            }
            if (msg!=null && (msg.contains("La CURP no se localizo en RENAPO") || msg.contains("La CURP se encuentra en un estatus Baja"))) {
                throw new RenapoBDTUException(RenapoBDTUException.INVALID_CURP);
            }
            if (msg!=null && msg.contains("Servicio NO disponible")) {
                ExceptionUtils.throwServiceException("BDTU");

            }
            throw new UnknowException("msg370");
        }
        BDTUPersonaRenapoResponse out = response.readEntity(BDTUPersonaRenapoResponse.class);
        log.log(Level.INFO, ">>>>>>>>>  Después de ejecutar el servicio bdtu {0}", out);
        
        request.getPayload().setBdtuResponse(out);
        
        return request;
    }

    private void comparaDatos(ValidarCandidatoOperadorRequest registroData) throws BusinessException {
        RenapoCurpOut renapo = registroData.getRenapoRequest().getRenapoCurpOut();
        BDTUPersonaRenapoResponse bdtu = registroData.getBdtuResponse();

        if (!registroData.getNss().equals(bdtu.getNss())) {
            throw new CurpNotBelongsToNSSException();
        }

        if (!renapo.getNombres().equals(bdtu.getNombre())) {
            log.log(Level.SEVERE, ">>>>>>>>>  RenapoBDTUInfoNotMacthingException getNombres");
            throw new RenapoBDTUInfoNotMacthingException();
        }

        if (!renapo.getApellido1().equals(bdtu.getPrimerApellido())) {
            log.log(Level.SEVERE, ">>>>>>>>>  RenapoBDTUInfoNotMacthingException getPrimerApellido");
            throw new RenapoBDTUInfoNotMacthingException();
        }

        if (!renapo.getApellido2().equals(bdtu.getSegundoApellido())) {
            log.log(Level.SEVERE, ">>>>>>>>>  RenapoBDTUInfoNotMacthingException getSegundoApellido");
            throw new RenapoBDTUInfoNotMacthingException();
        }
        Date renapoFecNac = null;
        Date bdtuFecNac = null;
        try {
            renapoFecNac = format.parse(renapo.getFechNac());
            bdtuFecNac = format.parse(bdtu.getFechaNacimiento());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (renapoFecNac != null && bdtuFecNac != null && renapoFecNac.compareTo(bdtuFecNac) != 0) {
            log.log(Level.SEVERE, ">>>>>>>>>  RenapoBDTUInfoNotMacthingException renapo.getFechNac()={0}", renapo.getFechNac());
            log.log(Level.SEVERE, ">>>>>>>>>  RenapoBDTUInfoNotMacthingException bdtu.getFechaNacimiento()={0}", bdtu.getFechaNacimiento());
            log.log(Level.SEVERE, ">>>>>>>>>  RenapoBDTUInfoNotMacthingException getFechaNacimiento");
            throw new RenapoBDTUInfoNotMacthingException();
        }

        if (renapo.getEstatusCURP() != null) {
            /*
                        Activas
            Estatus	Definición
            AN	Alta Normal
            AH	Alta con Homonimia
            CRA	CURP ReActivada
            RCN	Registro de Cambio No afectando a CURP
            RCC	Registro de Cambio Afectando a CURP

                    Desactivadas
            Estatus	Definición
            BAP	Baja por Documento Apócrifo
            BD	Baja por Defunción
            BDA	Baja por Duplicidad
            BCC	Baja por Cambio en CURP
            BCN	Baja no afectando a CURP
             */

            if (!(renapo.getEstatusCURP().equals("AN")
                    || renapo.getEstatusCURP().equals("AH")
                    || renapo.getEstatusCURP().equals("CRA")
                    || renapo.getEstatusCURP().equals("RCN")
                    || renapo.getEstatusCURP().equals("RCC"))) {

                List parameters = new LinkedList();
                parameters.add(renapo.getEstatusCURP());
                throw new CurpInvalidStatusException(parameters);
            }
        }
    }
}
