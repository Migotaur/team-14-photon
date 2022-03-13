package frontend;

import java.awt.event.*;
import javax.swing.JFrame;
import java.util.Properties;
import java.io.IOException;
import backend.*;
public class Game implements KeyListener{
	public static String resourceFolder;
	boolean keyF5 = false;

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
	
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_F5){
			System.out.println("The time is now");
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_F5: System.out.println("The time is now");break;
		}
		System.out.println("The time is now");
	}

	public void keyTyped(KeyEvent e)
	{
	}

	public void startGame()
	{
	        //Opens Splash Screen as own window, then opens the game
        	JFrame splashFrame = new JFrame();
      		Splash.FullSplash(splashFrame, 1500, 1000, "/frontend/logo.jpg", 3000);
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
