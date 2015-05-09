package icaro.infraestructura.clasesGeneradorasOrganizacion;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import icaro.infraestructura.entidadesBasicas.comunicacion.EventoRecAgte;
import icaro.infraestructura.entidadesBasicas.excepciones.ExcepcionEnComponente;
import icaro.infraestructura.entidadesBasicas.factorias.FactoriaComponenteIcaro;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.AgenteReactivoAbstracto;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.FactoriaAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfGestionAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.ItfUsoAgenteReactivo;
import icaro.infraestructura.patronAgenteReactivo.factoriaEInterfaces.imp.FactoriaAgenteReactivoInputObjImp0;
import icaro.infraestructura.recursosOrganizacion.configuracion.ItfUsoConfiguracion;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.ClaseGeneradoraRecursoTrazas;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza.NivelTraza;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ArranqueSistemaIniciadorPruebaModeloInputGestAccs {

    private static final long serialVersionUID = 1L;

    /**
     * M�todo de arranque principal de la organizaci�n
     * 
     * @param args
     *            Entrada: ruta completa hasta el fichero de configuraci�n
     */
    public static void main(String args[]) {

        boolean herramientaArrancada = false;

        // creamos los recursos de la organizaci�n

        ItfUsoConfiguracion configuracionExterna = null;
        ItfUsoRecursoTrazas recursoTrazas = null;
        String msgUsuario;
       


//        if (args.length == 0) {
//            System.err.println("Error. Ningun argumento recibido.\n Causa: Es necesario pasar como argumento la ruta del fichero de descripcion.\n Ejemplo: ./config/descripcionAcceso.xml");
//            int opcion = JOptionPane.showConfirmDialog(new JFrame(), "Descripción de Organizacion no encontrado. ¿Desea arrancar el asistente de creación de Descripción de Organización?", "Confirmación", JOptionPane.YES_NO_OPTION);
//           // if (opcion == JOptionPane.YES_OPTION) {
//           //     arrancarHerramienta();
//          //      herramientaArrancada = true;
//          //  } else {
//          //      System.exit(1);
//          //  }
//        } else {
            try {
            // Se crea el repositorio de interfaces y el recurso de trazas
               
                ItfUsoRepositorioInterfaces repositorioInterfaces = ClaseGeneradoraRepositorioInterfaces.instance();
                recursoTrazas = ClaseGeneradoraRecursoTrazas.instance();
                    repositorioInterfaces.registrarInterfaz(
                            NombresPredefinidos.ITF_USO + NombresPredefinidos.RECURSO_TRAZAS,
                            recursoTrazas);
                    repositorioInterfaces.registrarInterfaz(
                            NombresPredefinidos.ITF_GESTION + NombresPredefinidos.RECURSO_TRAZAS,
                            recursoTrazas);
                    // Guardamos el recurso de trazas y el repositorio de Itfs en la clase de nombres predefinidos
                     NombresPredefinidos.RECURSO_TRAZAS_OBJ = recursoTrazas;
                     NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ = repositorioInterfaces;
            //       NombresPredefinidos.DESCRIPCION_XML_POR_DEFECTO = NombresPredefinidos.RUTA_DESCRIPCIONES+args[0];
//                     NombresPredefinidos.DESCRIPCION_XML_POR_DEFECTO = args[0];
                } catch (Exception e) {
                    System.err.println("Error. No se pudo crear o registrar el recurso de trazas");
                    e.printStackTrace();
                //no es error cr�tico
               }
            // Se crea el iniciador que se encargara de crear el resto de componentes

            ItfGestionAgenteReactivo itfGestIniciador = null;
             ItfUsoAgenteReactivo itfUsoIniciador = null;
//                try {
    //        
                   
//                    FactoriaAgenteReactivo.instancia().crearAgenteReactivo( NombresPredefinidos.NOMBRE_INICIADOR, NombresPredefinidos.COMPORTAMIENTO_PORDEFECTO_INICIADOR);
//               
//                    ItfGestIniciador = (ItfGestionAgenteReactivo) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
//                            NombresPredefinidos.ITF_GESTION + NombresPredefinidos.NOMBRE_INICIADOR);
//                     ItfUsoIniciador = (ItfUsoAgenteReactivo) ClaseGeneradoraRepositorioInterfaces.instance().obtenerInterfaz(
//                            NombresPredefinidos.ITF_USO + NombresPredefinidos.NOMBRE_INICIADOR);
//                    // arranco la organizacion
//                  if ((ItfGestIniciador != null)&& (ItfUsoIniciador!= null)) {
//                    ItfGestIniciador.arranca();
             String rutaFicheroAutomata = "/icaro/pruebas/automataPruebas.xml";
             String rutaCarpetaAcciones = "icaro.pruebas";
             String identPropietario = "Iniciador";
             String origen = "prueba1";
             String destino = identPropietario;
             String estadoActual;
             Boolean esEstadoFinal;
             try {
//             AgenteReactivoAbstracto itfPrueba = FactoriaAgenteReactivoInputObjImp0.instance().
//                     crearAgenteReactivo(rutaFicheroAutomata,rutaCarpetaAcciones,identPropietario);
             FactoriaComponenteIcaro.instanceAgteReactInpObj().crearAgenteReactivo(identPropietario, rutaCarpetaAcciones);
             itfUsoIniciador =  (ItfUsoAgenteReactivo) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfazUso(identPropietario);
            itfGestIniciador = (ItfGestionAgenteReactivo) NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ.obtenerInterfazGestion(identPropietario);
            itfGestIniciador.arranca();
            EventoRecAgte eventoPrueba = new EventoRecAgte("comenzar", origen,destino);
//             estadoActual =itfUsoIniciador.getItfControl().getEstadoControlAgenteReactivo();
//             recursoTrazas.trazar(identPropietario, "Antes de enviar el evento Estoy en el estado : "+ estadoActual, NivelTraza.debug);
         
             itfUsoIniciador.aceptaEvento(eventoPrueba);
//            estadoActual =itfUsoIniciador.getItfControl().getEstadoControlAgenteReactivo();
//             recursoTrazas.trazar(identPropietario, "Despues de enviar el evento Estoy en el estado : "+ estadoActual, NivelTraza.debug);
              }
               catch (ExcepcionEnComponente e) {
                    msgUsuario = "Error. No se ha podido crear el gestor de organizacion con nombre " + NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION;
                    recursoTrazas.trazar(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION, msgUsuario, NivelTraza.error);
                    System.err.println(msgUsuario);
                    System.exit(1);
                }
                 catch (Exception e) {
                    msgUsuario = "Error. No se ha podido crear el gestor de organizacion con nombre " + NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION;
                    recursoTrazas.trazar(NombresPredefinidos.NOMBRE_GESTOR_ORGANIZACION, msgUsuario, NivelTraza.error);
                    System.err.println(msgUsuario);
                    System.exit(1);
                }
            }
     
    }
  