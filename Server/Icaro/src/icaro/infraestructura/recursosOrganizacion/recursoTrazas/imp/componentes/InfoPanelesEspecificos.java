package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstancia;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.TipoAgente;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.gui.PanelTrazasAbstracto;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.gui.PanelTrazasAgteCognitivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.gui.PanelTrazasAgteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.gui.PanelTrazasGenerico;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.gui.PanelTrazasRecurso;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InfoPanelesEspecificos {

    private Map tablaPanelesEspecificos;
    private LinkedList<String> listaElementosTrazables;
    private Set identsTiposEntidades;
    private ItfUsoConfiguracion itfConfig;

    public InfoPanelesEspecificos() {
        tablaPanelesEspecificos = new HashMap();
        identsTiposEntidades = new HashSet();
        listaElementosTrazables = new LinkedList<String>();
        for (TipoAgente tg : TipoAgente.values()) {
            identsTiposEntidades.add(tg.value());
        }
        identsTiposEntidades.add(NombresPredefinidos.NOMBRE_ENTIDAD_RECURSO);
    }

    public void addNewPanelEspecifico(InfoPanelEspecifico infoPanel) {
        tablaPanelesEspecificos.put(infoPanel.getIdentificador(), infoPanel);
    }

    public InfoPanelEspecifico getInfoPanel(String entityId) {
        return (InfoPanelEspecifico) tablaPanelesEspecificos.get(entityId);
    }

    public PanelTrazasAbstracto getPanelEspecifico(String entityId) {
        return (PanelTrazasAbstracto) tablaPanelesEspecificos.get(entityId);
    }

    public Set getIdentsPenels() {
        return tablaPanelesEspecificos.keySet();
    }

    public PanelTrazasAbstracto crearPanelparaEntidad(String entityId) {
        PanelTrazasAbstracto nuevoPanel = null;
        try {
            // Obtener el tipo de entidad
            if ((itfConfig == null) && (NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ != null)) {
                itfConfig = (ItfUsoConfiguracion) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfaz("Itf_Uso_Configuracion");
            }
        } catch (Exception ex) {
            Logger.getLogger(ClasificadorVisual.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (itfConfig == null) {
                nuevoPanel = new PanelTrazasAgteReactivo(entityId, "");
            } else {
                DescInstancia descComponente = itfConfig.getDescInstancia(entityId);
                if (descComponente == null) {
                    nuevoPanel = new PanelTrazasGenerico(entityId, "");
                } else if (descComponente.getCategoriaComponente().equals(NombresPredefinidos.NOMBRE_ENTIDAD_AGENTE)) {
                    if (descComponente.esDirigidoPorObjetivos()) {
                        nuevoPanel = new PanelTrazasAgteCognitivo(entityId, "");
                    } else {
                        nuevoPanel = new PanelTrazasAgteReactivo(entityId, "");
                    }
                } else if (descComponente.getCategoriaComponente().equals(NombresPredefinidos.NOMBRE_ENTIDAD_RECURSO)) {
                    nuevoPanel = new PanelTrazasRecurso(entityId, "");
                } else {
                    nuevoPanel = new PanelTrazasGenerico(entityId, "");
                }
            }
            tablaPanelesEspecificos.put(entityId, nuevoPanel);
        } catch (ExcepcionEnComponente | RemoteException ex) {
            Logger.getLogger(ClasificadorVisual.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nuevoPanel;
    }

    public synchronized PanelTrazasAbstracto crearPanelparaEntidad(String entityId, NombresPredefinidos.TipoEntidad entityType) {
        PanelTrazasAbstracto panel = (PanelTrazasAbstracto) tablaPanelesEspecificos.get(entityId);
        if (panel == null) {
            if (entityType.equals(NombresPredefinidos.TipoEntidad.noDefinido)) {
                panel = new PanelTrazasGenerico(entityId, "");
            } else if (entityType.equals(NombresPredefinidos.TipoEntidad.Reactivo)) {
                panel = new PanelTrazasAgteReactivo(entityId, "");
            } else if (entityType.equals(NombresPredefinidos.TipoEntidad.Recurso)) {
                panel = new PanelTrazasRecurso(entityId, "");
            } else {
                panel = new PanelTrazasAgteCognitivo(entityId, "");
            }
            tablaPanelesEspecificos.put(entityId, panel);
            listaElementosTrazables.add(entityId);
        }
        return panel;
    }
}
