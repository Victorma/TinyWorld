/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.visualizacionTipoJuego.imp;

import icaro.aplicaciones.informacion.dominioClases.aplicacionAcceso.InfoAccesoSinValidar;
import icaro.aplicaciones.recursos.visualizacionTipoJuego.imp.ClaseGeneradoraVisualizacionTipoJuego;
import icaro.aplicaciones.recursos.visualizacionAcceso.imp.NotificacionesEventosVisAcceso;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jzamorano
 */
public class NotificacionesEventosTipoJuego {
    protected ItfUsoRepositorioInterfaces itfUsoRepositorioInterfaces;
    protected ClaseGeneradoraVisualizacionTipoJuego generadoraVisualizador;
    protected InterfazUsoAgente itfUsoAgenteaReportar;
    protected String identificadorAgenteaReportar;
    protected String identificadordeEsteRecurso;
   
    private InterfazUsoAgente itfUsoAgenteReceptor;
    
   /* public NotificacionesEventosTipoJuego (ClaseGeneradoraVisualizaconTipoJuego generadoraVis){
        //Obtenemos las informaciones que necesitamos de la clase generadora del recurso
        generadoraVisualizador = generadoraVis;
        //identificadorEsteRecurso = generadorVis;
    }*/

    }
