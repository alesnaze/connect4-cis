package graphics;

import java.awt.*;

import javax.swing.*;

import sun.java2d.loops.DrawRect;

public class FrameAndBackground {
	public static void main(String[] args) {
//		Creating the Frame and setting it's properties [visibility, size, resizability and closing]
		JFrame frame = new JFrame("Connect 4");
		frame.setVisible(true);
		frame.setSize(800,600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
//		Getting the BackGround image in order to draw it later		
		final Image image = new ImageIcon("src/images/Connect4_bg.png").getImage();
//		Creating a JPanel which will hold the Background Image and the Playing Circles
		JPanel panel = new JPanel() {
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
//			Setting the JPanel's Size
		    public Dimension getPreferredSize() {
		        return new Dimension(800,600);
		    }
		};
		
//		CODE NOT IN USE
//		JPanel circlesPanel = new JPanel(new GridLayout(6,7,0,0));
//		for (int i=1;i<=42;i++) {
//			circlesPanel.add(new Ovals());
//		}

//		Adding the JPanel to the JFrame and edit their properties
		panel.setOpaque(false);
		frame.add(panel,BorderLayout.CENTER);
		frame.pack();

	}
}