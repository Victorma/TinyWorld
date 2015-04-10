/*
 * SolicitarDatos.java
 *
 * Creado 23 de abril de 2007, 12:52
 *
 * Telefonica I+D Copyright 2006-2007
 */
package icaro.aplicaciones.agentes.AgenteAplicacionAccesoCognitivo.tareas;

import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Tarea;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
/**
 * 
 * @author F Garijo
 */
public class SolicitarDatosAcceso extends Tarea {
    private String identAgenteOrdenante;
    private Objetivo contextoEjecucionTarea = null;
	@Override
	public void ejecutar(Object... params) {		
      //    String   identRecursoVisualizacionAcceso = "VisualizacionAcceso1";
          String identDeEstaTarea=this.getIdentTarea();
          String identRecursoVisualizacionAcceso = (String)params[0];
                    try {
         // Se busca la interfaz del visualizador en el repositorio de interfaces 
		ItfUsoVisualizadorAcceso visualizadorAcceso = (ItfUsoVisualizadorAcceso) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(
						NombresPredefinidos.ITF_USO + identRecursoVisualizacionAcceso);          
                if (visualizadorAcceso!=null)
                    visualizadorAcceso.mostrarVisualizadorAcceso(this.getAgente().getIdentAgente(), NombresPredefinidos.TIPO_COGNITIVO);
                else {
                    identAgenteOrdenante = this.getAgente().getIdentAgente();
                     this.generarInformeConCausaTerminacion(identDeEstaTarea, contextoEjecucionTarea, identAgenteOrdenante, "Error-AlObtener:Interfaz:"+identRecursoVisualizacionAcceso, CausaTerminacionTarea.ERROR);
                        }
                    } catch(Exception e) {
                        this.generarInformeConCausaTerminacion(identDeEstaTarea, contextoEjecucionTarea, identAgenteOrdenante, "Error-Acceso:Interfaz:"+identRecursoVisualizacionAcceso, CausaTerminacionTarea.ERROR);
			e.printStackTrace();
		}
	}
}
