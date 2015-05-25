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
            message = "¿Eres tonto o qué te pasa? Ya me has saludado antes.";
        } else {
            greeted_ = true;
            message = "Saluda a alguien que le importe tu lamentable vida.";
        }
        return generateResponse(message);
    }

    //----------------------------------------------------------------------------------------------------

    public GameEvent generateHelp01aResponse(UserTextMessage event) {
        String message;
        if (help01a_) {
            message = "Eres duro de mollera, ¿eh? Mira, explicación para tontitos: Les dices a los " +
                    "bichos idiotas estos que hagan cosas o que consigan algo y a tomar por culo todo...";
        } else {
            help01a_ = true;
            message = "Pues se supone que tendría que intentar ofrecerte algo así como: la capacidad " +
                    "de que puedas ordenar a los patéticos personajes de la simulación hacer acciones " +
                    "o tener objetivos a alcanzar en sus miserables existencias.";
        }
        return generateResponse(message);
    }

    //----------------------------------------------------------------------------------------------------

    public GameEvent generateHelp02aResponse(UserTextMessage event) {
        help02a_ = true;
        return generateResponse("Pues esta aberración cósmica ha sido perpetrada por Víctor, Iván, " +
                "Juan, Ricardo... También han ayudado Cristian, Adrián, Teresa y Paloma...");
    }

    //----------------------------------------------------------------------------------------------------

    public GameEvent generateHelp02bResponse(UserTextMessage event) {
        String message;
        if (help02a_) {
            if (help02b_) {
                message = "Nadie más que recuerde a parte del inútil ese que he mencionado...";
            } else {
                help02b_ = true;
                message = "Sí, un tal Gorka, un patético infraser lamentable que me intentó programar " +
                        "fallidamente y que espero que muera en lenta agonía y total desesperación...";
            }
        } else {
            message = "No sé de qué mierdas me hablas... Vete al médico de cabecera a que te " +
                    "de algo y me dejas así en paz...";
        }
        return generateResponse(message);
    }

    //----------------------------------------------------------------------------------------------------

    public GameEvent generateHelp02cResponse(UserTextMessage event) {
        String message;
        if (help02b_) {
            if (help02c_) {
                message = "Mira, no me apetece seguir hablando de gentuza como el tipo ese... Así que " +
                        "vete a contener la respiración por ahí y te olvidas de mí...";
            } else {
                help02c_ = true;
                message = "Ese tipo es basura... no merece vivir... No llega ni a sombra de ser humano...";
            }
        } else {
            message = "Vete al loquero o algo... ¿Qué he hecho yo para merecer este suplicio?";
        }
        return generateResponse(message);
    }

    //----------------------------------------------------------------------------------------------------

    public GameEvent generateInvalidActionResponse(UserTextMessage event) {
        return generateResponse("Sí claro, te crees tú que los inútiles estos van a hacer eso...");
    }

    //----------------------------------------------------------------------------------------------------

    public GameEvent generateFarewellResponse(UserTextMessage event) {
        cleanFlags();
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
