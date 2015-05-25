package icaro.aplicaciones.agentes.AgenteAplicacionGameManager.tareas;

import icaro.aplicaciones.informacion.game_manager.Partida;
import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.recursos.comunicacionChat.ItfUsoComunicacionChat;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class EnviarEventoAPartida extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {
        Partida partida = (Partida) params[0];
        GameEvent event = (GameEvent) params[1];
        String identAgenteOrdenante = getIdentAgente();

        try {
            // Se busca la interfaz del recurso en el repositorio de interfaces
            ItfUsoComunicacionChat recComunicacionChat = NombresPredefinidos.<ItfUsoComunicacionChat>
                    getUseInterface(VocabularioGestionCitas.IdentRecursoComunicacionChat);
            if (recComunicacionChat != null) {
                recComunicacionChat.enviarMensaje(getIdentAgente(), event);
            } else {
                identAgenteOrdenante = getAgente().getIdentAgente();
                generarInformeConCausaTerminacion(getIdentTarea(), null, identAgenteOrdenante,
                        "Error-AlObtener:Interfaz:" + VocabularioGestionCitas.IdentRecursoComunicacionChat,
                        CausaTerminacionTarea.ERROR);
            }
        } catch (Exception e) {
            generarInformeConCausaTerminacion(getIdentTarea(), null, identAgenteOrdenante,
                    "Error-Acceso:Interfaz:" + VocabularioGestionCitas.IdentRecursoComunicacionChat,
                    CausaTerminacionTarea.ERROR);
            e.printStackTrace(System.err);
        }
        getEnvioHechos().eliminarHechoWithoutFireRules(event);
    }

}
