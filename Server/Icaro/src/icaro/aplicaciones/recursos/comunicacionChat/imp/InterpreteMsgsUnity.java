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
import icaro.aplicaciones.recursos.comunicacionChat.imp.util.ConexionUnity;
import icaro.aplicaciones.recursos.extractorSemantico.ItfUsoExtractorSemantico;
import icaro.infraestructura.entidadesBasicas.comunicacion.ComunicacionAgentes;
import icaro.infraestructura.entidadesBasicas.comunicacion.MensajeSimple;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazUsoAgente;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FGarijo
 */
public class InterpreteMsgsUnity {

	private boolean _verbose = true;
	private String _userNameAgente = VocabularioGestionCitas.IdentConexionAgte;
	private ConexionUnity conectorUnity;
	private String identAgenteGestorDialogo;
	private String identRecExtractSemantico;
	private ComunicacionAgentes comunicator;
	private MensajeSimple mensajeAenviar;
	private InterfazUsoAgente itfAgenteDialogo;
	private ItfUsoExtractorSemantico itfUsoExtractorSem;
	private InfoConexionUsuario infoConecxInterlocutor;
	private HashSet anotacionesRelevantes;

	public InterpreteMsgsUnity() {
	}

	public InterpreteMsgsUnity(ConexionUnity comunicChat) {
		conectorUnity = comunicChat;
	}

	public synchronized void setConectorIrc(ConexionUnity ircConect) {
		conectorUnity = ircConect;
	}

	public synchronized void setItfusoAgenteGestorDialogo(InterfazUsoAgente itfAgteDialogo) {
		this.itfAgenteDialogo = itfAgteDialogo;
	}

	public synchronized void setIdentAgenteGestorDialogo(String idAgteDialogo) {
		this.identAgenteGestorDialogo = idAgteDialogo;
	}

	public synchronized void setIdentConexion(String usnAgte) {
		this._userNameAgente = usnAgte;
	}

	public synchronized void setItfusoRecExtractorSemantico(ItfUsoExtractorSemantico itfRecExtractorSem) {
		this.itfUsoExtractorSem = itfRecExtractorSem;
	}

	public void log(String line) {
		if (_verbose) {
			System.out.println(System.currentTimeMillis() + " " + line);
		}
	}

	public final void handleLine(String line) {
		this.log(line);
		
		// Check for normal messages to the channel.
		if (line.length() > 0) {
			this.onPrivateMessage("Yo", "YoNick", "host", line);
			return;
		}

		return;

	}

	/**
	 * This method is called once the ConexionIrc has successfully connected to
	 * the IRC server. The implementation of this method in the ConexionIrc
	 * abstract class performs no actions and may be overridden as required.
	 */
	protected void onConnect() {

	}

	/**
	 * This method carries out the actions to be performed when the ConexionIrc
	 * gets disconnected. This may happen if the ConexionIrc quits from the
	 * server, or if the connection is unexpectedly lost.
	 * <p>
	 * Disconnection from the IRC server is detected immediately if either we or
	 * the server close the connection normally. If the connection to the server
	 * is lost, but neither we nor the server have explicitly closed the
	 * connection, then it may take a few minutes to detect (this is commonly
	 * referred to as a "ping timeout").
	 * <p>
	 * If you wish to get your IRC bot to automatically rejoin a server after
	 * the connection has been lost, then this is probably the ideal method to
	 * override to implement such functionality.
	 * <p>
	 * The implementation of this method in the ConexionIrc abstract class
	 * performs no actions and may be overridden as required.
	 */
	protected void onDisconnect() {
	}

	/**
	 * This method is called whenever a message is sent to a channel. The
	 * implementation of this method in the ConexionIrc abstract class performs
	 * no actions and may be overridden as required.
	 *
	 * @param channel The channel to which the message was sent.
	 * @param sender The nick of the person who sent the message.
	 * @param login The login of the person who sent the message.
	 * @param hostname The hostname of the person who sent the message.
	 * @param message The actual message sent to the channel.
	 */
	protected void onMessage(String channel, String sender, String login, String hostname, String message) {
	}

	/**
	 * This method is called whenever a private message is sent to the
	 * ConexionIrc. The implementation of this method in the ConexionIrc
	 * abstract class performs no actions and may be overridden as required.
	 *
	 * @param sender The nick of the person who sent the private message.
	 * @param login The login of the person who sent the private message.
	 * @param hostname The hostname of the person who sent the private message.
	 * @param message The actual message.
	 */
	protected void onPrivateMessage(String sender, String login, String hostname, String textoUsuario) {

		// Se envia la información al extrator semantico se traducen las
		// anotaciones y se envia el contenido al agente de dialogo
		// de esta forma el agente recibe mensajes con entidades del modelo de
		// información
		HashSet anotacionesBusquedaPrueba = new HashSet();
		anotacionesBusquedaPrueba.add("Saludo");
		anotacionesBusquedaPrueba.add("Lookup");
		// esto habria que pasarlo como parametro
		if (infoConecxInterlocutor == null)
			infoConecxInterlocutor = new InfoConexionUsuario();
		infoConecxInterlocutor.setuserName(sender);
		infoConecxInterlocutor.sethost(hostname);
		infoConecxInterlocutor.setlogin(login);
		if (itfUsoExtractorSem != null) {
			try {
				anotacionesRelevantes = itfUsoExtractorSem.extraerAnotaciones(anotacionesBusquedaPrueba, textoUsuario);
				String anot = anotacionesRelevantes.toString();
				System.out.println(System.currentTimeMillis() + " " + anot);
				ArrayList infoAenviar = interpretarAnotaciones(sender, textoUsuario, anotacionesRelevantes);
				enviarInfoExtraida(infoAenviar, sender);
			} catch (Exception ex) {
				Logger.getLogger(InterpreteMsgsUnity.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	private void enviarInfoExtraida(ArrayList infoExtraida, String sender) {

		if (itfAgenteDialogo != null) {
			try {
				if (infoExtraida.size() == 0) {
					Notificacion infoAenviar = new Notificacion(sender);
					infoAenviar.setTipoNotificacion(VocabularioGestionCitas.ExtraccionSemanticaNull);
					mensajeAenviar = new MensajeSimple((Object) infoAenviar, sender, identAgenteGestorDialogo);
				} else if (infoExtraida.size() == 1) {
					Object infoAenviar = infoExtraida.get(0);
					mensajeAenviar = new MensajeSimple(infoAenviar, sender, identAgenteGestorDialogo);
				} else {
					mensajeAenviar = new MensajeSimple(infoExtraida, sender, identAgenteGestorDialogo);
					// mensajeAenviar.setColeccionContenido(infoExtraida); //
					// los elementos de la colección se meterán en el motor
				}

				itfAgenteDialogo.aceptaMensaje(mensajeAenviar);
			} catch (RemoteException ex) {
				Logger.getLogger(InterpreteMsgsUnity.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public void intentaDesconectar() {
		// throw new UnsupportedOperationException("Not supported yet."); //To
		// change body of generated methods, choose Tools | Templates.
		if (conectorUnity.isConnected())
			conectorUnity.disconnect();
	}

	private ArrayList interpretarAnotaciones(String interlocutor, String contextoInterpretacion, HashSet anotacionesRelevantes) {
		// recorremos las anotaciones obtenidas y las traducimos a objetos del
		// modelo de información
		ArrayList anotacionesInterpretadas = new ArrayList();
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
