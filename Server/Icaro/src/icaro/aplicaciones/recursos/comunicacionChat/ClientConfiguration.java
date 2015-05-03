package icaro.aplicaciones.recursos.comunicacionChat;

public class ClientConfiguration {
	
	private String identifAgenteGameManager;
	private String url;
	private Integer port;
	
	
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
	
	

}
