package backend;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.swing.JLabel;

public class Player
{	
	int score; // User ID will be initialized to 0 which may interfere with validating userID, if 0 is a valid userID.
	String userName;
	
	private int userID;
	private String firstName;
	private String lastName;	
	private String codeName;
	private String teamColor;
	private static int currentId = 0;
	JLabel playerLabel;
		
	Player()
	{}
	
	Player(int id, int s, int i, String u)
	{
		score = s;
		userName = u;
		this.codeName = "";
		this.userID = i;
	}
	
	public Player(Player p)
	{
		score = p.getScore();
		userID = p.getUserID();
		userName = p.getUserName();
		this.codeName = p.codeName;
	}
	
	@JsonCreator
	public Player(
			@JsonProperty("firstName") String firstName,
			@JsonProperty("lastName") String lastName,
			@JsonProperty("codename") String codeName,
			@JsonProperty("playerID") int playerId)
	{
		System.out.println(String.format("ID being given to JSON Player constructor is %d", currentId));
		this.userID = playerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.codeName = codeName;
	}
	
	public Player(int id, String codename)
	{
		this.userID = id;
		this.codeName = codename;
		this.teamColor = "";
	}
	
	public boolean hasValidPlayerinfo()
	{
		// This method is to confirm that this player has everything needed to insert an object into an SQL database with fields
		/*
		 * player (
  		 * id INT,
  		 * codename VARCHAR(30),
  		 * PRIMARY KEY(id)
  		 * );
		 * */
		
		int maxStringSize = SQLDatabaseConnection.DATABASE_STRING_MAX_LENGTH;
		if (this.firstName != "" && this.lastName != "" && this.codeName != "")
			if (this.firstName.length() <= maxStringSize && this.lastName.length() <= maxStringSize && this.codeName.length() <= maxStringSize)
				return true;
			else
				return false;
		else
			return false;
	}
	public void setTeam(String team){
		this.teamColor = team;
	}

	public String getTeam(){
		return this.teamColor;
	}

	public int getScore()
	{
		return score;
	}
	
	public int getUserID()
	{
		return userID;
	}
	
	String getUserName()
	{
		return userName;
	}
	
	public void setScore(int s)
	{
		score = s;
	}
	
	void setUserID(int i)
	{
		userID = i;
	}
	
	void setUserName(String n)
	{
		userName = n;
	}
	
	public void setLabel(JLabel label){
		playerLabel = label;
	}
	public static void main(String[] args)
	{
		String test = "10:20";
		String[] ids = test.split(":");
		System.out.println(ids[1]);
	}
	
	public void print()
	{
		System.out.println(String.format("ID: %s CodeName: %s Team: %s", this.userID, this.codeName, this.teamColor));
	}
	
	public JLabel getLabel(){
		return playerLabel;
	}

	public String getCodeName()
	{
		return this.codeName;
	}
	
	
	public String toJSON() throws JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper();
		String playerAsJSONString = mapper.writeValueAsString(this);
		return playerAsJSONString;
	}

}