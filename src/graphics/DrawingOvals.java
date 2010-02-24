package graphics;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

import chat.ChatPanel;

public class DrawingOvals extends JFrame implements Runnable {
	/**
	 * This class draws Ovals (Circles) on the main frame and checks whether any
	 * of these Ovals are being clicked on then it performs action according to
	 * the place of the circle
	 * */

	// define socket and buffer for connection
	Socket socket;
	static DataInputStream in = null;
	static DataOutputStream out = null;
	public static InetAddress ia;
	int serverWin = 0;
	int clientWin = 0;

	int x, y;

	static int full = 0; // number of circles played

	static JLabel[][] jlbl = new JLabel[6][7];

	static ImageIcon img = new ImageIcon("src/images/empty.png");
	ImageIcon green = new ImageIcon("src/images/green.png");
	ImageIcon red = new ImageIcon("src/images/red.png");

	static int PLAYER;

	public void run() {
		while (true) {
			try {
				InetAddress ia = socket.getInetAddress();
				String s = in.readUTF();
				String[] split = s.split("\\D");
				String yy = split[0];
				String xx = split[1];
				PLAYER = Integer.parseInt(split[2]);
				if (PLAYER == 3) {
					replayGame();
					PLAYER = 1;
				} else {
					int xIndex2 = Integer.parseInt(xx);
					int yIndex2 = Integer.parseInt(yy);
					if (xIndex2 == 10) {
						PLAYER = 1;
					} else if (PLAYER == 0) {
						getLbl(yIndex2, xIndex2);
						serverWin += 1;
//						replayGame();
						PLAYER = 1;
					} else {
						getLbl(yIndex2, xIndex2);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public DrawingOvals() {
		// Creating the Frame and setting it's properties [visibility, size,
		// resizability and closing]
		this.setVisible(true);
		this.setSize(800, 600);
		this.setTitle("Connect 4");
		this.setResizable(false);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		final Image icon = new ImageIcon("src/images/Connect4Logo.png")
				.getImage();
		this.setIconImage(icon);

		// Adding the JPanel to the Jthis and edit their properties
		Panel panel = new Panel(false);
		panel.setOpaque(false);
		Panel p = new Panel(true);
		p.setLayout(new FlowLayout(FlowLayout.LEFT, 185, 28));
		p.add(panel);
		this.add(p);
		this.pack();

		// adding chat panel to the this
		ChatPanel cp = new ChatPanel();
		this.add(cp, BorderLayout.SOUTH);
		// make send button the default button
		// when pressing enter
		cp.getRootPane().setDefaultButton(cp.send);

		// connection to the server socket

		try {
			socket = new Socket("localhost", 8451);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());

			Thread t = new Thread(this);
			t.start();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		panel.setLayout(new GridLayout(6, 7));
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				jlbl[i][j] = new JLabel();
				jlbl[i][j].setIcon((img));
				panel.add(jlbl[i][j]);

			}
		}
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseClick) {
				x = mouseClick.getX();
				y = mouseClick.getY();
				if (PLAYER == 1) {
					repaint();
				}
			}
		});
	}

	// Painting the Circles and filling them
	public void paint(Graphics g) {
		super.paint(g);

		// Drawing the Circles
		for (int i = 1; i <= 7; i++) {
			for (int j = 1; j <= 6; j++) {
				int ovalX = 130 + (i * 60);
				int ovalY = (j * 60);
				if (x >= ovalX && x <= 50 + ovalX) {
					if (y >= ovalY && y <= 50 + ovalY) {
						// getting the X and Y indexes
						int xIndex = (ovalX - 130) / 60;
						int yIndex = ovalY / 60;

						boolean isFull = ICheck.checkFull(full);
						try {
							if (isFull == true) {
								JOptionPane.showMessageDialog(null,
										"The board is full");
							} else {
								int row = ICheck.checkColumn(xIndex, img, jlbl);
								if (row == -1)
									JOptionPane.showMessageDialog(null,
											"not available here");
								else {
									full++;
									yIndex = row + 1;
									jlbl[yIndex - 1][xIndex - 1].setIcon(red);
									String win =  ICheck.checkwin( jlbl, red,green); 
									if (win == "none")
										out.writeUTF(yIndex + "x" + xIndex
												+ "x" + 2);
									else {
										out.writeUTF(yIndex + "x" + xIndex
												+ "x" + 0);
										clientWin += 1;
										System.out.println("Server: "
												+ serverWin + "\tClient: "
												+ clientWin);
										JOptionPane.showMessageDialog(null,
												"you win");
									}
									PLAYER = 2;
									out.flush();
								}
							}

						} catch (IOException ie) {
							ie.printStackTrace();
						}
					}
				}
			}
		}
	}

	public void getLbl(int yIndex2, int xIndex2) {
		jlbl[yIndex2 - 1][xIndex2 - 1].setIcon(green);
		String winn =  ICheck.checkwin( jlbl, red,green);
	}

	public Dimension getPreferredSize() {
		return new Dimension(800, 600);
	}

	public static void replayGame() {
		// replaying game by resetting the board to its initial state
		full = 0;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				jlbl[i][j].setIcon(img);
			}
		}
		// informing the Server with the Replay status
		if (PLAYER != 3) {
			try {
				out.writeUTF(0 + "x" + 0 + "x" + 3);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}