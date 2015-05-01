package icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes;

import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.gui.PanelTrazasAbstracto;

public class InfoPanelEspecifico {

    private String identificador;
    private String contenido;
    private PanelTrazasAbstracto panelEspecifico;

    public InfoPanelEspecifico(String etityId, String cont) {
        this.identificador = etityId;
        this.contenido = cont;
    }

    public void setId(String id) {
        this.identificador = id;
    }

    public void setContenido(String c) {
        this.contenido = c;
    }

    public void setPanelEspecifico(PanelTrazasAbstracto panelEspecif) {
        this.panelEspecifico = panelEspecif;
    }

    public PanelTrazasAbstracto getPanelEspecifico() {
        return this.panelEspecifico;
    }

    public String getIdentificador() {
        return this.identificador;
    }

    public String getContenido() {
        return this.contenido;
    }
}
