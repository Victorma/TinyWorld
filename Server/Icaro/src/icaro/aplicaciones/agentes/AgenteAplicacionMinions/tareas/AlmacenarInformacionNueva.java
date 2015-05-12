package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.informacion.minions.VocabularioControlMinions;
import icaro.aplicaciones.recursos.comunicacionChat.ItfUsoComunicacionChat;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class AlmacenarInformacionNueva extends TareaSincrona {

    public void ejecutar(Object... params) {

        //TODO ahora mismo solo responde la informacion
        String identDeEstaTarea = this.getIdentTarea();
        String identAgenteOrdenante = this.getIdentAgente();

        try {
            // // Se busca la interfaz del recurso en el repositorio de
            // interfaces
            ItfUsoComunicacionChat recComunicacionChat = (ItfUsoComunicacionChat) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ
                    .obtenerInterfazUso(VocabularioGestionCitas.IdentRecursoComunicacionChat);
            if (recComunicacionChat != null) {
                String mensajeAenviar = VocabularioControlMinions.TextoObservacion + params[0].toString();
                GameEvent evento = new GameEvent("show dialog");
                evento.setParameter("message", mensajeAenviar);
                recComunicacionChat.enviarMensaje(identAgente, evento);
            } else {
                identAgenteOrdenante = this.getAgente().getIdentAgente();
                this.generarInformeConCausaTerminacion(identDeEstaTarea, null, identAgenteOrdenante,
                        "Error-AlObtener:Interfaz:" + VocabularioGestionCitas.IdentRecursoComunicacionChat, CausaTerminacionTarea.ERROR);
            }
        } catch (Exception e) {
            this.generarInformeConCausaTerminacion(identDeEstaTarea, null, identAgenteOrdenante, "Error-Acceso:Interfaz:"
                    + VocabularioGestionCitas.IdentRecursoComunicacionChat, CausaTerminacionTarea.ERROR);
            e.printStackTrace(System.err);
        }
    }
}
