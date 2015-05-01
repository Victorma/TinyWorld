package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE;

public class Operacion {

    public String accionSemantica;
    public String estadoSiguiente;
    public boolean modoTransicionBloqueante;

    public Operacion(String accion, String estadoSig, boolean modo) {
        accionSemantica = accion;
        estadoSiguiente = estadoSig;
        modoTransicionBloqueante = modo;
    }
}
