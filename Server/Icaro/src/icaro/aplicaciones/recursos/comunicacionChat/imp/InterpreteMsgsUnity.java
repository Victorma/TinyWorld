package icaro.aplicaciones.recursos.comunicacionChat.imp;

import dasi.util.LogUtil;
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

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class InterpreteMsgsUnity {
    //****************************************************************************************************
    // Fields:
    //****************************************************************************************************

    private ConexionUnity unityConnector_;
    private MensajeSimple messageToSend_;
    private ItfUsoExtractorSemantico semanticExtractor_;
    private DescComportamientoAgente agentBehaviourDescriptor_;
    private final ClaseGeneradoraComunicacionChat resource_;
    private final Map<String, ClientConfiguration> clients_;
    private final String agentName_ = "AgenteGameManager";

    //****************************************************************************************************
    // Constructors:
    //****************************************************************************************************

    public InterpreteMsgsUnity(ConexionUnity comunicChat, Map<String, ClientConfiguration> clients,
            ClaseGeneradoraComunicacionChat recurso) {
        unityConnector_ = comunicChat;
        clients_ = clients;
        resource_ = recurso;
    }

    //****************************************************************************************************
    // Properties:
    //****************************************************************************************************

    public synchronized void setDescComportamientoGM(DescComportamientoAgente value) {
        agentBehaviourDescriptor_ = value;
    }

    public synchronized void setConectorIrc(ConexionUnity value) {
        unityConnector_ = value;
    }

    public synchronized void setItfusoRecExtractorSemantico(ItfUsoExtractorSemantico value) {
        semanticExtractor_ = value;
    }

    //****************************************************************************************************
    // Methods (public):
    //****************************************************************************************************

    public final void handleLine(InetAddress address, Integer port, String line) {
        // Show and check the received line is a valid string:
        LogUtil.logWithMs(line);
        if (line == null || line.length() <= 0) {
            return;
        }
        try {
            // Convert the text into a game event object:
            JSONObject message = new JSONObject(line);
            GameEvent ge = new GameEvent();
            ge.fromJSONObject(message);

            // Process the game event:
            if (ge.isNameEquals(GameEvent.LOGIN_EVENT)) {
                onLoginEvent(address, port, ge);
            } else {
                onGameEvent(address, port, ge);
            }
        } catch (JSONException jse) {
            LogUtil.logWithMs("Received message wasnt a correct JSON object. Ignoring...");
            LogUtil.logWithMs(jse.getMessage());
        }
    }

    //----------------------------------------------------------------------------------------------------

    public void intentaDesconectar() {
        if (unityConnector_.isConnected()) {
            unityConnector_.disconnect();
        }
    }

    //****************************************************************************************************
    // Methods (private):
    //****************************************************************************************************
    
    private String getAddressToString(InetAddress address, Integer port) {
        return address.getHostName() + ":" + port.toString();
    }
    
    //----------------------------------------------------------------------------------------------------

    private String getAddressToString(ClientConfiguration client) {
        return client.getUrl() + ":" + client.getPort();
    }

    //----------------------------------------------------------------------------------------------------

    private synchronized void onLoginEvent(InetAddress address, Integer port, GameEvent ge) {
        try {
            String clientAddress = getAddressToString(address, port);
            DescInstanciaAgente agentDescriptor = new DescInstanciaAgente();
            agentDescriptor.setId(agentName_ + "(" + clientAddress + ")");
            agentDescriptor.setDescComportamiento(agentBehaviourDescriptor_);
            FactoriaAgenteCognitivo.instance().crearAgenteCognitivo(agentDescriptor);

            ClientConfiguration configuration = new ClientConfiguration(resource_, agentDescriptor.getId(),
                    address, port);

            AgenteCognitivo agente = (AgenteCognitivo) ClaseGeneradoraConfiguracion.instance()
                    .repoIntfaces.obtenerInterfazGestion(agentDescriptor.getId());

            clients_.put(clientAddress, configuration);
            clients_.put(agentDescriptor.getId(), configuration);

            agente.arranca();

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    //----------------------------------------------------------------------------------------------------

    private void onGameEvent(InetAddress address, Integer port, GameEvent ge) {
        // Check if we have a client configuration from the address:
        String clientAddress = getAddressToString(address, port);
        ClientConfiguration client = clients_.get(clientAddress);
        if (client == null) return;

        // Check which destination will have the event:
        try {
            List<Object> infoToSend = new ArrayList<>();
            if (ge.isNameEquals(GameEvent.ACTION_EVENT)) {
                //TODO: Delete this...???
                Notificacion notif = new Notificacion(getAddressToString(client));
                notif.setTipoNotificacion((String) ge.getParameter("actionname"));
                infoToSend.add(notif);
                enviarInfoExtraida(client, infoToSend);
                //...
            } else if (ge.isNameEquals(GameEvent.SEND_TEXT_EVENT)) {
                //TODO: Complete this...
                HashSet anotacionesBusquedaPrueba = new HashSet();
                String message = ((String) ge.getParameter("message")).toLowerCase();
                if (semanticExtractor_ != null) {
                    // Cosas del extractor semántico
                }
                //...
            } else {
                enviarEvento(client, ge);
            }
        } catch (Exception ex) {
            LogUtil.logger(InterpreteMsgsUnity.class, ex);
        }
    }

    //----------------------------------------------------------------------------------------------------

    private void enviarEvento(ClientConfiguration client, GameEvent ge) {
        ItfUsoAgenteCognitivo gameManager = client.getItfUsoAgente();
        if (gameManager != null) {
            try {
                gameManager.aceptaMensaje(new MensajeSimple(ge, getAddressToString(client),
                        gameManager.getIdentAgente()));
            } catch (RemoteException ex) {
                LogUtil.logger(InterpreteMsgsUnity.class, ex);
            }
        }
    }

    //----------------------------------------------------------------------------------------------------
    
    private void enviarInfoExtraida(ClientConfiguration client, List<Object> infoExtraida) {
        ItfUsoAgenteCognitivo gameManager = client.getItfUsoAgente();
        if (gameManager != null) {
            try {
                String clientAddress = getAddressToString(client);
                if (infoExtraida.isEmpty()) {
                    Notificacion infoAenviar = new Notificacion(clientAddress);
                    infoAenviar.setTipoNotificacion(VocabularioGestionCitas.ExtraccionSemanticaNull);
                    messageToSend_ = new MensajeSimple(infoAenviar, clientAddress, gameManager.getIdentAgente());
                } else if (infoExtraida.size() == 1) {
                    Object infoAenviar = infoExtraida.get(0);
                    messageToSend_ = new MensajeSimple(infoAenviar, clientAddress, gameManager.getIdentAgente());
                } else {
                    messageToSend_ = new MensajeSimple(infoExtraida, clientAddress, gameManager.getIdentAgente());
                }
                gameManager.aceptaMensaje(messageToSend_);
            } catch (RemoteException ex) {
                LogUtil.logger(InterpreteMsgsUnity.class, ex);
            }
        }
    }

    /*
		HashSet anotacionesBusquedaPrueba = new HashSet();
		anotacionesBusquedaPrueba.add("saludo");
		anotacionesBusquedaPrueba.add("dni");
		anotacionesBusquedaPrueba.add("InicioPeticion");
		anotacionesBusquedaPrueba.add("Lookup");
		anotacionesBusquedaPrueba.add("nombre");
		anotacionesBusquedaPrueba.add("despedida");
		anotacionesBusquedaPrueba.add("fecha");
		anotacionesBusquedaPrueba.add("fechaNumero");
		anotacionesBusquedaPrueba.add("inicioAnulacion");
		anotacionesBusquedaPrueba.add("consulta");
		anotacionesBusquedaPrueba.add("si");
		anotacionesBusquedaPrueba.add("no");
		// esto habria que pasarlo como parametro
		if (infoConecxInterlocutor == null) {
			infoConecxInterlocutor = new InfoConexionUsuario();
		}
		infoConecxInterlocutor.setuserName(sender);
		infoConecxInterlocutor.sethost(hostname);
		infoConecxInterlocutor.setlogin(login);
		if (itfUsoExtractorSem != null) {
			try {
				/**
				 * Si se le pasa un null en vez de un conjunto de anotaciones de
				 * prueba, usa las que tiene por defecto el objeto (Lookup,
				 * Saludo e InicioPeticion)
				 * /
				anotacionesRelevantes = itfUsoExtractorSem.extraerAnotaciones(
						anotacionesBusquedaPrueba, textoUsuario);
				String anot = anotacionesRelevantes.toString();
				System.out.println(System.currentTimeMillis() + " " + anot);
				ArrayList infoAenviar = interpretarAnotaciones(sender,
						textoUsuario, anotacionesRelevantes);
				enviarInfoExtraida(infoAenviar, sender);
				// if ( itfAgenteDialogo!=null){
				// mensajeAenviar = new
				// MensajeSimple(infoAenviar,sender,identAgenteGestorDialogo);
				// itfAgenteDialogo.aceptaMensaje(mensajeAenviar);
				// // comunicator.enviarMsgaOtroAgente(mensajeAenviar);
				// }
			} catch (Exception ex) {
				Logger.getLogger(InterpreteMsgsIRC.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
    */
//    private InfoConexionUsuario infoConecxInterlocutor;
//    private List<Notificacion> interpretarAnotaciones(String interlocutor, String contextoInterpretacion, HashSet anotacionesRelevantes) {
//        // recorremos las anotaciones obtenidas y las traducimos a objetos del
//        // modelo de información
//        List<Notificacion> anotacionesInterpretadas = new ArrayList<Notificacion>();
//        Iterator annotTypesSal = anotacionesRelevantes.iterator();
//        while (annotTypesSal.hasNext()) {
//            Annotation annot = (Annotation) annotTypesSal.next();
//            String anotType = annot.getType();
//            if (anotType.equalsIgnoreCase("saludo")) {
//                anotacionesInterpretadas.add(interpretarAnotacionSaludo(contextoInterpretacion, annot));
//            }
//        }
//        return anotacionesInterpretadas;
//    }
//    private Notificacion interpretarAnotacionSaludo(String conttextoInterpretacion, Annotation anotacionSaludo) {
//        Notificacion notif = new Notificacion(infoConecxInterlocutor.getuserName());
//        // obtenemos el texto del saludo a partir de la anotacion
//
//        int posicionComienzoTexto = anotacionSaludo.getStartNode().getOffset().intValue();
//        int posicionFinTexto = anotacionSaludo.getEndNode().getOffset().intValue();
//        String msgNotif = conttextoInterpretacion.substring(posicionComienzoTexto, posicionFinTexto);
//        notif.setTipoNotificacion(anotacionSaludo.getType());
//        notif.setMensajeNotificacion(msgNotif);
//        return notif;
//    }
}
