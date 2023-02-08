/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TransfersPackage;

import PaymentsPackage.*;
import AlertMaker.AlertMaker;
import ShowParent.ShowParentFXMLController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.textfield.TextFields;
import schoolmanegementsssss.MainSceneFXMLController;
import schoolmanegementsssss.SqliteConnection;

/**
 * FXML Controller class
 *
 * @author amir
 */
public class TransfersFXMLController implements Initializable {

    @FXML
    private JFXTextField StudentId;

    @FXML
    private JFXTextField StudentLastName;
    @FXML
    private JFXRadioButton MaleRadioButton;
    @FXML
    private JFXRadioButton FemaleRadioButton;
    @FXML
    private JFXComboBox<String> ClassComboBox;

    @FXML
    private JFXComboBox<String> Adress;

    @FXML
    private JFXTextField ParentName;

    @FXML
    private JFXDatePicker RegisterDate;

    @FXML
    private TableColumn<TransfersClass, String> StudentIDCol;
    @FXML
    private TableColumn<TransfersClass, String> FirstNameCol;
    @FXML
    private TableColumn<TransfersClass, String> LastNameCol;
    @FXML
    private TableColumn<TransfersClass, String> LastNameCol1;
    @FXML
    private TableColumn<TransfersClass, String> GenderCol;
    @FXML
    private TableColumn<TransfersClass, String> ClassCol;
    @FXML
    private TableColumn<TransfersClass, String> PhoneCol;
    @FXML
    private TableColumn<TransfersClass, String> EmailCol;
    @FXML
    private TableColumn<TransfersClass, String> AddrssCol;
    @FXML
    private TableColumn<TransfersClass, String> HobbiesCol;
    @FXML
    private TableColumn<TransfersClass, String>  HobbiesCol1;

    @FXML
    private TableView<TransfersClass> table;
    @FXML
    private JFXTextField SearchTectField;
    @FXML
    private JFXComboBox<String> SearchComboBox;
    final ObservableList<String> strings = FXCollections.observableArrayList();
    TransfersAction StudentActionObject = new TransfersAction();
    String StudentGenderStrng = "null";
    ToggleGroup group = new ToggleGroup();//new toggle group to make user select only one type of gender
    @FXML
    private AnchorPane anch;
    @FXML
    private JFXComboBox<String> employeeCombobox;
    @FXML
    private JFXComboBox<String> ClassComboBox1;
    @FXML
    private JFXTextField depemp;
    @FXML
    private DatePicker todatepicker;
    @FXML
    private DatePicker fromdatepicker;
    @FXML
    private Text CountTotal;
    @FXML
    private Text SumTotal;
    @FXML
    private JFXComboBox<String> ClassComboBox11;

    @FXML
    private void AddStudent() throws SQLException, IOException {

        if (MaleRadioButton.isSelected()) {
            StudentGenderStrng = "عهدة النظم";

        } else {
            StudentGenderStrng = "مشتروات";
        }

        /*/
        StudentId, ClassComboBox,StudentGenderStrng, ParentName,
                    StudentLastName , depemp,  Adress,employeeCombobox,
                        s   ss  s
                   ,
                     RegisterDate*/
        if (StudentId.getText().isEmpty()
                || ClassComboBox.getValue() == null
                || ClassComboBox1.getValue() == null
                || StudentGenderStrng.equals("null")
                || ParentName.getText().isEmpty()
                || StudentLastName.getText().isEmpty()
                || depemp.getText().isEmpty()
                || Adress.getValue() == null
                || employeeCombobox.getValue() == null /*  || ((TextField) RegisterDate.getEditor()).getText().isEmpty()*/) {
            AlertMaker a = new AlertMaker();
            a.showErrorMessage("تنبيه", "من فضلك ادخل كافه البيانات المطلوبه");

        } else {
            /*(JFXTextField id, JFXComboBox classs, String gender, JFXTextField parentid,
            JFXTextField lastname, JFXTextField phone, JFXComboBox adress,JFXComboBox employee,
             JFXTextArea fname, JFXDatePicker regdate)*/
            StudentActionObject.addStudent(StudentId, ClassComboBox, StudentGenderStrng, ParentName,
                    StudentLastName, depemp, Adress, employeeCombobox,
                    ClassComboBox1,
                    RegisterDate, ClassComboBox11
            );
            StudentGenderStrng = "null";
            //  MaleRadioButton.setSelected(true);
            StudentActionObject.loadTable(table, SumTotal, CountTotal);
        }

    }
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    public static String ParentIDSave;
    //////////////////////////////////////////////////////
    ////////////////////////////////////////////////////// ///////////////////////////////////////
    ///////////////////////////////////////

    public static String getParentIDSave() {
        return ParentIDSave;
    }

