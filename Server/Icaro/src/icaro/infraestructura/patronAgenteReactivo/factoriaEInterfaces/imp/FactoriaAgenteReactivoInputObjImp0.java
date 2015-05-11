package icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp;

import icaro.infraestructura.entidadesBasicas.ConfiguracionTrazas;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestionPercepcion;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.ItfUsoAutomataEFsinAcciones;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFsinAcciones.imp.ClaseGeneradoraAutomataEFsinAcciones;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.DescComportamientoAgente;
import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb.RolAgente;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.FactoriaAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.FactoriaPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.PercepcionAbstracto;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ProcesadorInfoReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.imp.FactoriaControlAgteReactivoInputObjImp0;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import java.io.InputStream;
import java.rmi.RemoteException;

import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 * Produce instancias del patron
 *
 * @F Garijo
 * @created 20 Mayo 2010
 */
public class FactoriaAgenteReactivoInputObjImp0 extends FactoriaAgenteReactivo {

    /*
     * Crea una instancia del patron que crea un agente reactivo con el
     * control del automata Input Objetos
     * del agente.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Control del agente
     *
     * @uml.property name="control"
     * @uml.associationEnd
     */
    protected ProcesadorInfoReactivoAbstracto controlAgteReactivo;

//    protected ItfUsoRecursoTrazas trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
    private String nombreInstanciaAgente;
    protected AgenteReactivoImp2 agente;
    private NombresPredefinidos.TipoEntidad tipoEntidad = NombresPredefinidos.TipoEntidad.Reactivo;

//    protected ItfUsoRepositorioInterfaces repositorioIntfaces= NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
    /**
     * @uml.property name="itfGesControl"
     * @uml.associationEnd
     */
    //  protected InterfazGestion itfGesControl; //Control
    /**
     * Percepcion del agente
     *
     * @uml.property name="itfConsumidorPercepcion"
     * @uml.associationEnd
     */
    protected InterfazGestionPercepcion itfGestionPercepcion;
    /**
     * @uml.property name="itfProductorPercepcion"
     * @uml.associationEnd
     */
    protected ItfProductorPercepcion itfProductorPercepcion;
    /**
     * Nombre del agente a efectos de traza
     *
     * @uml.property name="nombre"
     */
    protected String nombre;
    /**
     * Estado del agente reactivo
     *
     * @uml.property name="estado"
     */
//    protected int estado = InterfazGestion.ESTADO_OTRO;
    /**
     * Acciones sem�nticas del agente reactivo
     *
     * @uml.property name="accionesSemanticas"
     * @uml.associationEnd
     */
//    protected AccionesSemanticasImp accionesSemanticas;
    /**
     * @uml.property name="dEBUG"
     */
    private boolean DEBUG = false;
    /**
     * Conocimiento del agente reactivo
     *
     * @uml.property name="itfUsoGestorAReportar"
     * @uml.associationEnd
     */
    protected ItfUsoAgenteReactivo itfUsoGestorAReportar;
    /**
     * @uml.property name="logger"
     * @uml.associationEnd multiplicity="(1 1)"
     */
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
    /**
     * @uml.property name="trazas"
     * @uml.associationEnd
     */

    private static FactoriaAgenteReactivoInputObjImp0 instance;

    public static FactoriaAgenteReactivoInputObjImp0 instance() throws ExcepcionEnComponente, RemoteException {
        try {
            if (instance == null) {
                instance = new FactoriaAgenteReactivoInputObjImp0();
            }
            return instance;
        } catch (Exception e) {
//            logger.fatal("\n\nError al comprobar los comportamientos de los gestores, agentes y recursos descritos en el fichero de descripcion del XML " + "FactoriaAgenteReactivoInputObjImp0 ");
            return null;
//	throw new ExcepcionEnComponente("\n\nError al comprobar los comportamientos de los gestores, agentes y recursos descritos en el fichero de descripcion del XML " );
        }
    }

