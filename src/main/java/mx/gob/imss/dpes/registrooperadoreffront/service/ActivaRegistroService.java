/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.registrooperadoreffront.service;

import javax.inject.Inject;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.common.exception.BadRequestException;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.registrooperadoreffront.assembler.RegistroPersonaAssembler;
import mx.gob.imss.dpes.registrooperadoreffront.model.ActivarRegistroRequest;
import mx.gob.imss.dpes.registrooperadoreffront.model.RegistroPersona;
import mx.gob.imss.dpes.registrooperadoreffront.restclient.RegistroPensionadoClient;
import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author eduardo.montesh
 * 
 * Guarda en la base de datos al usuario. Tabla Persona
 */
@Provider
public class ActivaRegistroService extends ServiceDefinition<ActivarRegistroRequest, ActivarRegistroRequest>{

    @Inject
    @RestClient
    RegistroPensionadoClient registroClient;
    
    @Override
    public Message<ActivarRegistroRequest> execute(Message<ActivarRegistroRequest> request) throws BusinessException {
        
        RegistroPersonaAssembler assembler = new RegistroPersonaAssembler();
        RegistroPersona persona = assembler.assemble(request.getPayload());
        
        persona.setPassword(
            DigestUtils.sha256Hex(request.getPayload().getPassword())
        );
        
        Response response = registroClient.persisteUsuario(persona);
        if (response.getStatus() == 200) {
        return request;
        }
        return response(null, ServiceStatusEnum.EXCEPCION, new BadRequestException(), null);
    }
    
}
