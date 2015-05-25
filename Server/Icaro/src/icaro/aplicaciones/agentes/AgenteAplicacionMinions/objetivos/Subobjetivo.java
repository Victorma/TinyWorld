package icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos;

import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

public abstract class Subobjetivo extends Objetivo {

    public Objetivo parent;
    public String owner;

    public Objetivo getParent() {
        return parent;
    }

    public void setParent(Objetivo parent) {
        this.parent = parent;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public abstract boolean esAtomico();
}
