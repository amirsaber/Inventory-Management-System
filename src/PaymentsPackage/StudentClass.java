/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PaymentsPackage;

/**
 *
 * @author Amir
 */
public class StudentClass {
   String id , fname , lname ,lname1 , gender , classs, phone , email , address , date,type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLname1() {
        return lname1;
    }

    public void setLname1(String lname1) {
        this.lname1 = lname1;
    }

    public StudentClass(String id, String fname, String lname, String lname1, String gender, String classs, String phone, String email, String address, String date, String type) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.lname1 = lname1;
        this.gender = gender;
        this.classs = classs;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.date = date;
        this.type = type;
    }

   
   

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

  
          
}
