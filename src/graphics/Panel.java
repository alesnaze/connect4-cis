package graphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings({ "serial", "unused" })
class Panel extends JPanel {
	/**
	 * This class draws the game's Background image and sets its dimension
	 * */
	final Image image = new ImageIcon("src/images/connect4.png").getImage();
	boolean background;
	
	public Panel(boolean bg) {
		if (bg == true) {
			background = true;
		}
		else {
			background = false;
		}
	}

	// Drawing the image
	protected void paintComponent(Graphics g) {
		if (background == true) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, this);
		}
	}

	// Setting the JPanel's Size
	public Dimension getPreferredSize() {
		return new Dimension(424, 363);
	}
}