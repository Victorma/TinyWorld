package icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.imp;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoInternoAgteReactivo;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ItfControlAgteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.rmi.RemoteException;
import java.util.logging.Level;
import org.apache.log4j.Logger;

public class ProcesadorItemsPercepReactivo {

    private ItfControlAgteReactivo itfControlReactivo;
    private AgenteReactivoAbstracto agente;
    private Logger log = Logger.getLogger(ProcesadorItemsPercepReactivo.class);
    private ItfUsoRecursoTrazas trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;

    public ProcesadorItemsPercepReactivo(AgenteReactivoAbstracto agente, ItfControlAgteReactivo itfControl) {
        this.agente = agente;
        this.itfControlReactivo = itfControl;
        try {
            this.agente.getIdentAgente();
        } catch (RemoteException ex) {
            java.util.logging.Logger.getLogger(ProcesadorItemsPercepReactivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ProcesadorItemsPercepReactivo() {
        this.agente = null;
        this.itfControlReactivo = null;
    }

    public synchronized void procesarItem(Object item) throws RemoteException {
        Object infoExtraida = null;
        if (item instanceof EventoInternoAgteReactivo) {
            itfControlReactivo.procesarInput(((EventoInternoAgteReactivo) item).input, ((EventoInternoAgteReactivo) item).valoresParametrosAccion);
        } else {
            if (item instanceof EventoSimple) {
                infoExtraida = ((EventoSimple) item).getContenido();
            } else if (item instanceof MensajeSimple) {
                infoExtraida = ((MensajeSimple) item).getContenido();
            }
            if (infoExtraida != null) {
                itfControlReactivo.procesarInfoControlAgteReactivo(infoExtraida);
            } else {
                log.error("No se ha podido extraer informacion valida del  " + item + " no reconocido");
                trazas.aceptaNuevaTraza(new InfoTraza(this.agente.getIdentAgente(), "Percepcion: Item " + item + " no se ha podido extraer informacion valida", InfoTraza.NivelTraza.debug));
            }
        }
    }
}
