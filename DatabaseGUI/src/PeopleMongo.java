import com.mongodb.BasicDBObject;


// wanted to write a code that will grab the values from certain fields,
// but that is not necessary at the moment 

public class PeopleMongo {

	
	public static String getId() {
		String vID = "empty";
		
		
		return vID;
		
	}
	
	/*
	public static final DBObject toDBObject(Person person) {
	    return new BasicDBObject("_id", person.getId())
	                     .append("name", person.getName())
	                     .append("address", new BasicDBObject("street", person.getAddress().getStreet())
	                                                  .append("city", person.getAddress().getTown())
	                                                  .append("phone", person.getAddress().getPhone()))
	                     .append("books", person.getBookIds());
	}
	*/
	
}
