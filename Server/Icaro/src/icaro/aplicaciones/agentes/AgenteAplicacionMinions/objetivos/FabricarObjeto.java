package icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos;

public class FabricarObjeto extends Subobjetivo {

    public String objeto;

    public FabricarObjeto(String objeto) {
        super.setgoalId("FabricarObjeto");
        this.objeto = objeto;
    }

    @Override
    public boolean esAtomico() {
        return false;
    }

}
