package icaro.aplicaciones.recursos.extractorSemantico;

import gate.Gate;
import icaro.aplicaciones.recursos.extractorSemantico.imp.ExtractorSemanticoImp;
import icaro.infraestructura.patronRecursoSimple.imp.ImplRecursoSimple;
import icaro.infraestructura.recursosOrganizacion.recursoTrazas.imp.componentes.InfoTraza;
import java.io.File;
import java.util.HashSet;

public class ClaseGeneradoraExtractorSemantico extends ImplRecursoSimple implements
		ItfUsoExtractorSemantico {

	private static final long serialVersionUID = 1L;
//	private ItfUsoRecursoTrazas trazas;
//	private ConsultaBBDD consulta;
        private ExtractorSemanticoImp extractorSem;

	public ClaseGeneradoraExtractorSemantico(String idInstanciaRecurso) throws Exception {
		
		super(idInstanciaRecurso);
// obtenemos la ruta del procesador que debe estar definida en las propiedades del recurso
//                ItfUsoConfiguracion config = (ItfUsoConfiguracion) repoIntfaces.obtenerInterfaz(NombresPredefinidos.ITF_USO+NombresPredefinidos.CONFIGURACION);
//			DescInstanciaRecursoAplicacion descRecurso = config.getDescInstanciaRecursoAplicacion(idInstanciaRecurso);
      File file1 = new File(ConfigRutasExtractorSemantico.DirectorioGateHome);          
      File gappFile = new File(ConfigRutasExtractorSemantico.DirectorioAppFile);
    Gate.setGateHome(file1);
		try {
                        extractorSem = new ExtractorSemanticoImp(ConfigRutasExtractorSemantico.DirectorioAppFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                        this.trazas.aceptaNuevaTraza(new InfoTraza(id,
  				"Se ha producido un error al crear el extractor semantico  "+e.getMessage()+
                                ": Verificar los parametros de creacion "
                                + "rutas y otros",
  				InfoTraza.NivelTraza.error));
			this.itfAutomata.transita("error");
			throw e;
		}
			
        extractorSem.incializar();
}
        @Override
	public HashSet extraerAnotaciones(HashSet anotacionesAencontrar,String textoUsuario)throws Exception{
            return extractorSem.extraerAnotaciones(anotacionesAencontrar,textoUsuario);
        }

	@Override
	public void termina() {

		try {
			super.termina();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}  
}