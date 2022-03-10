import javax.swing.JFrame;
public class ActionDisplayMain {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        ActionDisplay test = new ActionDisplay();
        
        frame.setSize(700, 500);
        frame.setVisible(true);
        frame.add(test);
    }
}