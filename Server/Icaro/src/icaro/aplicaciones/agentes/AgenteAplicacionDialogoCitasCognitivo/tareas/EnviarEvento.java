package icaro.aplicaciones.agentes.AgenteAplicacionDialogoCitasCognitivo.tareas;

import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.recursos.comunicacionChat.ItfUsoComunicacionChat;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.CausaTerminacionTarea;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class EnviarEvento extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {
        /**
         * Produce un saludo inicial y una presentacion de funcionalidad inicial al entrar en el
         * sistema
         */
        String identDeEstaTarea = this.getIdentTarea();
        String identAgenteOrdenante = this.getIdentAgente();
        GameEvent evento = (GameEvent) params[0];
        try {
            // // Se busca la interfaz del recurso en el repositorio de
            // interfaces
            ItfUsoComunicacionChat recComunicacionChat = (ItfUsoComunicacionChat) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ
                    .obtenerInterfazUso(VocabularioGestionCitas.IdentRecursoComunicacionChat);
            if (recComunicacionChat != null) {
                recComunicacionChat.comenzar(identAgenteOrdenante);
                String mensajeAenviar = evento.toString();
                recComunicacionChat.enviarMensagePrivado(mensajeAenviar);
            } else {
                identAgenteOrdenante = this.getAgente().getIdentAgente();
                this.generarInformeConCausaTerminacion(identDeEstaTarea, null, identAgenteOrdenante,
                        "Error-AlObtener:Interfaz:" + VocabularioGestionCitas.IdentRecursoComunicacionChat, CausaTerminacionTarea.ERROR);
            }
        } catch (Exception e) {
            this.generarInformeConCausaTerminacion(identDeEstaTarea, null, identAgenteOrdenante, "Error-Acceso:Interfaz:"
                    + VocabularioGestionCitas.IdentRecursoComunicacionChat, CausaTerminacionTarea.ERROR);
            e.printStackTrace();
        }
    }

}
