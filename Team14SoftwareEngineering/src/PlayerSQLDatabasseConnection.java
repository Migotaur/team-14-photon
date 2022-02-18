import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PlayerSQLDatabasseConnection extends SQLDatabaseConnection{
	
	public PlayerSQLDatabasseConnection()
	{
		super();
	}
	
	public ArrayList<Player> getPlayersFromDB() throws SQLException
	{
		ArrayList<Player> players = new ArrayList<Player>();
		ResultSet playerQuery = this.getConnection().createStatement().executeQuery("SELECT * FROM player;");
		while (playerQuery.next())
		{
			int id = playerQuery.getInt("ID");
			String firstName = playerQuery.getString("first_name");
			String lastName = playerQuery.getString("last_name");
			String codeName = playerQuery.getString("codename");
			players.add(new Player(id, firstName, lastName, codeName));
		}
		return players;
	}
	
	public boolean insertPlayerIntoDB(Player p)
	{
		if (this.hasValidConnection())
		{
			try {
				String command = String.format("INSERT INTO player (id, first_name, last_name, codename)\n" + 
						   "VALUES (%d, '%s', '%s', '%s');", p.getUserID(), p.getFirstName(), p.getLastName(), p.getCodeName());
				System.out.println(command);
				this.getConnection().createStatement().executeUpdate(command);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		else
			return false;
	}
	
	
	public static void main(String[] args) throws SQLException
	{

		PlayerSQLDatabasseConnection playdatabase = new PlayerSQLDatabasseConnection();
		
		if (playdatabase.hasValidConnection())
		{
			playdatabase.printTable();
		}
		else
		{
			System.out.println("Could not connect to database. Exiting");
		}
		
		Player myself = new Player(2, "Christopher", "Carter", "EngineeringIntrovert");
		playdatabase.insertPlayerIntoDB(myself);
		playdatabase.printTable();
		
	}
	
	public void printTable() throws SQLException
	{
		System.out.println("Conection to database was successful");
		ArrayList<Player> ps = this.getPlayersFromDB();
		for(Player p : ps)
		{
			p.print();
		}
	}
}
