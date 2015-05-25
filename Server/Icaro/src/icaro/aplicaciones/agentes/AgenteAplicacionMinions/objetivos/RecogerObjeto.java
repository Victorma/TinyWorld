package icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos;

import icaro.aplicaciones.informacion.minions.*;

public class RecogerObjeto extends Subobjetivo {

    public ItemData item;
    public String itemName;

    public RecogerObjeto(String itemName) {
        this.itemName = itemName;
    }

    public RecogerObjeto(ItemData item) {
        super.setgoalId("RecogerObjeto");
        this.item = item;
        this.itemName = item.getName();
    }

    public ItemData getItem() {
        return item;
    }

    public void setItem(ItemData item) {
        this.item = item;
    }

    @Override
    public boolean esAtomico() {
        return true;
    }

}
