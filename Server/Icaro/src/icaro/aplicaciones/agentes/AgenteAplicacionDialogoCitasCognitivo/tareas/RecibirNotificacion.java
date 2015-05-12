package icaro.aplicaciones.agentes.AgenteAplicacionDialogoCitasCognitivo.tareas;

import icaro.aplicaciones.informacion.gestionCitas.Notificacion;
import icaro.aplicaciones.informacion.minions.VocabularioControlMinions;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;

public class RecibirNotificacion extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {
        Notificacion notif = (Notificacion) params[0];

        String identAgente = VocabularioControlMinions.IdentAgenteMinion;

        ItfUsoAgenteCognitivo minion;
        try {
            minion = (ItfUsoAgenteCognitivo) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ
                    .obtenerInterfazUso(identAgente);
            if (minion != null) {
                MensajeSimple ms = new MensajeSimple(notif, this.getIdentAgente(), this.getAgente());
                minion.aceptaMensaje(ms);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.err);
        }
    }

}
