import java.sql.*;

public class DatabaseManager {
	
	Connection conn;
	
	// use getter to get vars from mainGUI
    MainGUI MG = new MainGUI();
    String varFirstName = MG.getFirstName();
 // use getter to get vars from mainGUI
	
    
    
	public DatabaseManager() { // string varFirstName, string varLastName, string varTelephone
		System.out.println("DatabaseManager instantiated.");
		

		
		try {
	    // create a mysql database connection	
		//String myDriver = "org.gjt.mm.mysql.Driver";
		String myDriver = "com.mysql.cj.jdbc.Driver";
	      
	    //Get Connection
	    String myUrl = "jdbc:mysql://127.0.0.1:8889/firstdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	    Class.forName(myDriver);

	    try {
	    	conn = DriverManager.getConnection(myUrl, "root", "root");
	    } catch (Exception e) { e.printStackTrace(); }
	         
	    // the mysql insert statement
	    String query = " insert into firsttable (first_name, last_name, telephone)"
	    	+ " values (?, ?, ?)";

        //String varFirstName = "Neu";
        String varLastName = "2312313";
        String varTelephone = "010190";
	    
	    // create the mysql insert preparedstatement
	    PreparedStatement preparedStmt = conn.prepareStatement(query);
	    preparedStmt.setString (1, varFirstName); // preparedStmt.setString (1, "Fanta"); // 
	    preparedStmt.setString (2, varLastName); //preparedStmt.setString (2, "Trinker");
	    preparedStmt.setString   (3, varTelephone); // preparedStmt.setString   (3, "1234");
	      
	    // execute the preparedstatement
	    preparedStmt.execute();
	    conn.close();
	    }
	    catch (Exception e) {
	    	System.err.println("Got an exception!");
	    	System.err.println(e.getMessage());
	    	e.printStackTrace();
	    }
	}	
}
