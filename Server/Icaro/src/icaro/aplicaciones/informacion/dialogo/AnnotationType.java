package icaro.aplicaciones.informacion.dialogo;

import java.util.HashSet;

/**
 * This class is used to enumerate the types of annotations.
 *
 * @author Gorkin
 */
public final class AnnotationType {
    public static final String NIL = "[nil]";
    public static final String ACTION = "accion";
    public static final String CARDINAL = "cardinal";
    public static final String PLACE = "lugar";
    public static final String NUMBER = "numero";
    public static final String OBJECT = "objeto";
    public static final String CHARACTER = "personaje";
    public static final String POSITION = "posicion";
    public static final String GREETING = "saludo";
    
    public static HashSet getAllSearchAnnotations() {
        HashSet annotations = new HashSet();
        annotations.add(ACTION);
        annotations.add(CARDINAL);
        annotations.add(PLACE);
        annotations.add(NUMBER);
        annotations.add(OBJECT);
        annotations.add(CHARACTER);
        annotations.add(POSITION);
        annotations.add(GREETING);
        //TODO: Complete this list...
        //...
        return annotations;
    }
}
