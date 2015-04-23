package icaro.aplicaciones.agentes.AgenteAplicacionGameManager.tareas;

import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
import icaro.aplicaciones.informacion.minions.VocabularioControlMinions;
import icaro.aplicaciones.recursos.comunicacionChat.ItfUsoComunicacionChat;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.aplicaciones.recursos.visualizacionTipoJuego.ItfUsoVisualizadorTipoJuego;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

public class SolicitarTipoJuego extends TareaSincrona {
                private String identAgenteOrdenante;
                private Objetivo contextoEjecucionTarea = null;
                @Override
	public void ejecutar(Object... params) {
		
		//TODO ahora mismo solo responde la informacion
		
		String identDeEstaTarea = this.getIdentTarea();
		String identRecursoVisualizacionTipoJuego = (String)params[0];
                
		
		try {
			// Buscamos la interfaz del visualizador en el repositorio de interfaces
                        ItfUsoVisualizadorTipoJuego visualizadorTipoJuego = (ItfUsoVisualizadorTipoJuego) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(NombresPredefinidos.ITF_USO + identRecursoVisualizacionTipoJuego);
                        if (visualizadorTipoJuego !=null)
                            visualizadorTipoJuego.mostrarVisualizadorTipoJuego(this.getAgente().getIdentAgente(), NombresPredefinidos.TIPO_COGNITIVO);
                        else{
                            identAgenteOrdenante = this.getAgente().getIdentAgente();
                            this.generarInformeConCausaTerminacion(identDeEstaTarea, contextoEjecucionTarea, identAgenteOrdenante, "Error-AlObtener: Interfaz:"+identRecursoVisualizacionTipoJuego, CausaTerminacionTarea.ERROR);
                        }
		} catch (Exception e) {
                        this.generarInformeConCausaTerminacion(identDeEstaTarea, contextoEjecucionTarea, identAgenteOrdenante, this, CausaTerminacionTarea.ERROR);
			e.printStackTrace();
		}
	}
}
