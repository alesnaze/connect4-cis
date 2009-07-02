package graphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Panel extends JPanel{
	final Image image = new ImageIcon("src/images/Connect4_bg.png").getImage();
	
	public Panel(){
		repaint();
	}

	// Drawing the image
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}	

	// Setting the JPanel's Size
    public Dimension getPreferredSize() {
        return new Dimension(800,600);
    }
}