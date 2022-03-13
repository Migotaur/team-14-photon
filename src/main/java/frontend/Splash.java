package frontend;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Splash {
    
    JLabel label;
    static Image splash_image;

    Splash(){   //Splash loads "logo.jpg" by default.
        if(splash_image == null) splash_image = loadImage("logo.jpg");

    }

    Splash(String fileName){    //In case your image isn't named "logo.jpg".
        if(splash_image == null) splash_image = loadImage(fileName);
    }

    public void CreateSplash(JFrame frame, int width, int height){  //Splash takes an existing JFrame and draws the image to the JFrame.
        ImageIcon icon = new ImageIcon(splash_image.getScaledInstance(width, height, Image.SCALE_DEFAULT));
        frame.setLayout(new FlowLayout());
        frame.setSize(width, height);
        label = new JLabel();

        label.setIcon(icon);
        frame.add(label);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void RemoveSplash(JFrame frame){ //Removes the component that Splash added to the JFrame.
        frame.remove(label);
        frame.validate();
        frame.repaint();
    }

    static public InputStream getFile(String fileName)
    {
            //Get file from resources folder
            ClassLoader classLoader = (new CombatClock()).getClass().getClassLoader();

            InputStream stream = classLoader.getResourceAsStream(fileName);

            try
            {
                if (stream == null)
                {
                    throw new Exception("Cannot find file " + fileName);
                }

                return stream;
            }
            catch (Exception e) {
                e.printStackTrace();

                System.exit(1);
            }

            return null;
    }
    
    public static void FullSplash(JFrame frame, int width, int height, String imageName, int time){  //Alternative method to display splash screen, but messes with the thread.
        ImageIcon icon = new ImageIcon(loadImage(imageName).getScaledInstance(width, height, Image.SCALE_DEFAULT));
        frame.setLayout(new FlowLayout());
        frame.setSize(width, height);
        JLabel splashLabel = new JLabel();

        splashLabel.setIcon(icon);
        frame.add(splashLabel);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try{
            Thread.sleep(time);
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        frame.remove(splashLabel);
        frame.validate();
        frame.repaint();
    }

    static public Image loadImage(String fileName){ //Function for retrieving images from file
		try{
			return ImageIO.read(getFile(fileName));
		} catch(IOException e){
			e.printStackTrace(System.err);
			System.exit(1);
		}
		return null;	//This statement should never be executed. If it does, something went terribly wrong.
	}
}
