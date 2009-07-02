package graphics;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Play extends JFrame{
	private int x,y;

	public static void main(String[] args) {
		Play frame = new Play();
	}

	public Play() {
		setVisible(true);
		setSize(550,400);
		setTitle("Main Menu");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		repaint();

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseClick) { 
				x=mouseClick.getX();
				y=mouseClick.getY();				
				if (x>=190 && x<=370) {
					if (y>=235 && y<=285) {
						setVisible(false);
						DrawingOvals game = new DrawingOvals();
						game.mainFrame();
					}
					else if (y>=288 && y<=348) {
						setVisible(false);
						AboutUs aboutFrame = new AboutUs();
					}
					else if (y>=350 && y<=497) {
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
        return new Dimension(550,400);
    }
}