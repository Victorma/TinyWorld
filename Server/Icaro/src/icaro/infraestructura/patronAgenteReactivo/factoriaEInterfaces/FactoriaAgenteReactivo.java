package icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces;

import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.entidadesBasicas.factorias.FactoriaComponenteIcaro;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

public abstract class FactoriaAgenteReactivo extends FactoriaComponenteIcaro {

    private static FactoriaAgenteReactivo instancia;
    protected Logger logger = Logger.getLogger(this.getClass().getCanonicalName());

    public static FactoriaAgenteReactivo instancia() {
        Log log = LogFactory.getLog(FactoriaAgenteReactivo.class);
        if (instancia == null) {
            String clase = System.getProperty("icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp",
                    "icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.FactoriaAgenteReactivoInputObjImp0");
            try {
                instancia = (FactoriaAgenteReactivo) Class.forName(clase).newInstance();
            } catch (Exception ex) {
                log.error("Implementacion de la factoria: FactoriaAgenteReactivoImp no encontrada", ex);
            }

        }
        return instancia;
    }

    public abstract void crearAgenteReactivo(DescInstanciaAgente descInstanciaAgente) throws ExcepcionEnComponente;

    public abstract void crearAgenteReactivo(String nombreInstanciaAgente, String rutaComportamiento) throws ExcepcionEnComponente;

}
