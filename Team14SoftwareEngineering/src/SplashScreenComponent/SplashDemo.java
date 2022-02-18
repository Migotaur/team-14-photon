package SplashScreenComponent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SplashDemo {   //Demonstration on how to use Splash class. Don't use SplashDemo.
    public static void main(String[] args) throws IOException {
        Splash testSplash = new Splash();   //Create a Splash object.
        JFrame testFrame = new JFrame();

        testSplash.CreateSplash(testFrame, 500, 500);   //Creates splash screen.

        //This segment is here so the splash screen stays up for a period of time before being removed.
        try{
            Thread.sleep(3000);
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        testSplash.RemoveSplash(testFrame); //Removes the splash image from the JFrame.

        //This is alternative way to use Splash. This doesn't require a Splash object, but it does mess with the thread.
        Splash.FullSplash(testFrame, 500, 500, "logo.jpg", 2000);
    }
}