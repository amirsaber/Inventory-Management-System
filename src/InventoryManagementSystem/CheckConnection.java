package schoolmanegementsssss;

import java.sql.*;
import static javafx.application.Application.STYLESHEET_CASPIAN;
import static javafx.application.Application.launch;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CheckConnection {
    Label lcon =new Label("Intiating connection...!");//a label to be shown in the scene to show that DB is connected or not
   
    Connection conn;//don't forget to move inside the constructor and make constructor as a method
public  CheckConnection() throws SQLException{
     ////////////////////////////////////////////
     
//for connection

        conn = SqliteConnection.Connector();
   if (conn == null) {
   System.out.println("Connection IS Not Successful");
    System.exit(1);
   }
   else 
   {System.out.println("Connection Is Successful");}
   
     lcon.setStyle("-fx-background-color: linear-gradient(to bottom, #D2D2D2, #F2F2F2);"
                + " -fx-border: 30px solid; -fx-border-color: white; -fx-background-radius: 10.0;"
                + " -fx-border-radius: 10.0");
        lcon.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD, 15));
  
  if ( !conn.isClosed()) {
      lcon.setText("  DB Is Connected  ");
  } else {
      
      lcon.setText("  DB Is Not Connected  ");
  }
}
 
}

////////////////////////////////////////////////