package icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp;

import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;

public class HebraMonitorizacion extends Thread {

    protected long milis;
    protected boolean finalizar;
    protected ItfUsoAgenteReactivo agente;
    protected String evento = "";

    public HebraMonitorizacion(long milis, ItfUsoAgenteReactivo agente, String eventoAProducir) {
        super("HebraMonitorizacion del agente reactivo " + agente.toString());
        this.milis = milis;
        this.finalizar = false;
        this.agente = agente;
        this.setDaemon(true);
        this.evento = eventoAProducir;
    }

    public void finalizar() {
        this.finalizar = true;
    }

    @Override
    public void run() {
        this.finalizar = false;
        while (!this.finalizar) {
            try {
                Thread.sleep(this.milis);
            } catch (InterruptedException ex) {
            }
            if (!this.finalizar) {
                try {
                    this.agente.aceptaEvento(new EventoRecAgte(this.evento, null, null));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
