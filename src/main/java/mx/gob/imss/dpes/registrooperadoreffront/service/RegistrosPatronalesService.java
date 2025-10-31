/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.registrooperadoreffront.service;

import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.registrooperadoreffront.model.ValidarCandidatoOperadorRequest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.entidadfinanciera.ot2.model.RegistrosPatronales;
import mx.gob.imss.dpes.registrooperadoreffront.model.RegistrosPatronalesOut;
import mx.gob.imss.dpes.registrooperadoreffront.restclient.RegistrosPatronalesClient;

/**
 *
 * @author juanf.barragan
 */
@Provider
public class RegistrosPatronalesService extends ServiceDefinition<ValidarCandidatoOperadorRequest, ValidarCandidatoOperadorRequest> {
    
    @Inject
    @RestClient
    RegistrosPatronalesClient client;
    
    public Message<ValidarCandidatoOperadorRequest> execute(Message<ValidarCandidatoOperadorRequest> request) throws BusinessException {
        
        Response response = null;
        RegistrosPatronalesOut out = new  RegistrosPatronalesOut();
        log.log(Level.INFO, ">>>>>>>>>  Antes de ejecutar el servicio Consulta Registros Patronales: " + request.getPayload().getCveEntidadFinanciera());
        
        try{
            response = client.load(request.getPayload().getCveEntidadFinanciera());
            
            List<RegistrosPatronales> outs = response.readEntity(new GenericType<List<RegistrosPatronales>>(){});
            out.setRegistrosPatronales(outs);
            request.getPayload().setRegistrosPatronalesOut(out);
        }catch(Exception e){
            request.getPayload().setRegistrosPatronalesOut(null);
        }
        return request;
    }
    
}
