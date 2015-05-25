package icaro.aplicaciones.informacion.dialogo;

import java.util.HashSet;

/**
 * This class is used to enumerate the types of annotations.
 *
 * @author Gorkin
 */
public final class AnnotationType {
    // General:
    public static final String NIL = "[nil]";

    // Grammar:
    public static final String EXCLAMACION = "simbolo_exclamacion";
    public static final String INTERROGATION = "simbolo_interrogacion";
    public static final String INTERROGATIVE = "interrogativo";
    public static final String PREPOSITION = "preposicion";
    public static final String POSITION = "posicion";

    // Verbs:
    public static final String ACTION = "accion";

    // Noun:
    public static final String INSULT = "insulto";
    public static final String NUMBER = "numero";
    public static final String CARDINAL = "cardinal";
    public static final String CHARACTER = "personaje";
    public static final String OBJECT = "objeto";
    public static final String PLACE = "lugar";

    // Phrases:
    public static final String GREETING = "saludo";
    public static final String FAREWELL = "despedida";
    public static final String YOU_ARE = "tu_eres";
    public static final String HELP_01A = "ayuda_01a";
    public static final String HELP_02A = "ayuda_02a";
    public static final String HELP_02B = "ayuda_02b";
    public static final String HELP_02C = "ayuda_02c";
    
    public static HashSet getAllSearchAnnotations() {
        HashSet annotations = new HashSet();

        //TODO: Complete this list...
        //annotations.add();
        //...

        annotations.add(EXCLAMACION);
        annotations.add(INTERROGATION);
        annotations.add(INTERROGATIVE);
        annotations.add(PREPOSITION);
        annotations.add(POSITION);
        
        annotations.add(ACTION);

        annotations.add(INSULT);
        annotations.add(NUMBER);
        annotations.add(CARDINAL);
        annotations.add(CHARACTER);
        annotations.add(OBJECT);
        annotations.add(PLACE);

        annotations.add(GREETING);
        annotations.add(FAREWELL);
        annotations.add(YOU_ARE);
        annotations.add(HELP_01A);
        annotations.add(HELP_02A);
        annotations.add(HELP_02B);
        annotations.add(HELP_02C);

        return annotations;
    }
}
