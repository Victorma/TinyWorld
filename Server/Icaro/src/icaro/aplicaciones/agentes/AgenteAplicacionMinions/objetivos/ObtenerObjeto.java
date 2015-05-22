package icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos;


public class ObtenerObjeto extends Subobjetivo {

    public String objeto;

    public ObtenerObjeto(String objeto) {
        super.setgoalId("ObtenerObjeto");
        this.objeto = objeto;
    }

    @Override
    public boolean esAtomico() {
        return false;
    }

}
