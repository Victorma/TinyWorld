package icaro.aplicaciones.informacion.dialogo;

import java.util.HashSet;

/**
 * This class is used to enumerate the types of annotations.
 *
 * @author Gorkin
 */
public final class AnnotationType {
    public static final String NIL = "[nil]";
    public static final String ACTION = "Accion";
    public static final String CARDINAL = "Cardinal";
    public static final String PLACE = "Lugar";
    public static final String NUMBER = "Numero";
    public static final String OBJECT = "Objeto";
    public static final String CHARACTER = "Personaje";
    public static final String POSITION = "Posicion";
    public static final String GREETING = "Saludo";
    
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
