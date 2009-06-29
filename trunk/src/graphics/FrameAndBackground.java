package graphics;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class FrameAndBackground extends JFrame{
	public static void main(String[] args) {
//		Creating the Frame and setting it's properties [visibility, size, resizability and closing]
		FrameAndBackground frame = new FrameAndBackground();
		frame.setVisible(true);
		frame.setSize(800,600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

//		Adding the JPanel to the JFrame and edit their properties
		Panel panel = new Panel();
		panel.setOpaque(false);
		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
	}

//	getting the mouse position when clicked
	int x, y;
	public FrameAndBackground() {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseClick) { 
				x=mouseClick.getX();
				y=mouseClick.getY();
				repaint();
			}
		});
	}
	
//	Painting the Circles and filling them
	public void paint(Graphics g) {
		super.paint(g);
//		Drawing the Circles
		for (int i=1;i<=7;i++) {
			for (int j=1;j<=6;j++) {
				g.setColor(Color.white);
				g.drawOval(130+(i*60), (j*60), 50, 50);

				if (x>=130+(i*60) && x<=180+(i*60)) {
					if (y>=(j*60) && y<=50+(j*60)) {
						g.fillOval(130+(i*60), (j*60), 50, 50);
					}
				}
			}
		}
	}	
}