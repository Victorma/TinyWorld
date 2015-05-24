package icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos;

import icaro.aplicaciones.informacion.minions.*;

public class DepositarObjeto extends Subobjetivo {

    public ItemData item;
    public Coord destination;
    
    public DepositarObjeto(ItemData item, Coord destination) {
        super.setgoalId("RecojerObjeto");
        this.item = item;
        this.destination = destination;
    }

    public ItemData getItem() {
        return item;
    }

    public void setItem(ItemData item) {
        this.item = item;
    }
    
    public Coord getDestination() {
        return destination;
    }

    public void setDestination(Coord destination) {
        this.destination = destination;
    }
    
    public boolean isNear(Coord myCoords) {
        boolean near = false;

        if (myCoords.getX() == destination.getX()
                && (Math.abs(myCoords.getY() - destination.getY()) <= 1))
            near = true;

        if (myCoords.getY() == destination.getY()
                && (Math.abs(myCoords.getX() - destination.getX()) <= 1))
            near = true;

        return near;
    }

}
