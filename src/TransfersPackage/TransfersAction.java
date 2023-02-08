/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TransfersPackage;

import PaymentsPackage.*;
import AlertMaker.AlertMaker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.controlsfx.control.CheckComboBox;
import schoolmanegementsssss.FXMLDocumentController;
import schoolmanegementsssss.MainSceneFXMLController;
import schoolmanegementsssss.SqliteConnection;

/**
 *
 * @author amir
 */
public class TransfersAction {

    Connection con = SqliteConnection.Connector();
    PreparedStatement ps;
    ResultSet rs;
    final ObservableList<TransfersClass> data = FXCollections.observableArrayList();
    final ObservableList<String> options = FXCollections.observableArrayList();
    private AlertMaker alert = new AlertMaker();

    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    public int getId() throws SQLException {
        int res = 0;
        try {
            String query = "select Max(ID) from Transfers";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);

            }
            res += 1;
            ps.close();
            rs.close();
        } catch (Exception e) {
            ps.close();
            rs.close();
        }
        return res;

    }
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////

    public void addStudent(JFXTextField id, JFXComboBox classs, String gender, JFXTextField parentid,
            JFXTextField lastname, JFXTextField phone, JFXComboBox adress, JFXComboBox employee,
            JFXComboBox fname, JFXDatePicker regdate, JFXComboBox adress1) throws SQLException, IOException {
        String lastnamesave = gender;

        int res = 0;
        // class is item , gender is source , adress is inventory
        int added = Integer.parseInt(lastname.getText());
        boolean isAbilable = IsAvilable(adress, classs, lastnamesave);
        int avilablequan = quantityy(adress, classs, lastnamesave);
        int newquant = newquantityy(adress, classs, lastnamesave);
        int usedquant = oldnewquantityy(adress, classs, lastnamesave);
        String status = (String) adress1.getValue();

        if (!isAbilable) {
            Alert alert = new Alert(Alert.AlertType.ERROR, " هذا الصنف غير موجود بهذا المخزن ");
            alert.setTitle("Failed !!");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (added > avilablequan) {
            Alert alert = new Alert(Alert.AlertType.ERROR, " الكمية المراد صرفها اكبر من الكميه الموجده بالمخزن ");
            alert.setTitle("Failed !!");
            alert.setHeaderText(null);
            alert.showAndWait();

        } else if (status.equals("جديد") && added > newquant) {
            Alert alert = new Alert(Alert.AlertType.ERROR, " الكميات الجديده المطلوبه اكبر من الكميات الجديده الموجوده بالمخزن ");
            alert.setTitle("Failed !!");
            alert.setHeaderText(null);
            alert.showAndWait();

        } else if (status.equals("مستعمل") && added > usedquant) {
            Alert alert = new Alert(Alert.AlertType.ERROR, " الكميات المستعمله المطلوبه اكبر من الكميات المستعمله بالمخزن ");
            alert.setTitle("Failed !!");
            alert.setHeaderText(null);
            alert.showAndWait();

        } else {

            String query = "Insert into Transfers (id,item,source,inv,quantity,department,place,departmentemployee,employee,date,type"
                    + ") "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            try {
                ps = con.prepareStatement(query);
                ps.setString(1, id.getText());
                ps.setString(2, (String) classs.getValue());
                ps.setString(3, gender);
                ps.setString(4, (String) adress.getValue());
                ps.setString(5, lastname.getText());
                ps.setString(6, (String) fname.getValue());
                ps.setString(7, parentid.getText());
                ps.setString(8, phone.getText());
                ps.setString(9, (String) employee.getValue());

                ps.setString(10, regdate.getValue().toString());
                ps.setString(11, (String) adress1.getValue());

                // ps.setString(6, phone.getText());
                //  ps.setString(7, email.getText());
                // ps.setString(8, address.getText());
                //  ps.setString(9, hobbies.getCheckModel().getCheckedItems().toString());
                // ps.setString(10, parentid.getText());
                //  ps.setString(11, birthdate.getValue().toString());
                // ps.setString(12, regdate.getValue().toString());
                // ps.setString(13, FXMLDocumentController.UserNameSave);
                ps.execute();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "تمت العملية بنجاح");
                alert.setTitle("Success !!");
                alert.setHeaderText(null);
                alert.showAndWait();
                ps.close();
                // addItemInnvrntory(adress, classs, gender, lastname);
                int h = Integer.parseInt(id.getText()) + 1;
                id.setText(Integer.toString(h));
                //  fname.clear();
                /////////////////////////////////////////
                /////////////////////////////////////////
                /////////////////////////////////////////
                /////////////////////////////////////////
                try {

                    String query1 = "select quantity from Inventory "
                            + "where "
                            + "name ='" + (String) adress.getValue() + "' And "
                            + "item_name ='" + (String) classs.getValue() + "' And "
                            + "item_type ='" + lastnamesave + "' ";
                    ps = con.prepareStatement(query1);

                    rs = ps.executeQuery();
                    while (rs.next()) {
                        res = rs.getInt(1);
                        System.out.println(res);

                    }
                    System.out.println(res);
                    System.out.println(added);
                    res = res - added;
                    System.out.println(res);

                    ps.close();
                    rs.close();

                } catch (Exception e1) {
                    System.out.println("e1");
                }
                try {

                    System.out.println((String) adress.getValue());

                    System.out.println((String) classs.getValue());
                    System.out.println(lastnamesave);

                    String query2 = "Update Inventory set quantity=? "
                            + "where "
                            + "name ='" + (String) adress.getValue() + "' And "
                            + "item_name ='" + (String) classs.getValue() + "' And "
                            + "item_type ='" + lastnamesave + "' ";

                    Label l = new Label();
                    l.setText(String.valueOf(res));
                    ps = con.prepareStatement(query2);
                    System.out.println(res);
                    ps.setString(1, l.getText());
                    ps.execute();

                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "تم خصم الكميات من المخزن");
                    alert1.setTitle("Success !!");
                    alert1.setHeaderText(null);
                    alert1.showAndWait();
                    ps.close();

                } catch (Exception e2) {
                    System.out.println("e2");

                }
                /////////////////////////////////////////
                /////////////////////////////////////////
                /////////////////////////////////////////
                /////////////////////////////////////////
                /////////////////////////////////////////

                // gender = "null";
                //  id.clear();
                /*  classs.getSelectionModel().clearSelection();
                adress.getSelectionModel().clearSelection();
                fname.getSelectionModel().clearSelection();
                employee.getSelectionModel().clearSelection();
                parentid.clear();
                lastname.clear();
                phone.clear();
                lastname.clear();
                regdate.setValue(LocalDate.now());*/
            } catch (SQLException e) {
                ps.close();
                Alert alert = new Alert(Alert.AlertType.ERROR, "حدث خطا ما");
                alert.setTitle("Failed !!");
                alert.setHeaderText(null);
                alert.showAndWait();
                System.out.println(e.getMessage());
                System.out.println(e.toString());
                ps.close();
            }
            if (status.equals("جديد")) {

                try {
                    String query1 = "select new from Inventory "
                            + "where "
                            + "name ='" + (String) adress.getValue() + "' And "
                            + "item_name ='" + (String) classs.getValue() + "' And "
                            + "item_type ='" + gender + "' ";
                    ps = con.prepareStatement(query1);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        res = rs.getInt(1);

                    }
                    res -= added;
                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    ps.close();
                    rs.close();
                }
                try {

                    String query2 = "Update Inventory set new=? "
                            + "where "
                            + "name ='" + (String) adress.getValue() + "' And "
                            + "item_name ='" + (String) classs.getValue() + "' And "
                            + "item_type ='" + gender + "' ";

                    Label l = new Label();
                    l.setText(String.valueOf(res));
                    ps = con.prepareStatement(query2);
                    System.out.println(res);
                    ps.setString(1, l.getText());
                    ps.execute();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "تم تعديل الكميات بالمخزن");
                    alert.setTitle("Success !!");
                    //   alert.setHeaderText(null);
                    //   alert.showAndWait();
                    gender = "null";
                    // id.clear();
                    classs.getSelectionModel().clearSelection();
                    adress.getSelectionModel().clearSelection();
                    fname.getSelectionModel().clearSelection();
                    employee.getSelectionModel().clearSelection();
                    parentid.clear();
                    lastname.clear();
                    phone.clear();
                    lastname.clear();
                    regdate.setValue(LocalDate.now());
                    ps.close();

                } catch (Exception e2) {
                    System.out.println("e2");

                }

            } else {
                try {
                    String query1 = "select used from Inventory "
                            + "where "
                            + "name ='" + (String) adress.getValue() + "' And "
                            + "item_name ='" + (String) classs.getValue() + "' And "
                            + "item_type ='" + gender + "' ";
                    ps = con.prepareStatement(query1);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        res = rs.getInt(1);

                    }
                    res -= added;
                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    ps.close();
                    rs.close();
                }
                try {

                    String query2 = "Update Inventory set used=? "
                            + "where "
                            + "name ='" + (String) adress.getValue() + "' And "
                            + "item_name ='" + (String) classs.getValue() + "' And "
                            + "item_type ='" + gender + "' ";

                    Label l = new Label();
                    l.setText(String.valueOf(res));
                    ps = con.prepareStatement(query2);
                    System.out.println(res);
                    ps.setString(1, l.getText());
                    ps.execute();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "تم تعديل الكميات بالمخزن");
                    alert.setTitle("Success !!");
                    //   alert.setHeaderText(null);
                    //   alert.showAndWait();
                    gender = "null";
                    // id.clear();
                    classs.getSelectionModel().clearSelection();
                    adress.getSelectionModel().clearSelection();
                    fname.getSelectionModel().clearSelection();
                    employee.getSelectionModel().clearSelection();
                    parentid.clear();
                    lastname.clear();
                    phone.clear();
                    lastname.clear();
                    regdate.setValue(LocalDate.now());
                    ps.close();

                } catch (Exception e2) {
                    System.out.println("e2");

                }
            }

        }//

    }

    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    boolean IsAvilable(JFXComboBox adress, JFXComboBox classs, String lastnamesave) {
        boolean check = false;

        try {

            String query1 = "select quantity from Inventory "
                    + "where "
                    + "name ='" + (String) adress.getValue() + "' And "
                    + "item_name ='" + (String) classs.getValue() + "' And "
                    + "item_type ='" + lastnamesave + "' ";
            ps = con.prepareStatement(query1);

            rs = ps.executeQuery();
            while (rs.next()) {

                check = true;
            }

            ps.close();
            rs.close();

        } catch (Exception e1) {
            System.out.println("e1");
        }

        return check;

    }
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////

    int quantityy(JFXComboBox adress, JFXComboBox classs, String lastnamesave) {
        int res = 0;

        try {

            String query1 = "select quantity from Inventory "
                    + "where "
                    + "name ='" + (String) adress.getValue() + "' And "
                    + "item_name ='" + (String) classs.getValue() + "' And "
                    + "item_type ='" + lastnamesave + "' ";
            ps = con.prepareStatement(query1);

            rs = ps.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);
                System.out.println(res);

            }

            ps.close();
            rs.close();

        } catch (Exception e1) {
            System.out.println("e1");
        }
        return res;
    }

