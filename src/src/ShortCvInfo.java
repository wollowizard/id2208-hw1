/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

/**
 *
 * @author alfredo
 */
public class ShortCvInfo {

    private String ssn;
    private String fname;
    private String lname;

    public ShortCvInfo(String ssn, String fname, String lname) {
        this.ssn = ssn;
        this.fname = fname;
        this.lname = lname;
    }

    public String getSsn() {
        return ssn;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
    
    
}
