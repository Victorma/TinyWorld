package icaro.aplicaciones.agentes.AgenteAplicacionGameManager.tareas;

import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
import icaro.aplicaciones.recursos.comunicacionChat.ItfUsoComunicacionChat;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

import java.rmi.RemoteException;

public class FinalizaAgente extends TareaSincrona{

	@Override
	public void ejecutar(Object... params) {
		try {
			ItfUsoComunicacionChat recComunicacionChat = (ItfUsoComunicacionChat) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ
					.obtenerInterfazUso(VocabularioGestionCitas.IdentRecursoComunicacionChat);
			
			if (recComunicacionChat != null) 
				recComunicacionChat.finalizaAgente(this.identAgente);
			
			this.agente.termina();
			NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.eliminarRegistroInterfaz(this.identAgente);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
