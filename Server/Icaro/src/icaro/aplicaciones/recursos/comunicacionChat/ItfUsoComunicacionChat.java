package icaro.aplicaciones.recursos.comunicacionChat;

import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

public interface ItfUsoComunicacionChat extends ItfUsoRecursoSimple {

    public void enviarMensaje(String identAgenteOrigen, GameEvent mensaje) throws Exception;
}
