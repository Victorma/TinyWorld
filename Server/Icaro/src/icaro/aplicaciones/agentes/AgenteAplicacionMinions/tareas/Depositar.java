package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
import icaro.aplicaciones.informacion.minions.Coord;
import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.informacion.minions.ItemData;
import icaro.aplicaciones.informacion.minions.MinionContext;
import icaro.aplicaciones.informacion.minions.MinionInfo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;

/**
 * @param 0: myInfo MinionInfo
 * @param 1: myContext MinionContext
 * @param 2: item ItemData
 * @param 3: destination Coord
 *
 * @author Ivan
 *
 */
public class Depositar extends TareaSincrona {

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
                ItemData item = (ItemData) params[2];
                Coord destination = (Coord) params[3];

                GameEvent ev = new GameEvent("drop item");
                ev.setParameter("minion_id", myInfo.getInstanceId());
                ev.setParameter("item", item);
                ev.setParameter("destination", destination);
                ev.setParameter("synchronous", true);

                // Event sending
                MensajeSimple ms = new MensajeSimple(ev, this.getIdentAgente(),
                        gameManager.getIdentAgente());
                gameManager.aceptaMensaje(ms);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

}
