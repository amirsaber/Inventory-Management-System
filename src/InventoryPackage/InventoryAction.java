/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryPackage;

import ItemsPackage.*;
import AlertMaker.AlertMaker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import schoolmanegementsssss.SqliteConnection;

/**
 *
 * @author amir
 */
public class InventoryAction {

    Connection con = SqliteConnection.Connector();
    PreparedStatement ps;
    ResultSet rs;
    final ObservableList<InventoryClass> data = FXCollections.observableArrayList();
    final ObservableList<String> options = FXCollections.observableArrayList();
    private AlertMaker alert = new AlertMaker();

    public int getId() throws SQLException {
        int res = 0;
        try {
            String query = "select Max(id) from Items";
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

    public int getCategoryId(JFXComboBox j) throws SQLException {
        int res = 0;
        try {
            String query = "select id from Category where name ='" + (String) j.getValue() + "' ";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                res = rs.getInt(1);

            }

            ps.close();
            rs.close();
        } catch (Exception e) {
            ps.close();
            rs.close();
        }
        return res;
    }

    public void addTeacher(JFXTextField id, JFXTextField fname, JFXTextField lastname, JFXComboBox job) throws SQLException {
        int link = getCategoryId(job);
        String query = "Insert into Items (id,name,company,category_id"
                + ") "
                + "VALUES (?,?,?,?)";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, id.getText());
            ps.setString(2, fname.getText());
            ps.setString(3, lastname.getText());
            //   ps.setString(4, gender);
            //  ps.setString(5, classs.getCheckModel().getCheckedItems().toString());
            //   ps.setString(6, phone.getText());
            //   ps.setString(7, email.getText());
            ps.setString(4, String.valueOf(link));
            /// ps.setString(9, (String) subject.getValue());
            //   ps.setString(10, salary.getText());
            //  ps.setString(11, age.getText());
            //  ps.setString(12, regdate.getValue().toString());
            // ps.setString(13, FXMLDocumentController.UserNameSave);
            ps.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, " بنجاح " + fname.getText() + " "/* + lastname.getText() */ + "" + "تم اضافه الصنف");
            alert.setTitle("Success !!");
            alert.setHeaderText(null);
            alert.showAndWait();
            ps.close();
            int h = Integer.parseInt(id.getText()) + 1;
            id.setText(Integer.toString(h));
            id.clear();
            fname.clear();
            lastname.clear();
            //  gender = "null";
            // gender = "null";
            // subject.getSelectionModel().clearSelection();
            // subject.setPromptText("Please Select the subject*");
            //    classs.sett("Please Select Class Of Student *");
            job.getSelectionModel().clearSelection();
            //  subject.getSelectionModel().clearSelection();
            ///    job.setPromptText("Please Select the subject*");
            ///   phone.clear();
            //   email.clear();
            // salary.clear();
            // classs.getCheckModel().clearChecks();
            //  age.clear();
            //  regdate.setValue(LocalDate.now());//set regestraion date by default to today
//
        } catch (SQLException e) {
            ps.close();
            Alert alert = new Alert(Alert.AlertType.ERROR, " حدث خطا لم تتم العملية بنجاح");

            alert.setTitle("Failed !!");
            alert.setHeaderText(null);
            alert.showAndWait();
            System.out.println(e.getMessage());
            System.out.println(e.toString());
            ps.close();
        }

    }
