package icaro.pruebas;

import icaro.infraestructura.entidadesBasicas.informes.Informe;

/**
 *
 * @author FGarijo
 */
public class InformeArranqueGestor extends Informe {

    public InformeArranqueGestor(String identEmisor, String contenido) {
        super(identEmisor, contenido);
        contenidoInforme = contenido;

    }

    @Override
    public String getContenidoInforme() {
        return (String) super.getContenidoInforme();
    }

}