    @Override
    public synchronized void crearAgenteReactivo(DescInstanciaAgente descInstanciaAgente) throws ExcepcionEnComponente {

        // Paso 1 Se obtienen los objetos de la descripcion
        String nombreInstanciaAgente = descInstanciaAgente.getId();
        DescComportamientoAgente descagente = descInstanciaAgente.getDescComportamiento();
        String nombreComportamiento = descagente.getNombreComportamiento();
        boolean esGestor = descagente.getRol() == RolAgente.GESTOR;
        this.recursoTrazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        // Paso 2 se obtienen los parametros necesarios para crear los componentes internos
        if (recursoTrazas != null) {
            recursoTrazas.aceptaNuevaTraza(new InfoTraza(nombreInstanciaAgente, tipoEntidad,
                    nombreInstanciaAgente + ":Creando  el Agente ...", NivelTraza.debug));
        }
//			AccionesSemanticasAgenteReactivo accionesSemanticasEspecificas = obtenerAcciones(descInstanciaAgente);
        // Obtener la ruta de las acciones dado que la factoria del automata ya lo valida al construir la tabla
        //		String rutaTabla = obtenerRutaTablaTransiciones(descInstanciaAgente);
        String rutaTabla = descInstanciaAgente.getDescComportamiento().getLocalizacionFicheroAutomata();
        String rutaAcciones = descInstanciaAgente.getDescComportamiento().getLocalizacionComportamiento();
        if ((rutaTabla != null)) {
            try {
// Se crea el objeto que implementa las interfaces de  uso y de gestion
                this.agente = new AgenteReactivoImp2(nombreInstanciaAgente);
// Se crea el control del agente por medio de su factoria
                //	ProcesadorEventosAbstracto control = FactoriaControlAgteReactivo.instancia().crearControlAgteReactivo( accionesSemanticasEspecificas,rutaTabla , nombreInstanciaAgente, itfConsumidorPercepcion, itfProductorPercepcion);
                controlAgteReactivo = FactoriaControlAgteReactivoInputObjImp0.instanceControlAgteReactInpObj().crearControlAgteReactivo(rutaTabla, rutaAcciones, agente);
//                itfControlAgteReactivo = (ItfControlAgteReactivo) control ;
// Se crea la percepcion y se le pasa la interfaz del control
                PercepcionAbstracto percepcion = FactoriaPercepcion.instancia().crearPercepcion(agente, controlAgteReactivo);
                itfGestionPercepcion = (InterfazGestionPercepcion) percepcion;
                itfProductorPercepcion = (ItfProductorPercepcion) percepcion;
                controlAgteReactivo.inicializarInfoGestorAcciones(nombreInstanciaAgente, itfProductorPercepcion);
                // paso 3.3 se crea la instancia que implementa las interfaces
                //	AgenteReactivoAbstracto patron = new AgenteReactivoImp(nombreInstanciaAgente,itfControlAgteReactivo,itfProductorPercepcion,itfConsumidorPercepcion);
                // Se crea el automata del ciclo de vida para implementar la interfaz de Gestion
                ItfUsoAutomataEFsinAcciones itfAutomata = (ItfUsoAutomataEFsinAcciones) ClaseGeneradoraAutomataEFsinAcciones.instance(NombresPredefinidos.FICHERO_AUTOMATA_CICLO_VIDA_COMPONENTE);
                this.agente.setComponentesInternos(nombreInstanciaAgente, itfAutomata, controlAgteReactivo, itfProductorPercepcion, itfGestionPercepcion);
                // paso 3. 4  Se define la interfaz de uso del agente creado en las acciones semánticas específicas
//        accionesSemanticasEspecificas.setItfUsoAgenteReactivo(agente);
//         accionesSemanticasEspecificas.setCtrlGlobalAgenteReactivo(agente);
                // Quedan definidos todos los objetos necesarios para implementar el ejemplar creado
                logger.debug(nombreInstanciaAgente + ":Creacion del Agente ...ok");
                if (recursoTrazas != null) {
                    recursoTrazas.aceptaNuevaTraza(new InfoTraza(nombreInstanciaAgente, tipoEntidad,
                            nombreInstanciaAgente + ":Creacion del Agente ...ok", NivelTraza.debug));
                }
                // Paso 4 Procedemos a registrar las interfaces de la instancia crada en el repositorio
                if (this.repositorioInterfaces == null) {
                    crearRepositorioInterfaces();
                }
                repositorioInterfaces.registrarInterfaz(
                        NombresPredefinidos.ITF_GESTION + nombreInstanciaAgente, (ItfGestionAgenteReactivo) agente);
                repositorioInterfaces.registrarInterfaz(
                        NombresPredefinidos.ITF_USO + nombreInstanciaAgente, (ItfUsoAgenteReactivo) agente);

                // activamos trazas pesadas
                agente.setDebug(false);
            } catch (ExcepcionEnComponente exc) {
                logger.error("Error al crear El CONTROL del agente. La factoria no puede crear la instancia o no se pueden obtener la interfaces : " + nombreInstanciaAgente, exc);
                System.err.println(" No se puede crear el control del agente. La factoria no puede crear la instancia.");
                exc.putCompDondeEstaContenido("patronAgenteReactivo.contol");
                exc.putParteAfectada("FactoriaControlAgenteReactivoImp");
                throw exc;
            } catch (Exception ex) {
                logger.error("Error AL CREAR LA PERCEPCON. La factoria no puede crear la instancia : " + nombreInstanciaAgente, ex);
                System.err
                        .println(" No se puede crear la Percepcion del agente. La factoria no puede crear la instancia.");
                throw new ExcepcionEnComponente("patronAgenteReactivo.contol", "posible error al crear l paercepcion", "percepcion", "");
            }

        } else {
            recursoTrazas.aceptaNuevaTraza(new InfoTraza(nombreInstanciaAgente,
                    nombreInstanciaAgente + ":No se puede crear el agente. El comportamiento no esta bien definido",
                    NivelTraza.error));
            System.err.println(" No se puede crear la Instancia del agente . La descripcion del comportamiento no es correcta.");
        }
    }

