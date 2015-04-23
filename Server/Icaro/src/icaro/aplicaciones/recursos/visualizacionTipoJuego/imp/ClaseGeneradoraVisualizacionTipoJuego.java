
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.visualizacionTipoJuego.imp;


import icaro.aplicaciones.recursos.visualizacionTipoJuego.ItfUsoVisualizadorTipoJuego;
import icaro.aplicaciones.recursos.visualizacionTipoJuego.imp.gui.PanelTipoJuego;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.rmi.RemoteException;
/**
 *
 * @author jzamorano
 */
public class ClaseGeneradoraVisualizacionTipoJuego extends ImplRecursoSimple implements ItfUsoVisualizadorTipoJuego{

    private final String nombredeEsteRecurso;
    private final ItfUsoRecursoTrazas trazas;
    //private final NotificacionesEventosTipoJuego notifAgente;
    //private final PanelTipoJuego ventanaTipoJuego;
    
    public ClaseGeneradoraVisualizacionTipoJuego(String idRecurso) throws RemoteException {
        super(idRecurso);
        nombredeEsteRecurso = idRecurso;
        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        //se crea el panel y las notificaciones
        
       // notifAgente = new NotificacionesEventosTipoJuego(this);
        //this.ventanaTipoJuego = new PanelTipoJuego(this,notifAgente);
    }

    @Override
    public void mostrarVisualizadorTipoJuego(String nombreAgente, String tipo) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cerrarVisualizadorTipoJuego() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
