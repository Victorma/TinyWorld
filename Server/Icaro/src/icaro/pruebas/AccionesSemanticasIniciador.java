package icaro.pruebas;

import icaro.gestores.informacionComun.VocabularioGestores;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.ComprobadorRutasEntidades;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaGestor;
import icaro.infraestructura.entidadesBasicas.factorias.FactoriaComponenteIcaro;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;

import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.FactoriaAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.HebraMonitorizacion;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

import icaro.infraestructura.recursosOrganizacion.configuracion.imp.ClaseGeneradoraConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.ClaseGeneradoraRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Implementacion por defecto de las acciones que se ejecutar�n por parte del gestor de agentes.
 * @author     
 */
public class AccionesSemanticasIniciador extends AccionesSemanticasAgenteReactivo {

    /**
     * Almacn de los nombres de los agentes que este gestor debe gestionar
     */
    protected Vector<String> nombresAgentesGestionados = new Vector<String>();
    /**
     * Depuracion para pruebas
     */
    protected static boolean DEBUG = true;
    private InterfazGestion itfGestionRecTrazas;
    private ItfUsoRecursoTrazas ItfUsoRecTrazas;
    private ItfGestionAgenteReactivo itfgestGestorInicial = null;
    private ItfUsoConfiguracion configuracionExterna = null;
    private String identGestorNodo = null;
    private String msgUsuario = null;
    private String rutaDescripcionOrganizacion = null;
    private String nombreAgente = null;
    private ItfUsoRepositorioInterfaces itfUsoRepositorio;

    /**
     * Hebra para la monitorizacion
     */
    private HebraMonitorizacion hebra;
    
    /**
     * Tiempo de monitorizacion
     */
    protected int tiempoParaNuevaMonitorizacion;
    protected final static String SUBTIPO_COGNITIVO = "Cognitivo";
    protected final static String SUBTIPO_REACTIVO = "Reactivo";
    private NombresPredefinidos.TipoEntidad tipoEntidad = NombresPredefinidos.TipoEntidad.Reactivo;

