package icaro.aplicaciones.recursos.comunicacionChat;

import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.recursos.comunicacionChat.imp.InterpreteMsgsUnity;
import icaro.aplicaciones.recursos.comunicacionChat.imp.util.ConexionUnity;
import icaro.aplicaciones.recursos.comunicacionChat.imp.util.OutputMessage;
import icaro.aplicaciones.recursos.extractorSemantico.ItfUsoExtractorSemantico;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescComportamientoAgente;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.configuracion.imp.ClaseGeneradoraConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClaseGeneradoraComunicacionChat extends ImplRecursoSimple implements ItfUsoComunicacionChat {

    /**
     *
     */
    private static final long serialVersionUID = -5467462045830794190L;
    private String identExtractorSem;

    private boolean conectado = false;

    private ConexionUnity comunicChat;
    private InterpreteMsgsUnity interpreteMsgUnity;
    private Map<String, ClientConfiguration> clients;

    public ClaseGeneradoraComunicacionChat(String idRecurso) throws RemoteException {
        super(idRecurso);

        identExtractorSem = VocabularioGestionCitas.IdentRecursoExtractorSemantico;
        try {
            trazas.aceptaNuevaTraza(new InfoTraza(this.getId(), "Creando el recurso " + idRecurso, InfoTraza.NivelTraza.debug));
            comunicChat = new ConexionUnity();
            clients = new HashMap<String, ClientConfiguration>();
            interpreteMsgUnity = new InterpreteMsgsUnity(comunicChat, clients, this);

            ClaseGeneradoraConfiguracion configuracion = ClaseGeneradoraConfiguracion.instance();
            DescComportamientoAgente dca = configuracion.getDescComportamientoAgente("AgenteAplicacionGameManager");
            interpreteMsgUnity.setDescComportamientoGM(dca);

            comunicChat.setInterpreteMsgs(interpreteMsgUnity);
            trazas.aceptaNuevaTraza(new InfoTraza(this.getId(), "Iniciando el recurso " + idRecurso, InfoTraza.NivelTraza.debug));
            comenzar();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            this.trazas.aceptaNuevaTraza(new InfoTraza(id, "Se ha producido un error al crear el extractor semantico  " + e.getMessage()
                    + ": Verificar los parametros de creacion " + "rutas y otros", InfoTraza.NivelTraza.error));
            this.itfAutomata.transita("error");
            throw new RemoteException("Exception during the chat resource initiation:", e);
        }
    }

    private void generarErrorCreacionComponente(String textoMensaje) {
        this.trazas.aceptaNuevaTraza(new InfoTraza(id, "Se ha producido un error al crear el extractor semantico  " + textoMensaje
                + ": Verificar los parametros de creacion ", InfoTraza.NivelTraza.error));
        this.itfAutomata.transita("error");

    }

    private void comenzar() throws Exception {
        try {
            ItfUsoExtractorSemantico itfExtractorSem = (ItfUsoExtractorSemantico) this.repoIntfaces.obtenerInterfazUso(identExtractorSem);
            if (itfExtractorSem == null) {
                this.generarErrorCreacionComponente("itfExtractorSemantico es null");
            } else {
                interpreteMsgUnity.setItfusoRecExtractorSemantico(itfExtractorSem);
            }
            if (itfExtractorSem == null) {
                throw new Exception("No semantic extractor found.");
            } else {
                conectar();
            }
        } catch (Exception ex) {
            Logger.getLogger(ClaseGeneradoraComunicacionChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Boolean conectar() throws Exception {
        if (conectado) {
            return true;
        } else {
            conectado = false;
        }

        while (!conectado) {
            comunicChat.connect(ConfigInfoComunicacionChat.SocketURL, ConfigInfoComunicacionChat.SocketPort);
            conectado = true;
        }
        return conectado;
    }

    private void desconectar() throws Exception {
        comunicChat.disconnect();
    }

    @Override
    public void termina() {
        try {
            super.termina();
            this.desconectar();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void enviarMensaje(String identAgenteOrigen, GameEvent mensaje)
            throws Exception {

        ClientConfiguration configuration = clients.get(identAgenteOrigen);

        if (configuration != null) {
            this.comunicChat.sendMessage(new OutputMessage(mensaje.toJSONObject().toString(), configuration));
        }

    }
}
