/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.registrooperadoreffront.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import mx.gob.imss.dpes.common.model.BaseModel;
import mx.gob.imss.dpes.support.config.CustomDateDeserializer;
import mx.gob.imss.dpes.support.config.CustomDateSerializer;
import mx.gob.imss.dpes.interfaces.renapo.model.RenapoCurpRequest;


/**
 *
 * @author eduardo.montesh
 */
@Data
public class RegistroRequest extends BaseModel {
    private String curp;
    private String nss;
    private Long numTelefono;
    private String correo;
    private String correoConfirmar;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date vigenciaToken;
    private String token;
    private String cvePerfil;
    
    private RenapoCurpRequest renapoRequest;
    
    private BDTURequest bdtuRequest;
    
}
