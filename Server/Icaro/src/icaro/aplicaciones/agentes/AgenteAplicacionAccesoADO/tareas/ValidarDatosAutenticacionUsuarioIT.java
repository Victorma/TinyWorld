/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.agentes.AgenteAplicacionAccesoADO.tareas;

/**
 *
 * @author FGarijo
 */
import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.InfoAccesoSinValidar;
import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.VocabularioSistemaAcceso;
import icaro.aplicaciones.recursos.persistenciaAccesoSimple.ItfUsoPersistenciaAccesoSimple;
import icaro.aplicaciones.recursos.visualizacionAcceso.ItfUsoVisualizadorAcceso;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Tarea;
/**
 *
 * @author Francisco J Garijo
 */
public class ValidarDatosAutenticacionUsuarioIT extends Tarea {
    private String identAgenteOrdenante;
    private Objetivo contextoEjecucionTarea = null;	
@Override
public void ejecutar(Object... params) {
         String   IdentRecursoVisualizacionAcceso = VocabularioSistemaAcceso.IdentRecursoVisualizacionAccesoInicial;
         String   IdentRecursoPersistencia =VocabularioSistemaAcceso.IdentRecursoPersistenciaAcceso ;
//	Se extraen  los datos de los parametros
//	PerformativaUsuario infoUsuario = (PerformativaUsuario) params[0];
        InfoContEvtMsgAgteReactivo infoUsuario = (InfoContEvtMsgAgteReactivo) params[0];
        Object [] parametrosAccion = (Object [])infoUsuario.getvaloresParametrosAccion();
        InfoAccesoSinValidar infoAcceso = (InfoAccesoSinValidar) parametrosAccion[0] ;
        try {
// Se obtienen las interfaces de los recursos. Si no se pueden obtener las interfaces se debe generar un informe de tarea
                String identTarea = this.getIdentTarea(); 
                identAgenteOrdenante = this.getIdentAgente();
		ItfUsoVisualizadorAcceso visualizadorAcceso = (ItfUsoVisualizadorAcceso) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ
					.obtenerInterfaz(NombresPredefinidos.ITF_USO + IdentRecursoVisualizacionAcceso);
                if (visualizadorAcceso==null)        
                   this.generarInformeConCausaTerminacion(identTarea, contextoEjecucionTarea, identAgenteOrdenante, "Error-Al Obtener la interfaz de uso de :"+IdentRecursoVisualizacionAcceso, CausaTerminacionTarea.ERROR);
                else
                   visualizadorAcceso.mostrarVisualizadorAcceso(identAgenteOrdenante, NombresPredefinidos.TIPO_COGNITIVO);                    
                ItfUsoPersistenciaAccesoSimple itfUsoPersistencia = (ItfUsoPersistenciaAccesoSimple) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz(
							NombresPredefinidos.ITF_USO + IdentRecursoPersistencia);
                if (itfUsoPersistencia == null)
                     this.generarInformeConCausaTerminacion(identTarea, contextoEjecucionTarea, identAgenteOrdenante, "Error-Al Obtener:Interfaz_"+IdentRecursoPersistencia, CausaTerminacionTarea.ERROR);
                else {
                    boolean resultadoValidacion = itfUsoPersistencia.compruebaUsuario(infoAcceso.tomaUsuario()
					, infoAcceso.tomaPassword());               
                    String contenidoInformeTarea;
                    if (resultadoValidacion){
                        contenidoInformeTarea = VocabularioSistemaAcceso.ResultadoAutenticacion_DatosValidos;
                    }else{
                        contenidoInformeTarea = VocabularioSistemaAcceso.ResultadoAutenticacion_DatosNoValidos;
                      }
                    this.generarInformeOK(identTarea, contextoEjecucionTarea, identAgenteOrdenante, contenidoInformeTarea);
                }
		} catch (Exception e) {
                    e.printStackTrace();
                    this.generarInformeConCausaTerminacion(this.getIdentTarea(), contextoEjecucionTarea, identAgenteOrdenante, "Error-AlObtener:Interfaz_Recurso_Persistencia", CausaTerminacionTarea.ERROR);
		}
    }
}
