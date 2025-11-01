/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.registrooperadoreffront.assembler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.gob.imss.dpes.common.assembler.BaseAssembler;
import mx.gob.imss.dpes.registrooperadoreffront.model.ActivarRegistroRequest;
import mx.gob.imss.dpes.registrooperadoreffront.model.RegistroPersona;

/**
 *
 * @author eduardo.montesh
 */
public class RegistroPersonaAssembler extends BaseAssembler<ActivarRegistroRequest, RegistroPersona> {

    @Override
    public RegistroPersona assemble(ActivarRegistroRequest source) {
        
        RegistroPersona persona = new RegistroPersona();
         
        try {
        Date fn = new SimpleDateFormat("dd/MM/yyyy").parse(source.getBdtuRequest().getBdtuOut().getFechaNacimiento());
        persona.setFecNacimiento(fn);
        } catch (ParseException ex) {
            Logger.getLogger(RegistroPersonaAssembler.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        persona.setNombre(source.getBdtuRequest().getBdtuOut().getNombre());
        persona.setPassword(source.getPassword());
        persona.setPrimerApellido(source.getBdtuRequest().getBdtuOut().getPrimerApellido());
        persona.setSegundoApellido(source.getBdtuRequest().getBdtuOut().getSegundoApellido());
        persona.setInfoToken(source.getInfoToken());
        log.log(Level.INFO,">>>>valor de persona : {0}", persona);
        return persona;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
