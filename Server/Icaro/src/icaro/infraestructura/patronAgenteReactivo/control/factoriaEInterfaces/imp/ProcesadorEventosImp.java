package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.imp;

import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.ItemControl;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.comunicacion.InfoContEvtMsgAgteReactivo;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.imp.AutomataEFEImp;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ProcesadorInfoReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfConsumidorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;

public class ProcesadorEventosImp extends ProcesadorInfoReactivoAbstracto {

    private boolean DEBUG = false;
    private AutomataEFEImp automataControl;
    private AccionesSemanticasAgenteReactivo accionesSemanticasAgenteCreado;
    private int estado = InterfazGestion.ESTADO_CREADO;
    private String nombre;
    private ItfConsumidorPercepcion percepcionConsumidor;
    private ItfProductorPercepcion percepcionProductor;

    public ProcesadorEventosImp(AutomataEFEImp automata, AccionesSemanticasAgenteReactivo accionesSemanticasEspecificas,
            ItfProductorPercepcion percProductor, ItfConsumidorPercepcion percConsumidor, String nombreDelControl) {
        super("Agente reactivo " + nombreDelControl);
        automataControl = automata;
        accionesSemanticasAgenteCreado = accionesSemanticasEspecificas;
        percepcionConsumidor = percConsumidor;
        percepcionProductor = percProductor;
        nombre = nombreDelControl;
    }

    @Override
    public void arranca() {
        if (DEBUG) {
            System.out.println(nombre + ": arranca()");
        }
        estado = InterfazGestion.ESTADO_ARRANCANDO;
        try {
            this.start();
            //start llama a run()
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void continua() {
        throw new java.lang.UnsupportedOperationException("El metodo continua() an no est implementado.");
    }

    @Override
    public int obtenerEstado() {
        if (DEBUG) {
            System.out.println(nombre + ": obtenerEstado()");
        }
        return estado;
    }

    @Override
    public void para() {
        throw new java.lang.UnsupportedOperationException("El mtodo para() an no est implementado.");
    }

    @Override
    public void run() {
        estado = InterfazGestion.ESTADO_ACTIVO;
        while (!(estado == InterfazGestion.ESTADO_TERMINANDO)) {
            int milis = 3600000;
            Object obj = null;
            try {
                obj = percepcionConsumidor.consumeConTimeout(milis);
            } catch (Exception e) {
                if (DEBUG) {
                    System.out.println(nombre + ": No ha llegado evento al control del Gestor en " + milis + " milisegundos");
                }
            }
            if (obj != null) {
                if (obj instanceof ItemControl) {
                    if (DEBUG) {
                        System.out.println(nombre + ":Percibido Evento de control");
                    }
                    tratarEventoControl((ItemControl) obj);
                } else if (obj instanceof EventoSimple) {
                    if (DEBUG) {
                        System.out.println(nombre + ":Percibido Evento de input");
                    }
                    tratarEventoInput((EventoSimple) obj);
                } else if (obj instanceof MensajeSimple) {
                    if (DEBUG) {
                        System.out.println(nombre + ":Percibido Evento de input");
                    }
                    tratarMensajeSimple((MensajeSimple) obj);
                }
                if (DEBUG) {
                    System.out.println("ERROR: " + nombre + ": Ha llegado al 'Control' del 'Gestor' un 'Evento' no reconocido");
                }
            }
            yield();
        }
    }

    @Override
    public void procesarInput(Object input, Object... paramsAccion) {
        if (paramsAccion == null) {
            automataControl.procesaInput(input);
        }
        automataControl.procesaInput(input, paramsAccion);
    }

    @Override
    public void inicializarInfoGestorAcciones(String identAgte, ItfProductorPercepcion itfEvtosInternos) {
        if (accionesSemanticasAgenteCreado != null) {
            accionesSemanticasAgenteCreado.inicializarAcciones(identAgte, this, itfEvtosInternos);
        }
    }

    @Override
    public void termina() {
        if (DEBUG) {
            System.out.println(nombre + ":terminando ...");
        }
        estado = InterfazGestion.ESTADO_TERMINADO;

    }

    private void tratarEventoControl(ItemControl ec) {
        switch (ec.operacion) {
            case ItemControl.OPERACION_TERMINAR:
                estado = InterfazGestion.ESTADO_TERMINANDO;
                break;
            case ItemControl.OPERACION_TIMEOUT:
                if (DEBUG) {
                    System.out.println(nombre + "Alerta: Ha llegado un timeout de inanicion: ");
                }
                break;
            default:
                if (DEBUG) {
                    System.out.println("ERROR: " + nombre + " :Evento de control desconocido");
                }
                break;
        }
    }

    private void tratarEventoInput(EventoSimple ei) {
        InfoContEvtMsgAgteReactivo infoControl = (InfoContEvtMsgAgteReactivo) ei.getContenido();
        String inputExtraido = infoControl.getInput().trim();
        if (estado == InterfazGestion.ESTADO_ACTIVO) {
            automataControl.procesaInput(inputExtraido, infoControl.getvaloresParametrosAccion());
        }
    }

    private void tratarMensajeSimple(MensajeSimple msg) {
        InfoContMsgAgteReactivo contMsg = (InfoContMsgAgteReactivo) msg.getContenido();
        String inputExtraido = contMsg.getInput();
        if (estado == InterfazGestion.ESTADO_ACTIVO) {
            automataControl.procesaInput(inputExtraido, contMsg.getvaloresParametrosAccion());
        }
    }

    public void setDebug(boolean d) {
        this.DEBUG = d;
    }

    public boolean getDebug() {
        return this.DEBUG;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int e) {
        this.estado = e;
    }

    @Override
    public void setGestorAReportar(ItfUsoAgenteReactivo itfUsoGestorAReportar) {
        accionesSemanticasAgenteCreado.setItfUsoGestorAReportar(itfUsoGestorAReportar);
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public String getEstadoControlAgenteReactivo() {
        return "OP No implementada";
    }

    @Override
    public synchronized void procesarInfoControlAgteReactivo(Object infoParaProcesar) {
        if (this.estado == InterfazGestion.ESTADO_ACTIVO) {
            if (infoParaProcesar instanceof InfoContEvtMsgAgteReactivo) {
                InfoContEvtMsgAgteReactivo infoParaAutomata = (InfoContEvtMsgAgteReactivo) infoParaProcesar;
                automataControl.procesaInput(infoParaAutomata.getInput(), infoParaAutomata.getvaloresParametrosAccion());
            } else {
                System.out.println(nombre + ": El input debe ser de  clase InfoContEvtMsgAgteReactivo  y el objeto es clase"
                        + infoParaProcesar.getClass() + " Cambiar el contenido del evento");
            }
        }
    }
}
