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
        String identAgenteOrdenante = getIdentAgente();
        Partida partida = (Partida) params[0];
        GameEvent objetivo = (GameEvent) ((GameEvent) params[1]).getParameter("objetivo");
        partida.validarObjetivo(objetivo);

        try {
            ItfUsoComunicacionChat recComunicacionChat = NombresPredefinidos.<ItfUsoComunicacionChat>
                    getUseInterface(VocabularioGestionCitas.IdentRecursoComunicacionChat);
            if (recComunicacionChat != null) {
                GameEvent evento = new GameEvent(VocabularioControlGameManager.NombreTipoNotificacionJuegoIniciado);
                evento.setParameter("partida", partida);
                recComunicacionChat.enviarMensaje(getIdentAgente(), evento);
            } else {
                identAgenteOrdenante = getAgente().getIdentAgente();
                generarInformeConCausaTerminacion(getIdentTarea(), null, identAgenteOrdenante,
                        "Error-AlObtener:Interfaz:" + VocabularioGestionCitas.IdentRecursoComunicacionChat,
                        CausaTerminacionTarea.ERROR);
            }
        } catch (Exception e) {
            generarInformeConCausaTerminacion(getIdentTarea(), null, identAgenteOrdenante, "Error-Acceso:Interfaz:"
                    + VocabularioGestionCitas.IdentRecursoComunicacionChat, CausaTerminacionTarea.ERROR);
            e.printStackTrace(System.err);
        }
    }
}
