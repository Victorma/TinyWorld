package icaro.aplicaciones.recursos.comunicacionChat;

import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;
import icaro.infraestructura.recursosOrganizacion.configuracion.imp.ClaseGeneradoraConfiguracion;

public class ClientConfiguration {
	
	private String identifAgenteGameManager;
	private String url;
	private Integer port;
	private ItfUsoAgenteCognitivo agente;
	
	public ClientConfiguration(String identifAgenteGameManager, String url,
			Integer port) {
		super();
		this.identifAgenteGameManager = identifAgenteGameManager;
		this.url = url;
		this.port = port;
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
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public ItfUsoAgenteCognitivo getItfUsoAgente() {
		if(this.agente == null)
			try {
				this.agente = (ItfUsoAgenteCognitivo) ClaseGeneradoraConfiguracion.instance().repoIntfaces.obtenerInterfazUso(identifAgenteGameManager);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
		return this.agente;
	}
	
	

}
