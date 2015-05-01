package icaro.infraestructura.recursosOrganizacion.recursoTrazas;

import icaro.infraestructura.entidadesBasicas.comunicacion.EventoSimple;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import java.util.List;

public interface ItfUsoRecursoTrazas {

    public void visualizacionDeTrazas(Boolean opcionTraza);

    public void aceptaNuevaTraza(InfoTraza traza);

    public void aceptaNuevaTrazaEnviarMensaje(MensajeSimple traza);

    public void aceptaNuevaTrazaEnviarEvento(EventoSimple traza);

    public void aceptaNuevaTrazaMensajeRecibido(MensajeSimple traza);

    public void aceptaNuevaTrazaEventoRecibido(String idAgente, EventoSimple evento);

    public void aceptaNuevaTrazaEjecReglas(String entityId, String infoAtrazar);

    public void aceptaNuevaTrazaActivReglas(String entityId, String infoAtrazar);

    public void visualizaNuevaTraza(InfoTraza traza);

    public void visualizarComponentesTrazables(List<String> listaElementosaTrazar);

    public void visualizarIdentFicheroDescrOrganizacion();

    public void pedirConfirmacionTerminacionAlUsuario();

    public void mostrarMensajeError(String mensaje);

    public void setIdentAgenteAReportar(String nombreAgente);

    public void trazar(String entidadEmisora, String infotraza, NivelTraza nivelTraza);
}
