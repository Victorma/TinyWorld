package icaro.aplicaciones.agentes.AgenteAplicacionGameManager.tareas;

import icaro.aplicaciones.informacion.game_manager.Partida;
import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.recursos.comunicacionChat.ItfUsoComunicacionChat;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class EnviarEventoAPartida extends TareaSincrona {

	@Override
	public void ejecutar(Object... params) {

		Partida partida = (Partida) params[0];
		GameEvent event = (GameEvent) params[1];

		String identAgenteOrdenante = this.getIdentAgente();

		try {
			// // Se busca la interfaz del recurso en el repositorio de
			// interfaces
			ItfUsoComunicacionChat recComunicacionChat = (ItfUsoComunicacionChat) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ
					.obtenerInterfazUso(VocabularioGestionCitas.IdentRecursoComunicacionChat);
			if (recComunicacionChat != null) {
				recComunicacionChat.enviarMensaje(this.identAgente, event);
			} else {
				identAgenteOrdenante = this.getAgente().getIdentAgente();
				this.generarInformeConCausaTerminacion(this.getIdentTarea(), null, identAgenteOrdenante, "Error-AlObtener:Interfaz:"
						+ VocabularioGestionCitas.IdentRecursoComunicacionChat, CausaTerminacionTarea.ERROR);
			}
		} catch (Exception e) {
			this.generarInformeConCausaTerminacion(this.getIdentTarea(), null, identAgenteOrdenante, "Error-Acceso:Interfaz:"
					+ VocabularioGestionCitas.IdentRecursoComunicacionChat, CausaTerminacionTarea.ERROR);
			e.printStackTrace(System.err);
		}
		
		this.getEnvioHechos().eliminarHechoWithoutFireRules(event);
	}

}
