/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.registrooperadoreffront.model;

import lombok.Data;
import mx.gob.imss.dpes.common.model.BaseModel;

/**
 *
 * @author eduardo.montesh
 */

@Data
public class ActivarRegistroRequest extends BaseModel {
    private String token;
    private String password;
    
    BDTURequest bdtuRequest;
    
    TokenRegistroUsusario infoToken;
    
}
