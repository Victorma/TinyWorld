package icaro.gestores.iniciador;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.ComprobadorRutasEntidades;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaGestor;
import icaro.infraestructura.entidadesBasicas.factorias.FactoriaComponenteIcaro;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.HebraMonitorizacion;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.configuracion.imp.ClaseGeneradoraConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccionesSemanticasIniciador extends AccionesSemanticasAgenteReactivo {

    protected Vector<String> nombresAgentesGestionados = new Vector<String>();
    protected static boolean DEBUG = true;
    private InterfazGestion ItfGestionRecTrazas;
    private ItfUsoRecursoTrazas ItfUsoRecTrazas;
    private ItfGestionAgenteReactivo ItfgestGestorInicial = null;
    private ItfUsoConfiguracion configuracionExterna = null;
    private String identGestorNodo = null;
    private String msgUsuario = null;
    private String rutaDescripcionOrganizacion = null;
    private HebraMonitorizacion hebra;
    protected int tiempoParaNuevaMonitorizacion;
    protected final static String SUBTIPO_COGNITIVO = "Cognitivo";
    protected final static String SUBTIPO_REACTIVO = "Reactivo";
    private NombresPredefinidos.TipoEntidad tipoEntidad = NombresPredefinidos.TipoEntidad.Reactivo;

    public AccionesSemanticasIniciador() {
        super();
        this.nombreAgente = NombresPredefinidos.NOMBRE_INICIADOR;
        try {
            itfUsoRepositorio = ClaseGeneradoraRepositorioInterfaces.instance();

            ItfUsoRecTrazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
            ItfGestionRecTrazas = (InterfazGestion) this.itfUsoRepositorio.obtenerInterfaz(
                    NombresPredefinidos.ITF_GESTION + NombresPredefinidos.RECURSO_TRAZAS);
            ItfUsoRecTrazas.visualizarIdentFicheroDescrOrganizacion();
            ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, tipoEntidad,
                    "Activacion  agente reactivo " + nombreAgente + "\n" + "Fichero Descripcion organizacion : " + NombresPredefinidos.DESCRIPCION_XML_POR_DEFECTO,
                    InfoTraza.NivelTraza.debug));

        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void verificarExistenciaEntidadesDescripcion() {
        nombreAgente = this.getNombreAgente();
        ItfUsoRecTrazas.setIdentAgenteAReportar(this.nombreAgente);
        ComprobadorRutasEntidades comprobadorRutas = new ComprobadorRutasEntidades();
        Boolean SeHapodidoLocalizarEsquema = true;
        Boolean SeHapodidoLocalizarFicheroDescripcion = true;
        this.trazas.trazar(nombreAgente, "Verificando la existencia de entidadesDescripcion", NivelTraza.debug);

        if (!comprobadorRutas.existeSchemaDescOrganizacion()) {
            ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                    "No se pudo encontrar fichero que define el esquema para interpretar la descripcion de la Organizacion",
                    InfoTraza.NivelTraza.error));
            SeHapodidoLocalizarEsquema = false;
        }
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
        try {
            trazas.trazar(this.nombreAgente, "Creacion de recursos nucleo Organizacion", NivelTraza.info);
            configuracionExterna = ClaseGeneradoraConfiguracion.instance();
            configuracionExterna.interpretarDescripOrganizacion(rutaDescripcionOrganizacion);
            if (!configuracionExterna.validarDescripOrganizacion()) {
                ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente,
                        "Se produjo un error al interpretar el fichero de descripcion de la organizacion",
                        InfoTraza.NivelTraza.error));

                this.informaraMiAutomata("error_InterpretacionDescripcionOrganizacion");
            } else {
                itfUsoRepositorio.registrarInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.CONFIGURACION, configuracionExterna);

                Boolean ConfiguracionTrazas = false;
                if (NombresPredefinidos.ACTIVACION_PANEL_TRAZAS_DEBUG.startsWith("t")) {
                    ConfiguracionTrazas = true;
                } else {
                    ItfGestionRecTrazas.termina();
                }
                ItfUsoRecTrazas.visualizacionDeTrazas(ConfiguracionTrazas);

                this.informaraMiAutomata("recursosNucleoCreados");
            }
        } catch (IllegalArgumentException ie) {
            System.err.println("Error. La organizacion no se ha compilado correctamente. Compruebe que los ficheros xml de los automatas se encuentren en el classpath.");
            ie.printStackTrace();
            System.exit(1);

        } catch (ExcepcionEnComponente ie) {
            msgUsuario = "Se produjo un error al interpretar el fichero de descripcion de la organizacion  \n"
                    + "La ruta especificada es : " + rutaDescripcionOrganizacion;
            ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador", msgUsuario, InfoTraza.NivelTraza.error));
            this.informaraMiAutomata("error_InterpretacionDescripcionOrganizacion");
        } catch (Exception e) {
            msgUsuario = "Se produjo un error al interpretar el fichero de descripcion de la organizacion  \n"
                    + "La ruta especificada es : " + rutaDescripcionOrganizacion;
            System.err.println(msgUsuario);
            e.printStackTrace();
            ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador", msgUsuario, InfoTraza.NivelTraza.error));
            this.informaraMiAutomata("error_InterpretacionDescripcionOrganizacion");
        }
    }

    public String localizarComportamientoGestorInicial(String identGestor) throws ExcepcionEnComponente, RemoteException {
        String locComportamientoGestor = null;
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
        if (configuracionExterna.despliegueOrgEnUnSoloNodo()) {
            identGestorNodo = NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION;
        } else {
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
            try {
                DescInstanciaGestor desCompGestor = configuracionExterna.getDescInstanciaGestor(identGestorNodo);
                FactoriaComponenteIcaro.instanceAgteReactInpObj().crearAgenteReactivo(desCompGestor);
                ItfgestGestorInicial = (ItfGestionAgenteReactivo) itfUsoRepositorio.obtenerInterfaz(
                        NombresPredefinidos.ITF_GESTION + identGestorNodo);

                this.informaraMiAutomata("GestorInicialCreado");

            } catch (ExcepcionEnComponente ie) {
                ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                        "No se pudo crear el comportamiento del Gestor inicial",
                        InfoTraza.NivelTraza.error));
                this.informaraMiAutomata("error_al_crearGestorInicial");

            } catch (Exception e) {
                System.err.println("Error. No se ha podido crear o registrar  el Gestor Inicial.");
                e.printStackTrace();
            }
        }
    }

    public void crearGestorOrganizacion() throws Exception {
        try {
            FactoriaComponenteIcaro.instanceAgteReactInpObj().crearAgenteReactivo(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION, NombresPredefinidos.COMPORTAMIENTO_PORDEFECTO_GESTOR_ORGANIZACION);
            ItfgestGestorInicial = (ItfGestionAgenteReactivo) itfUsoRepositorio.obtenerInterfaz(
                    NombresPredefinidos.ITF_GESTION + NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION);
            this.informaraMiAutomata("GestorOrganizacionCreado");

        } catch (ExcepcionEnComponente ie) {
            ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                    "No se pudo crear el comportamiento del Gestor de Organizacion",
                    InfoTraza.NivelTraza.error));
            this.informaraMiAutomata("error_al_crearGestorInicial");

        } catch (Exception e) {
            System.err.println("Error. No se ha podido crear o registrar  el Gestor de Organizacion.");
            e.printStackTrace();
        }
    }

    public void arrancarGestorInicialyTerminar() throws Exception {
        logger.debug("GestorIniciador: Arrancando gestor Inicial.");
        try {
            ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                    "Arrancando el Gestor Inicial . ", InfoTraza.NivelTraza.debug));

            if (ItfgestGestorInicial != null) {
                this.ItfgestGestorInicial.arranca();
                this.informaraMiAutomata("GestorInicial_arrancado_ok");

            } else {
                ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                        "La interfaz del GO es nula . ", InfoTraza.NivelTraza.error));
                this.informaraMiAutomata("error_al_arrancarGestorOrganizacion");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            this.informaraMiAutomata("error_al_arrancarGestorInicial");
            ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                    "No se ha podido  arrancar  el Gestor de Organizacion . ", InfoTraza.NivelTraza.error));
        }
    }

    public void decidirTratamientoErrorIrrecuperable() {
        logger.debug("GestorAgentes: Se decide cerrar el sistema ante un error critico irrecuperable.");
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

        try {
            this.informaraMiAutomata("tratamiento_terminar_agentes_y_gestor_agentes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recuperarErrorArranqueAgentes() {
        try {
            trazas.aceptaNuevaTraza(new InfoTraza(
                    nombreAgente,
                    "Fue imposible recuperar el error en el arranque de los agentes.",
                    InfoTraza.NivelTraza.debug));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            this.informaraMiAutomata("imposible_recuperar_arranque");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generarInformeErrorIrrecuperable() {
        logger.error("GestorIniciador: Se ha producido un   error irrecuperable.");
        try {
            trazas.aceptaNuevaTraza(new InfoTraza(
                    "Iniciador",
                    " Iniciador a la espera de terminacion por parte del usuario debido a un error irrecuperable.",
                    InfoTraza.NivelTraza.error));
            this.informaraMiAutomata("informe_generado");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void crearAgente() {
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

    public void monitorizarAgentes() {
        boolean errorEncontrado = false;
        Enumeration enume = nombresAgentesGestionados.elements();
        while (enume.hasMoreElements() && !errorEncontrado) {
            String nombre = (String) enume.nextElement();
            try {
                InterfazGestion itfGes = (InterfazGestion) this.itfUsoRepositorio.obtenerInterfaz(NombresPredefinidos.ITF_GESTION + nombre);
                int monitoriz = itfGes.obtenerEstado();
                if (monitoriz == InterfazGestion.ESTADO_ERRONEO_IRRECUPERABLE || monitoriz == InterfazGestion.ESTADO_ERRONEO_RECUPERABLE || monitoriz == InterfazGestion.ESTADO_TERMINADO || monitoriz == InterfazGestion.ESTADO_TERMINANDO) {
                    errorEncontrado = true;
                    logger.debug("GestorAgentes:Agente " + nombre + " est� en estado err�neo o terminado.");
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
                logger.error("GestorAgentes: No se pudo acceder al repositorio.");
                ex.printStackTrace();
            }
        }

        if (errorEncontrado) {
            try {
                this.informaraMiAutomata("error_al_monitorizar");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                this.informaraMiAutomata("agentes_ok");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void terminarPorPropiaVoluntad() throws Exception, Throwable {
        logger.debug("Iniciador: Terminando gestor de la organizacion y los recursos de la infraestructura.");
        ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                "Terminando el Iniciador.",
                InfoTraza.NivelTraza.debug));
        try {
            ((ItfGestionAgenteReactivo) this.itfUsoRepositorio
                    .obtenerInterfaz(NombresPredefinidos.ITF_GESTION
                            + this.nombreAgente))
                    .termina();
            itfUsoRepositorio.eliminarRegistroInterfaz(NombresPredefinidos.ITF_USO + this.nombreAgente);
            itfUsoRepositorio.eliminarRegistroInterfaz(NombresPredefinidos.ITF_GESTION + this.nombreAgente);
            ItfUsoRecTrazas.setIdentAgenteAReportar(this.identGestorNodo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        logger.debug("Iniciador: Terminando.");
        ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                "Terminando.",
                InfoTraza.NivelTraza.debug));
        if (this.hebra != null) {
            this.hebra.finalizar();
        }
    }

    public void procesarPeticionTerminacion() {
        logger.debug("Iniciador: Procesando la peticion de terminacion");
        ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                "Procesando la peticion de terminacion",
                InfoTraza.NivelTraza.debug));

        ItfUsoRecTrazas.pedirConfirmacionTerminacionAlUsuario();
    }

    public void comenzarTerminacionConfirmada() {
        logger.debug("Iniciador: Terminando recursos...");
        ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador",
                "Comenzando la terminacion de los recursos...",
                InfoTraza.NivelTraza.info));

        try {
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

    public void clasificaError() {
    }
}
