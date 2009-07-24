package graphics;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

public class DrawingOvals extends JFrame{
	/**
	 * This class draws Ovals (Circles) on the main frame and checks whether any of these Ovals are being clicked on
	 * then it performs action according to the place of the circle
	 * */
	public void mainFrame() {
		// Creating the Frame and setting it's properties [visibility, size, resizability and closing]
		DrawingOvals frame = new DrawingOvals();
		frame.setVisible(true);
		frame.setSize(800,600);
		frame.setTitle("Connect 4");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

		// Adding the JPanel to the JFrame and edit their properties
		Panel panel = new Panel();
		panel.setOpaque(false);
		frame.add(panel, BorderLayout.CENTER);

		frame.pack();
	}

	// getting the mouse position when clicked
	int x, y;
	public DrawingOvals() {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseClick) { 
				x=mouseClick.getX();
				y=mouseClick.getY();
				repaint();
			}
		});
	}
	
	// Creating an ArrayList that will hold the Ovals dimensions
	ArrayList<Integer> positions = new ArrayList<Integer>();
	// Painting the Circles and filling them
	public void paint(Graphics g) {
		super.paint(g);

		// Drawing the Circles
		for (int i=1;i<=7;i++) {
			for (int j=1;j<=6;j++) {
				g.setColor(Color.white);
				int ovalX = 130+(i*60);
				int ovalY = (j*60);
				g.drawOval(ovalX, ovalY, 50, 50);

				if (x>=ovalX && x<=50+ovalX) {
					if (y>=ovalY && y<=50+ovalY) {
						positions.add(i); // we have to change this "i" and put the circles dimensions
						g.fillOval(ovalX, ovalY, 50, 50);
//						System.out.println(positions.size());
					}
				}
			}
		}
	}

    public Dimension getPreferredSize() {
        return new Dimension(800,600);
    }
}