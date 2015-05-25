package icaro.infraestructura.clasesGeneradorasOrganizacion;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.factorias.FactoriaComponenteIcaro;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.ClaseGeneradoraRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;

public class ArranqueSistemaConCrtlGestorO {

    private static final long serialVersionUID = 1L;

    /**
     * Método de arranque principal de la organización.
     *
     * @param args Entrada: Ruta completa hasta el fichero de configuración.
     */
    public static void main(String args[]) {
        if (args.length == 0) {
            System.err.println("ERROR: Ningun argumento recibido.");
            System.err.println("-> Causa: Es necesario pasar como argumento la ruta del fichero de descripcion.");
            System.err.println("-> Ejemplo: ./config/descripcionAcceso.xml");
        } else {
            ItfUsoRecursoTrazas recursoTrazas = null;
            try {
                // Se crea el repositorio de interfaces y el recurso de trazas:
                ItfUsoRepositorioInterfaces repositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
                recursoTrazas = ClaseGeneradoraRecursoTrazas.instance();
                repositorioInterfaces.registrarInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS,
                        recursoTrazas);
                repositorioInterfaces.registrarInterfaz(
                        NombresPredefinidos.ITF_GESTION + NombresPredefinidos.RECURSO_TRAZAS,
                        recursoTrazas);
                // Guardamos el recurso de trazas y el repositorio de Itfs en la clase de nombres predefinidos:
                NombresPredefinidos.RECURSO_TRAZAS_OBJ = recursoTrazas;
                NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ = repositorioInterfaces;
                NombresPredefinidos.DESCRIPCION_XML_POR_DEFECTO = args[0];
            } catch (Exception e) {
                // No es error crítico:
                System.err.println("ERROR: No se pudo crear o registrar el recurso de trazas.");
                e.printStackTrace(System.err);
            }

            // Se crea el iniciador que se encargara de crear el resto de componentes:
            try {
                // Creo el agente gestor de organizacion:
                FactoriaComponenteIcaro.instanceAgteReactInpObj().crearAgenteReactivo(
                        NombresPredefinidos.NOMBRE_INICIADOR,
                        NombresPredefinidos.COMPORTAMIENTO_PORDEFECTO_INICIADOR);

                ClaseGeneradoraRepositorioInterfaces generadorInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
                ItfGestionAgenteReactivo ItfGestIniciador = (ItfGestionAgenteReactivo) generadorInterfaces.obtenerInterfaz(
                        NombresPredefinidos.ITF_GESTION + NombresPredefinidos.NOMBRE_INICIADOR);
                ItfUsoAgenteReactivo ItfUsoIniciador = (ItfUsoAgenteReactivo) generadorInterfaces.obtenerInterfaz(
                        NombresPredefinidos.ITF_USO + NombresPredefinidos.NOMBRE_INICIADOR);

                // Arranco la organizacion:
                if ((ItfGestIniciador != null) && (ItfUsoIniciador != null)) {
                    ItfGestIniciador.arranca();
                }
            } catch (Exception e) {
                String msgUsuario = "Error. No se ha podido crear el gestor de organizacion con nombre "
                        + NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION;
                if (recursoTrazas != null) {
                    recursoTrazas.trazar(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION, msgUsuario, NivelTraza.error);
                }
                System.err.println(msgUsuario);
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
    }
}
