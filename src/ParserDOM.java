import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
/**
 * @author Sergio
 * @version 1.0
 * @since 15/12/2023
 */
public class ParserDOM {
    /**
     * Metodo main
     * @param args
     */
    public static void main(String[] args) {
        //Crea una nueva instancia de DocumentBuilderFactory
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        //Método que hace que se ignoren los comentarios y no se creen nodos a partir de ellos
        documentBuilderFactory.setIgnoringComments(true);
        //Método que hace que se ignoren los espacios en blanco y las tabulaciones y no se creen nodos a partir de ellos
        documentBuilderFactory.setIgnoringElementContentWhitespace(true);

        try {
            System.out.println("Parser DOM");
            //Crea una nueva instancia de DocumentBuilder a partir de el DocumentBuilderFactory anterior
            DocumentBuilder dBuilder = documentBuilderFactory.newDocumentBuilder();
            //Crea un Document a partir del DocumentBuilder anterior, parseando un new File con la ruta del XML
            Document document = dBuilder.parse(new File("src\\xml_files\\prueba.xml"));
            //Devuelve el nodo raíz del documento y normaliza el árbol DOM
            document.getDocumentElement().normalize();
            //Llama al método muestraNodo pasándole document
            muestraNodo(document);
        } catch (ParserConfigurationException | IOException | SAXException error) {
            System.out.println(error.getMessage());
        }
    }

    /**
     * Metodo muestraNodo
     * @param nodo nodo a mostrar
     */
    private static void muestraNodo(Node nodo) {
        //Switch case que ejecuta un método u otro según el tipo de nodo que sea
        switch (nodo.getNodeType()) {
            case Node.DOCUMENT_NODE:
                System.out.println("Nodo Documento");
                muestraNodoDocumento(nodo);
                break;
            case Node.ELEMENT_NODE:
                System.out.println("Nodo Elemento");
                muestraNodoElemento(nodo);
                break;
            case Node.TEXT_NODE:
                //Si el nodo es de tipo texto y está vacío no se ejecuta ningún método
                if (nodo.getNodeValue().trim().isEmpty()) {
                    return;
                } else {
                    System.out.println("Nodo Texto");
                    muestraNodoTexto(nodo);
                }
                break;
            default:
                System.out.println("Tipo de nodo no soportado por el método");
        }
        //Guarda en una NodeList la los hijos del nodo
        NodeList hijos = nodo.getChildNodes();
        //Bucle que recorre los hijos y va llamando al método muestraNodo(Node nodo)
        for (int i = 0; i < hijos.getLength(); i++) {
            muestraNodo(hijos.item(i));
        }
    }

    /**
     * Método muestraNodoDocumento
     * @param nodo el nodo debe ser un nodo documento
     */
    private static void muestraNodoDocumento(Node nodo) {
        //Casteo a Document
        Document document = (Document) nodo;

        //Imprime la información del documento (añadí a prueba.xml una declaración para probar estos métodos)
        System.out.println("Versión XML: " + document.getXmlVersion());
        System.out.println("Codificación: " + document.getXmlEncoding());
        System.out.println("Standalone: " + document.getXmlStandalone());
    }

    private static void muestraNodoElemento(Node nodo) {
        //Casteo a Element
        Element element = (Element) nodo;

        //Imprime el nombre del elemento
        System.out.println("Nombre del elemento: " + element.getNodeName());

        //Guarda los atributos del elemento (si hay) en una variable
        NamedNodeMap atributos = element.getAttributes();

        //Bucle que recorre los atributos (si hay) e imprime su nombre y valor
        for (int i = 0; i < atributos.getLength(); i++) {
            System.out.println("Nombre del atributo: " + atributos.item(i).getNodeName());
            System.out.println("Valor del atributo: " + atributos.item(i).getNodeValue());
        }
    }

    /**
     *
     * @param nodo
     * @return no devuelve nada
     */
    private static void muestraNodoTexto(Node nodo) {
        //Casteo a text
        Text texto = (Text) nodo;

        //Imprime el valor del texto aplicándole un .trim(), por si acaso
        System.out.println("Valor del texto: " + texto.getNodeValue().trim());
    }
}
