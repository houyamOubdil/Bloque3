/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bloque4;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;
import org.w3c.dom.*;
import java.io.File;

public class TraducirXML {

    public static void main(String[] args) {
        try {
            String filePath = "C:\\Users\\liberadosecretaria1\\Documents\\Bloque 3\\LeerDom.xml";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document originalDoc = builder.parse(new File(filePath));
            Document newDoc = builder.newDocument();

            // Crear elemento raíz traducido
            Element newRoot = newDoc.createElement("Catalogo");
            newDoc.appendChild(newRoot);

            // Obtener lista de libros del documento original
            NodeList bookList = originalDoc.getElementsByTagName("Book");
            for (int i = 0; i < bookList.getLength(); i++) {
                Element originalBook = (Element) bookList.item(i);
                Element newBook = newDoc.createElement("Libro");
                newBook.setAttribute("id", originalBook.getAttribute("id"));

                // Traducir y copiar loos elementos hijos: 
                addTranslatedElement(newDoc, newBook, originalBook, "Author", "Autor");
                addTranslatedElement(newDoc, newBook, originalBook, "Title", "Título");
                addTranslatedElement(newDoc, newBook, originalBook, "Genre", "Genero");
                addTranslatedElement(newDoc, newBook, originalBook, "Price", "Precio");
                addTranslatedElement(newDoc, newBook, originalBook, "PublishDate", "Fecha_de_publicación");
                addTranslatedElement(newDoc, newBook, originalBook, "Description", "Descripción");

                // Añadir libro traducido a la raíz
                newRoot.appendChild(newBook);
            }

            // Configurar el transformador para agregar indentación
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            // Guardar el nuevo documento traducido
            DOMSource source = new DOMSource(newDoc);
            StreamResult result = new StreamResult(new File("libros.xml"));
            transformer.transform(source, result);

            System.out.println("Archivo XML traducido y guardado como libros.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTranslatedElement(Document newDoc, Element newParent, Element originalParent, String originalTag, String newTag) {
        NodeList nodeList = originalParent.getElementsByTagName(originalTag);
        if (nodeList.getLength() > 0) {
            Element originalElement = (Element) nodeList.item(0);
            Element newElement = newDoc.createElement(newTag);
            newElement.setTextContent(originalElement.getTextContent());
            newParent.appendChild(newElement);
        }
    }
}
