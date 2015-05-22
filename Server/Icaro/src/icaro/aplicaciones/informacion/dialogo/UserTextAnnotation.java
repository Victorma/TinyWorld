package icaro.aplicaciones.informacion.dialogo;

import gate.Annotation;

/**
 * This class represents an user text annotation inside a message.
 *
 * @author Gorkin
 */
public final class UserTextAnnotation {
    //****************************************************************************************************
    // Fields:
    //****************************************************************************************************
    
    private final String type_;
    private final String text_;
    private boolean enable_;

    //****************************************************************************************************
    // Constructors:
    //****************************************************************************************************
    
    public UserTextAnnotation(String type, String text) {
        type_ = type;
        text_ = text;
        enable_ = true;
    }

    //****************************************************************************************************
    // Properties:
    //****************************************************************************************************

    public String getType() {
        return type_;
    }

    public String getText() {
        return text_;
    }

    public boolean isEnable() {
        return enable_;
    }

    public void setEnable(boolean value) {
        enable_ = value;
    }

    //****************************************************************************************************
    // Methods:
    //****************************************************************************************************

    public static UserTextAnnotation make(String message, Annotation annotation) {
        int start = annotation.getStartNode().getOffset().intValue();
        int end = annotation.getEndNode().getOffset().intValue();
        String submessage = message.substring(start, end);
        return new UserTextAnnotation(annotation.getType(), submessage);
    }
    
    @Override
    public String toString() {
        return "{ type : " + type_ + ", text : " + text_ + ", enable : " + enable_ + "}";
    }
}
