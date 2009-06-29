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

//		Adding the JPanel to the JFrame and edit their properties
		Panel panel = new Panel();
		panel.setOpaque(false);
		frame.add(panel,BorderLayout.CENTER);
		frame.pack();

	}
}