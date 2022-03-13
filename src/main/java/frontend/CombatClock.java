package frontend;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
//Original file by Troy Watts.
public class CombatClock extends JLabel{
    public boolean timeUp = false;  //If you want something to happen when countdown reaches 0, check for this being true.

    CombatClock(){
        super();
    }

    public void StartCombat(){  //This should be called when the action starts. Lasts for 6 minutes by default.
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask(){
            int i = 360;
            DecimalFormat secondFormat = new DecimalFormat("00");
            public void run() {
                setText((i/60) + ":" + secondFormat.format(i%60));
                i--;
                if(i < 31){
                    setForeground(Color.RED);
                }
                if(i < 0){
                    timer.cancel();
                    timeUp = true;
                    setText("Game Over");
                }
            }
        }, 0, 1000);

    }
}
