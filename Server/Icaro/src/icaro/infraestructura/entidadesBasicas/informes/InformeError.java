/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package icaro.infraestructura.entidadesBasicas.informes;

/**
 *
 * @author Francisco J Garijo
 */
public class InformeError extends Informe {

    public InformeError(String identEmisor, String msgError) {
        super(identEmisor, msgError);

    }

    public String getMsgError() {
        return (String) this.contenidoInforme;
    }
}
