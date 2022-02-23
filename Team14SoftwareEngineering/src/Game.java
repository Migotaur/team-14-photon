import javax.swing.JFrame;

public class Game {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        PlayerEntryScreen test = new PlayerEntryScreen();
        
        frame.setSize(1500, 1000);
        frame.setVisible(true);
        frame.add(test);
        
    }
    
}