/*
 * PermitirAcceso.java
 *
 * Creado 23 de abril de 2007, 12:50
 *
 * Telefonica I+D Copyright 2006-2007
 */
package icaro.aplicaciones.agentes.AgenteAplicacionAccesoCognitivo.tareas;

import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.VocabularioSistemaAcceso;
import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Tarea;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.AdaptadorRegRMI;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

/**
 * 
 * @author F Garijo
 */
public class PermitirAcceso extends Tarea {
        private String identAgenteOrdenante;
        private Objetivo contextoEjecucionTarea = null;
	@Override
	public void ejecutar(Object... params) {
            String identDeEstaTarea=this.getIdentTarea();
            String identRecursoVisualizacionAcceso = (String)params[0];
		try {
                     identAgenteOrdenante = this.getIdentAgente();
			ItfUsoVisualizadorAcceso visualizadorAcceso = (ItfUsoVisualizadorAcceso) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ
					.obtenerInterfaz(NombresPredefinidos.ITF_USO + identRecursoVisualizacionAcceso);
                  if (visualizadorAcceso==null)
                        this.generarInformeConCausaTerminacion(identDeEstaTarea, contextoEjecucionTarea, identAgenteOrdenante, "Error-AlObtener:Interfaz_Recurso:"+identRecursoVisualizacionAcceso, CausaTerminacionTarea.ERROR);
                    else {
                        visualizadorAcceso.mostrarMensajeInformacion(identDeEstaTarea, "Acceso permitido. Termina el servicio de Acceso ");
                        visualizadorAcceso.cerrarVisualizadorAcceso();
                        this.generarInformeOK(identDeEstaTarea, contextoEjecucionTarea, identAgenteOrdenante, VocabularioSistemaAcceso.NotificacionAccesoAutorizado);
                        }
                } catch (Exception e) {
                    this.generarInformeConCausaTerminacion(identDeEstaTarea, contextoEjecucionTarea, identAgenteOrdenante, "Error-AlUtilizar:Interfaces_Recurso:"+identRecursoVisualizacionAcceso, CausaTerminacionTarea.ERROR);
                    e.printStackTrace();
		}
	}
        // El método generarInformOK crea un informe de tarea y se lo envía  al  procesador de objetivos
                // Los valores para crear el informe son los siguientes
		// Identificador de la Tarea que genera el informe : "Tarea:PermitirAcceso"
		// El contexto en el  que se ejecuta la tarea : ( Opcional) Identificador del objetivo en el que se ejecuta la tarea
                // Identificador del agente que ejecuta la  la tarea 
                //Texto del informe del resultado de la ejecución: "Autorizacion_Acceso_Notificado_Al_Usuario"
}
