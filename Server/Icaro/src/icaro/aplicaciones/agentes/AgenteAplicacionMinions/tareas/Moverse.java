package icaro.aplicaciones.agentes.AgenteAplicacionMinions.tareas;

import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
import icaro.aplicaciones.informacion.minions.Coord;
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
            if (agenteChat != null) {

                Coord coords = (Coord) params[0];
                int distance = (int) params[1];

                GameEvent ev = new GameEvent("move");
                ev.setParameter("entity", this.getIdentAgente());
                ev.setParameter("cell", coords);
                ev.setParameter("distance", distance);
                ev.setParameter("synchronous", true);

                MensajeSimple ms = new MensajeSimple(ev, this.getIdentAgente(), identAgenteDialogo);
                agenteChat.aceptaMensaje(ms);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

}
