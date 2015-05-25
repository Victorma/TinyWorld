package icaro.aplicaciones.recursos.comunicacionChat;

import java.net.InetAddress;

import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;

public class ClientConfiguration {

    private String identifAgenteGameManager;
    private String url;
    private InetAddress address;
    private Integer port;
    private ItfUsoAgenteCognitivo agente;
    private ClaseGeneradoraComunicacionChat recurso;

    public ClientConfiguration(ClaseGeneradoraComunicacionChat recurso, String identifAgenteGameManager, InetAddress address,
            Integer port) {
        super();
        this.identifAgenteGameManager = identifAgenteGameManager;
        this.url = address.getHostName();
        this.address = address;
        this.port = port;
        this.recurso = recurso;
    }

    public String getIdentifAgenteGameManager() {
        return identifAgenteGameManager;
    }

    public void setIdentifAgenteGameManager(String identifAgenteGameManager) {
        this.identifAgenteGameManager = identifAgenteGameManager;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public ItfUsoAgenteCognitivo getItfUsoAgente() {
        if (this.agente == null) {
            try {
                this.agente = (ItfUsoAgenteCognitivo) recurso.repoIntfaces.obtenerInterfazUso(identifAgenteGameManager);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }

        return this.agente;
    }

}