/////////////////////////////////////////////////////////////////////////////

    public void UpdateTeacher(JFXTextField id, JFXTextField fname, JFXTextField lastname,
            JFXTextField job, JFXTextField job1, JFXTextField job11) throws SQLException {
        //  int link = getCategoryId(job);
        String query = "Update Inventory set quantity=? , new=? , used=? "
                + "where name = '" + id.getText() + "'  "
                + "and item_name = '" + fname.getText() + "'  "
                + "and item_type = '" + lastname.getText() + "'  ";
        try {

            ps = con.prepareStatement(query);

            ps.setString(1, job.getText());
            ps.setString(2, job.getText());
            ps.setString(3, job.getText());

            ps.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "تم تحديث الصنف بنجاح");
            alert.setTitle("Success !!");
            alert.setHeaderText(null);
            alert.showAndWait();
            ps.close();
            int h = getId();
            //   id.setText(Integer.toString(h
            id.clear();

            fname.clear();
            lastname.clear();
            id.clear();
            job.clear();
            //  gender = "null";
            // gender = "null";
            //  subject.getSelectionModel().clearSelection();
            //  job.getSelectionModel().clearSelection();

            // phone.clear();
            //  email.clear();
            // salary.clear();
            // classs.getCheckModel().clearChecks();
            // age.clear();
            // regdate.setValue(LocalDate.now());//set regestraion date by default to today
            // phone.clear();
            //   email.clear();
//
        } catch (SQLException e) {
            ps.close();
            Alert alert = new Alert(Alert.AlertType.WARNING, "حدث خطا فشلت العملية");
            alert.setTitle("Failed !!");
            alert.setHeaderText(null);
            alert.showAndWait();
            System.out.println(e.getMessage());
            System.out.println(e.toString());
            ps.close();
        }

    }

    ////////////////////////////////////////////////////////////////////////////
    public void loadTable(TableView table) throws SQLException {
        for (int i = 0; i < table.getItems().size(); i++) { // to when i back to main menu by back button remove all fields on table
            table.getItems().clear();
        }
        try {

            String query = "SELECT * from Inventory where quantity > 0 ";
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next()) {

                data.add(new InventoryClass(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8)
                ));
                table.setItems(data);

            }

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
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    public void loadFromTable(JFXTextField id, JFXTextField fname, JFXTextField lastname,
             JFXTextField lastname1, TableView table,
            JFXTextField lastname2, JFXTextField lastname3,
            JFXTextField lastname4, JFXTextField lastname5
    )
            throws SQLException {

        InventoryClass tc = (InventoryClass) table.getSelectionModel().getSelectedItem();
        String che = tc.getId();
        //   InventoryClass tc1 = (InventoryClass) table.getSelectionModel().getSelectedItem();
        if (che.equals(null)) {
        } else {
            try {

                String query = "Select * from Inventory where name = ? and item_name = ? and item_type = ? ";
                ps = con.prepareStatement(query);
                ps.setString(1, tc.getId());
                ps.setString(2, tc.getFname());
                ps.setString(3, tc.getLname());
                rs = ps.executeQuery();
                //  String classss = null;
                // String regdatee = "";
                //  String sssssssss;
                //   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                while (rs.next()) {
                    id.setText(rs.getString(1));
                    fname.setText(rs.getString(2));
                    lastname.setText(rs.getString(3));
                    lastname1.setText(rs.getString(4));

                    lastname2.setText(rs.getString(5));
                    lastname3.setText(rs.getString(6));
                    lastname4.setText(rs.getString(7));
                    lastname5.setText(rs.getString(8));

                    //    gender.setText(rs.getString(4));
                    //   classss = (rs.getString(5));
                    //  phone.setText(rs.getString(6));
                    //  email.setText(rs.getString(7));
                    //  job.setValue(tc1.getGender());
                    //   subject.setValue(rs.getString(9));
                    //   salary.setText(rs.getString(10));
                    //   age.setText(rs.getString(11));
                    //    regdatee = (rs.getString("registerdate"));
                }
                //  System.out.println(classs);

                //    LocalDate localDate1 = LocalDate.parse(regdatee, formatter);
                //  regdate.setValue(localDate1);

                /*   classs.getCheckModel().clearChecks();
            if (classss.contains("1/1")) {
                classs.getCheckModel().check("1/1");
            }
            if (classss.contains("1/2")) {
                classs.getCheckModel().check("1/2");
            }
            if (classss.contains("1/3")) {
                classs.getCheckModel().check("1/3");
            }
            if (classss.contains("2/1")) {
                classs.getCheckModel().check("2/1");
            }
            if (classss.contains("2/2")) {
                classs.getCheckModel().check("2/2");
            }
            if (classss.contains("2/3")) {
                classs.getCheckModel().check("2/3");
            }
            if (classss.contains("3/1")) {
                classs.getCheckModel().check("3/1");
            }
            if (classss.contains("3/2")) {
                classs.getCheckModel().check("3/2");
            }
            if (classss.contains("3/3")) {
                classs.getCheckModel().check("3/3");
            }
            if (classss.contains("4/1")) {
                classs.getCheckModel().check("4/1");
            }
            if (classss.contains("4/2")) {
                classs.getCheckModel().check("4/2");
            }
            if (classss.contains("4/3")) {
                classs.getCheckModel().check("4/3");
            }
            if (classss.contains("5/1")) {
                classs.getCheckModel().check("5/1");
            }
            if (classss.contains("5/2")) {
                classs.getCheckModel().check("5/2");
            }
            if (classss.contains("5/3")) {
                classs.getCheckModel().check("5/3");
            }
            if (classss.contains("6/1")) {
                classs.getCheckModel().check("6/1");
            }
            if (classss.contains("6/2")) {
                classs.getCheckModel().check("6/2");
            }
            if (classss.contains("6/3")) {
                classs.getCheckModel().check("6/3");
            }*/
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
                    Logger.getLogger(InventoryClass.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////

    public void deleteTeacher(JFXTextField id, JFXTextField fname, JFXTextField lastname, JFXComboBox job) throws SQLException {

        Alert deleteStudent = new Alert(Alert.AlertType.CONFIRMATION);
        deleteStudent.setTitle("Confirmation dialog");
        deleteStudent.setContentText("Are you sure to delete");

        Optional<ButtonType> action = deleteStudent.showAndWait();

        try {
            if (action.get() == ButtonType.OK) {

                String query = "Delete from Items where ID =?  ";

                ps = con.prepareStatement(query);

                ps.setString(1, id.getText());

                ps.executeUpdate();

            }

            ps.close();
            int h = getId();
            id.setText(Integer.toString(h));
            id.clear();

            fname.clear();
            lastname.clear();
            // gender = "null";
            // gender = "null";
            job.getSelectionModel().clearSelection();
            //  subject.getSelectionModel().clearSelection();
            //  classs.setPromptText("Please Select Class Of Student *");
            // /   //    classs.sett("Please Select Class Of Student *");
            //   phone.clear();
            //   email.clear();
            //  salary.clear();
            //   classs.getCheckModel().clearChecks();
            //  age.clear();
            //  regdate.setValue(LocalDate.now());//set regestraion date by default to today

        } catch (Exception e1) {
            Alert CreateNewUserErrorAlert = new Alert(Alert.AlertType.ERROR);
            CreateNewUserErrorAlert.setHeaderText(null);
            CreateNewUserErrorAlert.setContentText(e1.getMessage());
            CreateNewUserErrorAlert.showAndWait();
            System.err.println(e1);
            ps.close();

        }

    }

    public void searchTeacher(JFXComboBox c, TableView te, Label l1, Label l2) throws SQLException {
        int record = 0;
        int quant = 0;
        String check = (String) c.getValue();
        if (check == "عرض جميع المخازن") {
            for (int i = 0; i < te.getItems().size(); i++) { // to when i back to main menu by back button remove all fields on table
                te.getItems().clear();
            }
            // loadTable(te);
            try {
                String query;
                query = "Select * from Inventory where quantity > 0";
                ps = con.prepareStatement(query);

                rs = ps.executeQuery();
                while (rs.next()) {
                    record++;
                    quant = quant + rs.getInt(4);
                    data.add(new InventoryClass(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8)
                    ));
                    te.setItems(data);

                }
                l1.setText(Integer.toString(record));
                l2.setText(Integer.toString(quant));
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
        } else {
            for (int i = 0; i < te.getItems().size(); i++) { // to when i back to main menu by back button remove all fields on table
                te.getItems().clear();
            }
            try {
                String query;
                query = "Select * from Inventory where  name= '" + c.getValue() + "' and quantity > 0 ";
                ps = con.prepareStatement(query);

                rs = ps.executeQuery();
                while (rs.next()) {
                    record++;
                    quant = quant + rs.getInt(4);
                    data.add(new InventoryClass(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8)
                    ));
                    te.setItems(data);

                }
                l1.setText(Integer.toString(record));
                l2.setText(Integer.toString(quant));
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
    }

    public void searchParentt(JFXTextField t, JFXComboBox c, TableView te, JFXComboBox c1, Label l1, Label l2) throws SQLException {
        String indicator = (String) c.getValue();
        int record = 0;
        int quant = 0;
        if (indicator.equals("اسم الصنف")) {
            indicator = "item_name";
        }
        if (indicator.equals("المصدر")) {
            indicator = "item_type";
        }
        if (indicator.equals("النوع")) {
            indicator = "type";
        }
        if (indicator.equals("الشركه")) {
            indicator = "company";
        }

        for (int i = 0; i < te.getItems().size(); i++) {
            te.getItems().clear();
        }

        try {
            String query;
            String check = (String) c1.getValue();
            if (check != "عرض جميع المخازن") {

                query = "Select * from Inventory where " + indicator + " like '" + t.getText() + "%' and quantity > 0"
                        + "and name = '" + c1.getValue() + "' ";
            } else {
                query = "Select * from Inventory where " + indicator + " like '%" + t.getText() + "%'  and quantity > 0";
            }
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next()) {
                record++;
                quant = quant + rs.getInt(4);
                data.add(new InventoryClass(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8)
                ));
                te.setItems(data);

            }
            l1.setText(Integer.toString(record));
            l2.setText(Integer.toString(quant));
            ps.close();
            rs.close();

        } catch (SQLException eViewAllParent) {
            System.err.println(eViewAllParent);
            Alert CreateNewUserErrorAlert = new Alert(Alert.AlertType.ERROR);
            CreateNewUserErrorAlert.setHeaderText(null);
            CreateNewUserErrorAlert.setContentText(eViewAllParent.getMessage());
            CreateNewUserErrorAlert.showAndWait();
            ps.close();
            rs.close();
        }

    }

}
