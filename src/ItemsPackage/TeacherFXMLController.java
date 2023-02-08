/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ItemsPackage;

import AlertMaker.AlertMaker;

import com.jfoenix.controls.JFXComboBox;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.CheckComboBox;
import schoolmanegementsssss.SqliteConnection;


/**
 * FXML Controller class
 *
 * @author Amir
 */
public class TeacherFXMLController implements Initializable {

   // @FXML
  //  private JFXTextField email;
   // @FXML
   // private JFXComboBox<String> job;

    //@FXML
  //  private JFXTextField salary;
  //  @FXML
 //  private JFXTextField age;

    @FXML
    private TableColumn<TeacherClass, String> FirstNameCol;
    @FXML
    private TableColumn<TeacherClass, String> LastNameCol;
   @FXML
    private TableColumn<TeacherClass, String> GenderCol;
 //  @FXML
 //   private TableColumn<TeacherClass, String> ClassCol;

 //   @FXML
 //   private TableColumn<TeacherClass, String> jobtitleCol;
 //   @FXML
   // private TableColumn<TeacherClass, String> subjectCol;
   
  // @FXML
   // private TableColumn<TeacherClass, String> ageCol;

  //  @FXML
  //  private TableColumn<TeacherClass, String> RegestrationDateCol;
  //  @FXML
  //  private TableColumn<TeacherClass, String> RegestrationByCol;
    @FXML
    private TableView<TeacherClass> table;
    final ObservableList<String> strings = FXCollections.observableArrayList();
    TeacherAction teacherActionObject = new TeacherAction();
 //   String teacherGenderStrng = "null";
  //  ToggleGroup group = new ToggleGroup();

    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField fname;
    @FXML
    private JFXTextField lname;
  //  @FXML
 //   private JFXRadioButton male;
 //   @FXML
 //   private JFXRadioButton female;
  //  @FXML
  //  private CheckComboBox<String> classs;
 //   @FXML
  //  private JFXTextField phone;
 //   @FXML
  //  private JFXDatePicker Register;

