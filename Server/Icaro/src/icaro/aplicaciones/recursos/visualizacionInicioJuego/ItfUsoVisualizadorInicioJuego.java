/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.visualizacionInicioJuego;
import icaro.infraestructura.patronRecursoSimple.ItfUsoRecursoSimple;
/**
 *
 * @author jzamorano
 */
public interface ItfUsoVisualizadorInicioJuego extends ItfUsoRecursoSimple {
    public void mostrarVisualizadorInicioJuego () throws Exception;
    public void cerrarVisualizadorInicioJuego () throws Exception;
}
