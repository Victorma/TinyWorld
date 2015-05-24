package icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos;

import icaro.aplicaciones.informacion.minions.*;

public class RecogerObjeto extends Subobjetivo {

    public ItemData item;

    public RecogerObjeto(ItemData item) {
    	super.setgoalId("RecogerObjeto");
        this.item = item;
    }

    public ItemData getItem() {
        return item;
    }

    public void setItem(ItemData item) {
        this.item = item;
    }

}
