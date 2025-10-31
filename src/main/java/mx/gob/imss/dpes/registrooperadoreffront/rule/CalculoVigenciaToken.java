/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.registrooperadoreffront.rule;

import java.util.Calendar;
import java.util.logging.Level;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.rule.BaseRule;
import mx.gob.imss.dpes.registrooperadoreffront.model.RegistroRequest;

/**
 *
 * @author edgar.arenas
 */
@Provider
public class CalculoVigenciaToken extends BaseRule<RegistroRequest, RegistroRequest> {
   
    final Integer HORAS = 72;
    @Override
    public RegistroRequest apply(RegistroRequest request){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR_OF_DAY, HORAS);
        request.setVigenciaToken(c.getTime());
        log.log(Level.INFO,">>>>VIGENCIA TOKEN : {0}", request.getVigenciaToken());
        return request;
    }
}
