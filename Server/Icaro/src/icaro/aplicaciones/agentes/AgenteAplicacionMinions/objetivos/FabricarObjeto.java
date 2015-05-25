package icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos;

import java.util.List;

import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.informacion.minions.ItemData;

public class FabricarObjeto extends Subobjetivo {

    public String objeto;
    public String[] combinacion;
    public boolean heObservado = false;
    public boolean esperandoReporteUso = false;

    public FabricarObjeto(String objeto) {
        super.setgoalId("FabricarObjeto");
        this.objeto = objeto;
    }
    
    @Override
    public boolean esAtomico() {
        return false;
    }
    
    public ItemData buscaObjetoResultante(GameEvent ge){
        
        
        List<Object> newItems = (List<Object>) ge.getParameter("newitems");
        for(Object io : newItems){
            ItemData id = (ItemData)io;
            if(id.getName().equalsIgnoreCase(objeto)){
                return id;
            }
        }
        
        return null;
        
    }

}
