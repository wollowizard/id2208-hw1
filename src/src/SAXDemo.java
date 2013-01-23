/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author alfredo
 *
 */
public class SAXDemo extends DefaultHandler {

    private boolean isSsn = false;
    private boolean isFname = false;
    private boolean isLname = false;
    private String ssn;
    private String fname;
    private String lname;

    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("ssn")) {
            isSsn = true;
        }
        if (qName.equalsIgnoreCase("firstname")) {
            isFname = true;
        }
        if (qName.equalsIgnoreCase("lastname")) {
            isLname = true;
        }
    }

    public void endElement(String uri, String localName,
            String qName) throws SAXException {
    }

    public void characters(char ch[], int start, int length) throws SAXException {

        if (isSsn) {
            ssn = new String(ch, start, length);
            isSsn = false;
        }
        if (isFname) {
            fname = new String(ch, start, length);
            isFname = false;
        }
        if (isLname) {
            lname = new String(ch, start, length);
            isLname = false;
        }
    }

    public ShortCvInfo getShortCvInfo() {
        return new ShortCvInfo(ssn, fname, lname);
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
        ShortCvInfo info=handler.getShortCvInfo();
        System.out.println(info.getFname() + info.getLname() + info.getSsn());
    }
}
