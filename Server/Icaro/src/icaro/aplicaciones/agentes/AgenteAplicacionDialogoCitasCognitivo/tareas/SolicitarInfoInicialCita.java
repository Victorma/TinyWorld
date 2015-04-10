/*
 * SolicitarDatos.java
 *
 * Creado 23 de abril de 2007, 12:52
 *
 * Telefonica I+D Copyright 2006-2007
 */
package icaro.aplicaciones.agentes.AgenteAplicacionDialogoCitasCognitivo.tareas;

import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
import icaro.aplicaciones.recursos.comunicacionChat.ItfUsoComunicacionChat;
import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Tarea;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
/**
 * 
 * @author F Garijo
 */
public class SolicitarInfoInicialCita extends TareaSincrona {
    private String identAgenteOrdenante;
    private Objetivo contextoEjecucionTarea = null;
	@Override
	public void ejecutar(Object... params) {		
      //    String   identRecursoVisualizacionAcceso = "VisualizacionAcceso1";
          String identDeEstaTarea=this.getIdentTarea();
            String identAgenteOrdenante = this.getIdentAgente();
          String identInterlocutor = (String)params[0];
          String preambulo =(String)params[1];
          String textoPeticion =(String)params[2];
                    try {
//         // Se busca la interfaz del recurso en el repositorio de interfaces 
		ItfUsoComunicacionChat recComunicacionChat = (ItfUsoComunicacionChat) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfazUso(
						VocabularioGestionCitas.IdentRecursoComunicacionChat);          
                if (recComunicacionChat!=null){
                    recComunicacionChat.comenzar(identAgenteOrdenante);
                    String mensajeAenviar = VocabularioGestionCitas.SaludoInicial2+ "  "+ identInterlocutor + "  "+
                            preambulo + "  "+
                            textoPeticion;
                    recComunicacionChat.enviarMensagePrivado(mensajeAenviar);
                }
                else {
                    identAgenteOrdenante = this.getAgente().getIdentAgente();
                     this.generarInformeConCausaTerminacion(identDeEstaTarea, contextoEjecucionTarea, identAgenteOrdenante, "Error-AlObtener:Interfaz:"+
                             VocabularioGestionCitas.IdentRecursoComunicacionChat, CausaTerminacionTarea.ERROR);
                        }
                    } catch(Exception e) {
                        this.generarInformeConCausaTerminacion(identDeEstaTarea, contextoEjecucionTarea, identAgenteOrdenante, "Error-Acceso:Interfaz:"+
                                VocabularioGestionCitas.IdentRecursoComunicacionChat, CausaTerminacionTarea.ERROR);
			e.printStackTrace();
		}
	}
}
