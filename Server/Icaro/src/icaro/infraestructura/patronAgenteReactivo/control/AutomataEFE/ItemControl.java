package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE;

public class ItemControl {

    public int operacion;
    public final static int OPERACION_TIMEOUT = 0;
    public final static int OPERACION_TERMINAR = 1;

    public ItemControl(int op) {
        operacion = op;
    }
}
