/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.registrooperadoreffront.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author eduardo.montesh
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(mx.gob.imss.dpes.common.exception.AlternateFlowMapper.class);
        resources.add(mx.gob.imss.dpes.common.exception.BusinessMapper.class);
        resources.add(mx.gob.imss.dpes.common.rule.MontoTotalRule.class);
        resources.add(mx.gob.imss.dpes.common.rule.PagoMensualRule.class);
        resources.add(mx.gob.imss.dpes.registrooperadoreffront.endpoint.ValidarCandidatoOperadorFrontEndpoint.class);
        resources.add(mx.gob.imss.dpes.registrooperadoreffront.rule.CalculoVigenciaToken.class);
        resources.add(mx.gob.imss.dpes.registrooperadoreffront.rule.CompararFechas.class);
        resources.add(mx.gob.imss.dpes.registrooperadoreffront.rule.GenerarToken.class);
        resources.add(mx.gob.imss.dpes.registrooperadoreffront.service.ActivaRegistroService.class);
        resources.add(mx.gob.imss.dpes.registrooperadoreffront.service.ActualizaPassword.class);
        resources.add(mx.gob.imss.dpes.registrooperadoreffront.service.ComparaBDTU.class);
        resources.add(mx.gob.imss.dpes.registrooperadoreffront.service.EnviaCambioPasswordService.class);
        resources.add(mx.gob.imss.dpes.registrooperadoreffront.service.EnviaConfirmacionRegistroUsuarioService.class);
        resources.add(mx.gob.imss.dpes.registrooperadoreffront.service.EnviaRecuperacionPasswordService.class);
        resources.add(mx.gob.imss.dpes.registrooperadoreffront.service.EnviaTokenPorCorreoService.class);
        resources.add(mx.gob.imss.dpes.registrooperadoreffront.service.LogginService.class);
        resources.add(mx.gob.imss.dpes.registrooperadoreffront.service.ObtenDatosRenapo.class);
        resources.add(mx.gob.imss.dpes.registrooperadoreffront.service.ObtenInfoTokenService.class);
        resources.add(mx.gob.imss.dpes.registrooperadoreffront.service.RecuperarPasswordService.class);
        resources.add(mx.gob.imss.dpes.registrooperadoreffront.service.RegistrarTokenUsuarioService.class);
        resources.add(mx.gob.imss.dpes.registrooperadoreffront.service.ValidarUsuarioService.class);
        resources.add(mx.gob.imss.dpes.registrooperadoreffront.util.MessageResolver.class);
        resources.add(mx.gob.imss.dpes.registrooperadoreffront.service.RegistrosPatronalesService.class);
    }
    
}