    @FXML
    private void UpdateStudent() throws SQLException, IOException {
        /*
        if (MaleRadioButton.isSelected()) {
            StudentGenderStrng = "عهدة النظم";

        } else {
            StudentGenderStrng = "مشتروات";
        }
        if ( StudentLastName.getText().isEmpty() || StudentGenderStrng.equals("null")
                || ClassComboBox.getSelectionModel().getSelectedItem().isEmpty() || PhoneNumber.getText().isEmpty()
                || ParentName.getText().isEmpty()) {
            AlertMaker a = new AlertMaker();
            a.showErrorMessage("Attention", "Please fill out all required fields");
        } else {
            StudentActionObject.updateStudent(StudentId, ClassComboBox,StudentGenderStrng, ParentName,
                    StudentLastName , PhoneNumber,  Adress,employeeCombobox,
                    StudentFirstName
                   ,
                     RegisterDate);
            StudentGenderStrng = "null";
            MaleRadioButton.setSelected(true);
            StudentActionObject.loadTable(table);
        }
         */
    }

    @FXML
    private void LoadFromTable() throws SQLException {
        /**
         *
         * JFXTextField id, JFXComboBox classs, Label gender, JFXTextField
         * parentid, JFXTextField lastname, JFXTextField phone, JFXComboBox
         * adress,JFXComboBox employee, JFXTextArea fname, JFXDatePicker regdate
         */
        Label l = new Label();
        StudentActionObject.loadFromTable(StudentId, ClassComboBox, l, ParentName,
                StudentLastName, depemp, Adress, employeeCombobox,
                ClassComboBox1,
                RegisterDate, table,ClassComboBox11);
        if (l.getText().equals("عهدة النظم")) {
            MaleRadioButton.setSelected(true);
        } else {
            FemaleRadioButton.setSelected(true);
        }
    }
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    ////////////////////////////////////////////////////// ///////////////////////////////////////
    ///////////////////////////////////////

    @FXML
    private void DeleteStudent() throws SQLException, IOException {
        if (MaleRadioButton.isSelected()) {
            StudentGenderStrng = "عهدة النظم";

        } else {
            StudentGenderStrng = "مشتروات";
        }
        if (StudentId.getText().isEmpty()
                || ClassComboBox.getValue() == null
                || ClassComboBox1.getValue() == null
                || StudentGenderStrng.equals("null")
                || ParentName.getText().isEmpty()
                || StudentLastName.getText().isEmpty()
                || depemp.getText().isEmpty()
                || Adress.getValue() == null
                || employeeCombobox.getValue() == null /*|| ((TextField) RegisterDate.getEditor()).getText().isEmpty()*/) {
            AlertMaker a = new AlertMaker();
            a.showErrorMessage("تنبيه", "من فضلك ادخل كافه البيانات المطلوبه");
        } else {
            StudentActionObject.deleteStudent(StudentId, ClassComboBox, StudentGenderStrng, ParentName,
                    StudentLastName, depemp, Adress, employeeCombobox,
                    ClassComboBox1,
                    RegisterDate, ClassComboBox11);
            MaleRadioButton.setSelected(true);
            StudentActionObject.loadTable(table, SumTotal, CountTotal);
        }

    }
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    ////////////////////////////////////////////////////// ///////////////////////////////////////
    ///////////////////////////////////////

    @FXML
    private void SearchStudent() throws SQLException {

        StudentActionObject.searchStudent(SearchTectField, SearchComboBox, fromdatepicker, todatepicker,
                SumTotal, CountTotal,
                table);

    }

///////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////
    @FXML
    private void LoadTableeeaaa() throws SQLException {
        Connection conn = SqliteConnection.Connector();
        StudentIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        FirstNameCol.setCellValueFactory(new PropertyValueFactory<>("fname"));
        LastNameCol.setCellValueFactory(new PropertyValueFactory<>("lname"));
        LastNameCol1.setCellValueFactory(new PropertyValueFactory<>("lname1"));
        GenderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        ClassCol.setCellValueFactory(new PropertyValueFactory<>("classs"));
        PhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        EmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        AddrssCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        HobbiesCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        HobbiesCol1.setCellValueFactory(new PropertyValueFactory<>("type"));

        String query = "";
        ResultSet rs;
        PreparedStatement ps;
        int sum = 0;
        int count = 0;
        final ObservableList<TransfersClass> data = FXCollections.observableArrayList();
        if (((TextField) todatepicker.getEditor()).getText().isEmpty()
                || ((TextField) fromdatepicker.getEditor()).getText().isEmpty() //   || OrderIndicator.getText().equals(" ")
                ) {
            AlertMaker alert = new AlertMaker();
            //  a.showErrorMessage("Attention", "Please fill out all required fields");
            alert.showErrorMessage("تنبيه", "ادخل تاريخ البداية و تاريخ النهاية");

        } else {
            SearchComboBox.getSelectionModel().clearSelection();
            SearchTectField.clear();
            for (int i = 0; i < table.getItems().size(); i++) {
                table.getItems().clear();
            }
            DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String From = fromdatepicker.getValue().format(myFormat);
            String To = todatepicker.getValue().format(myFormat);

            try {

                int index;
                index = SearchComboBox.getSelectionModel().getSelectedIndex();
                String sdt = SearchComboBox.getValue();
                System.out.println(From);
                System.out.println(To);

                query = "SELECT *  FROM    Transfers where date >= '" + From + "' and date <= '" + To + "' ";
                System.out.println(query);
                System.out.println(query);
                ps = conn.prepareStatement(query);

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

            } catch (Exception eViewAllPatient) {
                System.err.println(eViewAllPatient);
                Alert CreateNewUserErrorAlert = new Alert(Alert.AlertType.ERROR);
                CreateNewUserErrorAlert.setHeaderText(null);
                CreateNewUserErrorAlert.setContentText(eViewAllPatient.getMessage());
                CreateNewUserErrorAlert.showAndWait();

            }

        }

    }
///////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////

