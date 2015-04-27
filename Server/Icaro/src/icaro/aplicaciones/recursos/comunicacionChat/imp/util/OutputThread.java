/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.aplicaciones.recursos.comunicacionChat.imp.util;

/**
 *
 * @author FGarijo
 */
public class OutputThread extends Thread {

    /**
     * Constructs an OutputThread for the underlying PircBot. All messages sent to the IRC server
     * are sent by this OutputThread to avoid hammering the server. Messages are sent immediately if
     * possible. If there are multiple messages queued, then there is a one second delay imposed.
     *
     * @param bot The underlying PircBot instance.
     * @param outQueue The Queue from which we will obtain our messages.
     * @param bwriter The BufferedWriter to send lines to the IRC server.
     *
     */
    protected OutputThread(ConexionUnity bot, Queue outQueue) {
        _bot = bot;
        _outQueue = outQueue;
    }

    /**
     * This method starts the Thread consuming from the outgoing message Queue and sending lines to
     * the server.
     *
     */
    @Override
    public void run() {
        boolean running = true;
        while (running) {
            // Small delay to prevent spamming of the channel
            try {
                Thread.sleep(_bot.getMessageDelay());
            } catch (InterruptedException e) {
                // Do nothing.
            }
            String line = (String) _outQueue.next();
            _bot.sendRawLine(line);
        }
    }

    private ConexionUnity _bot = null;
    private Queue _outQueue = null;

}
