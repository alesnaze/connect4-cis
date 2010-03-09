package graphics;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.*;

import network.NewJFrame;
import mp3.MP3;

@SuppressWarnings({ "serial", "unused" })
/**
 * This class is responsible for drawing the main frame image and waiting
 * for a mouse response to perform a specific operation
 * */
public class Play extends JFrame {
	private int x, y;

	public static void main(String[] args) {
		Play frame = new Play();
	}

	/**
	 * This constructor sets the visibility of the frame, size, name, and
	 * whatever settings needed for this frame its also responsible for
	 * Listening to the Mouse and watching its clicks
	 * */
	public Play() {
		setVisible(true);
		setSize(550, 400);
		setTitle("Main Menu");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		final Image icon = new ImageIcon("src/images/Connect4Logo.png").getImage();
		setIconImage(icon);
		pack();
		repaint();

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseClick) {
				x = mouseClick.getX();
				y = mouseClick.getY();
				if (x >= 190 && x <= 370) {
					File filename = new File("src/sounds/sound11.mp3");// playing mp3 file
                    MP3 mp3 = new MP3(filename);
                    mp3.play();
					if (y >= 175 && y <= 225) {
						// When clicking the "Create Server" button, it'll
						// redirect you to the "DrawingOvalsServer.java" class
						setVisible(false);
						new DrawingOvalsServer();
					}
					if (y >= 235 && y <= 285) {
						// When clicking the "Start" button, it'll redirect you
						// to the "main game" frame
						setVisible(false);
						try {
							new NewJFrame();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (y >= 288 && y <= 348) {
						// When clicking the "About us" button, it'll redirect
						// you to the "About Us" frame
						setVisible(false);
						AboutUs aboutFrame = new AboutUs();
					} else if (y >= 350 && y <= 497) {
						// When clicking the "Exit" button, the program is
						// terminated
						System.exit(0);
					}
				}
			}
		});
	}

	final Image image = new ImageIcon("src/images/SplashScreen.png").getImage();

	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}

	public Dimension getPreferredSize() {
		return new Dimension(550, 400);
	}
}