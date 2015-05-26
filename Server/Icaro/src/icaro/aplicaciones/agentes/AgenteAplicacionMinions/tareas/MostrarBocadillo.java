package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.informacion.minions.MinionContext;
import icaro.aplicaciones.informacion.minions.MinionInfo;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;

/**
 * @param 0: minionId String
 * @param 1: myContext MinionContext
 * @param 2: emoteName String
 *
 * @author Ivan
 *
 */
public class MostrarBocadillo extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {

        ItfUsoAgenteCognitivo gameManager;

        // Common parameters
        int minionId = (int) params[0];
        MinionContext myContext = (MinionContext) params[1];

        try {
            gameManager = myContext.getItfAgenteGameManager();
            if (gameManager != null) {
                // Event creation
                GameEvent ev = new GameEvent("show emotion");
                ev.setParameter("minion_id", minionId);
                ev.setParameter("emotion name", (String) params[2]);
                
                // Event sending
                MensajeSimple ms = new MensajeSimple(ev, this.getIdentAgente(), gameManager.getIdentAgente());
                gameManager.aceptaMensaje(ms);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

}
