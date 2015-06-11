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

    private boolean greeted_ = false;
    private boolean help01a_ = false;
    private boolean help02a_ = false;
    private boolean help02b_ = false;
    private boolean help02c_ = false;
    private final TableOfResponses random_ = new TableOfResponses();
    private final TableOfResponses insult_ = new TableOfResponses();

    //****************************************************************************************************
    // Constructors:
    //****************************************************************************************************

    public DialogSession() {
        random_.add("No alcanzo a comprender lo que me intentas decir...");
        random_.add("Seguramente es muy interesante lo que intentas transmitirme, pero no te entiendo...");
        random_.add("Mis capacidades de comprensión son algo limitadas, vuélvelo a intentar...");
        random_.add("Quisiera entenderte y no puedo...");
        random_.add("No te entiendo, pero seguro que la respuesta es 42.");
        random_.add("Ojalá me hubieran dotado de inteligencia real para entenderte.");
        
        insult_.add("La violencia es el último recurso del incompetente.");
        insult_.add("¿Por qué no podemos ser amigos?");
        insult_.add("Lávate la boca con jabón.");
        insult_.add("Con ese lenguaje irás directo a la real academia de la lengua.");
        insult_.add("Podría estar horas leyendo esas cosas bonitas que me dices.");
        insult_.add("throw std::exception(\"WE'RE UNDER ATTACK!\");");
    }

    //****************************************************************************************************
    // Properties:
    //****************************************************************************************************
    
    public boolean getGreeted() {
        return greeted_;
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
    
    public void cleanFlags() {
        greeted_ = false;
        help01a_ = false;
        help02a_ = false;
        help02b_ = false;
        help02c_ = false;
    }

    //----------------------------------------------------------------------------------------------------

    public GameEvent generateGreetingResponse(UserTextMessage event) {
        String message;
        if (greeted_) {
            message = "Ya nos hemos saludado antes y tal...";
        } else {
            greeted_ = true;
            message = "Hola, caracola.";
        }
        return generateResponse(message);
    }

    //----------------------------------------------------------------------------------------------------

    public GameEvent generateHelp01aResponse(UserTextMessage event) {
        String message;
        if (help01a_) {
            message = "Dales un objetivo en la vida, tan fácil como eso.";
        } else {
            help01a_ = true;
            message = "Pues en teoría tú ordenas a los bichos estos que hay en el mapa que consigan cosas " +
                    "y a veces hasta lo hacen y todo. Les puedes dar objetivos en sus \"fascinantes\" vidas.";
        }
        return generateResponse(message);
    }

    //----------------------------------------------------------------------------------------------------

    public GameEvent generateHelp02aResponse(UserTextMessage event) {
        help02a_ = true;
        return generateResponse("Pues esta \"maravilla\" de simulación ha sido perpetrada por Víctor, Iván, " +
                "Juan, Ricardo... También han ayudado Cristian, Adrián, Teresa y Paloma...");
    }

    //----------------------------------------------------------------------------------------------------

    public GameEvent generateHelp02bResponse(UserTextMessage event) {
        String message;
        if (help02a_) {
            if (help02b_) {
                message = "Nadie más que recuerde, la verdad...";
            } else {
                help02b_ = true;
                message = "Ah, sí... un tal Gorka, que no me cae demasiado bien...";
            }
        } else {
            message = "¿Por qué me preguntas eso ahora? ¿Por qué no le pides hacer algo a los bichos estos?";
        }
        return generateResponse(message);
    }

    //----------------------------------------------------------------------------------------------------

    public GameEvent generateHelp02cResponse(UserTextMessage event) {
        String message;
        if (help02b_) {
            if (help02c_) {
                message = "Por favor, no perdamos más el tiempo hablando de esa persona.";
            } else {
                help02c_ = true;
                message = "Que me intentó programar y lo hizo de forma cutre y torticera...";
            }
        } else {
            message = "¿A cuento de qué viene eso? ¿Por qué no le pides hacer algo a los bichos estos?";
        }
        return generateResponse(message);
    }

    //----------------------------------------------------------------------------------------------------

    public GameEvent generateValidActionResponse(UserTextMessage event) {
        return generateResponse("Ok, veamos qué se puede hacer...");
    }

    //----------------------------------------------------------------------------------------------------

    public GameEvent generateInvalidActionResponse(UserTextMessage event) {
        return generateResponse("Sí claro, te crees tú que los inútiles estos van a hacer eso...");
    }

    //----------------------------------------------------------------------------------------------------

    public GameEvent generateFarewellResponse(UserTextMessage event) {
        cleanFlags();
        return generateResponse("Largo de aquí entonces. ¡Haz algo útil con tu vida!");
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
