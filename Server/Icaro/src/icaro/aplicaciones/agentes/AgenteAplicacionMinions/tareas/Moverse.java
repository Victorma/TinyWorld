package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;

public class Moverse extends TareaSincrona {
	
	private String identAgenteDialogo = VocabularioGestionCitas.IdentAgenteAplicacionDialogoCitas;
	
	@Override
	public void ejecutar(Object... params) {
		
		ItfUsoAgenteCognitivo agenteChat;
		try {
			agenteChat = (ItfUsoAgenteCognitivo) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ
					.obtenerInterfazUso(identAgenteDialogo);
			 if (agenteChat!=null){
				 GameEvent ev = new GameEvent("move");
				 ev.setParameter("entity", this.getIdentAgente());
				 
				 String movimiento = (String) params[0];
				 String destino = (String) params[1];
				 switch(movimiento){
				 case "zone" :  ev.setParameter("zone", destino); break;
				 case "coordinates" : ev.setParameter("coordinates", destino); break;
				 }
				
				 MensajeSimple ms = new MensajeSimple(ev, this.getIdentAgente(), identAgenteDialogo);
				 agenteChat.aceptaMensaje(ms);
	         }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
