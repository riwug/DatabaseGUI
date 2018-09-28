import java.sql.Connection;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
 
/**
 *
 * @author Narayan
 */
public class PopupDBTableview {
 
    //TABLE VIEW AND DATA
    private ObservableList<ObservableList> data;
    
    private TableView tableview;
 
    //CONNECTION DATABASE
    public void buildData() {
        Connection c;
        data = FXCollections.observableArrayList();
        try {
            c = DBConnect.connect();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT * from firsttable";
            //ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);
 
            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             *********************************
             */
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1)); //  TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+ 1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
 
                tableview.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
            }
 
            /**
             * ******************************
             * Data added to ObservableList *
             *******************************
             */
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);
 
            }
 
            //FINALLY ADDED TO TableView
            tableview.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
 
    
    public void init() {
    	Stage primaryStage = new Stage();
        primaryStage.setTitle("Tableview window");
        
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(50, 50, 50, 50));
    	
    	//TableView
        tableview = new TableView();
        buildData();
 
        //Main Scene
        Scene scene = new Scene(tableview, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
    }
}