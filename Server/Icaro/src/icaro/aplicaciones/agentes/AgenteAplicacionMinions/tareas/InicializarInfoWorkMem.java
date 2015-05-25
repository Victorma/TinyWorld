package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import dasi.util.TraceUtil;
import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.ObservarEntorno;
import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
import icaro.aplicaciones.informacion.minions.ConocimientosFabricacion;
import icaro.aplicaciones.recursos.comunicacionChat.ItfUsoComunicacionChat;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Focus;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

public class InicializarInfoWorkMem extends TareaSincrona {
    @Override
    public void ejecutar(Object... params) {
        try {
            ItfUsoComunicacionChat recComunicacionChat = (ItfUsoComunicacionChat) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfazUso(
                    VocabularioGestionCitas.IdentRecursoComunicacionChat);

            String identTarea = this.getIdentTarea();
            String nombreAgenteEmisor = this.getIdentAgente();
            this.getItfConfigMotorDeReglas().setDepuracionActivationRulesDebugging(true);
            this.getItfConfigMotorDeReglas().setfactHandlesMonitoring_afterActivationFired_DEBUGGING(true);

            ObservarEntorno observar = new ObservarEntorno();
            Focus f = new Focus();
            f.setFoco(observar);

            this.getEnvioHechos().insertarHechoWithoutFireRules(observar);
            this.getEnvioHechos().insertarHechoWithoutFireRules(new ConocimientosFabricacion());
            this.getEnvioHechos().insertarHecho(f);

        } catch (Exception e) {
            e.printStackTrace(System.err);
            TraceUtil.acceptNew(identAgente, identTarea, e);
        }
    }
}
