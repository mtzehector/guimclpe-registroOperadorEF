/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.registrooperadoreffront.endpoint;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.registrooperadoreffront.model.ValidarCandidatoOperadorRequest;
import mx.gob.imss.dpes.registrooperadoreffront.service.ObtenDatosRenapo;
import mx.gob.imss.dpes.registrooperadoreffront.service.ComparaBDTU;
import mx.gob.imss.dpes.registrooperadoreffront.service.RegistrosPatronalesService;
import mx.gob.imss.dpes.registrooperadoreffront.service.RelacionLaboralService;
import mx.gob.imss.dpes.support.util.ExceptionUtils;

/**
 *
 * @author juan.garfias
 */
@Path("validar-candidato-operador")
@RequestScoped
public class ValidarCandidatoOperadorFrontEndpoint extends BaseGUIEndPoint<BaseModel, BaseModel, BaseModel> {

    @Inject
    ObtenDatosRenapo renapo;

    @Inject
    ComparaBDTU bdtu;

    @Inject
    RelacionLaboralService relacionLaboralService;
    
    @Inject
    RegistrosPatronalesService registrosPatronalesService;
            
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response validaCandidatoOperador(ValidarCandidatoOperadorRequest request) throws BusinessException {

        log.log(Level.INFO, ">>>ValidarCandidatoOperadorFrontEndpoint.validaCandidatoOperador Recibe test RQ : " + request.getCurp() + " - " + request.getNss());

        validateInput(request);
        //ServiceDefinition[] steps = {validatePersona, renapo, bdtu, relacionLaboralService};
        ServiceDefinition[] steps = { renapo, bdtu, relacionLaboralService,registrosPatronalesService};
        Message<ValidarCandidatoOperadorRequest> response = renapo.executeSteps(steps, new Message<>(request));

        return toResponse(response);
    }

    private void validateInput(ValidarCandidatoOperadorRequest registroReq) throws BusinessException {
        checkMandatoryFields(registroReq);
    }

    private void checkMandatoryFields(ValidarCandidatoOperadorRequest registroReq) throws BusinessException {
        List<String> mandatoryFields = new LinkedList();
        mandatoryFields.add(registroReq.getCurp());
        mandatoryFields.add(registroReq.getNss());
        ExceptionUtils.checkMandatoryFields(mandatoryFields);
    }

}
