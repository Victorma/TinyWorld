/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.visualizacionTipoJuego;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
/**
 *
 * @author jzamorano
 */
public interface ItfUsoVisualizadorTipoJuego extends ItfUsoRecursoSimple {
    public void mostrarVisualizadorTipoJuego (String nombreAgente, String tipo) throws Exception;
    public void cerrarVisualizadorTipoJuego () throws Exception;
}
