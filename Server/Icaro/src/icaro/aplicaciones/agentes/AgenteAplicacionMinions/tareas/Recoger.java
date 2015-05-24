package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.informacion.minions.ItemData;
import icaro.aplicaciones.informacion.minions.MinionContext;
import icaro.aplicaciones.informacion.minions.MinionInfo;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;

/**
 * @param 0: myInfo MinionInfo
 * @param 1: myContext MinionContext
 * @param 2: item ItemData
 * 
 * @author Ivan
 *
 */
public class Recoger extends TareaSincrona {

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

                GameEvent ev = new GameEvent("pick item");
                ev.setParameter("minion_id", myInfo.getInstanceId());
                ev.setParameter("item", item);
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
