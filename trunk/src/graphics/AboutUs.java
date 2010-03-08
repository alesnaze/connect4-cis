package graphics;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.*;

import mp3.MP3;

@SuppressWarnings("serial")
public class AboutUs extends JFrame{
        /**
         * This class is responsible for creating the "About Us" Window which appears when clicking "About Us" button in the main frame
         * so it contains a constructor which is responsible for the frame settings and the Mouse Listener
         * and other two methods, one for drawing the image on the frame, and the other for setting its Dimentions
         * */

        private int x,y;

        public AboutUs(){
                /**
                 * This constructor sets the visibility of the frame, size, name, and whatever settings needed for this frame
                 * its also responsible for Listening to the Mouse and watching its clicks, so it can return to the main frame when the user clicks "main"
                 * */
                setVisible(true);
                setSize(550,400);
                setTitle("About Us");
                setResizable(false);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLocationRelativeTo(null);
                pack();
                repaint();

                addMouseListener(new MouseAdapter() {
                        public void mousePressed(MouseEvent mouseClick) {
                                x=mouseClick.getX();
                                y=mouseClick.getY();
                                if (x>475 && x<=550) {
                                    File filename = new File("src/sounds/sound11.mp3");// playing mp3 file 
                                    MP3 mp3 = new MP3(filename);
                                    mp3.play();
                                        if (y>=310 && y<=340) {
                                                // When clicking the "main" button, it'll redirect you to the "main" form
                                                setVisible(false);
                                                @SuppressWarnings("unused")
												Play splashScreen = new Play();
                                        }
                                        else if (y>=343 && y<=370 ) {
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
        return new Dimension(550,400);
    }
}