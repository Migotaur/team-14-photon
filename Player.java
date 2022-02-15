class Player
{
	int score, userID;
	String userName;
	
	Player()
	{}
	
	Player(int s, int i, String u)
	{
		score = s;
		userID = i;
		userName = u;
	}
	
	Player(Player p)
	{
		score = p.getScore();
		userID = p.getUserID();
		userName = p.getUserName();
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
	
}