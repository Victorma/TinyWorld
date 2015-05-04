package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos.AlcanzarPosicion;
import icaro.aplicaciones.informacion.minions.Coord;
import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class EvaluaResultadoMovimiento extends TareaSincrona {

	@Override
	public void ejecutar(Object... params) {
		AlcanzarPosicion obj = (AlcanzarPosicion) params[0];
		GameEvent report = (GameEvent) params[1];
		
		GameEvent origin = (GameEvent) report.getParameter("event");
		GameEvent status = (GameEvent) report.getParameter("status");
		
		Coord wantedCoord = obj.getCoord();
		Coord actualCoord = (Coord) status.getParameter("position");
		
		if(wantedCoord.distanceTo(actualCoord) == obj.distance){
			obj.setSolved();
		}else
			obj.setFailed();
		
		//Actualizamos el estado del objetivo
		this.getEnvioHechos().actualizarHecho(obj);
		
		// Borramos el reporte finalmente
		this.getEnvioHechos().eliminarHechoWithoutFireRules(report);
	}

}
