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

/**
 *
 * @author eduardo.montesh
 */

@Data
public class BDTUPersonaRenapoResponse extends BaseModel {
    
  @Getter @Setter private Long cveIdPersona;
  @Getter @Setter private String curp;
  
  @Getter @Setter private String nombre;
  @Getter @Setter private String primerApellido;
  @Getter @Setter private String segundoApellido;
  @Getter @Setter private String fechaNacimiento;

  @Getter @Setter private String nss;
  @Getter @Setter private Sexo sexo;
  @Getter @Setter private LugarNacimiento lugarNacimiento;
  
}

class LugarNacimiento extends BaseModel {
    @Getter @Setter private String clave;
    @Getter @Setter private String nombre;    
}

