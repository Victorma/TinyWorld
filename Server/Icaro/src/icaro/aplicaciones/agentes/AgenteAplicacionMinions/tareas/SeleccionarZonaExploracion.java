package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class SeleccionarZonaExploracion extends TareaSincrona {

	@Override
	public void ejecutar(Object... params) {
		String zona = (String) params[0];
	}

}
