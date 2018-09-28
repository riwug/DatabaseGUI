import java.sql.*;
import java.sql.Connection;

// https://blog.ngopal.com.np/2011/10/19/dyanmic-tableview-data-from-database/
// code to connect so DB from this homepage seems much more clean

public class DBConnWrite {
	
    private static Connection conn;
    private static String myUrl = "jdbc:mysql://127.0.0.1:8889/firstdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static String user = "root";
    private static String pass = "root";
	
	// use getter to get vars from mainGUI
    //PopupInsert popupInsert = new PopupInsert();
    //String[] vArray = popupInsert.getVars();
    
 
    // use getter to get vars from mainGUI  
    
    public void writeIntoDB(String[] vArray) {
	    // the mysql insert statement
	    String query = " insert into firsttable (first_name, last_name, telephone)"
	    	+ " values (?, ?, ?)";
	    
	    // create the mysql insert preparedstatement
	    try {
	    PreparedStatement preparedStmt = conn.prepareStatement(query);
	    preparedStmt.setString (1, vArray[0]); // preparedStmt.setString (1, "Fanta"); // 
	    preparedStmt.setString (2, vArray[1]); //preparedStmt.setString (2, "Trinker");
	    preparedStmt.setString   (3, vArray[2]); // preparedStmt.setString   (3, "1234");
	      
	    // execute the preparedstatement
	    preparedStmt.execute();
	    conn.close();
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
    }
    
	public DBConnWrite() { // string varFirstName, string varLastName, string varTelephone
		System.out.println("DatabaseManager instantiated.");
		try {
	    // create a mysql database connection	
		//String myDriver = "org.gjt.mm.mysql.Driver";
		String myDriver = "com.mysql.cj.jdbc.Driver";
	      
	    //Get Connection
	    Class.forName(myDriver);

	    try {
	    	conn = DriverManager.getConnection(myUrl, user, pass);
	    } catch (Exception e) { e.printStackTrace(); }
	         

	    }
	    catch (Exception e) {
	    	System.err.println("Got an exception!");
	    	System.err.println(e.getMessage());
	    	e.printStackTrace();
	    }
	
	}
	
	
	
	
	
	
}
