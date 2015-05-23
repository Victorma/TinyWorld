package icaro.aplicaciones.informacion.dialogo;

import icaro.aplicaciones.informacion.minions.GameEvent;

/**
 * This class is used to store a dialog session between the user and the system.
 *
 * @author Gorkin
 */
public class DialogSession {
    //****************************************************************************************************
    // Fields:
    //****************************************************************************************************
    
    private final TableOfResponses random_ = new TableOfResponses();

    //****************************************************************************************************
    // Constructors:
    //****************************************************************************************************

    public DialogSession() {
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

    //****************************************************************************************************
    // Methods:
    //****************************************************************************************************

    public GameEvent generateRandomResponse() {
        GameEvent victim = new GameEvent(GameEvent.RECEIVE_TEXT_EVENT);
        victim.setParameter("message", random_.getRandom());
        return victim;
    }

    public GameEvent generateGreetingResponse() {
        //TODO: Check this code...
        GameEvent victim = new GameEvent(GameEvent.RECEIVE_TEXT_EVENT);
        victim.setParameter("message", "¡Hola a tu p#$@ madre!");
        return victim;
        //...
    }
}
