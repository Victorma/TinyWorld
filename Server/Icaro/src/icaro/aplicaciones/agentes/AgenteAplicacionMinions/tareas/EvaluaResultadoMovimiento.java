package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.AlcanzarPosicion;
import icaro.aplicaciones.informacion.minions.Coord;
import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.informacion.minions.MinionInfo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

/**
 * Parametros:
 *
 * @param 0 : objetivo AlcanzarPosicion
 * @param 1 : report GameEvent
 *
 * @author Victorma
 *
 */
public class EvaluaResultadoMovimiento extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {

        AlcanzarPosicion obj = (AlcanzarPosicion) params[0];
        MinionInfo info = (MinionInfo) params[1];
        GameEvent report = (GameEvent) params[2];

        GameEvent origin = (GameEvent) report.getParameter("event");

        Coord wantedCoord = obj.getCoord();
        Coord actualCoord = info.getCoords();

        if (wantedCoord.distanceTo(actualCoord) == obj.distance) {
            obj.setSolved();
        } else {
            obj.setFailed();
        }

        //Actualizamos el estado del objetivo
        this.getEnvioHechos().actualizarHecho(obj);

        // Borramos el reporte finalmente
        this.getEnvioHechos().eliminarHechoWithoutFireRules(report);
    }

}
