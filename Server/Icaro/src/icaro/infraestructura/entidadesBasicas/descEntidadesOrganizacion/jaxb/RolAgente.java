//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.07.04 at 04:19:28 PM CEST 
//
package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for RolAgente.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <
 * pre>
 * &lt;simpleType name="RolAgente"> &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"> &lt;enumeration
 * value="AgenteAplicacion"/> &lt;enumeration value="Gestor"/> &lt;/restriction> &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "RolAgente")
@XmlEnum
public enum RolAgente {

    @XmlEnumValue("AgenteAplicacion")
    AGENTE_APLICACION("AgenteAplicacion"),
    @XmlEnumValue("Gestor")
    GESTOR("Gestor");
    private final String value;

    RolAgente(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RolAgente fromValue(String v) {
        for (RolAgente c : RolAgente.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
