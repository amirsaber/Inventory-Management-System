/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanegementsssss;

import AlertMaker.AlertMaker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import schoolmanegementsssss.SqliteConnection;

/**
 *
 * @author amir
 */
public class FXMLDocumentController implements Initializable {

    public static String UserNameSave;
    @FXML
    private Button LoginButton;
    @FXML
    private ImageView NozomImage;
    @FXML
    private ProgressBar bar;

    public String getUserNameSave() {
        return UserNameSave;
    }
    private ResultSet rs;

    private PreparedStatement ps;

    @FXML
    private JFXTextField usernamefield;
    @FXML
    private JFXPasswordField passwordfield;
    Connection conn = SqliteConnection.Connector();
    boolean checkk = false;

    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException {

        AlertMaker alert = new AlertMaker();
        if (usernamefield.getText().isEmpty() || passwordfield.getText().isEmpty()) {

            if (usernamefield.getText().isEmpty() && passwordfield.getText().isEmpty()) {
                alert.showSimpleAlert("تنبيه", "من فضلك ادخل اسم المستخدم و كلمة المرور");
            } else if (usernamefield.getText().isEmpty()) {
                alert.showSimpleAlert("تنبيه", "من فضلك ادخل اسم المستخدم");
            } else {
                alert.showSimpleAlert("تنبيه", "من فضلك ادخل كلمةالمرور");
            }
        } else {
            try {
                Timeline task = new Timeline(
                        new KeyFrame(
                                Duration.ZERO,
                                new KeyValue(bar.progressProperty(), 0)
                        ),
                        new KeyFrame(
                                Duration.seconds(1.5),
                                new KeyValue(bar.progressProperty(), 1)
                        )
                );
                String query = "Select * from users where usernamee=? and password=?";
                ps = conn.prepareStatement(query);

                ps.setString(1, usernamefield.getText());
                ps.setString(2, passwordfield.getText());
                rs = ps.executeQuery();

                if (rs.next()) {
                    bar.setOpacity(1);
                    task.play();
                    UserNameSave = usernamefield.getText();
                    System.out.println(UserNameSave);
                    PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
                    delay.setAutoReverse(true);

                    delay.play();
                    delay.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e4) {
                            try {
                                Parent root1 = FXMLLoader.load(getClass().getResource("MainSceneFXML.fxml"));
//("MainSceneFXML.fxml"));
                                Scene scene1 = new Scene(root1);

                                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                                window.setScene(scene1);

                                window.setMaximized(true);

                                usernamefield.clear();
                                passwordfield.clear();
                            } catch (IOException ex) {
                                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    });

                } else {
                    alert.showSimpleAlert("تنبيه", "اسم المستخدم و كلمة المرور غير متطابقتان");

                }
                ps.close();
                rs.close();

            } catch (Exception e1) {

                System.err.println(e1);
                alert.showSimpleAlert("Attention", e1.getMessage());

            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        /*     LoginButton.setStyle("-fx-font-family :Hacen Dalal");
        LoginButton.setText("تسجيلمممممممم الدخول");*/
        FadeTransition ft = new FadeTransition();
        ft.setNode(NozomImage);
        ft.setDuration(new Duration(3000));
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(10);
        ft.setAutoReverse(true);
        ft.play();

    }

}
