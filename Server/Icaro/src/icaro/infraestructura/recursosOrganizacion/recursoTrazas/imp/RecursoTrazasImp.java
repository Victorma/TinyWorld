package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.ClasificadorVisual;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.VisualizacionTrazasContr;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

public class RecursoTrazasImp extends ClaseGeneradoraRecursoTrazas implements Serializable {

    private transient ClasificadorVisual visual;
    private VisualizacionTrazasContr controlVisulizacionTrazas;

    public static enum TipoEntidad {
        Cognitivo, ADO, DirigidoPorObjetivos, Reactivo, Recurso, noDefinido
    }

    private Boolean activacionPanelTrazas = false;
    private transient Logger logger = Logger.getLogger(this.getClass().getCanonicalName());

    public RecursoTrazasImp(String id) throws RemoteException {
        super(id);
        creacionRecursoTrazas();
    }

    public void creacionRecursoTrazas() {
        controlVisulizacionTrazas = new VisualizacionTrazasContr();
        if (NombresPredefinidos.ACTIVACION_PANEL_TRAZAS_DEBUG.startsWith("t")) {
            activacionPanelTrazas = true;
            controlVisulizacionTrazas.activarVisualizacionTrazas();
        } else {
            controlVisulizacionTrazas.desactivarVisualizacionTrazas();
        }
    }

    @Override
    public void visualizacionDeTrazas(Boolean opcionTraza) {
        // Permite cambiar la opcion de visualizar o no las trazas
        if (opcionTraza) {
            this.controlVisulizacionTrazas.activarVisualizacionTrazas();
        } else {
            this.controlVisulizacionTrazas.desactivarVisualizacionTrazas();
        }

    }

    @Override
    public synchronized void trazar(String entidad, String infotraza, NivelTraza nivelTraza) {
        if (activacionPanelTrazas) {
            aceptaNuevaTraza(new InfoTraza(entidad, infotraza, nivelTraza));
        }
    }

    @Override
    public synchronized void aceptaNuevaTraza(InfoTraza traza) {
        if (activacionPanelTrazas) {
            controlVisulizacionTrazas.visualizarNuevaTraza(traza);
        }
    }

    @Override
    public synchronized void visualizaNuevaTraza(InfoTraza traza) {
        //saca la informacin slo de manera visual
        if (activacionPanelTrazas) {
            this.aceptaNuevaTraza(traza);
        }

    }

    @Override
    public void setIdentAgenteAReportar(String nombreAgente) {
        controlVisulizacionTrazas.setIdentAgenteAReportar(nombreAgente);
    }

    @Override
    public void visualizarComponentesTrazables(List<String> listaElementosaTrazar) {
        if (activacionPanelTrazas && (listaElementosaTrazar != null)) {
            visual.visualizarElementosTrazables(listaElementosaTrazar);
        }
    }

    @Override
    public void visualizarIdentFicheroDescrOrganizacion() {
        if (activacionPanelTrazas) {
            controlVisulizacionTrazas.visualizarIdentFicheroDescrOrganizacion();
        }

    }

    @Override
    public void termina() {
        //elimino todas las ventanas
        if (activacionPanelTrazas) {
            controlVisulizacionTrazas.cerrarVentanas();
        }
        super.termina();
    }

    @Override
    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public synchronized void pedirConfirmacionTerminacionAlUsuario() {
        controlVisulizacionTrazas.pedirConfirmacionTerminacionAlUsuario();
    }

    @Override
    public synchronized void aceptaNuevaTrazaEnviarMensaje(MensajeSimple trazaMensaje) {
        try {
            if (activacionPanelTrazas) {
                controlVisulizacionTrazas.visualizarEnvioMensaje(NombresPredefinidos.TipoEntidad.noDefinido, trazaMensaje);
            }
        } catch (Exception e) {
            logger.fatal("Error al mostrar la traza. Hay un problema con el recurso de trazas,", e);
        }
    }

    @Override
    public synchronized void aceptaNuevaTrazaEnviarEvento(EventoSimple trazaEvento) {
        try {
            if (activacionPanelTrazas) {
                controlVisulizacionTrazas.visualizarEnvioEvento(NombresPredefinidos.TipoEntidad.noDefinido, trazaEvento);
            }
        } catch (Exception e) {
            logger.fatal("Error al mostrar la traza. Hay un problema con el recurso de trazas,", e);
        }
    }

    public synchronized void trazarEnviarEvento(NombresPredefinidos.TipoEntidad tipoEnt, EventoSimple trazaEvento) {
        try {
            if (activacionPanelTrazas) {
                controlVisulizacionTrazas.visualizarEnvioEvento(tipoEnt, trazaEvento);

            }
        } catch (Exception e) {
            logger.fatal("Error al mostrar la traza. Hay un problema con el recurso de trazas,", e);
        }
    }

    @Override
    public synchronized void aceptaNuevaTrazaMensajeRecibido(MensajeSimple trazaMensaje) {
        try {
            if (activacionPanelTrazas) {
                controlVisulizacionTrazas.visualizarRecibirMensaje(NombresPredefinidos.TipoEntidad.noDefinido, trazaMensaje);
            }
        } catch (Exception e) {
            logger.fatal("Error al mostrar la traza. Hay un problema con el recurso de trazas,", e);
        }
    }

    @Override
    public synchronized void aceptaNuevaTrazaEventoRecibido(String entityId, EventoSimple trazaEvento) {
        try {
            if (activacionPanelTrazas) {
                controlVisulizacionTrazas.visualizarRecibirEvento(NombresPredefinidos.TipoEntidad.noDefinido, trazaEvento);
            }
        } catch (Exception e) {
            logger.fatal("Error al mostrar la traza. Hay un problema con el recurso de trazas,", e);
        }
    }

    public synchronized void trazaEventoRecibido(String entityId, NombresPredefinidos.TipoEntidad tipoEnt, EventoSimple trazaEvento) {
        try {
            if (activacionPanelTrazas) {
                controlVisulizacionTrazas.visualizarRecibirEvento(NombresPredefinidos.TipoEntidad.noDefinido, trazaEvento);
            }
        } catch (Exception e) {
            logger.fatal("Error al mostrar la traza. Hay un problema con el recurso de trazas,", e);
        }
    }

    @Override
    public synchronized void aceptaNuevaTrazaEjecReglas(String entityId, String infoAtrazar) {
        try {
            if (activacionPanelTrazas) {
                controlVisulizacionTrazas.visualizarTrazaEjecReglas(entityId, infoAtrazar);
            }
        } catch (Exception e) {
            logger.fatal("Error al mostrar la traza. Hay un problema con el recurso de trazas,", e);
        }
    }

    @Override
    public synchronized void aceptaNuevaTrazaActivReglas(String entityId, String infoAtrazar) {
        try {
            if (activacionPanelTrazas) {
                controlVisulizacionTrazas.visualizarTrazaActivacionReglas(entityId, infoAtrazar);
            }
        } catch (Exception e) {
            logger.fatal("Error al mostrar la traza. Hay un problema con el recurso de trazas,", e);
        }
    }
}