    @FXML
    private void loadaa() {
        try {
            StudentActionObject.loadTable(table, SumTotal, CountTotal);
        } catch (SQLException e1) {
        }
    }
///////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////
////////////////////////
///////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> StudentId.selectEnd());//to request focus when scene start
        // BirthDate.setValue(LocalDate.of(2000, Month.JANUARY, 1));//set local date for birthday pickerto first day in 2000's 
        RegisterDate.setValue(LocalDate.now());//set regestraion date by default to today
        //BirthDate.setShowWeekNumbers(false); // false to prevent user from input incorrect date
        RegisterDate.setShowWeekNumbers(false);// false to prevent user from input incorrect date
        strings.addAll("Reading", "Writing", "Studying", "Computer", "Coding", "Watching TV", "Going to Movies", "Walking",
                "Running", "Singing", "Acting", "Painting", "Football", "Handball", "Swimming", "Tennis"
        );// set some hobbies in list
        // checkComboBox.getItems().addAll(strings);// add list into checkcombobox

        MaleRadioButton.setToggleGroup(group);//add male radio button to group
        FemaleRadioButton.setToggleGroup(group);//add female radio button to group
        FemaleRadioButton.selectedColorProperty().set(Color.RED);//change selction color for female radio button

        StudentLastName.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                ParentName.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });//number validation for parent id textfield
        StudentId.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                StudentId.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });//number validation for student id textfield
        /*  PhoneNumber.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                PhoneNumber.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });//number validation for student phone number textfield*/

        try {
            int returnId = StudentActionObject.getId();
            StudentId.setText(Integer.toString(returnId));

        } catch (Exception e) {
        }

        SearchComboBox.getItems().addAll("الصنف", "المخزن", "مصدر الصنف",
                "القسم", "موظف فرع النظم"
        );
        MaleRadioButton.setSelected(true);
        //////////////////////////////////
        //////////////////////////////////
        //////////////////////////////////
/* this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.lname1 = lname1;
        this.gender = gender;
        this.classs = classs;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.date = date;*/
        StudentIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        FirstNameCol.setCellValueFactory(new PropertyValueFactory<>("fname"));
        LastNameCol.setCellValueFactory(new PropertyValueFactory<>("lname"));
        LastNameCol1.setCellValueFactory(new PropertyValueFactory<>("lname1"));
        GenderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        ClassCol.setCellValueFactory(new PropertyValueFactory<>("classs"));
        PhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        EmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        AddrssCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        HobbiesCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        HobbiesCol1.setCellValueFactory(new PropertyValueFactory<>("type"));
        
         


        //////////////////////////////////
        //////////////////////////////////
        //////////////////////////////////
        /* anch.setOnMouseEntered(e -> {
            try {
               StudentActionObject.loadTable(table, SumTotal, CountTotal);

            } catch (SQLException e1) {
            }

        });*/
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        ParentIDSave = ParentName.getText();
                        ShowParentFXMLController s = new ShowParentFXMLController();

                        try {
                            s.startStage();
                        } catch (IOException ex) {
                            Logger.getLogger(TransfersFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });

        try {
            Connection c = SqliteConnection.Connector();
            String SQLq = "SELECT name from Employees";
            PreparedStatement ps = c.prepareStatement(SQLq);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                employeeCombobox.getItems().add(rs.getString("name"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {

        }

        try {
            Connection c = SqliteConnection.Connector();
            String SQLq = "SELECT name from Departments";
            PreparedStatement ps = c.prepareStatement(SQLq);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ClassComboBox1.getItems().add(rs.getString("name"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {

        }

        try {
            String SQL = "SELECT name from Items";
            Connection c1 = SqliteConnection.Connector();
            PreparedStatement ps = c1.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ClassComboBox.getItems().add(rs.getString("name"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
        }

        Adress.getItems().addAll("احتياطي رئيس الفرع", "مخزن فرع النظم");
        ClassComboBox11.getItems().addAll("جديد", "مستعمل"
        );

        TextFields.bindAutoCompletion(ClassComboBox.getEditor(), ClassComboBox.getItems());
        TextFields.bindAutoCompletion(ClassComboBox1.getEditor(), ClassComboBox1.getItems());
        TextFields.bindAutoCompletion(employeeCombobox.getEditor(), employeeCombobox.getItems());

    }//

}//
