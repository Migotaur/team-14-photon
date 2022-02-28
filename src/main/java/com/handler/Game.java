package com.example.handler;

public class Game {
	//public static ArrayList<Player> players = new ArrayList<Player>();
	public static PlayerSQLDatabasseConnection playerDatabase = new PlayerSQLDatabasseConnection();
	
	public Game()
	{
		System.out.println("Created database");
		System.out.println("Here at least");
		playerDatabase.printTable();
	}
	
	public void serverRestart()
	{
		/* 
		 * At server restart I want to:
		 * 		reconfigure database so that all players in the database have an ID that was removed will be reallocated to another player
		 * 
		 * 		IE: 
		 * 		Original Database 
		 * 		(
		 * 			ID	Name	
		 * 			0 	Chris
		 * 			1	Mike
		 * 			2	Tom
		 * 			
		 * 		)
		 * 
		 * 		Player Mike is removed
		 * 
		 * 		Database before restart
		 * 		(
		 * 			ID	Name	
		 * 			0 	Chris
		 * 			2	Tom
		 * 			
		 * 		)
		 * 		Database After Restart
		 * 		(
		 * 			ID	Name	
		 * 			0 	Chris
		 * 			1	Tom
		 * 		)
		*/
	}
	
	
	
	
}
