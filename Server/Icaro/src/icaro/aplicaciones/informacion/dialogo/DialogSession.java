package icaro.aplicaciones.informacion.dialogo;

import icaro.aplicaciones.informacion.minions.GameEvent;
import java.util.Random;

/**
 * This class is used to store a dialog session between the user and the system.
 *
 * @author Gorkin
 */
public class DialogSession {
    //****************************************************************************************************
    // Fields:
    //****************************************************************************************************
    
    private final Random rand_ = new Random();

    //****************************************************************************************************
    // Constructors:
    //****************************************************************************************************

    public DialogSession() {
    }

    //****************************************************************************************************
    // Properties:
    //****************************************************************************************************

    //****************************************************************************************************
    // Methods:
    //****************************************************************************************************

    private int getRandom(int max) {
        return rand_.nextInt(max);
    }
    
    public GameEvent generateRandomResponse() {
        //TODO: Check this code...
        GameEvent victim = new GameEvent(GameEvent.RECEIVE_TEXT_EVENT);
        victim.setParameter("message", "¡No me importa una mierda lo que dices!");
        return victim;
        //...
    }

    public GameEvent generateGreetingResponse() {
        //TODO: Check this code...
        GameEvent victim = new GameEvent(GameEvent.RECEIVE_TEXT_EVENT);
        victim.setParameter("message", "¡Hola a tu p#$@ madre!");
        return victim;
        //...
    }
}
