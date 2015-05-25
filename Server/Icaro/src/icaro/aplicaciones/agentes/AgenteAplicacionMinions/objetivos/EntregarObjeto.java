package icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos;

import icaro.aplicaciones.informacion.minions.ItemData;

public class EntregarObjeto extends Subobjetivo {

    public String to;
    public ItemData item;
    
    public EntregarObjeto(String to, ItemData item){
        this.to = to;
        this.item = item;
    }
    
    @Override
    public boolean esAtomico() {
        return true;
    }

}
