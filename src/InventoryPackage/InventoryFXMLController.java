/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryPackage;

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
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.CheckComboBox;
import schoolmanegementsssss.SqliteConnection;


/**
 * FXML Controller class
 *
 * @author Amir
 */
public class InventoryFXMLController implements Initializable {

   // @FXML
  //  private JFXTextField email;
   // @FXML
   // private JFXComboBox<String> job;

    //@FXML
  //  private JFXTextField salary;
  //  @FXML
 //  private JFXTextField age;
 @FXML
    private TableColumn<InventoryClass, String> IDCol;
    @FXML
    private TableColumn<InventoryClass, String> FirstNameCol;
    @FXML
    private TableColumn<InventoryClass, String> LastNameCol;
   @FXML
    private TableColumn<InventoryClass, String> GenderCol;
    @FXML
    private TableColumn<InventoryClass, String> GenderCol2;
    @FXML
    private TableColumn<InventoryClass, String> GenderCol21;
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
    private TableView<InventoryClass> table;
    final ObservableList<String> strings = FXCollections.observableArrayList();
    InventoryAction
            teacherActionObject = new InventoryAction();
 //   String teacherGenderStrng = "null";
  //  ToggleGroup group = new ToggleGroup();

    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField searchtext;
    @FXML
    private JFXTextField fname;
    @FXML
    private JFXTextField lname;
    @FXML
    private JFXTextField quantitytextfield;
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

 
   // @FXML
  //  private TableColumn<TeacherClass, String> PhoneCol;
   // @FXML
 //   private TableColumn<TeacherClass, String> EmailCol;
 //   @FXML
   // private TableColumn<TeacherClass, String> salaryCol;
    @FXML
    private JFXComboBox<String> subjectCheckComboBox;
    @FXML
    private JFXComboBox<String> searchcombobox;
    @FXML
    private AnchorPane anch;
    @FXML
    private Label recordslabel;
    @FXML
    private Label quantitylabel;
     @FXML
    private TableColumn<InventoryClass, String> LastNameCol1;
   @FXML
    private TableColumn<InventoryClass, String> GenderCol1;
    @FXML
    private JFXTextField typetextfield;
    @FXML
    private JFXTextField companytextfield;
    @FXML
    private JFXTextField quantitytextfield1;
    @FXML
    private JFXTextField quantitytextfield11;
   
@FXML
    private void AddTeacher() throws SQLException {

      //  if (male.isSelected()) {
        //    teacherGenderStrng = "Legal";

        //} //else {
          //  teacherGenderStrng = "illegal";
        //}
       
        if (id.getText().isEmpty() ||fname.getText().isEmpty() || lname.getText().isEmpty() || subjectCheckComboBox.getSelectionModel().getSelectedItem().isEmpty() 
                ) {
            AlertMaker a = new AlertMaker();
            a.showErrorMessage("تنبيه", "من فضلك ادخل كافه البيانات المطلوبه");
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
        if (id.getText().isEmpty() ||fname.getText().isEmpty() || lname.getText().isEmpty() || subjectCheckComboBox.getSelectionModel().getSelectedItem().isEmpty() 
               ||quantitytextfield.getText().isEmpty()||quantitytextfield11.getText().isEmpty()||quantitytextfield11.getText().isEmpty() ) {
            AlertMaker a = new AlertMaker();
            a.showErrorMessage("تنبيه", "من فضلك ادخل كافه البيانات المطلوبه");
        } else {
            teacherActionObject.UpdateTeacher(id, fname, lname,
                     quantitytextfield,quantitytextfield1,quantitytextfield11
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
               quantitytextfield,
                table,typetextfield,companytextfield,
      quantitytextfield1,quantitytextfield11
      );
    /*   if (l.getText().equals("Legal")) {
            male.setSelected(true);
        } else {
            female.setSelected(true);
         
         
        }*/
    
    /*  InventoryClass tc = (InventoryClass) table.getSelectionModel().getSelectedItem();
        id.setText(tc.getId());
                fname.setText(tc.getFname());
                lname.setText(tc.getLname());
                quantitytextfield.setText(tc.getGender());*/

    }
@FXML
    private void deleteTeacher() throws SQLException {

        
         if (id.getText().isEmpty() ||fname.getText().isEmpty() || lname.getText().isEmpty() || subjectCheckComboBox.getSelectionModel().getSelectedItem().isEmpty() 
                ) {
            AlertMaker a = new AlertMaker();
            a.showErrorMessage("تنبيه", "من فضلك ادخل كافه البيانات المطلوبه");
        }else{
        teacherActionObject.deleteTeacher(id, fname, lname,
                subjectCheckComboBox
              );
      //  male.setSelected(true);
        teacherActionObject.loadTable(table);

    }}
@FXML
    private void SearchStudent() throws SQLException {

        teacherActionObject.searchTeacher( subjectCheckComboBox, table,recordslabel,quantitylabel);
    System.out.println(subjectCheckComboBox.getValue());
    searchtext.clear();
    
    
    }
@FXML
    private void SearchStudentttt() throws SQLException {
String ch = (String) searchcombobox.getValue();

if (ch.equals("اسم الصنف")||ch.equals("المصدر")||ch.equals("النوع")||ch.equals("الشركه")){
    recordslabel.setText("");
    quantitylabel.setText("");
        teacherActionObject.searchParentt(searchtext, searchcombobox, table,subjectCheckComboBox,recordslabel,quantitylabel);}
    //System.out.println(subjectCheckComboBox.getValue());
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
        
         subjectCheckComboBox.getItems().addAll("عرض جميع المخازن","مخزن فرع النظم","احتياطي رئيس الفرع");
         searchcombobox.getItems().addAll("اسم الصنف","المصدر","النوع","الشركه");
     //   classs.getItems().addAll(strings);
       // male.setToggleGroup(group);//add male radio button to group
        //female.setToggleGroup(group);//add female radio button to group
      //  female.selectedColorProperty().set(Color.RED);//change selction color for female radio button
        quantitytextfield.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                quantitytextfield.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        quantitytextfield1.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                quantitytextfield.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        quantitytextfield11.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                quantitytextfield.setText(newValue.replaceAll("[^\\d]", ""));
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

    /*    try {
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
        LastNameCol1.setCellValueFactory(new PropertyValueFactory<>("type"));
        GenderCol1.setCellValueFactory(new PropertyValueFactory<>("company"));
        GenderCol2.setCellValueFactory(new PropertyValueFactory<>("neww"));
        GenderCol21.setCellValueFactory(new PropertyValueFactory<>("old"));
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
      /*  anch.setOnMouseEntered(e -> {
            try {
                teacherActionObject.loadTable(table);
            } catch (SQLException e1) {
            }
        });*/
        
                
    }

}
