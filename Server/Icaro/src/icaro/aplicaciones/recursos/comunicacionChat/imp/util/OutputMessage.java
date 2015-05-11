package icaro.aplicaciones.recursos.comunicacionChat.imp.util;

import icaro.aplicaciones.recursos.comunicacionChat.ClientConfiguration;

public class OutputMessage {

    private String message;
    private ClientConfiguration client;

    public OutputMessage(String message, ClientConfiguration client) {
        super();
        this.message = message;
        this.client = client;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ClientConfiguration getClient() {
        return client;
    }

    public void setClient(ClientConfiguration client) {
        this.client = client;
    }

}
