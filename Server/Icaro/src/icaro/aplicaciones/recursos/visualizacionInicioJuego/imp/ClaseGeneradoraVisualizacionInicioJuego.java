
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.visualizacionInicioJuego.imp;

import icaro.aplicaciones.recursos.visualizacionInicioJuego.ItfUsoVisualizadorInicioJuego;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

/**
 *
 * @author jzamorano
 */
public class ClaseGeneradoraVisualizacionInicioJuego extends ImplRecursoSimple implements ItfUsoVisualizadorInicioJuego {

    private final String nombredeEsteRecurso;
    private final ItfUsoRecursoTrazas trazas;

    public ClaseGeneradoraVisualizacionInicioJuego(String idRecurso) throws Exception {
        super(idRecurso);
        nombredeEsteRecurso = idRecurso;
        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        this.mostrarVisualizadorInicioJuego();

    }

    @Override
    public void mostrarVisualizadorInicioJuego() throws Exception {
        trazas.aceptaNuevaTraza(new InfoTraza(nombredeEsteRecurso, "Mostrando visualizador...", InfoTraza.NivelTraza.debug));
    }

    @Override
    public void cerrarVisualizadorInicioJuego() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
