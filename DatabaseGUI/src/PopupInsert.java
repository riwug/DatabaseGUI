import java.sql.Connection;
import java.sql.PreparedStatement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopupInsert {
	
	//private String[] varList;
    /*private String vFirstName = "FjhjhName";
    private String vLastName = "LName";
    private String vTelephone = "012345";
    
    public String[] getVars() { 
    	String[] vArray = {vFirstName, vLastName, vTelephone};
    	return vArray; 
    	
    }*/
	Connection conn;
	
    public void init() {
    	 
    	Stage primaryStage = new Stage();
        primaryStage.setTitle("Popup Insert window");
        
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text scenetitle = new Text("Insert data");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        Label firstNameLabel = new Label("First Name:");
        grid.add(firstNameLabel, 0, 1);
        TextField firstNameTextField = new TextField(null);
        grid.add(firstNameTextField, 1, 1);
                
        Label lastNameLabel = new Label("Last Name:");
        grid.add(lastNameLabel, 0, 2);
        TextField lastNameTextField = new TextField(null);
        grid.add(lastNameTextField, 1, 2);

        Label telLabel = new Label("Tel:");
        grid.add(telLabel, 0, 3);
        TextField telTextField = new TextField(null);
        grid.add(telTextField, 1, 3);

        Label cluLabel = new Label("Cluster");
        grid.add(cluLabel, 0, 4);
        TextField cluTextField = new TextField(null);
        grid.add(cluTextField, 1, 4);
        
        Button btn = new Button("save data");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 5);
        
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent e) {
            	
            	String vFirstName = firstNameTextField.getText();
            	String vLastName = lastNameTextField.getText();
            	String vTelephone = telTextField.getText();
            	String vClu = cluTextField.getText();
            	
            	if ( (vFirstName != null) && (vLastName != null) && (vTelephone != null) && (vClu != null)) {
            		String[] vArray = { vFirstName, vLastName, vTelephone, vClu };
            		try {
            			conn = DBConnect.connect2();
            			//DBConnWrite dbconn = new DBConnWrite();	
            			writeIntoDB(vArray);
            		
            			firstNameTextField.setText(null);
            			lastNameTextField.setText(null);
            			telTextField.setText(null);
            			cluTextField.setText(null);
            		
            			actiontarget.setFill(Color.GREEN);
            			actiontarget.setText("Data saved!");
            			// Todo: data correct, Database updated!
            		}
            		catch (Exception e2) {}
            	}
            	else {
            		actiontarget.setFill(Color.FIREBRICK);
            		actiontarget.setText("Bitte alles ausfüllen!");
            		// Todo: new popup "Ausfüllen!" ...
            	}
            	        	
            	
                //actiontarget.setFill(Color.FIREBRICK);
                //actiontarget.setText("data saved in db! (not working)");
            }
        });
    
        Scene scene = new Scene(grid, 800,600);
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }
    
    public void writeIntoDB(String[] vArray) {
	    // the mysql insert statement
	    String query = " insert into firsttable (first_name, last_name, telephone, clu)"
	    	+ " values (?, ?, ?, ?)";
	    
	    // create the mysql insert preparedstatement
	    try {
	    PreparedStatement preparedStmt = conn.prepareStatement(query);
	    preparedStmt.setString (1, vArray[0]); // preparedStmt.setString first_name 
	    preparedStmt.setString (2, vArray[1]); //preparedStmt.setString last_name
	    preparedStmt.setString   (3, vArray[2]); // preparedStmt.setString   telephone
	    preparedStmt.setString   (4, vArray[3]); // preparedStmt.setString   Group X
	      
	    // execute the preparedstatement
	    preparedStmt.execute();
	    conn.close();
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
    }
    
}
