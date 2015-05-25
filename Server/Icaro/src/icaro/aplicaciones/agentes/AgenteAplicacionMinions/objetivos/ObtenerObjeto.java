package icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos;

import icaro.aplicaciones.informacion.minions.ItemData;


public class ObtenerObjeto extends Subobjetivo {

    public String objeto;
    public boolean heObservado = false;
    public ItemData objetoObtenido;

    public ObtenerObjeto(String objeto) {
        super.setgoalId("ObtenerObjeto");
        this.objeto = objeto;
    }
    public String getObjeto(){
        return objeto;
    }
    
    public ItemData getObjetoObtenido() {
        return objetoObtenido;
    }
    public void setObjetoObtenido(ItemData objetoObtenido) {
        this.objetoObtenido = objetoObtenido;
    }
    
    @Override
    public boolean esAtomico() {
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + " Objeto: " + objeto;
    }
    
}
