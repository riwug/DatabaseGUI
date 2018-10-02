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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
//import com.mongodb.BulkWriteOperation;
//import com.mongodb.BulkWriteResult;
//import com.mongodb.Cursor;
import com.mongodb.DB;
//import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
//import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

// most of the code comes from here
// https://www.mongodb.com/blog/post/getting-started-with-mongodb-and-java-part-i

public class PopupMongo {

    TextField firstNameTextFieldOut = new TextField(null);
    TextField lastNameTextFieldOut = new TextField(null);
    private String[] vSend;
    
    TextField searchIDTextField = new TextField(null);
    
	String user = "armindbuser";
	String passwd = "abc123";
	String databaseName = "firstMongoDatabaseName";
	String collectionName = "fristMongoCollectionName";
    
    public void init() {
    	
    	// first make the scene
   	 
    	Stage primaryStage = new Stage();
        primaryStage.setTitle("Popup Mongo Window");
        
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text scenetitle = new Text("Mongo Scene with no function");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        Label idLabel = new Label("ID:");
        grid.add(idLabel, 0, 1);
        TextField idTextField = new TextField(null);
        grid.add(idTextField, 1, 1);
        
        Label firstNameLabel = new Label("First Name:");
        grid.add(firstNameLabel, 0, 2);
        TextField firstNameTextField = new TextField(null);
        grid.add(firstNameTextField, 1, 2);
       
        Label lastNameLabel = new Label("Last Name:");
        grid.add(lastNameLabel, 0, 3);
        TextField lastNameTextField = new TextField(null);
        grid.add(lastNameTextField, 1, 3);
        
        Button btn = new Button("save data");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 5);
        
        Label lastNameLabelOut = new Label("Last Name:");
        grid.add(lastNameLabelOut, 0, 10);
        grid.add(lastNameTextFieldOut, 1, 10);
        
        
        Label firstNameLabelOut = new Label("First Name:");
        grid.add(firstNameLabelOut, 0, 9);
        grid.add(firstNameTextFieldOut, 1, 9);
        
        lastNameTextFieldOut.setEditable(false);
        firstNameTextFieldOut.setEditable(false);
        
        
        Button btnGet = new Button("get data");
        HBox hbBtnGet = new HBox(10);
        hbBtnGet.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtnGet.getChildren().add(btnGet);
        grid.add(hbBtnGet, 1, 8);
        
