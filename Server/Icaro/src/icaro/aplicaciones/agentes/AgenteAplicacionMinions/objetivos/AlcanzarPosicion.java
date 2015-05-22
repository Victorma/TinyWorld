package icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos;

import icaro.aplicaciones.informacion.minions.Coord;

public class AlcanzarPosicion extends Subobjetivo {

    public Coord coord;
    public int distance;

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

    @Override
    public boolean esAtomico() {
        return true;
    }

}
