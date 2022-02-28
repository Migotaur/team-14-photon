package com.example.handler;

import org.springframework.boot.SpringApplication;

import javax.swing.JFrame;
import java.sql.SQLException;
import java.util.Collections;

public class Game {
	//public static ArrayList<Player> players = new ArrayList<Player>();
	public static PlayerSQLDatabasseConnection playerDatabase = new PlayerSQLDatabasseConnection();
	
	public Game()
	{
		System.out.println("Created database");
		System.out.println("Here at least");
		playerDatabase.printTable();
	}

	public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame();
        PlayerEntryScreen test = new PlayerEntryScreen();

        frame.setSize(1500, 1000);
        frame.setVisible(true);
        frame.add(test);

        PlayerSQLDatabasseConnection playdatabase = new PlayerSQLDatabasseConnection();

        if (playdatabase.hasValidConnection())
        {
            playdatabase.printTable();
        }
        else
        {
            System.out.println("Could not connect to database. Exiting");
        }

		SpringApplication webapp = new SpringApplication(RequestHandler.class);
		if (System.getenv("PORT") != null)
			webapp.setDefaultProperties(Collections.singletonMap("server.port", System.getenv("PORT")));
		webapp.run(args);

        Player myself = new Player("0", "Christopher", "Carter", "EngineeringIntrovert");
        playdatabase.AddPlayerToDatabase(myself);
        Player testPlayer = new Player("1", "Jake", "Smith", "Blimp");
        playdatabase.AddPlayerToDatabase(testPlayer);
		Player testPlayer2 = new Player("2", "James", "Bond", "Agent47");
		playdatabase.AddPlayerToDatabase(testPlayer2);
        playdatabase.printTable();

		PlayerSQLDatabasseConnection.getConnection().close();
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
