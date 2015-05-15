package icaro.aplicaciones.informacion.minions;

import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;

public class MinionContext {

	private ItfUsoAgenteCognitivo itfAgenteGameManager;
	private String idAgenteGameManager;
	public MinionContext(ItfUsoAgenteCognitivo itfAgenteGameManager,
			String idAgenteGameManager) {
		super();
		this.itfAgenteGameManager = itfAgenteGameManager;
		this.idAgenteGameManager = idAgenteGameManager;
	}
	public ItfUsoAgenteCognitivo getItfAgenteGameManager() {
		return itfAgenteGameManager;
	}
	public void setItfAgenteGameManager(ItfUsoAgenteCognitivo itfAgenteGameManager) {
		this.itfAgenteGameManager = itfAgenteGameManager;
	}
	public String getIdAgenteGameManager() {
		return idAgenteGameManager;
	}
	public void setIdAgenteGameManager(String idAgenteGameManager) {
		this.idAgenteGameManager = idAgenteGameManager;
	}
	
	
	
}
