package graphics;

import java.awt.*;

import javax.swing.*;

import sun.java2d.loops.DrawRect;

public class FrameAndBackground {
	private static final String Graphics = null;

	public static void main(String[] args) {
		JFrame frame = new JFrame("Connect 4");
		frame.setVisible(true);
		frame.setSize(800,600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		final Image image = new ImageIcon("src/images/Connect4_bg.png").getImage();
		JPanel panel = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image, 0, 0, null);
				for (int i=1;i<=7;i++) {
					for (int j=1;j<=6;j++) {
						g.setColor(Color.white);
						g.drawOval(150+(i*55), (j*55), 50, 50);
					}
				}
			}
		    public Dimension getPreferredSize() {
		        return new Dimension(800,600);
		    }
		};
//		JPanel circlesPanel = new JPanel(new GridLayout(6,7,0,0));
//		for (int i=1;i<=42;i++) {
//			circlesPanel.add(new Ovals());
//		}
		panel.setOpaque(false);
		frame.add(panel,BorderLayout.CENTER);
		frame.pack();

	}
}