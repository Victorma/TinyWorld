/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.AgenteAplicacionGameManager.tareas;

import icaro.aplicaciones.agentes.AgenteAplicacionGameManager.objetivos.IniciarJuego;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author jzamorano
 */
public class InicializarInfoWorkMem extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {

        try {
            String identTare = this.getIdentTarea();
            String nombreAgenteEmisor = this.getIdentAgente();
            this.getItfConfigMotorDeReglas().setDepuracionActivationRulesDebugging(true);
            this.getItfConfigMotorDeReglas().setfactHandlesMonitoring_afterActivationFired_DEBUGGING(true);
            this.getEnvioHechos().insertarHechoWithoutFireRules(new Focus());
            this.getEnvioHechos().insertarHechoWithoutFireRules(new IniciarJuego());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            trazas.aceptaNuevaTraza(new InfoTraza(this.getIdentAgente(), "Error al ejecutar la tarea" + this.getIdentTarea() + e, InfoTraza.NivelTraza.error));
        }
    }
}
