package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.RecogerObjeto;
import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.informacion.minions.ItemData;
import icaro.aplicaciones.informacion.minions.MinionInfo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

/**
 * Parametros:
 *
 * @param 0 : objetivo AlcanzarPosicion
 * @param 1 : info MinionInfo
 * @param 1 : report GameEvent
 *
 * @author Ivan
 *
 */
public class EvaluaResultadoRecoger extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {

        RecogerObjeto obj = (RecogerObjeto) params[0];
        MinionInfo info = (MinionInfo) params[1];
        GameEvent report = (GameEvent) params[2];

        GameEvent ge = (GameEvent) report.getParameter("event");
        ItemData item = (ItemData) ge.getParameter("item");

        if (item.get_minionID() == info.getInstanceId()) {
            obj.setSolved();
        } else {
            obj.setFailed();
        }

        // Actualizamos el estado del objetivo
        this.getEnvioHechos().actualizarHecho(obj);

        // Borramos el reporte finalmente
        this.getEnvioHechos().eliminarHechoWithoutFireRules(report);
    }

}
