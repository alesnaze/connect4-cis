package graphics;

import java.awt.*;
import javax.swing.*;

class ChatPanel extends JPanel {
	/**
	 * This class draws the game's Background image and sets its dimension
	 * */
	final Image image = new ImageIcon("src/images/chat.png").getImage();

	public ChatPanel() {
		repaint();
	}

	// Drawing the image
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}

	// Setting the JPanel's Size
	public Dimension getPreferredSize() {
		return new Dimension(800, 150);
	}
}