/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.registrooperadoreffront.restclient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author juanf.barragan
 */
@Path("/registrospatronales")
@RegisterRestClient
public interface RegistrosPatronalesClient {
    
    @GET
    @Path("/entidadFinanciera/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response load(@PathParam("id") Long id);
    
}
