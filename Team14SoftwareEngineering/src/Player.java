package com.example.handler;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;

public class Player
{	
	int score; // User ID will be initialized to 0 which may interfere with validating userID, if 0 is a valid userID.
	String userName;
	
	private String userID;
	private String firstName;
	private String lastName;	
	private String codeName;
	private String teamColor;
	
	private static int currentId = 0;
		
	Player()
	{}
	
	Player(int id, int s, int i, String u)
	{
		score = s;
		userID = String.valueOf(i);
		userName = u;
		this.firstName = "";
		this.lastName = "";
		this.codeName = "";
		this.teamColor = "";
		this.userID = String.valueOf(id);
	}
	
	public Player(Player p)
	{
		score = p.getScore();
		userID = p.getUserID();
		userName = p.getUserName();
		this.firstName = p.firstName;
		this.lastName = p.lastName;
		this.codeName = p.codeName;
	}
	
	// Thoughs before slepp id sjould not be given in constructor
	
	@JsonCreator
	public Player(
			@JsonProperty("firstName") String firstName,
			@JsonProperty("lastName") String lastName,
			@JsonProperty("codename") String codeName)
	{
		System.out.println(String.format("ID being given to JSON Player constructor is %d", currentId));
		this.userID = createPlayerUniqueID(firstName, lastName, codeName);
		this.firstName = firstName;
		this.lastName = lastName;
		this.codeName = codeName;
	}
	
	public Player(String id, String firstname, String lastName, String codename)
	{
		this.userID = id;
		this.firstName = firstname;
		this.lastName = lastName;
		this.codeName = codename;
	}
	
	public boolean hasValidPlayerinfo()
	{
		// This method is to confirm that this player has everything needed to insert an object into an SQL database with fields
		/*
		 * player (
  		 * id INT,
  		 * first_name VARCHAR(30),
  		 * last_name VARCHAR(30),
  		 * codename VARCHAR(30),
  		 * PRIMARY KEY(id)
  		 * );
		 * */
		
		int maxStringSize = SQLDatabaseConnection.DATABASE_STRING_MAX_LENGTH;
		if (this.firstName != "" && this.lastName != "" && this.codeName != "" && !this.userID.isEmpty())
			if (this.firstName.length() <= maxStringSize && this.lastName.length() <= maxStringSize && this.codeName.length() <= maxStringSize)
				return true;
			else
				return false;
		else
			return false;
	}
	
	int getScore()
	{
		return score;
	}
	
	public String getUserID()
	{
		return userID;
	}
	
	String getUserName()
	{
		return userName;
	}
	
	void setScore(int s)
	{
		score = s;
	}
	
	void setUserID(String i)
	{
		userID = i;
	}
	
	void setUserName(String n)
	{
		userName = n;
	}
	
	public static void main(String[] args)
	{
		Player p = new Player("test", "test", "test");
		System.out.println(p.hashCode());
	}
	
	public void print()
	{
		System.out.println(String.format("ID: %s FirstName: %s LastName: %s CodeName: %s", this.userID, this.firstName, this.lastName, this.codeName));
	}
	
	public String getFirstName()
	{
		return this.firstName;
	}
	
	public String getLastName()
	{
		return this.lastName;
	}	
	public String getCodeName()
	{
		return this.codeName;
	}

	//create teams
	public String setTeam(String team)
	{
		teamColor = team;
	}
	
	public String getTeam()
	{
		return this.teamColor;
	}
	
	private static String createPlayerUniqueID(String firstName, String lastName, String codename)
	{
		String playerKeyString = String.format("%s:%s:%s", firstName, lastName, codename);
		MessageDigest md = null;
		
		try {
			 md = MessageDigest.getInstance("SHA-256"); 
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
        byte[] bytes = md.digest(playerKeyString.getBytes(StandardCharsets.UTF_8));
        StringBuilder result = new StringBuilder();
        for (byte b : bytes)
        	result.append(String.format("%02x", b));
        return result.toString();

	}
	
	public String toJSON() throws JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper();
		String playerAsJSONString = mapper.writeValueAsString(this);
		return playerAsJSONString;
	}


}