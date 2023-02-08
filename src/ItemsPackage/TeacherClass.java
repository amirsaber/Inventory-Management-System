/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ItemsPackage;

/**
 *
 * @author Amir
 */
public class TeacherClass {
    String id,fname,lname,gender
            ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

  

   

    public TeacherClass(String id, String fname, String lname, String gender) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        
    }

  
}
