package icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos;

import icaro.aplicaciones.informacion.minions.Coord;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.Objetivo;

public class AlcanzarPosicion extends Objetivo {

    public Coord coord;
    public int distance;
    
    public Objetivo parent;
    
	public Objetivo getParent() {
		return parent;
	}

	public void setParent(Objetivo parent) {
		this.parent = parent;
	}

    public AlcanzarPosicion(Coord coordinates) {
        this(coordinates, 0);
    }

    public AlcanzarPosicion(Coord coordinates, int distance) {
        super.setgoalId("AlcanzarPosicion");
        this.coord = coordinates;
        this.distance = distance;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

}
