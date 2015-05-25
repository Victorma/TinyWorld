package icaro.aplicaciones.agentes.AgenteAplicacionGameManager.tareas;

import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.informacion.game_manager.Partida;
import icaro.aplicaciones.informacion.game_manager.VocabularioControlGameManager;
import icaro.aplicaciones.recursos.comunicacionChat.ItfUsoComunicacionChat;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class InicializarPartida extends TareaSincrona {

    public void ejecutar(Object... params) {
        //TODO ahora mismo solo responde la informacion
        String identAgenteOrdenante = getIdentAgente();
        Partida partida = new Partida(getAgente(), repoInterfaces, (GameEvent) params[0]);
        getEnvioHechos().insertarHecho(partida);

        try {
            // Se busca la interfaz del recurso en el repositorio de interfaces
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
