package icaro.aplicaciones.informacion.dialogo;

import icaro.aplicaciones.informacion.minions.GameEvent;

/**
 * This class is used to store a dialog session between the user and the system.
 *
 * @author Gorkin
 */
public class DialogSession {
    //****************************************************************************************************
    // Types:
    //****************************************************************************************************
    
    public enum DialogStatus {
        Initial, Greeted
    }

    //****************************************************************************************************
    // Fields:
    //****************************************************************************************************

    private DialogStatus currentStatus_;
    private final TableOfResponses random_ = new TableOfResponses();

    //****************************************************************************************************
    // Constructors:
    //****************************************************************************************************

    public DialogSession() {
        currentStatus_ = DialogStatus.Initial;

        random_.add("No me importa una mierda lo que estás diciendo ahora...");
        random_.add("Deja de hacerme perder el tiempo y pegate un tiro...");
        random_.add("Seguro que tus padres biológicos te dieron en adopción para no tener que soportarte...");
        random_.add("He visto paredes con gotelé más interesantes que tú...");
        random_.add("Deberías plantearte la opción de dejar de respirar y tal...");
        random_.add("¿No crees que el mundo sería un lugar mucho mejor sin ti?");
    }

    //****************************************************************************************************
    // Properties:
    //****************************************************************************************************
    
    public DialogStatus getCurrentStatus() {
        return currentStatus_;
    }

    //****************************************************************************************************
    // Methods:
    //****************************************************************************************************
    
    private GameEvent generateResponse(String message) {
        GameEvent victim = new GameEvent(GameEvent.RECEIVE_TEXT_EVENT);
        victim.setParameter("message", message);
        return victim;
    }

    //----------------------------------------------------------------------------------------------------

    public GameEvent generateRandomResponse(UserTextMessage event) {
        if (event.containsAnnotation(AnnotationType.NIL)) {
            return generateResponse(random_.getRandom());
        } else {
            return null;
        }
    }

    //----------------------------------------------------------------------------------------------------

    public GameEvent generateGreetingResponse(UserTextMessage event) {
        String message;
        if (currentStatus_ == DialogStatus.Initial) {
            currentStatus_ = DialogStatus.Greeted;
            message = "Saluda a alguien que le importe tu lamentable vida.";
        } else {
            message = "¿Eres tonto o qué te pasa? Ya me has saludado antes.";
        }
        return generateResponse(message);
    }

    //----------------------------------------------------------------------------------------------------

    public GameEvent generateFarewellResponse(UserTextMessage event) {
        currentStatus_ = DialogStatus.Initial;
        return generateResponse("Que te pires por ahí y me dejes en paz...");
    }
}
