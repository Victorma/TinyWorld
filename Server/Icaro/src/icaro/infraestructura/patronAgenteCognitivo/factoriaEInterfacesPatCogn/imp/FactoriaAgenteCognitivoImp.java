package icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.imp;

//import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.ItfUsoAgenteCognitivo;
//import icaro.infraestructura.patronAgenteCognitivo.factoriaEInterfacesPatCogn.FactoriaAgenteCognitivo;
//import icaro.infraestructura.entidadesBasicas.ExcepcionEnComponente;
//import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
//import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaAgente;
//import icaro.infraestructura.entidadesBasicas.interfaces.InterfazGestion;
//import icaro.infraestructura.recursosOrganizacion.recursoTrazas.ItfUsoRecursoTrazas;
//import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
//import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.ItfUsoRepositorioInterfaces;
//import icaro.infraestructura.recursosOrganizacion.repositorioInterfaces.imp.ClaseGeneradoraRepositorioInterfaces;
//import java.io.File;
//import java.util.logging.Level;
//import org.apache.log4j.Logger;
//
///**
// * Implementation for Cognitive Agent Factory
// * @author carf
// * @author Carlos Celorrio
// */
//public class FactoriaAgenteCognitivoImp extends FactoriaAgenteCognitivo {
//
//	@Override
//	public void createCognitiveAgent(DescInstanciaAgente descInstanciaAgente)
//			throws Exception {
//
//		String agentName = descInstanciaAgente.getId();
//		String descName = descInstanciaAgente.getDescComportamiento().getNombreComportamiento();
//
//		String nombreAgenteEnMinusculas = descName.substring(0, 1).toLowerCase()
//				+ descName.substring(1);
///*
//		String ficheroReglas = NombresPredefinidos.RUTA_PROCESO_RESOLUCION_COGNITIVO;
//		ficheroReglas = ficheroReglas.replaceAll(NombresPredefinidos.regex_ruta_automata_package, nombreAgenteEnMinusculas);
//		ficheroReglas = ficheroReglas.replaceAll(NombresPredefinidos.regex_ruta_automata_behaviour, descName);
//*/
//
//		// Get interface repository
//		ItfUsoRepositorioInterfaces repositorio = NombresPredefinidos.REPOSITORIO_INTERFACES_OBJ;
//		ItfUsoRecursoTrazas trazas = NombresPredefinidos.RECURSO_TRAZAS_OBJ;
//	try {
//// Validamos el comportamiento que nos dan como entrada
//        String rutaComportamiento = descInstanciaAgente.getDescComportamiento().getLocalizacionComportamiento();
//        String rutaReglas = rutaComportamiento + "."+ NombresPredefinidos.CARPETA_REGLAS;
//                rutaReglas = obtenerRutaValidaReglas ( rutaReglas );
//
//		// Create cognitive agent
//		trazas.aceptaNuevaTraza(new InfoTraza(agentName,
//                    agentName +": Creating agent "+agentName,
//				InfoTraza.NivelTraza.debug));
//		AgenteCognitivotImp agent = new AgenteCognitivotImp(agentName, rutaReglas);
//
//		// Register cognitive agent
//		trazas.aceptaNuevaTraza(new InfoTraza(agentName,
//				agentName+": Registering agent "+agentName,
//				InfoTraza.NivelTraza.debug));
//		repositorio.registrarInterfaz(NombresPredefinidos.ITF_USO+agentName, (ItfUsoAgenteCognitivo)agent );
//		repositorio.registrarInterfaz(NombresPredefinidos.ITF_GESTION+agentName, (InterfazGestion)agent);
//        }
//	catch ( ExcepcionEnComponente exc) {
////			logger.error("Error al crear El CONTROL del agente. La factoria no puede crear la instancia o no se pueden obtener la interfaces : " + nombreInstanciaAgente, exc);
//            System.err
//					.println(" No se puede crear el control del agente. La factoria no puede crear la instancia.");
//                    exc.putCompDondeEstaContenido("patronAgenteCognitivo.contol");
//                    exc.putParteAfectada("FactoriaControlAgenteCognitivoImp");
//                    throw exc;
//        }
//	}
//private String obtenerRutaValidaReglas (String rutaComportamiento)throws ExcepcionEnComponente {
//    // La ruta del comportamiento no incluye la clase
//    // Obtenemos la clase de AS en l aruta
//            rutaComportamiento =File.separator+rutaComportamiento.replace(".", File.separator);
//      String rutaBusqueda = "src"+rutaComportamiento;
//       File f = new File(rutaBusqueda);
//       String[] ficherosRuta = f.list() ;
//   // Buscamos la clase de acciones semanticas en el array con los ficheros
//        Boolean ficheroEncontrado=false;
//        String nombreFichero = "";
//        int i = 0;
//        while((i<ficherosRuta.length)&&!ficheroEncontrado){
//             nombreFichero = ficherosRuta[ i ];
//            if (nombreFichero.startsWith(NombresPredefinidos.PREFIJO_REGLAS )  ){
//                        ficheroEncontrado = true;
//           }
//        i++;
//        }
//
//
//   /*     for (int j=0; (j<ficherosRuta.length)&&!ficheroEncontrado; j++){
//             nombreFichero = ficherosRuta[ i ];
//         if (nombreFichero.startsWith(NombresPredefinidos.PREFIJO_AUTOMATA )  ){
//                        ficheroEncontrado = true;
//                            }
//            }
//   /*/
//        if (ficheroEncontrado)
//         return rutaComportamiento.replace(File.separator, "/")+"/"+nombreFichero;
//        {
//
//            throw new ExcepcionEnComponente ( "PatronAgenteCognitivo", "No se encuentra el fichero de reglas en la ruta :"+rutaComportamiento,"Factoria del Agente Reactivo","Class obtenerClaseAccionesSemanticas(DescInstanciaAgente instAgente)"  );
//                }
//
//    }
//}
