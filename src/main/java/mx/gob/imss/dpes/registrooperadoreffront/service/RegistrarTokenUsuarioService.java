/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.registrooperadoreffront.service;

import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.UnknowException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.registrooperadoreffront.model.RegistroRequest;
import mx.gob.imss.dpes.registrooperadoreffront.restclient.RegistroPensionadoClient;
import mx.gob.imss.dpes.support.util.ExceptionUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author edgar.arenas
 */
@Provider
public class RegistrarTokenUsuarioService extends ServiceDefinition<RegistroRequest, RegistroRequest> {
    
  @Inject
  @RestClient
  private RegistroPensionadoClient client;
  
    @Override
    public Message<RegistroRequest> execute(Message<RegistroRequest> request) throws BusinessException {

        log.log(Level.INFO, ">>>>>>>FRONT Registrar Token: {0}", request.getPayload());
        Response event = null;
        try {
            event = client.create(request.getPayload());
        } 
        catch (RuntimeException e) {
            e.printStackTrace();
            log.log(Level.SEVERE, ">>>>>>>  !!!--- ERROR: RuntimeException message=\n{0}", e.getMessage());
            if (e.getMessage().contains("Unknown error, status code 404") || e.getMessage().contains("Unknown error, status code 502")) {
                log.log(Level.SEVERE, ">>>>>>>  !!!--- ERROR: Servicio no encontrado {0}", "RegistroOperadorEFFront");
                ExceptionUtils.throwServiceException("RegistroOperadorEFFront");
            }
          throw new UnknowException();
        }
        
        if (event.getStatus() == 200) {
            return request;
        }
        return response(null, ServiceStatusEnum.EXCEPCION, new UnknowException(), null);
    }
}
