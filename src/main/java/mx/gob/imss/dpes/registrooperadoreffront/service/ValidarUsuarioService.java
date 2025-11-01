/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.registrooperadoreffront.service;

import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.BadRequestException;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.exception.UnknowException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.registrooperadoreffront.exception.EMailExistException;
import mx.gob.imss.dpes.registrooperadoreffront.exception.UserOrPwdInvalidException;
import mx.gob.imss.dpes.registrooperadoreffront.model.RegistroRequest;
import mx.gob.imss.dpes.registrooperadoreffront.restclient.RegistroPensionadoClient;
import mx.gob.imss.dpes.registrooperadoreffront.rule.CalculoVigenciaToken;
import mx.gob.imss.dpes.registrooperadoreffront.rule.GenerarToken;
import mx.gob.imss.dpes.support.util.ExceptionUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author edgar.arenas
 */
@Provider
public class ValidarUsuarioService extends ServiceDefinition<RegistroRequest, RegistroRequest> {
    
  @Inject
  @RestClient
  private RegistroPensionadoClient client;
  
  @Inject
  private CalculoVigenciaToken rule1;
  
  @Inject
  private GenerarToken rule2;
    
    @Override
    public Message<RegistroRequest> execute(Message<RegistroRequest> request) throws BusinessException {

        log.log(Level.INFO, ">>>>>>>FRONT Valida Usuario : {0}", request.getPayload());
        Integer validaUsuario = 0;
        try {
            validaUsuario = client.validarUsuario(request.getPayload());
        } 
        
        catch (RuntimeException e) {
            e.printStackTrace();
            log.log(Level.SEVERE, ">>>>>>>  !!!--- ERROR: RuntimeException message=\n{0}", e.getMessage());
            if (e.getMessage().contains("Unknown error, status code 404") || e.getMessage().contains("Unknown error, status code 502")) {
                log.log(Level.SEVERE, ">>>>>>>  !!!--- ERROR: Servicio no encontrado {0}", "RegistroOperadorEFFront");
                ExceptionUtils.throwServiceException("RegistroOperadorEFFront");
            }
            if (e.getMessage().contains("Unknown error, status code 500")) {
                log.log(Level.SEVERE, ">>>>>>>  !!!--- ERROR UserOrPwdInvalidException");
                throw new UserOrPwdInvalidException();
            }

            throw new UnknowException();
        }
        log.log(Level.INFO, ">>>>>>>FRONT Usuario= {0}", validaUsuario);

        if (validaUsuario == 0) {

            rule1.apply(request.getPayload());
            rule2.apply(request.getPayload());
            log.log(Level.INFO, ">>>>>>>REQUEST DESPUES DE APLICAR RULES : {0}", request);
            return new Message<>(request.getPayload());
        }

        return response(null, ServiceStatusEnum.EXCEPCION, new EMailExistException(), null);
    }
    

  
}
