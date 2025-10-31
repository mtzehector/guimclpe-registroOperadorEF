/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.registrooperadoreffront.service;

import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.common.exception.UnknowException;
import mx.gob.imss.dpes.interfaces.relacionlaboral.model.InfoCIRelacionLaboral;
import mx.gob.imss.dpes.interfaces.relacionlaboral.model.RelacionLaboralIn;
import mx.gob.imss.dpes.interfaces.relacionlaboral.model.RelacionLaboralOut;
import mx.gob.imss.dpes.registrooperadoreffront.model.ValidarCandidatoOperadorRequest;
import mx.gob.imss.dpes.registrooperadoreffront.restclient.RelacionLaboralClient;
import mx.gob.imss.dpes.support.util.ExceptionUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author eduardo.montesh
 */
@Provider
public class RelacionLaboralService extends ServiceDefinition<ValidarCandidatoOperadorRequest, ValidarCandidatoOperadorRequest> {

    @Inject
    @RestClient
    RelacionLaboralClient relacionLaboralClient;

    @Override
    public Message<ValidarCandidatoOperadorRequest> execute(Message<ValidarCandidatoOperadorRequest> request) throws BusinessException {
        Response response = null;
        log.log(Level.INFO, ">>>>>>>>>  Antes de ejecutar el servicio Relacion Laboral: " + request.getPayload().getNss());

        try {
            RelacionLaboralIn in = new RelacionLaboralIn();
            in.setNss(request.getPayload().getNss());
            response = relacionLaboralClient.load(in);

            log.log(Level.INFO, "Status: {0}", response.getStatus());

        } catch (RuntimeException e) {
            e.printStackTrace();
            log.log(Level.SEVERE, ">>>>>>>  !!!--- ERROR: RuntimeException message=\n{0}", e.getMessage());
            if (e.getMessage().contains("Unknown error, status code 404") || e.getMessage().contains("Unknown error, status code 502")) {
                log.log(Level.SEVERE, ">>>>>>>  !!!--- ERROR: Servicio no encontrado {0}", "RegistroOperadorEFFront");
                ExceptionUtils.throwServiceException("Relacion Laboral");
            }
            throw new UnknowException();
        }
        RelacionLaboralOut out = null;

        out = response.readEntity(RelacionLaboralOut.class);
        if (out.getCode().equals("002")) {
            log.log(Level.SEVERE, "Muestra mensaje de error al Codigo 002");
            throw new BusinessException("msg355");
        }
        log.log(Level.SEVERE, "pasa por aqui");

        log.log(Level.SEVERE, ">>>>>>>  Empieza a validar relación laboral más reciente: " + out.getListInfoRelacionesLaborales().size());

        int i = 0;
        for (InfoCIRelacionLaboral info : out.getListInfoRelacionesLaborales()) {
            if (info.getLstInfoRelacionesLaborales().getFecFinRelLab().equals("9999-12-31")) {
                out.setRegistroPatronalActual(info.getLstInfoRelacionesLaborales().getRegPatron());
            }
        }

        request.getPayload().setRelacionLaboral(out);

        log.log(Level.INFO, ">>>>>>>>>  Después de ejecutar el servicio Relacion Laboral {0}" + out);

        return request;

    }

}
