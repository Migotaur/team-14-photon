package backend;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

public class SQLDatabaseConnection {

	public static final int DATABASE_STRING_MAX_LENGTH = 30;
	protected static Connection databaseConnection = null;
	
	public SQLDatabaseConnection ()
	{
		try {
			this.initializeConnection();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean AddPlayerToDatabase (Player p)
	{
		return true;
	}
	
	private void initializeConnection() throws URISyntaxException, SQLException {
		URI dbUri = new URI("postgres://ckjgyhboppqkvq:2faf1ee460a9891cc529dcc7cf0a157cf2f1d5998b0b02712605d5d00090a3e6@ec2-184-73-243-101.compute-1.amazonaws.com:5432/dh48266umj9m1");

	    String username = dbUri.getUserInfo().split(":")[0];
	    String password = dbUri.getUserInfo().split(":")[1];
	    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
	    //String dbUrl = "jdbc:postgresql://ckjgyhboppqkvq:2faf1ee460a9891cc529dcc7cf0a157cf2f1d5998b0b02712605d5d00090a3e6@ec2-184-73-243-101.compute-1.amazonaws.com:5432/dh48266umj9m1";
	    System.out.println(String.format("Username: %s, Pass: %s", username, password));
	    
	    databaseConnection = DriverManager.getConnection(dbUrl, username, password);
	}
	
	protected static Connection getConnection()
	{
		return SQLDatabaseConnection.databaseConnection;
	}
	
	public boolean hasValidConnection ()
	{
		if (SQLDatabaseConnection.databaseConnection != null)
			return true;
		else
			return false;
	}
	
}
