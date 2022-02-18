class Player
{	
	int score, userID; // User ID will be initialized to 0 which may interfere with validating userID, if 0 is a valid userID.
	String userName;
	private String firstName;
	private String lastName;
	private String codeName;
	
	Player()
	{}
	
	Player(int s, int i, String u)
	{
		score = s;
		userID = i;
		userName = u;
		this.firstName = "";
		this.lastName = "";
		this.codeName = "";
	}
	
	Player(Player p)
	{
		score = p.getScore();
		userID = p.getUserID();
		userName = p.getUserName();
	}
	
	public Player(int id, String firstName, String lastName, String codeName)
	{
		this.userID = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.codeName = codeName;
	}
	
	public boolean hasValidPlayerinfo()
	{
		// This method is to confirm that this player has everything needed to insert an object into an SQL database with fields
		/*
		 * player (
  		 * id INT,
  		 * first_name VARCHAR(30),
  		 * last_name VARCHAR(30),
  		 * codename VARCHAR(30)
  		 * );
		 * */
		
		int maxStringSize = SQLDatabaseConnection.DATABASE_STRING_MAX_LENGTH;
		if (this.firstName != "" && this.lastName != "" && this.codeName != "" && this.userID != 0)
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
	
	int getUserID()
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
	
	void setUserID(int i)
	{
		userID = i;
	}
	
	void setUserName(String n)
	{
		userName = n;
	}
	
	public static void main(String[] args)
	{
		System.out.println("This is a test");
	}
	
	public void print()
	{
		System.out.println(String.format("ID: %d FirstName: %s LastName: %s CodeName: %s", this.userID, this.firstName, this.lastName, this.codeName));
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

}