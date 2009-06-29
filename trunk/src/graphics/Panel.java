package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

class Panel extends JPanel{
	final Image image = new ImageIcon("src/images/Connect4_bg.png").getImage();

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Drawing the image
		g.drawImage(image, 0, 0, null);

		// Drawing the Circles
		for (int i=1;i<=7;i++) {
			for (int j=1;j<=6;j++) {
				g.setColor(Color.white);
				g.drawOval(150+(i*55), (j*55), 50, 50);
			}
		}
	}

	// Setting the JPanel's Size
    public Dimension getPreferredSize() {
        return new Dimension(800,600);
    }
}