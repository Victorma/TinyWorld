package icaro.infraestructura.entidadesBasicas.comunicacion;

import java.io.Serializable;
import java.util.ArrayList;

public class MensajeSimple implements Serializable {
    //****************************************************************************************************
    // Constants:
    //****************************************************************************************************

    private static final long serialVersionUID = 1624889937599726865L;

    //****************************************************************************************************
    // Fields:
    //****************************************************************************************************

    private Object content_;
    private Object transmitter_;
    private Object receiver_;

    //****************************************************************************************************
    // Constructors:
    //****************************************************************************************************

    public MensajeSimple() {
        content_ = null;
        transmitter_ = null;
        receiver_ = null;
    }

    public MensajeSimple(Object contenido, Object emisor, Object receptor) {
        content_ = contenido;
        transmitter_ = emisor;
        receiver_ = receptor;
    }

    public MensajeSimple(ArrayList contenido, Object emisor, Object receptor) {
        content_ = contenido;
        transmitter_ = emisor;
        receiver_ = receptor;
    }

    //****************************************************************************************************
    // Properties:
    //****************************************************************************************************

    public Object getContenido() {
        return content_;
    }

    public void setContenido(Object contenido) {
        content_ = contenido;
    }

    public Object getEmisor() {
        return transmitter_;
    }

    public void setEmisor(Object emisor) {
        transmitter_ = emisor;
    }

    public Object getReceptor() {
        return receiver_;
    }

    public void setReceptor(Object receptor) {
        receiver_ = receptor;
    }

    public ArrayList getColeccionContenido() {
        if (isContenidoColection()) {
            return (ArrayList) content_;
        } else {
            return null;
        }
    }

    public void setColeccionContenido(ArrayList colContenido) {
        content_ = colContenido;
    }

    public boolean isContenidoColection() {
        return content_ instanceof ArrayList;
    }

    //****************************************************************************************************
    // Methods:
    //****************************************************************************************************

    public void addObjectAlContenido(Object elemento) {
        if (content_ == null) {
            content_ = new ArrayList();
        }
        if (content_ instanceof ArrayList) {
            ((ArrayList) content_).add(elemento);
        }
    }

    @Override
    public String toString() {
        return "Emisor: " + transmitter_ + ", Receptor: " + receiver_;
    }
}
