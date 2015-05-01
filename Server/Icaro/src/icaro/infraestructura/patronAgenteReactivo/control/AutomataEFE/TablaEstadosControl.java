package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE;

import java.util.Hashtable;

public class TablaEstadosControl {

    private Hashtable<String, Integer> clasificacionEstados = new Hashtable<String, Integer>();
    private String identificadorEstadoInicial = "";
    private String identificadorEstadoErrorInterno = "EstadoErrorInterno";
    private Hashtable<String, Hashtable<String, Operacion>> inputsDeEstados = new Hashtable<String, Hashtable<String, Operacion>>();
    public final static int TIPO_DE_ESTADO_FINAL = 3;
    public final static int TIPO_DE_ESTADO_INICIAL = 0;
    public final static int TIPO_DE_ESTADO_INTERMEDIO = 2;

    public TablaEstadosControl() {
    }

    public String dameEstadoInicial() {
        return identificadorEstadoInicial;
    }

    public Operacion dameOperacion(String estadoActual, String input) {
        Hashtable operaciones = (Hashtable) inputsDeEstados.get(estadoActual);
        return (Operacion) operaciones.get(input);
    }

    public boolean esEstadoFinal(String estado) {
        return (((Integer) clasificacionEstados.get(estado)).intValue() == TablaEstadosControl.TIPO_DE_ESTADO_FINAL);
    }

    public boolean esInputValidoDeEstado(String estado, String input) {
        Hashtable inp = (Hashtable) inputsDeEstados.get(estado);
        if (inp != null) {
            return (inp.containsKey(input));
        } else {
            return false;
        }
    }

    public void putEstado(String identificador, int tipo) {
        clasificacionEstados.put(identificador, new Integer(tipo));
        inputsDeEstados.put(identificador, new Hashtable<String, Operacion>());
        if (tipo == TIPO_DE_ESTADO_INICIAL) {
            identificadorEstadoInicial = identificador;
        }
    }

    public String crearEstadoErrorInterno() {
        putEstado(identificadorEstadoErrorInterno, TIPO_DE_ESTADO_FINAL);
        return identificadorEstadoErrorInterno;
    }

    public void putTransicion(String estado, String input, String accion, String estadoSiguiente, String modo) {
        boolean modoBloqueante = modo.equalsIgnoreCase("bloqueante");
        Operacion operacion = new Operacion(accion, estadoSiguiente, modoBloqueante);
        Hashtable<String, Operacion> inputsDeUnEstado = (Hashtable<String, Operacion>) inputsDeEstados.get(estado);
        inputsDeUnEstado.put(input, operacion);
    }

    public void putTransicionUniversal(String input, String accion, String estadoSig, String modo) {
        java.util.Enumeration iteradorClaves = clasificacionEstados.keys();
        while (iteradorClaves.hasMoreElements()) {
            String estadoPivote = (String) iteradorClaves.nextElement();
            if ((!esEstadoFinal(estadoPivote)) && (!esInputValidoDeEstado(estadoPivote, input))) {
                putTransicion(estadoPivote, input, accion, estadoSig, modo);
            }
        }
    }

    @Override
    public String toString() {
        String dev = "LEYENDA:   Estado: input/accion -> estado siguiente";
        dev += "\n------------------------------------------------------";
        java.util.Enumeration nombres = clasificacionEstados.keys();

        String input = "";
        String accion = "";
        String estsig = "";
        String id = "";
        while (nombres.hasMoreElements()) {
            id = (String) nombres.nextElement();

            Hashtable inp = (Hashtable) inputsDeEstados.get(id);
            if (!inp.isEmpty()) {
                java.util.Enumeration inps = inp.keys();
                while (inps.hasMoreElements()) {
                    input = (String) inps.nextElement();
                    Operacion op = (Operacion) inp.get(input);
                    accion = op.accionSemantica;
                    estsig = op.estadoSiguiente;
                    dev += "\n" + id + ": " + input + " / " + accion + " -> " + estsig;
                }

            } else {
                dev += "\n" + id + " <- ES UN ESTADO FINAL";
            }

        }
        return dev += "\n------------------------------------------------------";
    }
}