        Label searchIDLabel = new Label("Search ID:");
        grid.add(searchIDLabel, 0, 11);
        grid.add(searchIDTextField, 1, 11);
        
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        actiontarget.setText("nothing will happen until ?");
        

        
        btn.setOnAction(new EventHandler<ActionEvent>() {
       	 
            @Override
            public void handle(ActionEvent e) {
            	        
            	String vID = idTextField.getText();
            	String vLastName = lastNameTextField.getText();
            	String vFirstName = firstNameTextField.getText();
            			
            		
            	if ( (vID != null) && (vLastName != null) && (vFirstName != null)) {
            		vSend = new String[] {vID, vLastName, vFirstName};

            		try {		
            			writeIntoMongoDB(vSend);
            			
            			actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("data saved in mongoDB!");
            		}
            		catch (Exception e2) {}
            	}
        		else {
            		actiontarget.setFill(Color.FIREBRICK);
            		actiontarget.setText("Bitte alles ausfüllen!");
            	}
        	}
        });
        
        
        btnGet.setOnAction(new EventHandler<ActionEvent>() {
          	 
            @Override
            public void handle(ActionEvent e3) {
            	
            		try {		
            			
            			readFromMongoDB(searchIDTextField.getText()); // just giving him an array to read what he just wrote
            			// message about sending
            			actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("read from mongoDB!");
            		}
            		catch (Exception e4) {}
            	}
        	});
        
        
        Scene scene = new Scene(grid, 800,600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    public void writeIntoMongoDB(String[] vSend) { // DBObject dataToWrite
    	
    	// old format for DBCollection to write, changed because it was depricated
        // new is MongoDatabase
        /*DBObject dataToWrite = new BasicDBObject("_id", vSend[0])
    	                            .append("lastName", vSend[1])
    	                            .append("firstName", vSend[2])
    	                            .append("address", new BasicDBObject("street", "An der Liebe 69")
    	                                                         .append("city", "Liebesstein")
    	                                                         .append("state", "Traumland")
    	                                                         .append("zip", 11111))
    	                            .append("books", books);
    	*/
        
    	//https://stackoverflow.com/questions/35392797/how-to-connect-to-mongodb-3-2-in-java-with-username-and-password
    	MongoClientURI uri = new MongoClientURI("mongodb://"+ user + ":" + passwd+ "@localhost:27017/?authSource=" + databaseName);
    	MongoClient mongoClient = new MongoClient(uri);
    	MongoDatabase db = mongoClient.getDatabase(databaseName);
    	
    	// get collections from db
    	for (String databaseName : db.listCollectionNames()) {
    	    
    		System.out.println("Name of Database YO:");
    		System.out.println(databaseName);
        	System.out.println("____________________________________________");
    	}


    	// good example
    	// http://zetcode.com/db/mongodbjava/
    	MongoCollection<Document> collection = db.getCollection(collectionName);
    	
   	   	//List<Integer> books = Arrays.asList(27464, 747854); //ORI
    	String[] books = {"Liebe", "Liebellen"};
    	
    	// create List to write into collection
    	// with insertMany you need to pass a List
    	List<Document> dataToWrite = new ArrayList<>();
    	Document d1 = new Document("_id", vSend[0]);
        d1.append("lastName", vSend[1]);
        d1.append("firstName", vSend[2]);
        d1.append("Time added", getCurrentTimeUsingDate());
        dataToWrite.add(d1);
        
        // can add more than one at a time     
/*    	Document d2 = new Document("_id", vSend[0]);
        d2.append("lastName", vSend[1]);
        d2.append("firstName", vSend[2]);
        d2.append("objectAppended", "2");
        dataToWrite.add(d2);*/


		System.out.println("Data to write YO:");
		System.out.println("____________________________________________");
		System.out.println(dataToWrite);
    	

    	
    	try {
    		collection.insertMany(dataToWrite);
    	}
    	catch (Exception e2) {}
    	
    	mongoClient.close();
    }

    
    public void readFromMongoDB(String idString) { // DBObject dataToWrite
 	
    	MongoClientURI uri = new MongoClientURI("mongodb://"+ user + ":" + passwd+ "@localhost:27017/?authSource=" + databaseName);
    	
    	MongoClient mongoClient = new MongoClient(uri);
    	//MongoClient mongoClient = new MongoClient(new ServerAddress("localhost",27017), Arrays.asList(credential));
    	MongoDatabase db = mongoClient.getDatabase(databaseName);
    	MongoCollection<Document> collection = db.getCollection(collectionName);
    	    
    	
		// We iterate through the documents of the collection. The find() method finds all documents in the collection.
    	System.out.println("Content of Collection AFTER new insert YO:");
    	System.out.println("____________________________________________");  
    	
    	try (MongoCursor<Document> cur = collection.find().iterator()) {
    		while (cur.hasNext()) {

    	        Document doc = cur.next();
    	        
    	        List list = new ArrayList(doc.values());
    	        System.out.print(list.get(0));
    	        System.out.print(": ");
    	        System.out.print(list.get(1));
    	        System.out.print(": ");
    	        System.out.println(list.get(2));
    	    }
    	}
    	
    	
    	//search for _id from search textField
    	// To specify an empty filter (i.e. match all documents in a collection), use an empty Document object.
    	ArrayList<Document> searchResult = (ArrayList<Document>) collection.find(new Document("_id", idString)).into(new ArrayList<Document>());

        System.out.println("_______");
    	System.out.println("Index 0");
    	System.out.println(searchResult.get(0).get("firstName"));
    	
    	//System.out.print("Gesucht nach ID: Output of FirstName");
    	//System.out.println(searchResult[0]);

    	// Note that you’ll need to cast the value to a String, as the compiler only knows that it’s an Object.
    	String vFirstNameFromDB = String.valueOf(searchResult.get(0).get("firstName"));
    	String vLastNameFromDB = String.valueOf(searchResult.get(0).get("lastName"));
    	
    	firstNameTextFieldOut.setText(vFirstNameFromDB);
    	lastNameTextFieldOut.setText(vLastNameFromDB);
    	
    	mongoClient.close();
    	
    }
    
    
    public String getCurrentTimeUsingDate() {
        Date date = new Date();
        String strDateFormat = "hh:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        // System.out.println("Current time of the day using Date - 12 hour format: " + formattedDate);
        return formattedDate.toString();
    }
    
    Block<Document> printBlock = new Block<Document>() {
        @Override
        public void apply(final Document document) {
            System.out.println(document.toJson());
        }
 };
    
}
