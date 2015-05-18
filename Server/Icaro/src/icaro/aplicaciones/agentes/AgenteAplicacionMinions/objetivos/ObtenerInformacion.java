package icaro.aplicaciones.agentes.AgenteAplicacionMinions.objetivos;


public class ObtenerInformacion extends Subobjetivo {

    public String objeto;

    public ObtenerInformacion(String objeto) {
        this.objeto = objeto;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

}
