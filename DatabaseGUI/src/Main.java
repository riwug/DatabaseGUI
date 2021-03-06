import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
 
public class Main extends Application {
	
	//private DBConnection databaseManager;
	
    public static void main(String[] args) {
        launch(args);
    }   

    
    @Override
    public void start(Stage primaryStage) {
    	
        primaryStage.setTitle("JavaFX Welcome");
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text scenetitle = new Text("Main Window");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        
        // Insert Button
        Button btn = new Button("Insert Data - mySQL");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 2);
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent e) {
            	PopupInsert popupInsert = new PopupInsert();
            	popupInsert.init();
            }
        });
        // Insert Button     

        // Tableview Button
        Button btnTableview = new Button("View mySQL Tableview");
        HBox hbBtnTableview = new HBox(10);
        hbBtnTableview.setAlignment(Pos.BOTTOM_LEFT);
        hbBtnTableview.getChildren().add(btnTableview);
        grid.add(hbBtnTableview, 1, 3);
        
        btnTableview.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent e) {
            	PopupDBTableview popupDBTableview = new PopupDBTableview();
            	popupDBTableview.init();
            }
        });
        // Tableview Button     
        
        
        // Mongo Button
        Button btnMongoview = new Button("Mongo Insert");
        HBox hbBtnMongoview = new HBox(10);
        hbBtnMongoview.setAlignment(Pos.BOTTOM_LEFT);
        hbBtnMongoview.getChildren().add(btnMongoview);
        grid.add(hbBtnMongoview, 1, 4);
        
        btnMongoview.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent e) {
            	PopupMongo popupMongo = new PopupMongo();
            	popupMongo.init();
            }
        });
        // Mongo Button  
        
        Scene scene = new Scene(grid, 800, 600);
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }
}
