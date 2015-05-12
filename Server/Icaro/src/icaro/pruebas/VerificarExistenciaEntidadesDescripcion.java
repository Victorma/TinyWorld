package icaro.pruebas;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionSincrona;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.ComprobadorRutasEntidades;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.HebraMonitorizacion;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import java.util.Vector;

/*
 * Implementacion por defecto de las acciones que se ejecutaron por parte del gestor de agentes.
 * @author     
 */
public class VerificarExistenciaEntidadesDescripcion extends AccionSincrona {

    /**
     * Almacn de los nombres de los agentes que este gestor debe gestionar
     */
    protected Vector<String> nombresAgentesGestionados = new Vector<String>();

    /**
     * Depuracion para pruebas
     */
    protected static boolean DEBUG = true;
    private InterfazGestion ItfGestionRecTrazas;
    private ItfUsoRecursoTrazas ItfUsoRecTrazas;
    private ItfGestionAgenteReactivo ItfgestGestorInicial = null;
    private ItfUsoConfiguracion configuracionExterna = null;
    private String identGestorNodo = null;
    private String msgUsuario = null;
    private String rutaDescripcionOrganizacion = null;
    private String nombreAgente;
    private ItfUsoRepositorioInterfaces itfUsoRepositorio;

    /**
     * Hebra para la monitorizacion
     */
    private HebraMonitorizacion hebra;
    private Thread currentthread;

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
    public VerificarExistenciaEntidadesDescripcion() {
        super();
        this.nombreAgente = NombresPredefinidos.NOMBRE_INICIADOR;
        try {
            itfUsoRepositorio = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;

            ItfUsoRecTrazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
            ItfGestionRecTrazas = (InterfazGestion) this.itfUsoRepositorio.obtenerInterfaz(
                    NombresPredefinidos.ITF_GESTION + NombresPredefinidos.RECURSO_TRAZAS);
            ItfUsoRecTrazas.visualizarIdentFicheroDescrOrganizacion();
            ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza(this.nombreAgente, tipoEntidad,
                    "Activacion  agente reactivo " + nombreAgente + "\n" + "Fichero Descripcion organizacion : " + NombresPredefinidos.DESCRIPCION_XML_POR_DEFECTO,
                    InfoTraza.NivelTraza.debug));

        } catch (Exception e2) {
            e2.printStackTrace(System.err);
        }

    }

    @Override
    public void ejecutar(Object... params) {
        // Se verifican las rutas donde deben encontrarse las entidades de descripcion:
        // El esquema de descripcion de la organizacion, el fichero de descripcion y el paquete jaxb
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
                this.procesarInput("existenEntidadesDescripcion", null);

            } else {
                this.procesarInput("errorLocalizacionFicherosDescripcion", null);
            }
        } catch (Exception e2) {
            e2.printStackTrace(System.err);
            ItfUsoRecTrazas.aceptaNuevaTraza(new InfoTraza("Iniciador", tipoEntidad,
                    "No se ha podido enviar un evento a si mismo notificando la  validacion de las rutas de las entidades de descripcion . ",
                    InfoTraza.NivelTraza.error));
        }
    }

    public void getInfoObjectInput(Object obj) {
    }

}
