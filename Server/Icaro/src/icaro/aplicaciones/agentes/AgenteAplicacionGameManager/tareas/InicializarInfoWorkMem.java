package icaro.aplicaciones.agentes.AgenteAplicacionGameManager.tareas;

import dasi.util.TraceUtil;
import icaro.aplicaciones.agentes.AgenteAplicacionGameManager.objetivos.IniciarJuego;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

/**
 *
 * @author jzamorano
 */
public class InicializarInfoWorkMem extends TareaSincrona {
    @Override
    public void ejecutar(Object... params) {
        try {
            getItfConfigMotorDeReglas().setDepuracionActivationRulesDebugging(true);
            getItfConfigMotorDeReglas().setfactHandlesMonitoring_afterActivationFired_DEBUGGING(true);
            getEnvioHechos().insertarHechoWithoutFireRules(new Focus());
            getEnvioHechos().insertarHechoWithoutFireRules(new IniciarJuego());
        } catch (Exception e) {
            TraceUtil.acceptNew(identAgente, identTarea, e);
            e.printStackTrace(System.err);
        }
    }
}
