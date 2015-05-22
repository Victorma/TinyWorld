package icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

public abstract class Subobjetivo extends Objetivo {

    public Objetivo parent;
    
	public Objetivo getParent() {
		return parent;
	}

	public void setParent(Objetivo parent) {
		this.parent = parent;
	}
	
	public abstract boolean esAtomico();
}
