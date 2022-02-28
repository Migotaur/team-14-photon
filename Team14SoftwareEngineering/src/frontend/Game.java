package frontend;
import javax.swing.JFrame;

public class Game {

    public static void main(String[] args) {
        //Opens Splash Screen as own window, then opens the game
        JFrame splashFrame = new JFrame();
        Splash.FullSplash(splashFrame, 1500, 1000, "logo.jpg", 3000);
        splashFrame.dispose();

        //Code to show player entry screen
        JFrame frame = new JFrame();
        PlayerEntryScreen test = new PlayerEntryScreen();
        frame.setSize(1000, 750);
        frame.setVisible(true);
        frame.add(test);
        
    }
    
}