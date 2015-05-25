package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.informacion.minions.MinionContext;
import icaro.aplicaciones.informacion.minions.MinionInfo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;

/**
 * @param 0: myInfo MinionInfo
 * @param 2: ge GameEvent
 *
 * @author Ivan
 *
 */
public class Observar extends TareaSincrona {

    ItfUsoAgenteCognitivo gameManager;

    @Override
    public void ejecutar(Object... params) {

        MinionInfo myInfo = (MinionInfo) params[0];
        MinionContext myContext = (MinionContext) params[1];

        try {
            gameManager = myContext.getItfAgenteGameManager();
            if (gameManager != null) {
                GameEvent ev = new GameEvent("observe");
                ev.setParameter("minion_id", myInfo.getInstanceId());

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
