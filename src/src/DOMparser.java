/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Gerard
 */
public class DOMparser {
    
    public ArrayList<String> giveCompaniesCodes(String ssnParam){
        ArrayList<String> codes=new ArrayList<String>();
        
        //set SSN field 
        String myssn = ssnParam;
        //if(args.length > 0){
          //  myssn = args[0];
        //}
        
        //Get a factory object for DocumentBuilder objects 
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        
        // to make the parser a validating parse 
        builderFactory.setValidating(true);
        
        // to parse a XML docuemnt with a namespace 
        builderFactory.setNamespaceAware(true);

        //to ignore white spaces between elements
        builderFactory.setIgnoringElementContentWhitespace(true);

        //specifies schema language for validation
        builderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", 
                "http://www.w3.org/2001/XMLSchema");

        //specifies the XML schema document to be used for validation. 
        builderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource",
                "employmentOfficeSchema.xsd");
       
        //Get a DocumentBuilder (parser) object 
        DocumentBuilder builder = null; 
        try {
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DOMparser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //generate Output XML file
        Document output = builder.newDocument();
        
        //create employments root element
        Element rootElement = output.createElement("employments");
	output.appendChild(rootElement);
        //create workExperiences element
        Element wesElement = output.createElement("workExperiences");
	rootElement.appendChild(wesElement);

        //Parse the XML input file to create a document object that represents the input XML file 
        Document document = null;
        try {      
            document = builder.parse(new File("src/xml/employmentOffice.xml"));
        } catch (SAXException | IOException ex) {
            Logger.getLogger(DOMparser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Get the root element of the document
        Element element = document.getDocumentElement();
        
        //Get a list of nodes in the docuemnt
        //NodeList nodes = element.getChildNodes();
        
        //get list of person elements
        NodeList personList = document.getElementsByTagName("person");
        //check person list
        for(int i=0; i<personList.getLength(); i++){
            
            //select element from the list
            Node node = personList.item(i);          

            if(node instanceof Element){
                //get the person element
                Element person = (Element) node;
                
                //get the ssn of the person element
                String ssn = person.getElementsByTagName("ssn").item(0).getTextContent();
                //System.out.println("SSN number: "+ssn);
                
                //check the ssn number
                if(ssn.equals(myssn)){
                    //get the workExpiriences element
                    Element wes = (Element) person.getElementsByTagName("workExperiences").item(0);
                    NodeList weList = wes.getChildNodes();
                    
                    for(int j=0; j<weList.getLength(); j++){
                        
                        //create workExperience element
                        Element weElement = output.createElement("workExperience");
                        wesElement.appendChild(weElement);
                        
                        System.out.println("\nWork experience "+j);
                        Element we = (Element) weList.item(j);
                        
                        //companyCode elememt
                        String companyCode = we.getElementsByTagName("companyCode").item(0).getTextContent();
			Element companyCodeElement = output.createElement("companyCode");
                        companyCodeElement.appendChild(output.createTextNode(companyCode));
                        weElement.appendChild(companyCodeElement);
                        System.out.println("Company code : " + companyCode);
                        
                        
                        //add code of the current company!!!!!!!!!
                        codes.add(companyCode);
                        
                        //companyName element
                        String companyName = we.getElementsByTagName("companyName").item(0).getTextContent();
			Element companyNameElement = output.createElement("companyName");
                        companyNameElement.appendChild(output.createTextNode(companyName));
                        weElement.appendChild(companyNameElement);
                        System.out.println("Company name : " + companyName);
                        
			//fromDate element
                        String fromDate = we.getElementsByTagName("fromDate").item(0).getTextContent();
                        Element fromDateElement = output.createElement("fromDate");
                        fromDateElement.appendChild(output.createTextNode(fromDate));
                        weElement.appendChild(fromDateElement);
                        System.out.println("From : " + fromDate);
                        
                        //upToDate element
			String upToDate = we.getElementsByTagName("upToDate").item(0).getTextContent();
                        Element upToDateElement = output.createElement("upToDate");
                        upToDateElement.appendChild(output.createTextNode(upToDate));
                        weElement.appendChild(upToDateElement);
                        System.out.println("To : " + upToDate);
                    }
                }
            }
        }
        // write the content into xml file
        //get Transformer Factory
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        //get transformer to fill XML output file
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(DOMparser.class.getName()).log(Level.SEVERE, null, ex);
        }
        //generate DOM tree source from the output document
        DOMSource source = new DOMSource(output);
        //get stream to fill the output file
        StreamResult result = new StreamResult(new File("src/xml/employmentOutput.xml"));
        //fill the XML output file using the stream with the DOM tree
        try {
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            Logger.getLogger(DOMparser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codes;
    }
 
    
}
