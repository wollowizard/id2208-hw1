/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

/**
 *
 * @author alfredo
 */
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import company.db.*;
import java.util.ArrayList;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

public class JAXBCompanyInfoExtractor {

    private String filepath;
    private ArrayList<String> companycodes = new ArrayList<>();
    private ListCompaniesType listofcompanies;
    JAXBContext jc;

    public JAXBCompanyInfoExtractor(String filepath, ArrayList<String> companies) throws JAXBException {
        this.filepath = filepath;
        this.companycodes = companies;
        jc = JAXBContext.newInstance("company.db");
    }

    public void importXml() throws JAXBException {
        
        Unmarshaller u = jc.createUnmarshaller();
        JAXBElement el = (JAXBElement) u.unmarshal(new File(filepath));
        ListCompaniesType lc = (ListCompaniesType) el.getValue();
        System.out.println("-----" + lc.getCompanyInfo().get(0).getCompanyCode());
        listofcompanies = lc;
    }

    public ListCompaniesType filter() {
        ListCompaniesType newlc = new ListCompaniesType();
        for (String companycode : companycodes) {
            for (CompanyInfoType c : listofcompanies.getCompanyInfo()) {
                if (c.getCompanyCode().equalsIgnoreCase(companycode)) {
                    newlc.getCompanyInfo().add(c);
                }
            }
        }
        return newlc;
    }

    public void exportXml(ListCompaniesType lc, String targetPath) throws JAXBException {

        File file=new File(targetPath);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(new JAXBElement<ListCompaniesType>(new QName("uri", "local"), ListCompaniesType.class, lc), file);
        

    }

    public static void main(String args[]) {
        ArrayList<String> codes = new ArrayList<>();
        codes.add("54321");
        
        try {
            JAXBCompanyInfoExtractor ie = new JAXBCompanyInfoExtractor("src/xml/companyInfo.xml", codes);
            ie.importXml();
            ListCompaniesType lc = ie.filter();
            ie.exportXml(lc, "src/xml/filteredCompanies.xml");
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