    public void crearAgenteReactivo(String rutaTabla, String rutaAcciones, String agenteId) throws ExcepcionEnComponente {
        nombreInstanciaAgente = agenteId;
        //     String rutaTabla = descInstanciaAgente.getDescComportamiento().getLocalizacionFicheroAutomata();
//                        String rutaAcciones = descInstanciaAgente.getDescComportamiento().getLocalizacionComportamiento();
        if ((rutaTabla != null)) {
            try {
// Se crea el objeto que implementa las interfaces de  uso y de gestion
                this.agente = new AgenteReactivoImp2(nombreInstanciaAgente);
// Se crea el control del agente por medio de su factoria
                //	ProcesadorEventosAbstracto control = FactoriaControlAgteReactivo.instancia().crearControlAgteReactivo( accionesSemanticasEspecificas,rutaTabla , nombreInstanciaAgente, itfConsumidorPercepcion, itfProductorPercepcion);
                controlAgteReactivo = FactoriaControlAgteReactivoInputObjImp0.instanceControlAgteReactInpObj().crearControlAgteReactivo(rutaTabla, rutaAcciones, agente);
//                itfControlAgteReactivo = (ItfControlAgteReactivo) control ;
// Se crea la percepcion y se le pasa la interfaz del control
                PercepcionAbstracto percepcion = FactoriaPercepcion.instancia().crearPercepcion(agente, controlAgteReactivo);
                itfGestionPercepcion = (InterfazGestionPercepcion) percepcion;
                itfProductorPercepcion = (ItfProductorPercepcion) percepcion;
                controlAgteReactivo.inicializarInfoGestorAcciones(nombreInstanciaAgente, itfProductorPercepcion);
                // paso 3.3 se crea la instancia que implementa las interfaces
                //	AgenteReactivoAbstracto patron = new AgenteReactivoImp(nombreInstanciaAgente,itfControlAgteReactivo,itfProductorPercepcion,itfConsumidorPercepcion);
                // Se crea el automata del ciclo de vida para implementar la interfaz de Gestion
                ItfUsoAutomataEFsinAcciones itfAutomata = (ItfUsoAutomataEFsinAcciones) ClaseGeneradoraAutomataEFsinAcciones.instance(NombresPredefinidos.FICHERO_AUTOMATA_CICLO_VIDA_COMPONENTE);
                this.agente.setComponentesInternos(nombreInstanciaAgente, itfAutomata, controlAgteReactivo, itfProductorPercepcion, itfGestionPercepcion);
                // paso 3. 4  Se define la interfaz de uso del agente creado en las acciones semánticas específicas
//        accionesSemanticasEspecificas.setItfUsoAgenteReactivo(agente);
//         accionesSemanticasEspecificas.setCtrlGlobalAgenteReactivo(agente);
                // Quedan definidos todos los objetos necesarios para implementar el ejemplar creado
                logger.debug(nombreInstanciaAgente + ":Creacion del Agente ...ok");
                if (recursoTrazas != null) {
                    recursoTrazas.aceptaNuevaTraza(new InfoTraza(nombreInstanciaAgente, tipoEntidad,
                            nombreInstanciaAgente + ":Creacion del Agente ...ok", NivelTraza.debug));
                }
                // Paso 4 Procedemos a registrar las interfaces de la instancia crada en el repositorio
                if (this.repositorioInterfaces == null) {
                    crearRepositorioInterfaces();
                }
                repositorioInterfaces.registrarInterfaz(
                        NombresPredefinidos.ITF_GESTION + nombreInstanciaAgente, (ItfGestionAgenteReactivo) agente);
                repositorioInterfaces.registrarInterfaz(
                        NombresPredefinidos.ITF_USO + nombreInstanciaAgente, (ItfUsoAgenteReactivo) agente);

                // activamos trazas pesadas
                agente.setDebug(false);
            } catch (ExcepcionEnComponente exc) {
                logger.error("Error al crear El CONTROL del agente. La factoria no puede crear la instancia o no se pueden obtener la interfaces : " + nombreInstanciaAgente, exc);
                System.err.println(" No se puede crear el control del agente. La factoria no puede crear la instancia.");
                exc.putCompDondeEstaContenido("patronAgenteReactivo.contol");
                exc.putParteAfectada("FactoriaControlAgenteReactivoImp");
                throw exc;
            } catch (Exception ex) {
                logger.error("Error AL CREAR LA PERCEPCON. La factoria no puede crear la instancia : " + nombreInstanciaAgente, ex);
                System.err
                        .println(" No se puede crear la Percepcion del agente. La factoria no puede crear la instancia.");
                throw new ExcepcionEnComponente("patronAgenteReactivo.contol", "posible error al crear l paercepcion", "percepcion", "");
            }

        } else {
            recursoTrazas.aceptaNuevaTraza(new InfoTraza(nombreInstanciaAgente,
                    nombreInstanciaAgente + ":No se puede crear el agente. El comportamiento no esta bien definido",
                    NivelTraza.error));
            System.err.println(" No se puede crear la Instancia del agente . La descripcion del comportamiento no es correcta.");
        }
    }

