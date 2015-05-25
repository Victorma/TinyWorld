package icaro.aplicaciones.agentes.AgenteAplicacionGameManager.tareas;

import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
import icaro.aplicaciones.recursos.comunicacionChat.ItfUsoComunicacionChat;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.procesadorCognitivo.TareaSincrona;

public class FinalizaAgente extends TareaSincrona {

    @Override
    public void ejecutar(Object... params) {
        try {
            ItfUsoComunicacionChat recComunicacionChat = NombresPredefinidos.<ItfUsoComunicacionChat>
                    getUseInterface(VocabularioGestionCitas.IdentRecursoComunicacionChat);
            if (recComunicacionChat != null) {
                recComunicacionChat.finalizaAgente(getIdentAgente());
            }
            getAgente().termina();
            NombresPredefinidos.removeInterfaceRegister(getIdentAgente());
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

}
