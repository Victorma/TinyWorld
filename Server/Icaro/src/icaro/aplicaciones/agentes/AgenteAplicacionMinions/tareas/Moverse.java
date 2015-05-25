package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
import icaro.aplicaciones.informacion.minions.Coord;
import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.informacion.minions.MinionContext;
import icaro.aplicaciones.informacion.minions.MinionInfo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;

/**
 * @param 0: myInfo MinionInfo
 * @param 1: myContext MinionContext
 * @param 2: destination Coords
 * @param 3: distance Integer
 *
 * @author Victorma
 *
 */
public class Moverse extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {

        ItfUsoAgenteCognitivo gameManager;

        // Common parameters
        MinionInfo myInfo = (MinionInfo) params[0];
        MinionContext myContext = (MinionContext) params[1];

        try {
            gameManager = myContext.getItfAgenteGameManager();
            if (gameManager != null) {

                // Particular parameters
                Coord coords = (Coord) params[2];
                int distance = (int) params[3];

                // Event creation
                GameEvent ev = new GameEvent("move");
                ev.setParameter("entity", myInfo.getInstanceId());
                ev.setParameter("cell", coords);
                ev.setParameter("distance", distance);
                ev.setParameter("synchronous", true);

                // Event sending
                MensajeSimple ms = new MensajeSimple(ev, this.getIdentAgente(), gameManager.getIdentAgente());
                gameManager.aceptaMensaje(ms);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

}
