package icaro.aplicaciones.recursos.comunicacionChat.imp.util;

import dasi.util.LogUtil;
import icaro.aplicaciones.recursos.comunicacionChat.imp.InterpreteMsgsUnity;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.StringTokenizer;

public class InputThread extends Thread {

    /**
     * The InputThread reads lines from the IRC server and allows the PircBot to handle them.
     *
     * @param bot An instance of the underlying PircBot.
     * @param breader The BufferedReader that reads lines from the server.
     * @param bwriter
     */
    private InterpreteMsgsUnity _interpreteMensajes = null;
    private DatagramSocket _socket = null;

    public static final int MAX_LINE_LENGTH = 510;

    protected InputThread(InterpreteMsgsUnity intrpmsg, DatagramSocket socket) {
        _interpreteMensajes = intrpmsg;
        _socket = socket;
    }

    /**
     * Sends a raw line to the IRC server as soon as possible, bypassing the outgoing message queue.
     *
     * @param line The raw line to send to the IRC server.
     */
    public void sendOutputMessage(OutputMessage outputMessage) {
        try {

            ByteBuffer bytes = Charset.forName("UTF-8").encode(outputMessage.getMessage());

            DatagramPacket dato = new DatagramPacket(
                    bytes.array(), // El array de bytes
                    bytes.capacity(), // Su longitud
                    outputMessage.getClient().getAddress(), // Destinatario
                    outputMessage.getClient().getPort());   // Puerto del destinatario
            _socket.send(dato);
            LogUtil.logWithMs(">>>" + outputMessage.getMessage());
        } catch (Exception e) {
            // Silent response - just lose the line.
        }
    }

    private String receiveData(DatagramPacket dt) throws IOException {
        _socket.receive(dt);
        CharBuffer cb = Charset.forName("UTF-8").decode(ByteBuffer.wrap(dt.getData()));
        String s = cb.toString();
        dt.setData(new byte[1024]);
        return s;
    }

    /**
     * Called to start this Thread reading lines from the IRC server. When a line is read, this method calls the
     * handleLine method in the PircBot, which may subsequently call an 'onXxx' method in the PircBot subclass. If any
     * subclass of Throwable (i.e. any Exception or Error) is thrown by your method, then this method will print the
     * stack trace to the standard output. It is probable that the PircBot may still be functioning normally after such
     * a problem, but the existance of any uncaught exceptions in your code is something you should really fix.
     *
     */
    @Override
    public void run() {
        boolean running = true;
        try {
            while (running) {
                try {
                    String line = null;
                    DatagramPacket data = new DatagramPacket(new byte[1024], 1024);

                    while ((line = receiveData(data)) != null) {
                        try {
                            LogUtil.logWithMs("<<<" + line);
                            _interpreteMensajes.handleLine(data.getAddress(), data.getPort(), line);
                        } catch (Throwable t) {
                            // Stick the whole stack trace into a String so we can output it nicely.
                            StringWriter sw = new StringWriter();
                            PrintWriter pw = new PrintWriter(sw);
                            t.printStackTrace(pw);
                            pw.flush();
                            StringTokenizer tokenizer = new StringTokenizer(sw.toString(), "\r\n");
                            synchronized (_interpreteMensajes) {
                                LogUtil.logWithMs("### Your implementation  is faulty and you have");
                                LogUtil.logWithMs("### allowed an uncaught Exception or Error to propagate in your");
                                LogUtil.logWithMs("### code. It may be possible for PircBot to continue operating");
                                LogUtil.logWithMs("### normally. Here is the stack trace that was produced: -");
                                LogUtil.logWithMs("### ");
                                while (tokenizer.hasMoreTokens()) {
                                    LogUtil.logWithMs("### " + tokenizer.nextToken());
                                }
                            }
                        }
                    }
                    if (line == null) {
                        // The server must have disconnected us.
                        running = false;
                    }
                } catch (InterruptedIOException iioe) {
                    // This will happen if we haven't received anything from the server for a while.
                    // So we shall send it a ping to check that we are still connected.
                    //this.sendRawLine("PING " + (System.currentTimeMillis()/1000));
                    // Now we go back to listening for stuff from the server...
                }
            }
        } catch (Exception e) {
        }

        // If we reach this point, then we must have disconnected.
        synchronized (_interpreteMensajes) {
            // But only disconnect if something else hasn't caused us to do so already!
            _interpreteMensajes.intentaDesconectar();
        }

    }

}
