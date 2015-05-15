package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.informacion.minions.MinionContext;
import icaro.aplicaciones.informacion.minions.MinionInfo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;

public class Observar extends TareaSincrona {

    private String identAgenteDialogo = VocabularioGestionCitas.IdentAgenteAplicacionDialogoCitas;

    @Override
    public void ejecutar(Object... params) {
    	
    	MinionInfo myInfo = (MinionInfo) params[0];
    	MinionContext myContext = (MinionContext) params[1];
    	
        ItfUsoAgenteCognitivo agenteChat;
        try {
            agenteChat = (ItfUsoAgenteCognitivo) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ
                    .obtenerInterfazUso(identAgenteDialogo);
            if (agenteChat != null) {
                GameEvent ev = new GameEvent("observe");
                ev.setParameter("entity", myInfo.getName());

                MensajeSimple ms = new MensajeSimple(ev, this.getIdentAgente(), myContext.getIdAgenteGameManager());
                myContext.getItfAgenteGameManager().aceptaMensaje(ms);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

}
