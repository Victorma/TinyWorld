package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.imp;

import icaro.infraestructura.entidadesBasicas.ConfiguracionTrazas;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.ProcesadorInfoReactivoAbstracto;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.FactoriaAutomatas;
import icaro.infraestructura.entidadesBasicas.componentesBasicos.automatas.automataEFconGesAcciones.InterpreteAutomataEFconGestAcciones;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.entidadesBasicas.factorias.FactoriaComponenteIcaro;
import icaro.infraestructura.patronAgenteReactivo.control.GestorAccionesAgteReactivoImp;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfConsumidorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import org.apache.log4j.Logger;

public class FactoriaControlAgteReactivoInputObjImp0 extends FactoriaComponenteIcaro {

    private static final long serialVersionUID = 1L;
    protected String nombreAgente;
    protected InterpreteAutomataEFconGestAcciones interpAutomat = null;
    private boolean DEBUG = false;
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());

    public synchronized ProcesadorInfoReactivoAbstracto crearControlAgteReactivo(String nombreFicheroTablaEstados, String rutaFicheroAccs, AgenteReactivoAbstracto agente) throws ExcepcionEnComponente {
        ConfiguracionTrazas.configura(logger);
        try {
            nombreAgente = agente.getIdentAgente();
            interpAutomat = FactoriaAutomatas.instanceAtms().
                    crearInterpreteAutomataEFconGestorAcciones(nombreAgente, nombreFicheroTablaEstados, rutaFicheroAccs, DEBUG);
            GestorAccionesAgteReactivoImp gestorAcciones = new GestorAccionesAgteReactivoImp();
            gestorAcciones.setPropietario(nombreAgente);

            interpAutomat.setGestorAcciones(gestorAcciones);
            ProcesadorInfoReactivoAbstracto controlAgte = new ProcesadorInfoReactivoAutInObjImp0(interpAutomat, gestorAcciones, agente);
            return controlAgte;
        } catch (Exception ex) {
            this.generarErrorCreacionAutomata(nombreFicheroTablaEstados);
            throw new ExcepcionEnComponente("AutomataEFEImp", "no se pudo crear el Automata EFE", "Automta EFE", "automataControl = new AutomataEFEImp(");
        }
    }

    private void generarErrorCreacionAutomata(String nombreFicheroTablaEstados) {
        logger.error("Error al crear el automata del agente " + nombreAgente + " utilizando el fichero " + nombreFicheroTablaEstados);
        if (this.recursoTrazas == null) {
            this.crearRecursoTrazas();
        }
        recursoTrazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
                "Error al crear el automata del agente " + nombreAgente + " utilizando el fichero " + nombreFicheroTablaEstados,
                InfoTraza.NivelTraza.error));
    }

    public synchronized ProcesadorEventosImp crearControlAgteReactivo(AccionesSemanticasAgenteReactivo accionesSemanticasEspecificas, String nombreFicheroTablaEstados, String nombreDelAgente, ItfConsumidorPercepcion percConsumidor,
            ItfProductorPercepcion percProductor) throws ExcepcionEnComponente {
        return null;
    }

    public ProcesadorInfoReactivoAbstracto crearControlAgteReactivo(AccionesSemanticasAgenteReactivo accionesSemanticasEspecificas, String nombreFicheroTablaEstados, AgenteReactivoAbstracto agente
    ) throws ExcepcionEnComponente {
        return null;
    }
}
