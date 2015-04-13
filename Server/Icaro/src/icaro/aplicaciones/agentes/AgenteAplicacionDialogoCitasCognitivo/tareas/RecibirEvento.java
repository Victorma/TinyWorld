package icaro.aplicaciones.agentes.AgenteAplicacionDialogoCitasCognitivo.tareas;

import icaro.aplicaciones.informacion.minions.Evento;
import icaro.aplicaciones.informacion.minions.InformeEvento;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;

public class RecibirEvento extends TareaSincrona {


	@Override
	public void ejecutar(Object... params) {
		InformeEvento iv = (InformeEvento) params[0];
		Evento ev = iv.evento;
		
		String identAgente = (String) ev.getParameter("agente");
		
		ItfUsoAgenteCognitivo minion;
		try {
			minion = (ItfUsoAgenteCognitivo) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ
					.obtenerInterfazUso(identAgente);
			 if (minion!=null){				
				 MensajeSimple ms = new MensajeSimple(ev, this.getIdentAgente(), this.getAgente());
				 minion.aceptaMensaje(ms);
	         }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
