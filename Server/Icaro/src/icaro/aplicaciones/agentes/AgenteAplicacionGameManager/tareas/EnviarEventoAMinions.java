package icaro.aplicaciones.agentes.AgenteAplicacionGameManager.tareas;

import icaro.aplicaciones.informacion.game_manager.Partida;
import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;

import java.util.ArrayList;
import java.util.List;

public class EnviarEventoAMinions extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		Partida partida = (Partida) params[0];
		GameEvent event = (GameEvent) params[1];

		String identAgenteOrdenante = this.getIdentAgente();
		
		List<String> minions = partida.minions;
		if(event.getParameter("toMinion") != null){
			minions = new ArrayList<String>();
			minions.add((String)event.getParameter("toMinion"));
		}
		
		try{
			ItfUsoAgenteCognitivo itfMinion;
			for(String m : minions){
				itfMinion =  (ItfUsoAgenteCognitivo) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfazUso(m);
				if (itfMinion != null) {
					itfMinion.aceptaMensaje(new MensajeSimple(event, this.identAgente, m));
				} else {
					identAgenteOrdenante = this.getAgente().getIdentAgente();
					this.generarInformeConCausaTerminacion(this.getIdentTarea(), null, identAgenteOrdenante, "Error-AlObtener:Interfaz:"
							+ m, CausaTerminacionTarea.ERROR);
				}
			}
		
		} catch (Exception e) {
			this.generarInformeConCausaTerminacion(this.getIdentTarea(), null, identAgenteOrdenante, "Error-Acceso:Interfaz:"
					+ VocabularioGestionCitas.IdentRecursoComunicacionChat, CausaTerminacionTarea.ERROR);
			e.printStackTrace(System.err);
		}
		
		this.getEnvioHechos().eliminarHechoWithoutFireRules(event);
	}

}
