/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import com.sun.org.apache.bcel.internal.generic.GETFIELD;
import company.db.ListCompaniesType;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author alfredo
 *
 */
public class SAXparser extends DefaultHandler {

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
         System.err.println("Usage: java SAXparser <xml-file>");
         System.exit(1);
         }*/


        SAXparser handler = new SAXparser();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        parser.parse(new File("src/xml/shortCv.xml"), handler);
        ShortCvInfo info = handler.getShortCvInfo();
        System.out.println("Starting computation for " + info.getFname() + " " + info.getLname() + " " + info.getSsn());

        //
        DOMparser domp = new DOMparser();
        ArrayList<String> codes = domp.doIt(info.getSsn());


        //
        JAXBCompanyInfoExtractor ie = new JAXBCompanyInfoExtractor("src/xml/companyInfo.xml", codes);
        ie.importXml();
        ListCompaniesType lc = ie.filter();
        ie.exportXml(lc, "src/xml/companyInfoOutput.xml");

        //xlst to get avg of transcripts
        Source xmlInput = new StreamSource(new File("src/xml/transcript.xml"));
        Source xsl = new StreamSource(new File("src/xml/transcriptTransformer.xsl"));
        Result xmlOutput = new StreamResult(new File("src/xml/transcriptOutput.xml"));
        
        Transformer transformer = TransformerFactory.newInstance().newTransformer(xsl);
        transformer.transform(xmlInput, xmlOutput);
        
        
        ////xlst to merge everything




    }
}