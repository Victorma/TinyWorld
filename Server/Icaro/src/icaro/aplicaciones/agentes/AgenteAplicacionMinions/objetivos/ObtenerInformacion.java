package icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

public class ObtenerInformacion extends Objetivo {

    public String objeto;
    
    public Objetivo parent;
    
	public Objetivo getParent() {
		return parent;
	}

	public void setParent(Objetivo parent) {
		this.parent = parent;
	}

    public ObtenerInformacion(String objeto) {
        this.objeto = objeto;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

}
