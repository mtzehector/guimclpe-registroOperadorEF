/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.registrooperadoreffront.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.interfaces.relacionlaboral.model.RelacionLaboralOut;
import mx.gob.imss.dpes.interfaces.renapo.model.RenapoCurpRequest;

/**
 *
 * @author eduardo.montesh
 */
@Data
public class ValidarCandidatoOperadorRequest extends BaseModel {

    @Getter
    @Setter
    private String curp;
    @Getter
    @Setter
    private String nss;
    @Getter
    @Setter
    private RenapoCurpRequest renapoRequest;
    @Getter
    @Setter
    private BDTUPersonaRenapoResponse bdtuResponse;
    @Getter
    @Setter
    private RelacionLaboralOut relacionLaboral;
    @Getter
    @Setter
    private Long cveEntidadFinanciera;
    @Getter
    @Setter
    private RegistrosPatronalesOut registrosPatronalesOut;
}
