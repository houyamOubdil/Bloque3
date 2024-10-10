/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.bloque4;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Bloque3 {

    public static void main(String[] args) {
        try {
            String filePath = "C:\\Users\\liberadosecretaria1\\Documents\\Bloque 3\\LeerDom.xml";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filePath));
            
            // Mostrar IDs
            mostrarIDs(doc);

            // Mostrar autores y títulos
            mostrarAutoresYTitulos(doc);

            // Mostrar títulos y precios ordenados
            mostrarTitulosYPreciosOrdenados(doc);

            // Mostrar libros por género
            mostrarLibrosPorGenero(doc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
// 1. Mostrar los Ids de libros: 
    private static void mostrarIDs(Document doc) {
        NodeList bookList = doc.getElementsByTagName("Book");
        for (int i = 0; i < bookList.getLength(); i++) {
            Element book = (Element) bookList.item(i);
            String id = book.getAttribute("id");
            System.out.println("ID del libro: " + id);
        }
    }
//2. Mostrar los autores y titulos:
    private static void mostrarAutoresYTitulos(Document doc) {
        NodeList bookList = doc.getElementsByTagName("Book");
        for (int i = 0; i < bookList.getLength(); i++) {
            Element book = (Element) bookList.item(i);
            String author = book.getElementsByTagName("Author").item(0).getTextContent();
            String title = book.getElementsByTagName("Title").item(0).getTextContent();
            System.out.println("Autor: " + author + ", Título: " + title);
        }
    }
//3. Mostrar los titulos y precios de barato a caro:
    private static void mostrarTitulosYPreciosOrdenados(Document doc) {
        NodeList bookList = doc.getElementsByTagName("Book");
        ArrayList<Book> books = new ArrayList<>();

        for (int i = 0; i < bookList.getLength(); i++) {
            Element bookElement = (Element) bookList.item(i);
            String title = bookElement.getElementsByTagName("Title").item(0).getTextContent();
            double price = Double.parseDouble(bookElement.getElementsByTagName("Price").item(0).getTextContent());
            books.add(new Book(title, price));
        }

        Collections.sort(books, Comparator.comparingDouble(Book::getPrice));

        for (Book book : books) {
            System.out.println("Título: " + book.getTitle() + ", Precio: " + book.getPrice());
        }
    }
//$. Mostrar Titulos y genero de los libros: 
    private static void mostrarLibrosPorGenero(Document doc) {
        NodeList bookList = doc.getElementsByTagName("Book");
        for (int i = 0; i < bookList.getLength(); i++) {
            Element book = (Element) bookList.item(i);
            String genre = book.getElementsByTagName("Genre").item(0).getTextContent();
            String title = book.getElementsByTagName("Title").item(0).getTextContent();
            System.out.println("Género: " + genre + ", Título: " + title);
        }
    }
    static class Book {
        private String title;
        private double price;

        public Book(String title, double price) {
            this.title = title;
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public double getPrice() {
            return price;
        }
    }
}
