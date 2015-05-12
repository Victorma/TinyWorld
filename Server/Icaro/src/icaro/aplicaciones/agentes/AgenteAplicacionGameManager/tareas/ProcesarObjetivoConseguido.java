package icaro.aplicaciones.agentes.AgenteAplicacionGameManager.tareas;

import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.informacion.game_manager.Partida;
import icaro.aplicaciones.informacion.game_manager.VocabularioControlGameManager;
import icaro.aplicaciones.recursos.comunicacionChat.ItfUsoComunicacionChat;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class ProcesarObjetivoConseguido extends TareaSincrona {

    public void ejecutar(Object... params) {

        String identDeEstaTarea = this.getIdentTarea();
        String identAgenteOrdenante = this.getIdentAgente();

        Partida partida = (Partida) params[0];

        GameEvent objetivo = (GameEvent) ((GameEvent) params[1]).getParameter("objetivo");

        partida.validarObjetivo(objetivo);

        try {
            ItfUsoComunicacionChat recComunicacionChat = (ItfUsoComunicacionChat) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ
                    .obtenerInterfazUso(VocabularioGestionCitas.IdentRecursoComunicacionChat);
            if (recComunicacionChat != null) {
                GameEvent evento = new GameEvent(VocabularioControlGameManager.NombreTipoNotificacionJuegoIniciado);
                evento.setParameter("partida", partida);
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
