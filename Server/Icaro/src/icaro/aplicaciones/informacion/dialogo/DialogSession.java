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
    private final TableOfResponses insult_ = new TableOfResponses();

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
        
        insult_.add("Es lo más bonito que me ha dicho nunca alguien tan inútil como tú.");
        insult_.add("Eso es justamente lo que tu madre me dijo que pensaba de ti.");
        insult_.add("Estoy sorprendido de tus amplias aptitudes mentales, seguramente funcionas con agentes inteligentes o algo así...");
        insult_.add("Si fueras a un concurso de estúpidos te echarían a gorrazos por abusón.");
        insult_.add("Pues al menos a mí las únicas personas que me rodean no son asistentes sociales pagados por el estado.");
        insult_.add("¿Es que nunca te vas a cansar de tu propia idiotez? Ah, lo siento, no había reparado en tu ineficiencia mental.");
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

    //----------------------------------------------------------------------------------------------------

    public GameEvent generateInsultResponse(UserTextMessage event) {
        return generateResponse(insult_.getRandom());
    }

    //----------------------------------------------------------------------------------------------------

    public GameEvent generateRandomResponse(UserTextMessage event) {
        if (event.containsAnnotation(AnnotationType.NIL)) {
            return generateResponse(random_.getRandom());
        } else {
            return null;
        }
    }
}
