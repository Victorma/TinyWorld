/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.comunicacionChat.imp;

import gate.Annotation;
import icaro.aplicaciones.informacion.gestionCitas.InfoConexionUsuario;
import icaro.aplicaciones.informacion.gestionCitas.Notificacion;
import icaro.aplicaciones.informacion.gestionCitas.VocabularioGestionCitas;
import icaro.aplicaciones.informacion.minions.GameEvent;
import icaro.aplicaciones.recursos.comunicacionChat.ClaseGeneradoraComunicacionChat;
import icaro.aplicaciones.recursos.comunicacionChat.ClientConfiguration;
import icaro.aplicaciones.recursos.comunicacionChat.imp.util.ConexionUnity;
import icaro.aplicaciones.recursos.extractorSemantico.ItfUsoExtractorSemantico;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescComportamientoAgente;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.AgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.FactoriaAgenteCognitivo;
import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;
import icaro.infraestructura.recursosOrganizacion.configuracion.imp.ClaseGeneradoraConfiguracion;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author FGarijo
 */
public class InterpreteMsgsUnity {

    private boolean _verbose = true;
    private ConexionUnity conectorUnity;
    //private String identRecExtractSemantico;
    private MensajeSimple mensajeAenviar;
    private ItfUsoExtractorSemantico itfUsoExtractorSem;
    private InfoConexionUsuario infoConecxInterlocutor;
    private DescComportamientoAgente dca;
    private ClaseGeneradoraComunicacionChat recurso;

    private Map<String, ClientConfiguration> clients;

    public InterpreteMsgsUnity(ConexionUnity comunicChat, Map<String, ClientConfiguration> clients, ClaseGeneradoraComunicacionChat recurso) {
        conectorUnity = comunicChat;
        this.clients = clients;
        this.recurso = recurso;
    }

    public synchronized void setDescComportamientoGM(DescComportamientoAgente dca) {
        this.dca = dca;
    }

    public synchronized void setConectorIrc(ConexionUnity ircConect) {
        conectorUnity = ircConect;
    }

    public synchronized void setItfusoRecExtractorSemantico(ItfUsoExtractorSemantico itfRecExtractorSem) {
        this.itfUsoExtractorSem = itfRecExtractorSem;
    }

    public void log(String line) {
        if (_verbose) {
            System.out.println(System.currentTimeMillis() + " " + line);
        }
    }

    public final void handleLine(String url, Integer port, String line) {
        this.log(line);
        if (line.length() <= 0) {
            return;
        }

        try {
            JSONObject message = new JSONObject(line);

            GameEvent ge = new GameEvent();
            ge.fromJSONObject(message);

            //TODO add GameEvent deserialization check
            switch (ge.name) {
                case "login":
                    this.onClientConnect(url, port, ge);
                    break;
                default:
                    this.onGameEvent(url, port, ge);
                    break;
            }

        } catch (JSONException jse) {
            this.log("Received message wasnt a correct JSON object. Ignoring...");
        }

        return;

    }

    /**
     * This method is called once the ConexionIrc has successfully connected to the IRC server. The implementation of
     * this method in the ConexionIrc abstract class performs no actions and may be overridden as required.
     */
    protected void onConnect() {

    }

    /**
     * This method carries out the actions to be performed when the ConexionIrc gets disconnected. This may happen if
     * the ConexionIrc quits from the server, or if the connection is unexpectedly lost.
     * <p>
     * Disconnection from the IRC server is detected immediately if either we or the server close the connection
     * normally. If the connection to the server is lost, but neither we nor the server have explicitly closed the
     * connection, then it may take a few minutes to detect (this is commonly referred to as a "ping timeout").
     * <p>
     * If you wish to get your IRC bot to automatically rejoin a server after the connection has been lost, then this is
     * probably the ideal method to override to implement such functionality.
     * <p>
     * The implementation of this method in the ConexionIrc abstract class performs no actions and may be overridden as
     * required.
     */
    protected void onDisconnect() {
    }

    private String agentName = "AgenteGameManager";

