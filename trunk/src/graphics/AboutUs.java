package graphics;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class AboutUs extends JFrame{
	private int x,y;
	
	public AboutUs(){
		setVisible(true);
		setSize(550,400);
		setTitle("About Us");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		repaint();
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseClick) {
				x=mouseClick.getX();
				y=mouseClick.getY();
				if (x>475 && x<=550) {
					if (y>=310 && y<=340) {
						setVisible(false);
						Play splashScreen = new Play();
					}
					else if (y>=343 && y<=370 ) {
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