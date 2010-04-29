package graphics;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.*;

import mp3.MP3;

@SuppressWarnings({ "serial", "unused" })
/**
 * This class is responsible for creating the "About Us" Window
 * */
public class AboutUs extends JFrame{
        private int x,y;

        /**
         * This constructor sets the visibility of the frame, size, name, and whatever settings needed for this frame
         * its also responsible for Listening to the Mouse and watching its clicks, so it can return to the main frame when the user clicks "main"
         * */
        public AboutUs(){
                setVisible(true);
                setSize(500, 385);
                setTitle("About Us");
                setResizable(false);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLocationRelativeTo(null);
                pack();
                repaint();
                JLabel goBack = new JLabel();
                goBack.setBounds(455, 295, 30, 25);
                JLabel exit = new JLabel();
                exit.setBounds(455, 335, 30, 25);
                goBack.setToolTipText("go back");
                exit.setToolTipText("Exit");
                addMouseListener(new MouseAdapter() {
                        public void mousePressed(MouseEvent mouseClick) {
                                x=mouseClick.getX();
                                y=mouseClick.getY();
                                if (x>455 && x<=485) {
                                    File filename = new File("src/sounds/sound11.mp3");// playing mp3 file 
                                    MP3 mp3 = new MP3(filename);
                                    mp3.play();
                                        if (y>=295 && y<=320) {
                                                // When clicking the "main" button, it'll redirect you to the "main" form
                                                
                                        	setVisible(false);
												Play splashScreen = new Play();
                                        }
                                        else if (y>=335 && y<=360 ) {
                                                // When clicking the "Exit" button, the program is terminated
                                            System.exit(0);
                                        }
                                }
                        }
                });
        }

        final Image image = new ImageIcon("src/images/AboutUs.png").getImage();
        public void paint(Graphics g) {
                g.drawImage(image, 0, 0, null);
        }

    public Dimension getPreferredSize() {
    	return new Dimension(500, 385);
    }
}