    protected synchronized void onClientConnect(String url, Integer port, GameEvent ge) {

        try {

            DescInstanciaAgente descInstanciaAgente = new DescInstanciaAgente();
            descInstanciaAgente.setId(agentName + "(" + url + ":" + port + ")");
            descInstanciaAgente.setDescComportamiento(dca);
            FactoriaAgenteCognitivo.instance().crearAgenteCognitivo(descInstanciaAgente);

            ClientConfiguration configuration = new ClientConfiguration(recurso, descInstanciaAgente.getId(), url, port);

            AgenteCognitivo agente = (AgenteCognitivo) ClaseGeneradoraConfiguracion.instance().repoIntfaces.obtenerInterfazGestion(descInstanciaAgente.getId());

            //Map it for better response
            clients.put(url + ":" + port.toString(), configuration);
            clients.put(descInstanciaAgente.getId(), configuration);

            agente.arranca();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void onClientDisconnect(String url, Integer port, GameEvent ge) {

    }

    /**
     * This method is called whenever a message is sent to a channel. The implementation of this method in the
     * ConexionIrc abstract class performs no actions and may be overridden as required.
     *
     * @param channel The channel to which the message was sent.
     * @param sender The nick of the person who sent the message.
     * @param login The login of the person who sent the message.
     * @param hostname The hostname of the person who sent the message.
     * @param message The actual message sent to the channel.
     */
    protected void onMessage(String url, Integer port, GameEvent ge) {
    }

    /**
     * This method is called whenever a private message is sent to the ConexionIrc. The implementation of this method in
     * the ConexionIrc abstract class performs no actions and may be overridden as required.
     *
     * @param sender The nick of the person who sent the private message.
     * @param login The login of the person who sent the private message.
     * @param hostname The hostname of the person who sent the private message.
     * @param message The actual message.
     */
    protected void onGameEvent(String url, Integer port, GameEvent ge) {

        ClientConfiguration client = clients.get(url + ":" + port);
        if (client == null) {
            return;
        }

        if (itfUsoExtractorSem != null) {
            try {
                List<Object> infoAEnviar = new ArrayList<Object>();

                if (ge.name.equalsIgnoreCase("action")) {
                    Notificacion notif = new Notificacion(client.getUrl() + ":" + client.getPort());
                    notif.setTipoNotificacion((String) ge.getParameter("actionname"));
                    infoAEnviar.add(notif);
                    enviarInfoExtraida(client, infoAEnviar);
                } else {
                    enviarEvento(client, ge);
                }
            } catch (Exception ex) {
                Logger.getLogger(InterpreteMsgsUnity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void enviarEvento(ClientConfiguration client, GameEvent ge) {
        ItfUsoAgenteCognitivo gameManager = client.getItfUsoAgente();
        if (gameManager != null) {
            try {
                gameManager.aceptaMensaje(new MensajeSimple((Object) ge, client.getUrl() + ":" + client.getPort(), gameManager.getIdentAgente()));
            } catch (RemoteException ex) {
                Logger.getLogger(InterpreteMsgsUnity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void enviarInfoExtraida(ClientConfiguration client, List<Object> infoExtraida) {

        ItfUsoAgenteCognitivo gameManager = client.getItfUsoAgente();

        if (gameManager != null) {
            try {
                if (infoExtraida.size() == 0) {
                    Notificacion infoAenviar = new Notificacion(client.getUrl() + ":" + client.getPort());
                    infoAenviar.setTipoNotificacion(VocabularioGestionCitas.ExtraccionSemanticaNull);
                    mensajeAenviar = new MensajeSimple((Object) infoAenviar, client.getUrl() + ":" + client.getPort(), gameManager.getIdentAgente());
                } else if (infoExtraida.size() == 1) {
                    Object infoAenviar = infoExtraida.get(0);
                    mensajeAenviar = new MensajeSimple(infoAenviar, client.getUrl() + ":" + client.getPort(), gameManager.getIdentAgente());
                } else {
                    mensajeAenviar = new MensajeSimple(infoExtraida, client.getUrl() + ":" + client.getPort(), gameManager.getIdentAgente());
                }

                gameManager.aceptaMensaje(mensajeAenviar);
            } catch (RemoteException ex) {
                Logger.getLogger(InterpreteMsgsUnity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void intentaDesconectar() {
        // throw new UnsupportedOperationException("Not supported yet."); //To
        // change body of generated methods, choose Tools | Templates.
        if (conectorUnity.isConnected()) {
            conectorUnity.disconnect();
        }
    }

    private List<Notificacion> interpretarAnotaciones(String interlocutor, String contextoInterpretacion, HashSet anotacionesRelevantes) {
        // recorremos las anotaciones obtenidas y las traducimos a objetos del
        // modelo de información
        List<Notificacion> anotacionesInterpretadas = new ArrayList<Notificacion>();
        Iterator annotTypesSal = anotacionesRelevantes.iterator();
        while (annotTypesSal.hasNext()) {
            Annotation annot = (Annotation) annotTypesSal.next();
            String anotType = annot.getType();
            if (anotType.equalsIgnoreCase("saludo")) {
                anotacionesInterpretadas.add(interpretarAnotacionSaludo(contextoInterpretacion, annot));
            }
        }
        return anotacionesInterpretadas;
    }

    private Notificacion interpretarAnotacionSaludo(String conttextoInterpretacion, Annotation anotacionSaludo) {
        Notificacion notif = new Notificacion(this.infoConecxInterlocutor.getuserName());
        // obtenemos el texto del saludo a partir de la anotacion

        int posicionComienzoTexto = anotacionSaludo.getStartNode().getOffset().intValue();
        int posicionFinTexto = anotacionSaludo.getEndNode().getOffset().intValue();
        String msgNotif = conttextoInterpretacion.substring(posicionComienzoTexto, posicionFinTexto);
        notif.setTipoNotificacion(anotacionSaludo.getType());
        notif.setMensajeNotificacion(msgNotif);
        return notif;
    }
}
