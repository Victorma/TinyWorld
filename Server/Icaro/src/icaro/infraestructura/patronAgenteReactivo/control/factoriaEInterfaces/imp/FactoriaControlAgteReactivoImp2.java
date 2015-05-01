package icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.imp;

import icaro.infraestructura.entidadesBasicas.ConfiguracionTrazas;
import icaro.infraestructura.patronAgenteReactivo.control.factoriaEInterfaces.FactoriaControlAgteReactivo;
import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.patronAgenteReactivo.control.acciones.AccionesSemanticasAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.imp.EjecutorDeAccionesImp;
import icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE.imp.AutomataEFEImp;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfConsumidorPercepcion;
import icaro.infraestructura.patronAgenteReactivo.percepcion.factoriaEInterfaces.ItfProductorPercepcion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;

import org.apache.log4j.Logger;

public class FactoriaControlAgteReactivoImp2 extends FactoriaControlAgteReactivo {

    private static final long serialVersionUID = 1L;
    protected String nombreAgente;
    protected EjecutorDeAccionesImp accionesSemanticas;
    protected AutomataEFEImp automataControl = null;
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
    protected ItfUsoRecursoTrazas trazas;

    @Override
    public ProcesadorInfoReactivoImp crearControlAgteReactivo(AccionesSemanticasAgenteReactivo accionesSemanticasEspecificas, String nombreFicheroTablaEstados, AgenteReactivoAbstracto agente) throws ExcepcionEnComponente {
        ConfiguracionTrazas.configura(logger);
        trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
        accionesSemanticasEspecificas.setLogger(logger);
        try {
            nombreAgente = agente.getIdentAgente();
            this.accionesSemanticas = new EjecutorDeAccionesImp(accionesSemanticasEspecificas);
            automataControl = new AutomataEFEImp(nombreFicheroTablaEstados, accionesSemanticas, AutomataEFEImp.NIVEL_TRAZA_DESACTIVADO, nombreAgente);
            return new ProcesadorInfoReactivoImp(automataControl, accionesSemanticasEspecificas, agente);
        } catch (Exception e) {
            logger.error("Error al crear el automata del agente " + nombreAgente + " utilizando el fichero " + nombreFicheroTablaEstados);
            trazas.aceptaNuevaTraza(new InfoTraza(nombreAgente,
                    "Error al crear el automata del agente " + nombreAgente + " utilizando el fichero " + nombreFicheroTablaEstados,
                    InfoTraza.NivelTraza.error));
            throw new ExcepcionEnComponente("AutomataEFEImp", "no se pudo crear el Automata EFE", "Automta EFE", "automataControl = new AutomataEFEImp(");
        }
    }

    @Override
    public ProcesadorEventosImp crearControlAgteReactivo(AccionesSemanticasAgenteReactivo accionesSemanticasEspecificas, String nombreFicheroTablaEstados, String nombreDelAgente, ItfConsumidorPercepcion percConsumidor,
            ItfProductorPercepcion percProductor) throws ExcepcionEnComponente {
        return null;
    }
}
