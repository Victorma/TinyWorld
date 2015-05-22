package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import dasi.util.TraceUtil;
//import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
//import icaro.aplicaciones.recursos.comunicacionChat.ItfUsoComunicacionChat;
//import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class InicializarInfoWorkMem extends TareaSincrona {
    @Override
    public void ejecutar(Object... params) {
        try {
//            ItfUsoComunicacionChat recComunicacionChat = NombresPredefinidos.<ItfUsoComunicacionChat>
//                    getUseInterface(VocabularioGestionCitas.IdentRecursoComunicacionChat);
            this.getItfConfigMotorDeReglas().setDepuracionActivationRulesDebugging(true);
            this.getItfConfigMotorDeReglas().setfactHandlesMonitoring_afterActivationFired_DEBUGGING(true);
            this.getEnvioHechos().insertarHechoWithoutFireRules(new Focus());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            TraceUtil.acceptNew(identAgente, identTarea, e);
        }
    }
}
