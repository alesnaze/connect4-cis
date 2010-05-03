package logic;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings({ "serial", "unused" })
/**
 * This class draws the main frame's Background image and sets its dimension
 * */
class Panel extends JPanel {
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

	/** Drawing the image */
	protected void paintComponent(Graphics g) {
		if (background == true) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, this);
		}
	}

	/** Setting the JPanel's Size */
	public Dimension getPreferredSize() {
		return new Dimension(424, 363);
	}
}