    @FXML
    private TableColumn<TeacherClass, String> IDCol;
   // @FXML
  //  private TableColumn<TeacherClass, String> PhoneCol;
   // @FXML
 //   private TableColumn<TeacherClass, String> EmailCol;
 //   @FXML
   // private TableColumn<TeacherClass, String> salaryCol;
    @FXML
    private JFXComboBox<String> subjectCheckComboBox;
    @FXML
    private AnchorPane anch;
    @FXML
    private CheckComboBox<?> classs;
    @FXML
    private JFXComboBox<String> searchcombobox;
    @FXML
    private JFXTextField searchtext;
@FXML
    private void AddTeacher() throws SQLException {

      //  if (male.isSelected()) {
        //    teacherGenderStrng = "Legal";

        //} //else {
          //  teacherGenderStrng = "illegal";
        //}
       
        if (id.getText().isEmpty() || fname.getText().isEmpty() || lname.getText().isEmpty() || subjectCheckComboBox.getSelectionModel().getSelectedItem().isEmpty() 
                ) {
            AlertMaker a = new AlertMaker();
            a.showErrorMessage("تنبيه", "من فضلك ادخل كافة البيانات المطلوبة");
        } else {
            teacherActionObject.addTeacher(id, fname, lname,
                     subjectCheckComboBox
                   );
          //  teacherGenderStrng = "null";
            //male.setSelected(true);
            teacherActionObject.loadTable(table);
        }
    }

@FXML
  private void UpdateTeacher() throws SQLException {

     /*   if (male.isSelected()) {
            teacherGenderStrng = "Legal";

        } else {
          teacherGenderStrng = "illegal";
        }*/
        
        if (id.getText().isEmpty() || fname.getText().isEmpty() || lname.getText().isEmpty() || subjectCheckComboBox.getSelectionModel().getSelectedItem().isEmpty() 
                ) {
            AlertMaker a = new AlertMaker();
            a.showErrorMessage("تنبيه", "من فضلك ادخل كافة البيانات المطلوبة");
        } else {
            teacherActionObject.UpdateTeacher(id, fname, lname,
                     subjectCheckComboBox
                   );
          // teacherGenderStrng = "null";
            //male.setSelected(true);
            teacherActionObject.loadTable(table);
        }

    }
@FXML
    private void LoadFromTable() throws SQLException {

        Label l = new Label();
        teacherActionObject.loadFromTable(id, fname, lname,
                subjectCheckComboBox,
                 table);
    /*   if (l.getText().equals("Legal")) {
            male.setSelected(true);
        } else {
            female.setSelected(true);
        }*/

    }
@FXML
    private void deleteTeacher() throws SQLException {
 
        if (id.getText().isEmpty() || fname.getText().isEmpty() || lname.getText().isEmpty() || subjectCheckComboBox.getSelectionModel().getSelectedItem().isEmpty() 
                ) {
            AlertMaker a = new AlertMaker();
            a.showErrorMessage("تنبيه", "من فضلك ادخل كافة البيانات المطلوبة");
        } else{
        teacherActionObject.deleteTeacher(id, fname, lname,
                subjectCheckComboBox
              );
      //  male.setSelected(true);
        teacherActionObject.loadTable(table);

    }}
@FXML
    private void SearchStudent() throws SQLException {

        teacherActionObject.searchTeacher( searchtext,searchcombobox, table);
  
    
    
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           Platform.runLater(() -> id.selectEnd());//to request focus when scene start
       // Register.setValue(LocalDate.now());//set regestraion date by default to today

        //Register.setShowWeekNumbers(false);// false to prevent user from input incorrect date
        strings.addAll("1/1","1/2","1/3","2/1","2/2","2/3"
                 ,"3/1","3/2","3/3" ,"4/1","4/2","4/3" ,"5/1","5/2","5/3" ,"6/1","6/2","6/3"
        );
          searchcombobox.getItems().addAll("الرقم التسلسلي","اسم الصنف","نوع الصنف","الشركه");
     //   classs.getItems().addAll(strings);
       // male.setToggleGroup(group);//add male radio button to group
        //female.setToggleGroup(group);//add female radio button to group
      //  female.selectedColorProperty().set(Color.RED);//change selction color for female radio button
        id.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                id.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
       // phone.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
      //      if (!newValue.matches("\\d*")) {
         //       PhoneCol.setText(newValue.replaceAll("[^\\d]", ""));
        //    }
     //   });//number validation for student phone number textfield

       // salary.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
        //    if (!newValue.matches("\\d*")) {
           //     salary.setText(newValue.replaceAll("[^\\d]", ""));
        //    }

      //  });
     /*   age.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                age.setText(newValue.replaceAll("[^\\d]", ""));
            }

        });*/
//number validation for parent id textfield

      /*  try {
            int returnId = teacherActionObject.getId();
            id.setText(Integer.toString(returnId));

        } catch (Exception e) {
        }*/

        /*job.getItems().addAll("خارج الفرع", "المخزن"
        );*/
       // subjectCheckComboBox.getItems().addAll("جديد", "مستعمل"
       // );

       // male.setSelected(true);
        IDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        FirstNameCol.setCellValueFactory(new PropertyValueFactory<>("fname"));
        LastNameCol.setCellValueFactory(new PropertyValueFactory<>("lname"));
        GenderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
      /*  ClassCol.setCellValueFactory(new PropertyValueFactory<>("classs"));
        PhoneCol.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        EmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        jobtitleCol.setCellValueFactory(new PropertyValueFactory<>("jobtitle"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        RegestrationDateCol.setCellValueFactory(new PropertyValueFactory<>("registerdate"));
        RegestrationByCol.setCellValueFactory(new PropertyValueFactory<>("regname"));*/
        //////////////////////////////////
        //////////////////////////////////
        //////////////////////////////////
        anch.setOnMouseEntered(e -> {
            try {
                teacherActionObject.loadTable(table);
            } catch (SQLException e1) {
            }
        });
        
                String SQL = "SELECT name from Category";        
        Connection c = SqliteConnection.Connector();
        try {
           
            PreparedStatement ps = c.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                subjectCheckComboBox.getItems().add(rs.getString("name"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
              
        }
    }

   

}
