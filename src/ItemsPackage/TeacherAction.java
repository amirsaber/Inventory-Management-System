/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ItemsPackage;

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
import javafx.scene.control.TableView;
import schoolmanegementsssss.SqliteConnection;

/**
 *
 * @author amir
 */
public class TeacherAction {

    Connection con = SqliteConnection.Connector();
    PreparedStatement ps;
    ResultSet rs;
    final ObservableList<TeacherClass> data = FXCollections.observableArrayList();
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "   بنجاح  " + fname.getText() + " "/* + lastname.getText() */ + "" + "تم اضافة صنف ");
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
            Alert alert = new Alert(Alert.AlertType.ERROR, "Add item " + fname.getText() + " " + lastname.getText() + " " + "has been failed");
            alert.setTitle("Failed !!");
            alert.setHeaderText(null);
            alert.showAndWait();
            System.out.println(e.getMessage());
            System.out.println(e.toString());
            ps.close();
        }

    }
/////////////////////////////////////////////////////////////////////////////

    public void UpdateTeacher(JFXTextField id, JFXTextField fname, JFXTextField lastname, JFXComboBox job) throws SQLException {
        int link = getCategoryId(job);
        String query = "Update Items set name=?, company=?, category_id=?"
                + "where ID='" + id.getText() + "'  ";
        try {

            ps = con.prepareStatement(query);

            ps.setString(1, fname.getText());
            ps.setString(2, lastname.getText());
            //  ps.setString(3, gender);
            // ps.setString(4, classs.getCheckModel().getCheckedItems().toString());
            //ps.setString(5, phone.getText());
            // ps.setString(6, email.getText());
            ps.setString(3, String.valueOf(link));
            //    ps.setString(9, salary.getText());
            //   ps.setString(10, age.getText());
            //  ps.setString(11, regdate.getValue().toString());
            ps.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Item " + fname.getText() + " "/* + lastname.getText() + " " */ + "has been updated");
            alert.setTitle("Success !!");
            alert.setHeaderText(null);
            alert.showAndWait();
            ps.close();
            int h = getId();
            id.setText(Integer.toString(h));
            id.clear();
            fname.clear();
            lastname.clear();
            //  gender = "null";
            // gender = "null";
            //  subject.getSelectionModel().clearSelection();
            job.getSelectionModel().clearSelection();

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
            Alert alert = new Alert(Alert.AlertType.WARNING, "Update Item " + fname.getText() + " " + lastname.getText() + " " + "has been failed");
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

            String query = "SELECT o.id, o.name, o.company, i.name\n"
                    + "FROM Items o\n"
                    + "INNER JOIN Category i\n"
                    + "on o.category_id = i.id";
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next()) {

                data.add(new TeacherClass(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
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

    public void loadFromTable(JFXTextField id, JFXTextField fname, JFXTextField lastname, JFXComboBox job, TableView table) throws SQLException {
        try {

            TeacherClass tc = (TeacherClass) table.getSelectionModel().getSelectedItem();
            TeacherClass tc1 = (TeacherClass) table.getSelectionModel().getSelectedItem();
            String query = "Select * from Items where id = ? ";
            ps = con.prepareStatement(query);
            ps.setString(1, tc.getId());
            rs = ps.executeQuery();
            //  String classss = null;
            // String regdatee = "";
            //  String sssssssss;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            while (rs.next()) {
                id.setText(rs.getString(1));
                fname.setText(rs.getString(2));
                lastname.setText(rs.getString(3));
                //    gender.setText(rs.getString(4));
                //   classss = (rs.getString(5));
                //  phone.setText(rs.getString(6));
                //  email.setText(rs.getString(7));
                job.setValue(tc1.getGender());
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
                Logger.getLogger(TeacherClass.class.getName()).log(Level.SEVERE, null, ex);
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

    public void searchTeacher(JFXTextField t, JFXComboBox c, TableView te) throws SQLException {
        String indicator = (String) c.getValue();
        if (indicator.equals("الرقم التسلسلي")) {
            indicator = "id";
        }
        if (indicator.equals("اسم الصنف")) {
            indicator = "name";
        }
        if (indicator.equals("نوع الصنف")) {
            indicator = "category_id";
        }
        if (indicator.equals("الشركه")) {
            indicator = "company";
        }
       
        for (int i = 0; i < te.getItems().size(); i++) { // to when i back to main menu by back button remove all fields on table
            te.getItems().clear();
        }
        try {
            String query;
            if (indicator.equals("gender")) {
                query = "Select * from Items where " + indicator + " like '" + t.getText() + "%' ";
            } else {
                query = "Select * from Items where " + indicator + " like '%" + t.getText() + "%' ";
            }
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next()) {

                data.add(new TeacherClass(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
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
}