    /**
     * Constructor
     */
    public AccionesSemanticasIniciador() {
        super();
        this.nombreAgente = NombresPredefinidos.NOMBRE_INICIADOR;
        this.ItfUsoRecTrazas = trazas;
        try {
            itfUsoRepositorio = ClaseGeneradoraRepositorioInterfaces.instance();
            trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
            itfGestionRecTrazas = (InterfazGestion) this.itfUsoRepositorio.obtenerInterfaz(
                    NombresPredefinidos.ITF_GESTION + NombresPredefinidos.RECURSO_TRAZAS);
            if (trazas == null) {
                trazas = ClaseGeneradoraRecursoTrazas.instance();
                ItfUsoRecTrazas = trazas;
                itfUsoRepositorio.registrarInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS,
                        trazas);
                itfUsoRepositorio.registrarInterfaz(
                        NombresPredefinidos.ITF_GESTION + NombresPredefinidos.RECURSO_TRAZAS,
                        trazas);
                // Guardamos el recurso de trazas y el repositorio de Itfs en la clase de nombres predefinidos
                NombresPredefinidos.RECURSO_TRAZAS_OBJ = trazas;
                NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ = itfUsoRepositorio;
            }
            trazas.visualizarIdentFicheroDescrOrganizacion();
            trazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, tipoEntidad,
                    "Activacion  agente reactivo " + nombreAgente + "\n" + "Fichero Descripcion organizacion : " + NombresPredefinidos.DESCRIPCION_XML_POR_DEFECTO,
                    InfoTraza.NivelTraza.debug));

        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void verificarExistenciaEntidadesDescripcion() {
        // Se verifican las rutas donde deben encontrarse las entidades de descripcion:
        // El esquema de descripcion de la organizacion, el fichero de descripcion y el paquete jaxb
        ComprobadorRutasEntidades comprobadorRutas = new ComprobadorRutasEntidades();
        Boolean SeHapodidoLocalizarEsquema = true;
        Boolean SeHapodidoLocalizarFicheroDescripcion = true;

        rutaDescripcionOrganizacion = comprobadorRutas.buscarDescOrganizacion(NombresPredefinidos.DESCRIPCION_XML_POR_DEFECTO);
        if (rutaDescripcionOrganizacion == null) {
            ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                    "No se pudo encontrar fichero de descripcion de la Organizacion",
                    InfoTraza.NivelTraza.error));
            SeHapodidoLocalizarFicheroDescripcion = false;
        }
        try {
            if (SeHapodidoLocalizarEsquema && SeHapodidoLocalizarFicheroDescripcion) {
                this.informaraMiAutomata("existenEntidadesDescripcion");
            } else {
                this.informaraMiAutomata("errorLocalizacionFicherosDescripcion");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador", tipoEntidad,
                    "No se ha enviar un evento a si mismo notificando la  validacion de las rutas de las entidades de descripcion . ", InfoTraza.NivelTraza.error));
        }
    }

    public void crearRecursosNucleoOrganizacion() throws Exception {
        // Intento crear los recursos en orden pero si se producen errores se capturan, se visualizan y
        // se pude intentar dialogar con el usuario para que los corrija
        try {
            // Se crea el recurso de configuración y posteriormente se debería dar orden al recurso
            // de trazas para pintar el proceso.
            trazas.trazar(this.nombreAgente, "Creacion de recursos nucleo Organizacion", NivelTraza.info);
            configuracionExterna = ClaseGeneradoraConfiguracion.instance();
            // Se crea una configuracion con el fichero de descripcion de la organizacion
            rutaDescripcionOrganizacion = NombresPredefinidos.RUTA_DESCRIPCIONES + NombresPredefinidos.DESCRIPCION_XML_POR_DEFECTO + ".xml";
            configuracionExterna.interpretarDescripOrganizacion(rutaDescripcionOrganizacion);
            if (!configuracionExterna.validarDescripOrganizacion()) {
                ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
                        "Se produjo un error al interpretar el fichero de descripcion de la organizacion",
                        InfoTraza.NivelTraza.error));
            } else {
                // registro la configuracion
                itfUsoRepositorio.registrarInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.CONFIGURACION, configuracionExterna);
                // Ahora que la configuracion es correcta , interpreto las propiedades globales
                // y pongo la configuracion de trazas definida por el usuario
                Boolean ConfiguracionTrazas = false;
                if (NombresPredefinidos.ACTIVACION_PANEL_TRAZAS_DEBUG.startsWith("t")) {
                    ConfiguracionTrazas = true;
                } else {
                    this.informaraMiAutomata("recursosNucleoCreados");
                }
            }
            this.informaraMiAutomata("recursosNucleoCreados");
        } catch (IllegalArgumentException ie) {
            System.err.println("Error. La organizacion no se ha compilado correctamente. Compruebe que los ficheros xml de los automatas se encuentren en el classpath.");
            ie.printStackTrace();
            System.exit(1);

        } catch (ExcepcionEnComponente ie) {
            msgUsuario = "Se produjo un error tras informar a mi automata de que todos los recurso del nucleo estaban creados  \n"
                    + "La ruta especificada es : " + rutaDescripcionOrganizacion;
            ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador", msgUsuario, InfoTraza.NivelTraza.error));
        } catch (Exception e) {
            msgUsuario = "Se produjo un error tras informar a mi automata de que todos los recurso del nucleo estaban creados  \n"
                    + "La ruta especificada es : " + rutaDescripcionOrganizacion;
            System.err.println(msgUsuario);
            e.printStackTrace();
            ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador", msgUsuario, InfoTraza.NivelTraza.error));
        }
    }

    public String localizarComportamientoGestorInicial(String identGestor) throws ExcepcionEnComponente, RemoteException {

        String locComportamientoGestor = null;
        // Se obtiene el gestor inicial de la descripción de la organizacion. Puede ser un gestor de organizacion o un gestor de nodo
        try {
            DescInstanciaGestor desCompGestor = configuracionExterna.getDescInstanciaGestor(identGestor);
            locComportamientoGestor = desCompGestor.getDescComportamiento().getLocalizacionComportamiento();
            if (identGestor.startsWith(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION)) {
                if (locComportamientoGestor.isEmpty()) {
                    locComportamientoGestor = NombresPredefinidos.PAQUETE_GESTOR_ORGANIZACION;
                }

            } else if (identGestorNodo.startsWith(NombresPredefinidos.NOMBRE_GESTOR_NODO)) {
                if (locComportamientoGestor.isEmpty()) {
                    locComportamientoGestor = NombresPredefinidos.COMPORTAMIENTO_PORDEFECTO_GESTOR_NODO;
                }
            } else {
                // El gestor inicial debe ser un gestor de organización o un gestor de nodo y no se ha definido ninguno
                ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                        "No se pudo crear el comportamiento del Gestor Inicial porque no hay un Gestor de Organizacion o un Gestor de Nodo definido."
                        + " Revisar el fichero de Descripcion de la  Organizacion",
                        InfoTraza.NivelTraza.error));

            }

        } catch (ExcepcionEnComponente ex) {
            Logger.getLogger(AccionesSemanticasIniciador.class.getName()).log(Level.SEVERE, null, ex);
            ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                    "No se pudo crear el comportamiento del Gestor Inicial porque no se pudo obtener el comportamiento del  Gestor de Organizacion o del Gestor de Nodo definido."
                    + " Revisar el fichero de Descripcion de la  Organizacion",
                    InfoTraza.NivelTraza.error));
        }
        return locComportamientoGestor;
    }

    public void crearGestorInicial() throws Exception {

        String locComportamientoGestor = null;
        // Se obtiene el gestor inicial de la descripción de la organizacion. Puede ser un gestor de organizacion o un gestor de nodo
        if (configuracionExterna.despliegueOrgEnUnSoloNodo()) {
            identGestorNodo = NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION;
        } else {
            // Si la organizacion es distribuida el iniciador mira si esta en el nodo del GO o en otro nodo
            String identNodoGO = configuracionExterna.getHostComponente(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
            String thisHost = InetAddress.getLocalHost().getHostName();
            if (thisHost.equalsIgnoreCase(identNodoGO)) // Esta en el nodo del GO se crea un Gestor de Organizacion
            {
                identGestorNodo = NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION;
            } else {
                identGestorNodo = NombresPredefinidos.NOMBRE_GESTOR_NODO; // Se crea un Gestor de Nodo
            }
        }

        locComportamientoGestor = this.localizarComportamientoGestorInicial(identGestorNodo);

        if (!locComportamientoGestor.isEmpty()) {
            // Se crea el gestor y se registra en el registro RMI en el caso en que  la organizacion sea distribuida
            try {
                DescInstanciaGestor desCompGestor = configuracionExterna.getDescInstanciaGestor(identGestorNodo);
                FactoriaComponenteIcaro.instanceAgteReactInpObj().crearAgenteReactivo(desCompGestor);
                itfgestGestorInicial = (ItfGestionAgenteReactivo) itfUsoRepositorio.obtenerInterfaz(
                        NombresPredefinidos.ITF_GESTION + identGestorNodo);
                this.informaraMiAutomata("GestorInicialCreado");

            } catch (ExcepcionEnComponente ie) {
                ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                        "No se pudo crear el comportamiento del Gestor inicial",
                        InfoTraza.NivelTraza.error));
            } catch (Exception e) {
                System.err.println("Error. No se ha podido crear o registrar  el Gestor Inicial.");
                e.printStackTrace();
            }
        }
    }

    public void crearGestorOrganizacion() throws Exception {
        try {
            FactoriaAgenteReactivo.instancia().crearAgenteReactivo(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION, NombresPredefinidos.COMPORTAMIENTO_PORDEFECTO_GESTOR_ORGANIZACION);
            itfgestGestorInicial = (ItfGestionAgenteReactivo) itfUsoRepositorio.obtenerInterfaz(
                    NombresPredefinidos.ITF_GESTION + NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
            this.informaraMiAutomata("GestorOrganizacionCreado");

        } catch (ExcepcionEnComponente ie) {
            ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                    "No se pudo crear el comportamiento del Gestor de Organizacion",
                    InfoTraza.NivelTraza.error));

        } catch (Exception e) {
            System.err.println("Error. No se ha podido crear o registrar  el Gestor de Organizacion.");
            e.printStackTrace();
        }
    }

    /**
     * arranca los agentes que se especifiquen en la config.
     */
    public void arrancarGestorInicialyTerminar() throws Exception {
        try {
            trazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                    "Arrancando el Gestor Inicial . ", InfoTraza.NivelTraza.debug));

            if (itfgestGestorInicial != null) {
                // arranca  el Gestor de  Organizacion y Termina el iniciador
                this.itfgestGestorInicial.arranca();
                this.procesarInput(new InformeArranqueGestor(identGestorNodo, VocabularioGestores.ResultadoArranqueGestorOK), null);
            } else {
                ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                        "La interfaz del GO es nula . ", InfoTraza.NivelTraza.error));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                    "No se ha podido  arrancar  el Gestor de Organizacion . ", InfoTraza.NivelTraza.error));

        }

    }

    public void analizarInformeArranque(InformeArranqueGestor informeRecibido) {
        if (informeRecibido.getContenidoInforme().equals(VocabularioGestores.ResultadoArranqueGestorOK)) {
            try {
                this.terminarPorPropiaVoluntad();
            } catch (Exception ex) {
                Logger.getLogger(AccionesSemanticasIniciador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Throwable ex) {
                Logger.getLogger(AccionesSemanticasIniciador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void analizarInformeArranque(InformeArranqueGestor informeRecibido, String par1, String par2) {
        if (informeRecibido.getContenidoInforme().equals(VocabularioGestores.ResultadoArranqueGestorOK)) {
            try {
                this.terminarPorPropiaVoluntad();
            } catch (Exception ex) {
                Logger.getLogger(AccionesSemanticasIniciador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Throwable ex) {
                Logger.getLogger(AccionesSemanticasIniciador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Decide que hacer en caso de fallos en los agentes.
     */
    public void decidirTratamientoErrorIrrecuperable() {
        // el tratamiento ser� siempre cerrar todo el chiringuito
        try {

            trazas.aceptaNuevaTraza(new InfoTraza(
                    this.nombreAgente,
                    "Se decide cerrar el sistema ante un error critico irrecuperable.",
                    InfoTraza.NivelTraza.debug));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            trazas.aceptaNuevaTraza(new InfoTraza(
                    nombreAgente,
                    "Terminado proceso de arranque automatico de agentes.",
                    InfoTraza.NivelTraza.debug));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /**
     * intenta arrancar los agentes que especifique la config y hayan dado problemas.
     */
    public void recuperarErrorArranqueAgentes() {
        // por defecto no se implementan pol�ticas de recuperaci�n
        try {
            trazas.aceptaNuevaTraza(new InfoTraza(
                    nombreAgente,
                    "Fue imposible recuperar el error en el arranque de los agentes.",
                    InfoTraza.NivelTraza.debug));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /**
     * Elabora un informe del estado en el que se encuentran los agentes y lo env�a al sistema de
     * trazas.
     */
    public void generarInformeErrorIrrecuperable() {
        // Producimos traza de un error
        try {
            trazas.aceptaNuevaTraza(new InfoTraza(
                    "Iniciador",
                    " Iniciador a la espera de terminacion por parte del usuario debido a un error irrecuperable.",
                    InfoTraza.NivelTraza.error));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /**
     * Crea y arranca un agente. Es necesario pasar las caracter�sticas del agente a crear por
     * par�metro.
     */
    public void crearAgente() {
        // esto hay que recuperarlo de los parametros
        try {

            trazas.aceptaNuevaTraza(new InfoTraza(
                    nombreAgente,
                    "crearAgente():Este metodo no esta implementado",
                    InfoTraza.NivelTraza.debug));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        throw new UnsupportedOperationException();
    }

    /**
     * Monitoriza secuencialmente todos los agentes activos que est�n definidos como necesarios en
     * la configuración.
     */
    public void monitorizarAgentes() {
        boolean errorEncontrado = false;
        // recuperar todos los interfaces de gesti�n del repositorio que estamos gestionando
        Enumeration enume = nombresAgentesGestionados.elements();
        while (enume.hasMoreElements() && !errorEncontrado) {
            String nombre = (String) enume.nextElement();
            try {
                InterfazGestion itfGes = (InterfazGestion) this.itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_GESTION + nombre);
                int monitoriz = itfGes.obtenerEstado();
                if (monitoriz == InterfazGestion.ESTADO_ERRONEO_IRRECUPERABLE || monitoriz == InterfazGestion.ESTADO_ERRONEO_RECUPERABLE || monitoriz == InterfazGestion.ESTADO_TERMINADO || monitoriz == InterfazGestion.ESTADO_TERMINANDO) {
                    errorEncontrado = true;
                    try {
                        ItfUsoRecursoTrazas trazas = (ItfUsoRecursoTrazas) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
                                NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS);
                        trazas.aceptaNuevaTraza(new InfoTraza(
                                this.nombreAgente,
                                "Agente " + nombre + " esta en estado erroneo o terminado.",
                                InfoTraza.NivelTraza.debug));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                errorEncontrado = true;
                ex.printStackTrace();
            }
        }
    }

    /**
     * destruye los recursos que se crearon a lo largo del ciclo de vida de este agente.
     */
    public void terminarPorPropiaVoluntad() throws Exception, Throwable {
        // termina el gestor.
        // puede esperarse a que terminen los agentes
        ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                "Terminando el Iniciador.",
                InfoTraza.NivelTraza.debug));
        try {
			// se obtiene la propia interfaz de gestion para terminar
            ((ItfGestionAgenteReactivo) this.itfUsoRepositorio
                    .obtenerInterfaz(NombresPredefinidos.ITF_GESTION
                            + this.nombreAgente))
                    .termina();
            // quitamos las interfaces del repositorio de interfaces
            itfUsoRepositorio.eliminarRegistroInterfaz(NombresPredefinidos.ITF_USO + this.nombreAgente);
            itfUsoRepositorio.eliminarRegistroInterfaz(NombresPredefinidos.ITF_GESTION + this.nombreAgente);
            // definimos el gestor al que debe enviar los eventos  el RC
            ItfUsoRecTrazas.setIdentAgenteAReportar(this.identGestorNodo);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                "Terminando.",
                InfoTraza.NivelTraza.debug));
        if (this.hebra != null) {
            this.hebra.finalizar();
        }
    }

    public void procesarPeticionTerminacion() {
        ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                "Procesando la peticion de terminacion",
                InfoTraza.NivelTraza.debug));

        ItfUsoRecTrazas.pedirConfirmacionTerminacionAlUsuario();
    }

    public void comenzarTerminacionConfirmada() {
        ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                "Comenzando la terminacion de los recursos...",
                InfoTraza.NivelTraza.info));

        try {
            // y a continuacion se termina el gestor
            // terminarPorPropiaVoluntad();if (this.hebra != null)
            if (this.hebra != null) {
                this.hebra.finalizar();
            }
            System.exit(0);
        } catch (Exception ex) {
            Logger.getLogger(AccionesSemanticasIniciador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(AccionesSemanticasIniciador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void clasificaError() {
    }

    @Override
    public void ejecutar(Object... params) {
    }

    public void getInfoObjectInput(Object obj) {
    }
}
