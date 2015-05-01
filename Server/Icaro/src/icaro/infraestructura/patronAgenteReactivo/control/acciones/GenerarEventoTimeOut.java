package icaro.infraestructura.patronAgenteReactivo.control.acciones;

import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;

public class GenerarEventoTimeOut extends Thread {

    private long milis;
    private String inputAenviar;
    private String origen;
    private String destino;
    private ItfUsoRepositorioInterfaces repositorio;

    public GenerarEventoTimeOut(long milis, String identInput, String origen, String destino, ItfUsoRepositorioInterfaces repositorio) {
        super();
        this.milis = milis;
        this.inputAenviar = identInput;
        this.origen = origen;
        this.destino = destino;
        this.repositorio = repositorio;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        try {
            sleep(milis);
            ItfUsoAgenteReactivo destinatario = (ItfUsoAgenteReactivo) repositorio.obtenerInterfaz(NombresPredefinidos.ITF_USO + destino);
            destinatario.aceptaEvento(new EventoRecAgte(inputAenviar, origen, destino));
        } catch (Exception e) {
            System.err.println("Error al enviar evento de timeout");
            e.printStackTrace();
        }
    }
}
