package icaro.infraestructura.patronAgenteReactivo.control.AutomataEFE;

import icaro.infraestructura.entidadesBasicas.NombresPredefinidos;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParserTablaEstados {

    Document document;

    public XMLParserTablaEstados(String nombreFich) throws ExcepcionNoSePudoCrearAutomataEFE {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(this.getClass().getResourceAsStream(nombreFich));
        } catch (SAXException sxe) {
            Exception x = sxe;
            if (sxe.getException() != null) {
                x = sxe.getException();
            }
            System.out.println("Se ha producido un error al procesar el fichero XML: "
                    + x.getMessage());
            throw new ExcepcionNoSePudoCrearAutomataEFE("XMLParserTablaEstados", "error al procesar el fichero XML" + nombreFich,
                    "document = builder.parse(this.getClass().getResourceAsStream(nombreFich)");
        } catch (ParserConfigurationException pce) {
            System.out.println("No se pudo construir un analizador XML con las opciones especificadas referido al fichero XML: "
                            + nombreFich);
            throw new ExcepcionNoSePudoCrearAutomataEFE("XMLParserTablaEstados", "no se pudo construir un analizador XML a partir del  fichero XML: " + nombreFich,
                    "document = builder.parse(this.getClass().getResourceAsStream(nombreFich)");
        } catch (IOException ioe) {
            System.out.println("Error de lectura en el fichero XML. Est usted seguro de que el fichero '"
                    + nombreFich + "' esta ahi?");
            throw new ExcepcionNoSePudoCrearAutomataEFE("XMLParserTablaEstados", "Error de lectura en el fichero XML : " + nombreFich,
                    "document = builder.parse(this.getClass().getResourceAsStream(nombreFich)");
        }
    }

    public String extraerDescripcionTablaEstados() {
        return document.getElementsByTagName("tablaEstados").item(0)
                .getAttributes().item(0).getNodeValue();
    }

    public TablaEstadosControl extraeTablaEstados() throws ExcepcionNoSePudoCrearAutomataEFE {
        TablaEstadosControl tabla = new TablaEstadosControl();

        org.w3c.dom.Node nodo;
        org.w3c.dom.NamedNodeMap mapaNombreNodo;
        org.w3c.dom.NodeList listaNodos;
        String id;

        org.w3c.dom.NodeList nlInicial = document.getElementsByTagName("estadoInicial");

        nodo = nlInicial.item(0);
        mapaNombreNodo = nodo.getAttributes();
        id = mapaNombreNodo.getNamedItem("idInicial").getNodeValue();
        tabla.putEstado(id, TablaEstadosControl.TIPO_DE_ESTADO_INICIAL);

        listaNodos = nodo.getChildNodes();
        procesarListaNodosTransicion(id, listaNodos, tabla);

        org.w3c.dom.NodeList nlIntermedios = document.getElementsByTagName("estado");

        for (int i = 0; i < nlIntermedios.getLength(); i++) {
            nodo = nlIntermedios.item(i);
            mapaNombreNodo = nodo.getAttributes();
            id = mapaNombreNodo.getNamedItem("idIntermedio").getNodeValue();
            tabla.putEstado(id, TablaEstadosControl.TIPO_DE_ESTADO_INTERMEDIO);

            listaNodos = nodo.getChildNodes();
            procesarListaNodosTransicion(id, listaNodos, tabla);
        }

        org.w3c.dom.NodeList nlFinales = document.getElementsByTagName("estadoFinal");
        for (int i = 0; i < nlFinales.getLength(); i++) {
            nodo = nlFinales.item(i);
            mapaNombreNodo = nodo.getAttributes();
            id = mapaNombreNodo.getNamedItem("idFinal").getNodeValue();
            tabla.putEstado(id, TablaEstadosControl.TIPO_DE_ESTADO_FINAL);

            listaNodos = nodo.getChildNodes();
            procesarListaNodosTransicion(id, listaNodos, tabla);
        }

        org.w3c.dom.NodeList nlUniversal = document.getElementsByTagName("transicionUniversal");
        if (nlUniversal.getLength() > 0) {
            for (int i = 0; i < nlUniversal.getLength(); i++) {
                nodo = nlUniversal.item(i);
                mapaNombreNodo = nodo.getAttributes();
                String input = mapaNombreNodo.getNamedItem("input").getNodeValue();
                String accion = mapaNombreNodo.getNamedItem("accion").getNodeValue();
                String estadoSig = mapaNombreNodo.getNamedItem("estadoSiguiente").getNodeValue();
                String modo = mapaNombreNodo.getNamedItem("modoDeTransicion").getNodeValue();
                tabla.putTransicionUniversal(input, accion, estadoSig, modo);
            }
        }
        return tabla;
    }

    public Set<String> extraeConjuntoInputs() {
        Set<String> conjuntoInputs = new HashSet<String>();
        NodeList listaTransiciones = document.getElementsByTagName("transicion");
        for (int i = 0; i < listaTransiciones.getLength(); i++) {
            org.w3c.dom.Node transicion = listaTransiciones.item(i);
            Node input = transicion.getAttributes().getNamedItem("input");
            conjuntoInputs.add(input.getNodeValue());
        }
        listaTransiciones = document.getElementsByTagName("transicionUniversal");
        for (int i = 0; i < listaTransiciones.getLength(); i++) {
            org.w3c.dom.Node transicion = listaTransiciones.item(i);
            Node input = transicion.getAttributes().getNamedItem("input");
            conjuntoInputs.add(input.getNodeValue());
        }
        return conjuntoInputs;
    }

    private void procesarListaNodosTransicion(String idEstado,
            org.w3c.dom.NodeList listaTransiciones,
            TablaEstadosControl tablaEstados) {
        org.w3c.dom.Node nodo, nodoAux;
        org.w3c.dom.NamedNodeMap mapaNombreNodo;
        String input, accion, estadoSig, modo;
        for (int i = 0; i < listaTransiciones.getLength(); i++) {
            nodo = listaTransiciones.item(i);
            String idNodo = nodo.getNodeName();
            if (idNodo.equalsIgnoreCase("transicion")) {
                mapaNombreNodo = nodo.getAttributes();
                input = mapaNombreNodo.getNamedItem("input").getNodeValue();
                nodoAux = mapaNombreNodo.getNamedItem("accion");
                if (nodoAux == null) {
                    accion = NombresPredefinidos.ACCION_VACIA_AUTOMATA_EF;
                } else {
                    accion = mapaNombreNodo.getNamedItem("accion").getNodeValue();
                }
                estadoSig = mapaNombreNodo.getNamedItem("estadoSiguiente").getNodeValue();
                nodoAux = mapaNombreNodo.getNamedItem("modoDeTransicion");
                if (nodoAux == null) {
                    modo = NombresPredefinidos.NOMBRE_MODO_TRANSICION_AUTOMATA_EF;
                } else {
                    modo = nodoAux.getNodeValue();
                }
                tablaEstados.putTransicion(idEstado, input, accion, estadoSig, modo);
            }
        }
    }
}
