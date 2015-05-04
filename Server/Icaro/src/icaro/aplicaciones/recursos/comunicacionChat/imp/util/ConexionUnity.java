package icaro.aplicaciones.recursos.comunicacionChat.imp.util;

import icaro.aplicaciones.recursos.comunicacionChat.imp.InterpreteMsgsUnity;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ConexionUnity {

	private InterpreteMsgsUnity interpreteMsg;
	private InputThread input;
	private OutputThread output;
	private DatagramSocket socket;
	private Queue outQueue;
	private boolean connected = false;
	
	public void setInterpreteMsgs(InterpreteMsgsUnity interpreteMsgUnity) {
		interpreteMsg = interpreteMsgUnity;
	}
	
	public void connect(String url, String port){
		
		try {
			socket = new DatagramSocket(
					Integer.parseInt(port),
					InetAddress.getByName(url));
			
			// Now start the InputThread to read all other lines from the server.
	        // pasamos como parametro el interprete de mensajes para que procese las lineas
			input = new InputThread(interpreteMsg, socket);
			input.start();
	        
	        // Now start the outputThread that will be used to send all messages.
	        if (output == null) {
	        	output = new OutputThread(this, outQueue = new Queue());
	        	output.start();
	        }
	        
	        connected = true;
	        
		} catch (SocketException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isConnected() {
		return connected;
	}

	public void disconnect() {
		socket.close();
	}

	public long getMessageDelay() {
		return 1;
	}

	public void sendOutputMessage(OutputMessage om) {
		input.sendOutputMessage(om);
	}

    /**
     * Sends a message to a channel or a private message to a user.  These
     * messages are added to the outgoing message queue and sent at the
     * earliest possible opportunity.
     *
     * @param target The name of the channel or user nick to send to.
     * @param message The message to send.
     */
    public final void sendMessage(OutputMessage message) {
    	outQueue.add(message);
    }
	
}
