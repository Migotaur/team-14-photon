package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class PlayerSQLDatabasseConnection extends SQLDatabaseConnection{
	
	@JsonProperty("players")
	private static Hashtable<Integer, Player> players = new Hashtable<>();
	
	public PlayerSQLDatabasseConnection()
	{
		super();
		updatePlayerArray();
	}
	
	private static Hashtable<Integer, Player> updatePlayerArray()
	{
		
		ResultSet playerQuery;
		try {
			playerQuery = SQLDatabaseConnection.getConnection().createStatement().executeQuery("SELECT * FROM players;");

		players.clear();
		while (playerQuery.next())
		{
			int playerId = playerQuery.getInt("id");
			String codeName = playerQuery.getString("codename");
			Player p = new Player(playerId, codeName);
			players.put(p.getUserID(), p);
		}
		return players;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean AddPlayerToDatabase(Player p)
	{
		if (this.hasValidConnection())
		{
			if (!players.containsKey(p.getUserID())) 
			{
				try {
					String command = String.format("INSERT INTO players (id, codename)\n" + 
							   "VALUES ('%s', '%s');", p.getUserID(), p.getCodeName());
					getConnection().createStatement().executeUpdate(command);
					updatePlayerArray();
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
				return true;
			}
			else
			{
				System.out.println("Player is already in database");
				return false;
			}
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
		
		//playdatabase.deleteRecord("2");
		//Player myself = new Player("Christopher", "Carter", "EngineeringIntrovert");
		//playdatabase.AddPlayerToDatabase(myself);
		//System.out.println("\n\n\n\n");
		//playdatabase.printTable();
		PlayerSQLDatabasseConnection.getConnection().close();
		
	}
	
	public boolean deleteRecord(String primaryKey)
	{
		if (this.hasValidConnection())
		{
			try {
				String command = String.format("DELETE FROM players WHERE Id='%s'", primaryKey);
				PlayerSQLDatabasseConnection.getConnection().createStatement().executeUpdate(command);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			updatePlayerArray();
			return true;
		}
		else
			return false;
	}
	
	public void printTable()
	{
		if (this.hasValidConnection())
		{
			if (!isPlayerArraySyncedToDatabasae())
				updatePlayerArray();
			for(Player p : players.values())
			{
				p.print();
			}
		}
	}
	
	private static boolean isPlayerArraySyncedToDatabasae()
	{
		ResultSet playerQuery = null;
		int count = -1;
		try {
			playerQuery = SQLDatabaseConnection.getConnection().createStatement().executeQuery("SELECT COUNT(*) FROM players;");
			playerQuery.next();
			count = playerQuery.getInt("count");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

		if (count != players.size())
			return false;
		else
			return true;
	}
	
	public static int getCurrentDBSize()
	{
		if (!isPlayerArraySyncedToDatabasae())
			updatePlayerArray();
		return players.size();
	}
	
	public String toJSON() throws JsonProcessingException
	{
		updatePlayerArray();
		ArrayList<Player> playersArrayList = new ArrayList<Player>(players.values());
		Hashtable<String, ArrayList<Player>> jsonDictionaryObject = new Hashtable<String, ArrayList<Player>>();
		jsonDictionaryObject.put("Players", playersArrayList);
		ObjectMapper mapper = new ObjectMapper();
		
		String playerAsJSONString = mapper.writeValueAsString(jsonDictionaryObject);
		return playerAsJSONString;
	}
	
	public Hashtable<Integer, Player> getPlayers()
	{
		return players;
	}
}