///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
    int newquantityy(JFXComboBox adress, JFXComboBox classs, String lastnamesave) {
        int res = 0;

        try {

            String query1 = "select new from Inventory "
                    + "where "
                    + "name ='" + (String) adress.getValue() + "' And "
                    + "item_name ='" + (String) classs.getValue() + "' And "
                    + "item_type ='" + lastnamesave + "' ";
            ps = con.prepareStatement(query1);

            rs = ps.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);
                System.out.println(res);

            }

            ps.close();
            rs.close();

        } catch (Exception e1) {
            System.out.println("e1");
        }
        return res;
    }

///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
    int oldnewquantityy(JFXComboBox adress, JFXComboBox classs, String lastnamesave) {
        int res = 0;

        try {

            String query1 = "select used from Inventory "
                    + "where "
                    + "name ='" + (String) adress.getValue() + "' And "
                    + "item_name ='" + (String) classs.getValue() + "' And "
                    + "item_type ='" + lastnamesave + "' ";
            ps = con.prepareStatement(query1);

            rs = ps.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);
                System.out.println(res);

            }

            ps.close();
            rs.close();

        } catch (Exception e1) {
            System.out.println("e1");
        }
        return res;
    }

///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    public void addItemInnvrntory(JFXComboBox adress, JFXComboBox classs, String gender, JFXTextField lastname) throws SQLException {
    }

    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    public void updateStudent(JFXTextField id, JFXComboBox classs, String gender, JFXTextField parentid,
            JFXTextField lastname, JFXTextField phone, JFXComboBox adress, JFXComboBox employee,
            JFXTextArea fname, JFXDatePicker regdate) throws SQLException, IOException {
        /*
         String query = "Insert into Orders (ID,itemname,source,quantity,cost,employee_name,department_id,notes,date"
                + ") "
                + "VALUES (?,?,?,?,?,?,?,?,?)";*/
        String query = "Update Orders set itemname=?, source=?,  source_information=? ,quantity=?,"
                + " cost=?, employee_name=?  ,department_id=?, notes=? , date=? "
                + "where ID='" + id.getText() + "'  ";
        try {
            ps = con.prepareStatement(query);
            //   ps.setString(1, id.getText());
            //     ps.setString(1, id.getText());
            ps.setString(1, (String) classs.getValue());
            ps.setString(2, gender);
            ps.setString(3, parentid.getText());
            ps.setString(4, lastname.getText());
            ps.setString(5, phone.getText());
            ps.setString(6, (String) employee.getValue());
            ps.setString(7, (String) adress.getValue());
            ps.setString(8, fname.getText());
            ps.setString(9, regdate.getValue().toString());

            ps.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Order " + "has been update");
            alert.setTitle("Success !!");
            alert.setHeaderText(null);
            alert.showAndWait();
            ps.close();
            int h = getId();
            id.setText(Integer.toString(h));
            fname.clear();
            lastname.clear();
            gender = "null";
            gender = "null";
            classs.getSelectionModel().clearSelection();
            adress.getSelectionModel().clearSelection();
            employee.getSelectionModel().clearSelection();
            //  classs.setPromptText("Please Select Class Of Student *");
            //    classs.sett("Please Select Class Of Student *");
            phone.clear();

            parentid.clear();

            regdate.setValue(LocalDate.now());//set regestraion date by default to today

        } catch (SQLException e) {
            ps.close();

            Alert alert = new Alert(Alert.AlertType.WARNING, "Update Order " + "has been failed");
            alert.setTitle("Failed !!");
            alert.setHeaderText(null);
            alert.showAndWait();
            System.out.println(e.getMessage());
            System.out.println(e.toString());
            ps.close();
        }

    }
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////

    public void loadTable(TableView table, Text SumTotal, Text CountTotal) throws SQLException {

        int sum = 0;
        int count = 0;
        for (int i = 0; i < table.getItems().size(); i++) { // to when i back to main menu by back button remove all fields on table
            table.getItems().clear();
        }
        try {

            String query = "Select * from Transfers";
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next()) {

                data.add(new TransfersClass(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11)
                ));
                table.setItems(data);
                float c = Float.parseFloat(rs.getString(5));
                sum += c;
                count++;
            }
            SumTotal.setText(Float.toString(sum));
            CountTotal.setText(Integer.toString(count));
            ps.close();
            rs.close();
            //        table.getColumns().addAll(tid, tfn, tln, te, tg, tmn, tm, tbd, tvd);

        } catch (Exception eViewAllPatient) {
            System.err.println(eViewAllPatient);
            Alert CreateNewUserErrorAlert = new Alert(Alert.AlertType.ERROR);
            CreateNewUserErrorAlert.setHeaderText(null);
            CreateNewUserErrorAlert.setContentText(eViewAllPatient.getMessage());
            CreateNewUserErrorAlert.showAndWait();
            ps.close();
            rs.close();
        }
    }
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////

    public void loadFromTable(JFXTextField id, JFXComboBox classs, Label l, JFXTextField parentid,
            JFXTextField lastname, JFXTextField phone, JFXComboBox adress, JFXComboBox employee,
            JFXComboBox fname,
            JFXDatePicker regdate, TableView table, JFXComboBox adress1
    ) throws SQLException {
        try {

            TransfersClass sc = (TransfersClass) table.getSelectionModel().getSelectedItem();
            String query = "Select * from Transfers where ID = ? ";
            ps = con.prepareStatement(query);
            ps.setString(1, sc.getId());

            rs = ps.executeQuery();
            String hobbiess = null;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String birth = "";
            String reg;
            while (rs.next()) {

                /*  ps.setString(1, id.getText());
            ps.setString(2, (String) classs.getValue());
            ps.setString(3, gender);
            ps.setString(4, (String) adress.getValue());
            ps.setString(5, lastname.getText());
            ps.setString(6, (String) fname.getValue());
            ps.setString(7, parentid.getText());
            ps.setString(8, phone.getText());
            ps.setString(9, (String) employee.getValue());*/
                id.setText(rs.getString(1));
                classs.setValue(rs.getString(2));
                l.setText(rs.getString(3));
                adress.setValue(rs.getString(4));
                lastname.setText(rs.getString(5));
                fname.setValue(rs.getString(6));
                parentid.setText(rs.getString(7));
                phone.setText(rs.getString(8));
                employee.setValue(rs.getString(9));
                birth = (rs.getString(10));
                adress1.setValue(rs.getString(11));
                LocalDate localDate1 = LocalDate.parse(birth, formatter);
                regdate.setValue(localDate1);

            }
            System.out.println(hobbiess);
            LocalDate localDate1 = LocalDate.parse(birth, formatter);
            //  LocalDate localDate2 = LocalDate.parse(regdatee, formatter);
            //    birthdate.setValue(localDate1);

            //   regdate.setValue(localDate2);
            //  hobbies.getCheckModel().clearChecks();
            /*   if (hobbiess.contains("Reading")) {
                hobbies.getCheckModel().check("Reading");
            }
            if (hobbiess.contains("Writing")) {
                hobbies.getCheckModel().check("Writing");
            }
            if (hobbiess.contains("Computer")) {
                hobbies.getCheckModel().check("Computer");
            }
            if (hobbiess.contains("Coding")) {
                hobbies.getCheckModel().check("Coding");
            }
            if (hobbiess.contains("Watching TV")) {
                hobbies.getCheckModel().check("Watching TV");
            }
            if (hobbiess.contains("Going to Movies")) {
                hobbies.getCheckModel().check("Going to Movies");
            }
            if (hobbiess.contains("Walking")) {
                hobbies.getCheckModel().check("Walking");
            }
            if (hobbiess.contains("Running")) {
                hobbies.getCheckModel().check("Running");
            }
            if (hobbiess.contains("Singing")) {
                hobbies.getCheckModel().check("Singing");
            }
            if (hobbiess.contains("Acting")) {
                hobbies.getCheckModel().check("Acting");
            }
            if (hobbiess.contains("Painting")) {
                hobbies.getCheckModel().check("Painting");
            }
            if (hobbiess.contains("Football")) {
                hobbies.getCheckModel().check("Football");
            }
            if (hobbiess.contains("Handball")) {
                hobbies.getCheckModel().check("Handball");
            }
            if (hobbiess.contains("Swimming")) {
                hobbies.getCheckModel().check("Swimming");
            }
            if (hobbiess.contains("Tennis")) {
                hobbies.getCheckModel().check("Tennis");
            }
            if (hobbiess.contains("Studying")) {
                hobbies.getCheckModel().check("Studying");
            }/*/
            ps.close();
            rs.close();

        } catch (Exception e) {

            System.err.println(e);
            Alert CreateNewUserErrorAlert = new Alert(Alert.AlertType.ERROR);
            CreateNewUserErrorAlert.setHeaderText(null);
            CreateNewUserErrorAlert.setContentText(e.getMessage());
            CreateNewUserErrorAlert.showAndWait();
            try {
                ps.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentClass.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    ////////////////////////////////////////////////////// ///////////////////////////////////////
    ///////////////////////////////////////

    public void deleteStudent(JFXTextField id, JFXComboBox classs, String gender, JFXTextField parentid,
            JFXTextField lastname, JFXTextField phone, JFXComboBox adress, JFXComboBox employee,
            JFXComboBox fname, JFXDatePicker regdate, JFXComboBox adress1) throws SQLException {
        //  conn = DriverManager.getConnection("jdbc:sqlite:Clinic_Manager_DB.db");
        String lastnamesave = gender;
        String classsave = (String) classs.getValue();
        String adresssave = (String) adress.getValue();
        int status_added = Integer.parseInt(lastname.getText());

        String status = (String) adress1.getValue();

        Alert deleteStudent = new Alert(Alert.AlertType.CONFIRMATION);
        deleteStudent.setTitle("Confirmation dialog");
        deleteStudent.setContentText("Are you sure to delete");
        deleteStudent.setHeaderText("Delete");
        int res = 0;
        int added = Integer.parseInt(lastname.getText());
        Optional<ButtonType> action = deleteStudent.showAndWait();

        try {
            if (action.get() == ButtonType.OK) {

                String query = "Delete from Transfers where id =?  ";

                ps = con.prepareStatement(query);

                ps.setString(1, id.getText());

                ps.executeUpdate();

            }

            ps.close();
            int h = getId();
            id.setText(Integer.toString(h));
            //   fname.clear();

            employee.getSelectionModel().clearSelection();

            phone.clear();

            parentid.clear();
            regdate.setValue(LocalDate.now());//set regestraion date by default to today

        } catch (Exception e1) {
            Alert CreateNewUserErrorAlert = new Alert(Alert.AlertType.ERROR);
            CreateNewUserErrorAlert.setHeaderText(null);
            CreateNewUserErrorAlert.setContentText(e1.getMessage());
            CreateNewUserErrorAlert.showAndWait();
            //   System.err.println(e1);
            ps.close();

        }
        try {
            if (action.get() == ButtonType.OK) {
                String query1 = "select quantity from Inventory "
                        + "where "
                        + "name ='" + (String) adress.getValue() + "' And "
                        + "item_name ='" + (String) classs.getValue() + "' And "
                        + "item_type ='" + lastnamesave + "' ";
                ps = con.prepareStatement(query1);

                rs = ps.executeQuery();
                while (rs.next()) {
                    res = rs.getInt(1);
                    System.out.println(res);

                }
                System.out.println(res);
                System.out.println(added);
                res = res + added;
                System.out.println(res);

                ps.close();
                rs.close();
            }
        } catch (Exception e1) {
            System.out.println("e1");
        }

        try {
            if (action.get() == ButtonType.OK) {
                System.out.println((String) adress.getValue());

                System.out.println((String) classs.getValue());
                System.out.println(lastnamesave);

                String query2 = "Update Inventory set quantity=? "
                        + "where "
                        + "name ='" + (String) adress.getValue() + "' And "
                        + "item_name ='" + (String) classs.getValue() + "' And "
                        + "item_type ='" + lastnamesave + "' ";

                Label l = new Label();
                l.setText(String.valueOf(res));
                ps = con.prepareStatement(query2);
                System.out.println(res);
                ps.setString(1, l.getText());
                ps.execute();

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "تم استرجاع الكمية بالمخازن");
                alert.setTitle("Success !!");
                alert.setHeaderText(null);
                alert.showAndWait();
                ps.close();
                /*/
                JFXTextField id, JFXComboBox classs, String gender, JFXTextField parentid,
            JFXTextField lastname, JFXTextField phone, JFXComboBox adress, JFXComboBox employee,
            JFXComboBox fname, JFXDatePicker regdate*/

 /*  id.clear();
                classs.getSelectionModel().clearSelection();
                adress.getSelectionModel().clearSelection();
                fname.getSelectionModel().clearSelection();
                employee.getSelectionModel().clearSelection();
                parentid.clear();
                lastname.clear();
                phone.clear();
                lastname.clear();
                regdate.setValue(LocalDate.now());
                gender = "null";*/
            }

        } catch (Exception e2) {
            System.out.println("e2");

        }
        if (action.get() == ButtonType.OK) {
            if (status == "جديد") {

                try {
                    String query1 = "select new from Inventory "
                            + "where "
                            + "name ='" + (String) adress.getValue() + "' And "
                            + "item_name ='" + (String) classs.getValue() + "' And "
                            + "item_type ='" + gender + "' ";
                    ps = con.prepareStatement(query1);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        res = rs.getInt(1);

                    }
                    res += status_added;
                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    ps.close();
                    rs.close();
                }
                try {

                    String query2 = "Update Inventory set new=? "
                            + "where "
                            + "name ='" + (String) adress.getValue() + "' And "
                            + "item_name ='" + (String) classs.getValue() + "' And "
                            + "item_type ='" + gender + "' ";

                    Label l = new Label();
                    l.setText(String.valueOf(res));
                    ps = con.prepareStatement(query2);
                    System.out.println(res);
                    ps.setString(1, l.getText());
                    ps.execute();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "تم تعديل الكميات بالمخزن");
                    alert.setTitle("Success !!");
                    //   alert.setHeaderText(null);
                    //   alert.showAndWait();
                    id.clear();
                    classs.getSelectionModel().clearSelection();
                    adress.getSelectionModel().clearSelection();
                    fname.getSelectionModel().clearSelection();
                    employee.getSelectionModel().clearSelection();
                    parentid.clear();
                    lastname.clear();
                    phone.clear();
                    lastname.clear();
                    regdate.setValue(LocalDate.now());
                    gender = "null";
                    ps.close();

                } catch (Exception e2) {
                    System.out.println("e2");

                }
            } else {
                try {
                    String query1 = "select used from Inventory "
                            + "where "
                            + "name ='" + (String) adress.getValue() + "' And "
                            + "item_name ='" + (String) classs.getValue() + "' And "
                            + "item_type ='" + gender + "' ";
                    ps = con.prepareStatement(query1);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        res = rs.getInt(1);

                    }
                    res += status_added;
                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    ps.close();
                    rs.close();
                }
                try {

                    String query2 = "Update Inventory set used=? "
                            + "where "
                            + "name ='" + (String) adress.getValue() + "' And "
                            + "item_name ='" + (String) classs.getValue() + "' And "
                            + "item_type ='" + gender + "' ";

                    Label l = new Label();
                    l.setText(String.valueOf(res));
                    ps = con.prepareStatement(query2);
                    System.out.println(res);
                    ps.setString(1, l.getText());
                    ps.execute();
                    id.clear();
                    classs.getSelectionModel().clearSelection();
                    adress.getSelectionModel().clearSelection();
                    fname.getSelectionModel().clearSelection();
                    employee.getSelectionModel().clearSelection();
                    parentid.clear();
                    lastname.clear();
                    phone.clear();
                    lastname.clear();
                    regdate.setValue(LocalDate.now());
                    gender = "null";
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "تم تعديل الكميات بالمخزن");
                    alert.setTitle("Success !!");
                    //   alert.setHeaderText(null);
                    //   alert.showAndWait();
                    ps.close();

                } catch (Exception e2) {
                    System.out.println("e2");

                }
            }

        }
        ps.close();
    }
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////

    public void searchStudent(JFXTextField t, JFXComboBox c, DatePicker fromdatepicker, DatePicker todatepicker,
            Text SumTotal, Text CountTotal,
            TableView te) throws SQLException {
        String indicator = (String) c.getValue();
        if (indicator.equals("الصنف")) {
            indicator = "item";
        }
        if (indicator.equals("المخزن")) {
            indicator = "inv";
        }
        if (indicator.equals("مصدر الصنف")) {
            indicator = "source";
        }
        if (indicator.equals("القسم")) {
            indicator = "department";
        }
        if (indicator.equals("موظف فرع النظم")) {
            indicator = "employee";
        }
        for (int i = 0; i < te.getItems().size(); i++) { // to when i back to main menu by back button remove all fields on table
            te.getItems().clear();
        }
        try {
            String query;
            if (indicator == "gender"
                    || ((TextField) todatepicker.getEditor()).getText().isEmpty()
                    || ((TextField) fromdatepicker.getEditor()).getText().isEmpty()) {

                query = "Select * from Transfers where " + indicator + " like '%" + t.getText() + "%' ";
                System.out.println(query);
            } else {
                DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String From = fromdatepicker.getValue().format(myFormat);
                String To = todatepicker.getValue().format(myFormat);
                query = "Select * from Transfers where " + indicator + " like '%" + t.getText() + "%' "
                        + " and date between '" + From + "' and '" + To + "'"
                        + ""
                        + "";

                System.out.println(query);
            }
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next()) {

                data.add(new TransfersClass(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11)
                ));
                te.setItems(data);

            }

            ps.close();
            rs.close();

        } catch (Exception eViewAllPatient) {
            System.err.println(eViewAllPatient);
            Alert CreateNewUserErrorAlert = new Alert(Alert.AlertType.ERROR);
            CreateNewUserErrorAlert.setHeaderText(null);
            CreateNewUserErrorAlert.setContentText(eViewAllPatient.getMessage());
            CreateNewUserErrorAlert.showAndWait();
            ps.close();
            rs.close();
        }

    }

///////////////////////////////////////
    ///////////////////////////////////////
}///////
