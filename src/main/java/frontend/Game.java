package frontend;

import javax.swing.JFrame;
import java.util.Properties;
import java.io.IOException;

public class Game {
	public static String resourceFolder;

	public Game()
	{
		try
		{
			Properties projectProperties = new Properties();
			projectProperties.load(this.getClass().getResourceAsStream("project.properties"));
			Game.resourceFolder = System.getProperty("user.dir") + (String) projectProperties.get("project.resources");
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void startGame()
	{
	        //Opens Splash Screen as own window, then opens the game
        	JFrame splashFrame = new JFrame();
      		Splash.FullSplash(splashFrame, 1500, 1000, resourceFolder + "/logo.jpg", 3000);
		splashFrame.dispose();

        	//Code to show player entry screen
        	JFrame frame = new JFrame();
        	PlayerEntryScreen test = new PlayerEntryScreen();
        	frame.setSize(1000, 750);
        	frame.setVisible(true);
        	frame.add(test);

	}


    public static void main(String[] args) {
        Game game = new Game();
	game.startGame();
    }
}
