package icaro.aplicaciones.agentes.AgenteAplicacionDialogoCitasCognitivo.tareas;

import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Tarea;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.AdaptadorRegRMI;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;




public class PeticionTerminacion extends Tarea {
//        private String identDeEstaTarea= "PermitirAcceso";
        private String identAgenteOrdenante;
        private Objetivo contextoEjecucionTarea = null;
//        private String identRecursoVisualizacionAcceso = "VisualizacionAcceso1";
	@Override
	public void ejecutar(Object... params) {
	
	// Cerramos el visualizador y pedimos al gestor de agentes que termine
             String identDeEstaTarea=getClass().getSimpleName();
             String identRecursoVisualizacionAcceso = (String)params[0];
               try {
                  
                     identAgenteOrdenante = this.getAgente().getIdentAgente();
			ItfUsoVisualizadorAcceso visualizadorAcceso = (ItfUsoVisualizadorAcceso) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ
					.obtenerInterfaz(
							NombresPredefinidos.ITF_USO + identRecursoVisualizacionAcceso);
                  if (visualizadorAcceso==null)
                    visualizadorAcceso = (ItfUsoVisualizadorAcceso)AdaptadorRegRMI.getItfRecursoRemoto(identRecursoVisualizacionAcceso, NombresPredefinidos.ITF_USO);
                    if (visualizadorAcceso==null)
                        this.generarInformeConCausaTerminacion(identDeEstaTarea, contextoEjecucionTarea, identAgenteOrdenante, "Error-AlObtener:Interfaz_Recurso:"+identRecursoVisualizacionAcceso, CausaTerminacionTarea.ERROR);
                    else {
                    visualizadorAcceso.cerrarVisualizadorAcceso();
                    ItfUsoAgenteReactivo gestorAgentes = (ItfUsoAgenteReactivo) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.NOMBRE_GESTOR_AGENTES);
			 if (gestorAgentes==null)
                            gestorAgentes = (ItfUsoAgenteReactivo)AdaptadorRegRMI.getItfAgenteRemoto(NombresPredefinidos.NOMBRE_GESTOR_AGENTES, NombresPredefinidos.ITF_USO);
                            if (gestorAgentes==null)
                            this.generarInformeConCausaTerminacion(identDeEstaTarea, contextoEjecucionTarea, identAgenteOrdenante, "Error-AlObtener:Interfaz_Agente:"+NombresPredefinidos.NOMBRE_GESTOR_AGENTES, CausaTerminacionTarea.ERROR);
                        else {
                            gestorAgentes.aceptaEvento(new EventoRecAgte("peticion_terminar_todo", this.getAgente().getIdentAgente(), null));
			
                            MensajeSimple mensaje = new MensajeSimple();
                            mensaje.setEmisor("Task:PedirTerminacionAGestor");
                            mensaje.setReceptor("AgenteAplicacionAcceso1");
                            mensaje.setContenido("peticionTerminacion");
                            this.getAgente().aceptaMensaje(mensaje);
                            }
                        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
                      this.generarInformeConCausaTerminacion(identDeEstaTarea, contextoEjecucionTarea, identAgenteOrdenante, "Error-AlUtilizar:Interfaces_Recurso:"+identRecursoVisualizacionAcceso, CausaTerminacionTarea.ERROR);
			e.printStackTrace();
		}
		
	}



}
