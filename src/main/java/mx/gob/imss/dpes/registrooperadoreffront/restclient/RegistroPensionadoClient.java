/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.registrooperadoreffront.restclient;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.registrooperadoreffront.model.ActivarRegistroRequest;
import mx.gob.imss.dpes.registrooperadoreffront.model.LogginRequest;
import mx.gob.imss.dpes.registrooperadoreffront.model.RegistroPersona;
import mx.gob.imss.dpes.registrooperadoreffront.model.RegistroRequest;
import mx.gob.imss.dpes.registrooperadoreffront.util.ExceptionMapper;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author edgar.arenas
 */
@Path("/registroPensionado")
@RegisterRestClient
//@RegisterProvider(value = ExceptionMapper.class,priority = 50)
public interface RegistroPensionadoClient {
    
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response create(RegistroRequest request);
  
  @POST
  @Path("/usuario")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Integer validarUsuario(RegistroRequest request);
      
  @POST
  @Path("/tokenInfo")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response obtenInfoToken(ActivarRegistroRequest token);
  
  @POST
  @Path("/registro")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response persisteUsuario(RegistroPersona persona);
  
  @POST
  @Path("/actualizar/password")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response actualizarPassword(ActivarRegistroRequest token);
  
  @POST
  @Path("/acceder")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response loggeo(LogginRequest request);
    
}
