/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.registrooperadoreffront.service;

import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.renapo.model.RenapoCurpIn;
import mx.gob.imss.dpes.interfaces.renapo.model.RenapoCurpRequest;
import mx.gob.imss.dpes.registrooperadoreffront.exception.RenapoCurpException;
import mx.gob.imss.dpes.registrooperadoreffront.model.RegistroRequest;
import mx.gob.imss.dpes.registrooperadoreffront.model.ValidarCandidatoOperadorRequest;
import mx.gob.imss.dpes.renapo.service.RenapoCurpService;

/**
 * 
 * @author juan.garfias
 */
@Provider
public class ObtenDatosRenapo extends  ServiceDefinition<ValidarCandidatoOperadorRequest, ValidarCandidatoOperadorRequest>{
       
    @Inject
    RenapoCurpService renapo;
    
    @Override
    public Message<ValidarCandidatoOperadorRequest> execute(Message<ValidarCandidatoOperadorRequest> request) throws BusinessException {
        log.log( Level.INFO, ">>>>ObtenDatosRenapo Request: {0}", request.getPayload());
        
        RenapoCurpRequest renapoRequest = new RenapoCurpRequest();
        RenapoCurpIn in = new RenapoCurpIn();
        
        in.setCurp(request.getPayload().getCurp());      
        renapoRequest.setRenapoCurpIn(in);
        
        Message<RenapoCurpRequest> response = renapo.execute(new Message<>(renapoRequest));
        
        log.log( Level.INFO, ">>>>  ObtenDatosRenapo response= {0}",response);

	
        if (Message.isExito(response)) {
            request.getPayload().setRenapoRequest(response.getPayload());
            return request;
        }
        log.log(Level.SEVERE, "!!!   ERROR >>>>ObtenDatosRenapo Request: {0}", request.getPayload());
        return response(null, ServiceStatusEnum.EXCEPCION, new RenapoCurpException(), null);
    }
    
    
}
