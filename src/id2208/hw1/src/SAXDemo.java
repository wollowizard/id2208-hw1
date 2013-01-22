/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id2208.hw1.src;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author alfredo
 */
public class SAXDemo extends DefaultHandler {

    @Override
    public void startDocument() {
        System.out.println("***Start of Document***");
    }

    @Override
    public void endDocument() {
        System.out.println("***End of Document***");
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("FIRSTNAME")) {
            bfname = true;
        }

        System.out.print("<" + qName);
        int n = attributes.getLength();
        for (int i = 0; i < n; i += 1) {
            System.out.print(" " + attributes.getLocalName(i) + "='" + attributes.getValue(i) + "'");
        }
        System.out.println(">");
    }

    public void characters(char[] ch, int start, int length) {
        System.out.println(new String(ch, start, length).trim());
    }

    public void endElement(String namespaceURI, String localName, String qName) throws
            SAXException {
        System.out.println("</" + qName + ">");
    }

    public static void main(String args[]) throws Exception {
        /*if (args.length != 1) {
         System.err.println("Usage: java SAXDemo <xml-file>");
         System.exit(1);
         }*/
        SAXDemo handler = new SAXDemo();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        parser.parse(new File("src/id2208/hw1/xml/shortCv.xml"), handler);
    }
}
