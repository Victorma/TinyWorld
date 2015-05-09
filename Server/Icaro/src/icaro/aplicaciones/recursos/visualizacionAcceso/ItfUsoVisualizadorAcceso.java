package icaro.aplicaciones.recursos.visualizacionAcceso;

import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;

/**
 * 
 *@author     Felipe Polo
 *@created    30 de noviembre de 2007
 */

public interface ItfUsoVisualizadorAcceso extends ItfUsoRecursoSimple{

	public void mostrarVisualizadorAcceso(String nombreAgente,String tipo) throws Exception;
	public void cerrarVisualizadorAcceso() throws Exception;
  	public void mostrarMensajeInformacion(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeAviso(String titulo,String mensaje) throws Exception;
  	public void mostrarMensajeError(String titulo,String mensaje) throws Exception;	
}