    @Override
    public synchronized void crearAgenteReactivo(String agenteId, String rutaComportamiento) {
        String rutaAutomata = obtenerRutaValidaAutomata(rutaComportamiento);
        if (rutaAutomata != null) {
            try {
                this.crearAgenteReactivo(rutaAutomata, rutaComportamiento, agenteId);
            } catch (ExcepcionEnComponente ex) {
                java.util.logging.Logger.getLogger(FactoriaAgenteReactivoInputObjImp0.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            recursoTrazas.aceptaNuevaTraza(new InfoTraza(nombreInstanciaAgente,
                    nombreInstanciaAgente + ":No se puede crear el agente. No se encuentra fichero de la tabla de estados en la ruta :+"
                    + rutaComportamiento + " Revisar la existencia del fichero en esa ruta",
                    NivelTraza.error));
            System.err.println(" No se puede crear la Instancia del agente . La descripcion del comportamiento no es correcta.");
        }
    }

    private String obtenerRutaValidaAutomata(String rutaComportamiento) {
        String msgInfoUsuario;
        rutaComportamiento = "/" + rutaComportamiento.replace(".", "/");
        String rutaAutomata = rutaComportamiento + "/" + NombresPredefinidos.FICHERO_AUTOMATA;
        InputStream input = this.getClass().getResourceAsStream(rutaAutomata);
        logger.debug(rutaAutomata + "?" + ((input != null) ? "  OK" : "  null"));
        if (input != null) {
            return rutaAutomata;
        } else {
            // intentamos otra política de nombrado
            String nombreEntidad = rutaComportamiento.substring(rutaComportamiento.lastIndexOf("/") + 1);
            String primerCaracter = nombreEntidad.substring(0, 1);
            nombreEntidad = nombreEntidad.replaceFirst(primerCaracter, primerCaracter.toUpperCase());
            rutaAutomata = rutaComportamiento + "/automata" + nombreEntidad + ".xml";
            input = this.getClass().getResourceAsStream(rutaAutomata);
            if (input != null) {
                return rutaAutomata;
            } else {
                // la entidad no se encuentra o no esta definida 
                msgInfoUsuario = "Error no se encuentra el fichero especificado \n"
                        + "Para el comportamiento:" + rutaComportamiento
                        + "En la ruta: " + rutaAutomata + "\n"
                        + "Verifique la existencia del fichero en el directorio src \n";
                logger.fatal(msgInfoUsuario);
                //              throw new ExcepcionEnComponente ( "PatronAgenteReactivo", "No se encuentra el fichero del automata  en la ruta :"+rutaAutomata,"Factoria del Agente Reactivo",this.getClass().getName()  );
                return null;
            }
        }
    }

    /**
     * Introduce un nuevo evento en la percepcion
     *
     * @param evento Evento que llega nuevo
     */
    public void setParametrosLoggerAgReactivo(String archivoLog, String nivelLog) {
        ConfiguracionTrazas.configura(logger, archivoLog, nivelLog);
    }
}

