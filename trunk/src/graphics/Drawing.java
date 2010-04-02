package graphics;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import mp3.MP3;
import chat.Chat;
import chat.ChatPanel;

@SuppressWarnings( { "serial", "unused", "deprecation" })
/**
 * A class that's responsible for initializing the game components and
 * specifies how the game should be painted and who's turn is to be played.
 * */
public class Drawing extends JFrame implements Runnable {
	static Socket socket;
	static DataInputStream in = null;
	static DataOutputStream out = null;
	// Labels to show information about both players
	public static JLabel clientPlayer, serverPlayer;
	protected static JLabel clientLabel, serverLabel, clientScore, serverScore,
			redLabel, greenLabel, waitingImageLabel, waitingLabel;
	protected static JTextField nameField;
	protected static JButton nameFieldButton;
	// intial value of score for server and client
	int serverWin = 0;
	int clientWin = 0;

	int x, y; // to record mouse position
	static int full = 0; // number of painted circles
	static JLabel[][] jlbl = new JLabel[6][7]; // Labels to store painted images
												// of circles by both players
	static ImageIcon img = new ImageIcon("src/images/empty.png");
	ImageIcon green = new ImageIcon("src/images/green.png");
	ImageIcon red = new ImageIcon("src/images/red.png");
	ImageIcon color = new ImageIcon();
	ImageIcon secondColor = new ImageIcon();

	static int PLAYER, PLAYERNumber; // determine who's turn is to be played
	static boolean lestiner;

	static Panel panel;

	/**
	 * Thread method to start reading input from the other player and split it
	 * to know if there's any specified signal (i.e: win signal, replay signal)
	 * and then perform an action according to that signal.
	 * 
	 * @see ICheck
	 * */
	public void run() {
		if (PLAYERNumber == 2) {
			Server.initiateServer();
		}
		while (true) {
			try {
				InetAddress ia = socket.getInetAddress();
				String s = in.readUTF();
				String[] split = s.split("\\D");
				PLAYER = Integer.parseInt(split[2]);
				if (PLAYER == 3) {
					// replay game if the Server send the raplay signal
					replayGame();
					if (PLAYERNumber == 2) {
						PLAYER = 2;
					} else {
						PLAYER = 1;
					}
				} else {
					// indexes of the last painted circle by the Server
					String yy = split[0];
					String xx = split[1];
					int xIndex2 = Integer.parseInt(xx);
					int yIndex2 = Integer.parseInt(yy);
					if (xIndex2 == 10) {
						PLAYER = 1;
					} else if (PLAYER == 0) {
						// PLAYER equals 0 if the other player wins
						getLbl(yIndex2, xIndex2);
						if (PLAYERNumber == 1) {
							serverWin += 1;
							serverScore.setText("Score:  " + serverWin);
						} else if (PLAYERNumber == 2) {
							clientWin += 1;
							clientScore.setText("Score:  " + clientWin);
						}
						File filename = new File("src/sounds/boo-03.mp3");// playing
																			// mp3
																			// file
						MP3 mp3 = new MP3(filename);
						mp3.play();
					} else {
						getLbl(yIndex2, xIndex2);
						File filename = new File("src/sounds/sound6.mp3");// playing
																			// mp3
																			// file
						MP3 mp3 = new MP3(filename);
						mp3.play();
					}
				}
			} catch (IOException e) {
				ChatPanel.t.stop();
				File filename = new File("src/sounds/alert.mp3");// playing mp3
																	// file
				MP3 mp3 = new MP3(filename);
				mp3.play();
				cleanUp();
				System.exit(1);
			}
		}
	}

	/**
	 * Creating the Frame and setting it's properties [visibility, size,
	 * resizability and closing], and Drawing all the needed contents [buttons,
	 * labels, etc.].
	 * */
	public void initComponents() {
		this.setVisible(true);
		this.setSize(800, 600);

		clientLabel.setForeground(new java.awt.Color(254, 254, 254));
		clientLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 25));
		serverLabel.setForeground(new java.awt.Color(254, 254, 254));
		serverLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 25));
		clientPlayer.setForeground(new java.awt.Color(254, 254, 254));
		serverPlayer.setForeground(new java.awt.Color(254, 254, 254));
		clientScore = new JLabel("Score:  " + clientWin);
		clientScore.setForeground(new java.awt.Color(254, 254, 254));
		serverScore = new JLabel("Score:  " + serverWin);
		serverScore.setForeground(new java.awt.Color(254, 254, 254));
		greenLabel = new JLabel();
		greenLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/images/green.png")));
		greenLabel.setMaximumSize(new java.awt.Dimension(60, 60));
		greenLabel.setMinimumSize(new java.awt.Dimension(60, 60));
		redLabel = new JLabel();
		redLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/images/red.png")));
		redLabel.setMaximumSize(new java.awt.Dimension(60, 60));
		redLabel.setMinimumSize(new java.awt.Dimension(60, 60));
		nameField = new JTextField();
		nameField.setBackground(new java.awt.Color(0, 0, 0));
		nameField.setForeground(new java.awt.Color(254, 254, 254));
		nameFieldButton = new JButton("Submit");

		waitingImageLabel = new JLabel();
		waitingImageLabel.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/images/waitingtoconnect.png")));
		waitingImageLabel.setMaximumSize(new java.awt.Dimension(600, 250));
		waitingImageLabel.setMinimumSize(new java.awt.Dimension(600, 250));

		waitingLabel = new JLabel("Please enter your name");
		waitingLabel.setForeground(new java.awt.Color(254, 254, 254));
		waitingLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 25));

		this.setResizable(false);
		this.setDefaultCloseOperation(cleanUpOnClose());
		final Image icon = new ImageIcon("src/images/Connect4Logo.png")
				.getImage();
		this.setIconImage(icon);

		// Adding the JPanel to the JFrame and edit their properties
		panel = new Panel(false);
		panel.setOpaque(false);
		Panel p = new Panel(true);
		p.setLayout(new FlowLayout(FlowLayout.LEFT, 185, 28));
		p.add(panel);

		this.add(nameField);
		nameField.setBounds(330, 250, 120, 25);
		nameField.requestFocus();
		this.add(nameFieldButton);
		nameFieldButton.setBounds(330, 280, 120, 25);
		this.add(waitingLabel);
		waitingLabel.setBounds(150, 87, 600, 250);
		this.add(waitingImageLabel);
		waitingImageLabel.setBounds(100, 87, 600, 250);
		this.add(clientLabel);
		clientLabel.setBounds(645, 100, 135, 35);
		this.add(serverLabel);
		serverLabel.setBounds(25, 100, 135, 35);
		this.add(clientPlayer);
		clientPlayer.setBounds(665, 140, 135, 35);
		this.add(serverPlayer);
		serverPlayer.setBounds(45, 140, 135, 35);
		this.add(clientScore);
		clientScore.setBounds(665, 185, 135, 35);
		this.add(serverScore);
		serverScore.setBounds(45, 185, 135, 35);
		this.add(greenLabel);
		greenLabel.setBounds(45, 250, 60, 60);
		this.add(redLabel);
		redLabel.setBounds(665, 250, 60, 60);
		this.add(p);
		this.pack();
		
		panel.setLayout(new GridLayout(6, 7));
		// setting a grid to draw the circles on it
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				jlbl[i][j] = new JLabel();
				jlbl[i][j].setIcon(img);
				panel.add(jlbl[i][j]);
			}
		}
	}

	/**
	 * adding all needed lestiners to the frame and panel
	 * */
	public void Lestiners() {
		// getting the mouse position when clicked
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseClick) {
				if (lestiner == true) {
					x = mouseClick.getX();
					y = mouseClick.getY();
					if ((PLAYER == 2 && PLAYERNumber == 2)
							|| (PLAYER == 1 && PLAYERNumber == 1)) {
						repaint();
					}
				}
			}
		});

		nameField.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
				if (nameField.getText().length() > 14) {
					nameField.setText(nameField.getText().substring(0, 14));
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
			}
		});
	}

	/** getting the last played label and check if somebody won */
	public void getLbl(int yIndex2, int xIndex2) {
		jlbl[yIndex2 - 1][xIndex2 - 1].setIcon(color);
		String winn = ICheck.checkwin(jlbl, red, green);
	}

	/** Setting the Frame's Size */
	public Dimension getPreferredSize() {
		return new Dimension(800, 600);
	}

	/** replaying game by re-setting the board to its initial state */
	public static void replayGame() {
		if (PLAYER == 3) {
			full = 0;
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 7; j++) {
					jlbl[i][j].setIcon(img);
				}
			}
		}

		// informing the Server with the Replay status
		else {
			File filename = new File("src/sounds/alert.mp3");// playing mp3 file
			MP3 mp3 = new MP3(filename);
			mp3.play();
			String[] options = { "Yes", "No", "Exit" };
			int option = JOptionPane.showOptionDialog(null,
					"Are you sure you want to replay?", "Replay?",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);
			if (option == 0) {
				full = 0;
				for (int i = 0; i < 6; i++) {
					for (int j = 0; j < 7; j++) {
						jlbl[i][j].setIcon(img);
					}
				}
				try {
					out.writeUTF(0 + "x" + 0 + "x" + 3);
				} catch (IOException e) {
					File filename1 = new File("src/sounds/alert.mp3");// playing
																		// mp3
																		// file
					MP3 mp31 = new MP3(filename1);
					mp31.play();
					cleanUp();
					System.exit(1);
				}
			} else if (option == 2) {
				File filename2 = new File("src/sounds/alert.mp3");// playing mp3
																	// file
				MP3 mp32 = new MP3(filename2);
				mp32.play();
				String[] eOptions = { "Yes", "No" };
				int eOption = JOptionPane.showOptionDialog(null,
						"Are you sure you want to exit?", "Exit?",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, eOptions,
						eOptions[1]);
				if (eOption == 0) {
					cleanUp();
					System.exit(0);
				}
			}
		}
	}

	/**
	 * A method for controlling the frame components, enable, disable, hide and
	 * show them. this is important to make sure the server player won't do
	 * anything wrong by using components to send for example when the socket is
	 * not yet created.
	 * */
	public static void controlComponents(boolean enabled) {
		lestiner = enabled;
		Chat.sendSpace.setEnabled(enabled);
		Chat.send.setEnabled(enabled);
		Chat.replay.setEnabled(enabled);
		waitingImageLabel.setVisible(!enabled);
		waitingLabel.setVisible(!enabled);
		nameField.setVisible(!enabled);
		nameFieldButton.setVisible(!enabled);
		serverPlayer.setVisible(enabled);
		clientPlayer.setVisible(enabled);
		clientScore.setVisible(enabled);
		serverScore.setVisible(enabled);
		greenLabel.setVisible(enabled);
		redLabel.setVisible(enabled);
		clientLabel.setVisible(enabled);
		serverLabel.setVisible(enabled);
	}

	/**
	 * Cleaning up by closing all the open sockets and streams before
	 * terminating the game to make sure it won't cause any problems
	 * */
	public static void cleanUp() {
		if (PLAYERNumber == 2) {
			Server.serverCleanUp();
		}

		try {
			if (in != null) {
				in.close();
				in = null;
			}
		} catch (IOException e) {
			in = null;
		}

		try {
			if (out != null) {
				out.close();
				out = null;
			}
		} catch (IOException e) {
			out = null;
		}

		try {
			if (socket != null) {
				socket.close();
				socket = null;
			}
		} catch (IOException e) {
			socket = null;
		}

		try {
			if (Chat.socket != null) {
				Chat.socket.close();
				Chat.socket = null;
			}
		} catch (IOException e) {
			Chat.socket = null;
		}

		try {
			if (Chat.in != null) {
				Chat.in.close();
				Chat.in = null;
			}
		} catch (IOException e) {
			Chat.in = null;
		}

		try {
			if (Chat.out != null) {
				Chat.out.close();
				Chat.out = null;
			}
		} catch (IOException e) {
			Chat.out = null;
		}
	}

	/** Cleaning up if the user exits using the default close operation */
	static int cleanUpOnClose() {
		cleanUp();
		return 3;
	}
	
	/**
	 * Drawing the circles in the grid, filling them with the right color if
	 * they're clicked, then check if anyone won the game
	 * @see ICheck
	 * */
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
								// try the following if the board is full
								File filename = new File("src/sounds/alert.mp3");
                                MP3 mp3 = new MP3(filename);
                                mp3.play();
								String[] options = { "Replay", "Exit" };
								int option = JOptionPane.showOptionDialog(null,
										"The board is full.", "Replay?",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.INFORMATION_MESSAGE, null,
										options, options[0]);
								if (option == 0) {
									replayGame();
								} else {
									cleanUp();
									System.exit(0);
								}
							} else {
								int row = ICheck.checkColumn(xIndex, img, jlbl);
								if (row == -1)
									JOptionPane.showMessageDialog(null,
											"not available here");
								else {
									full++;
									yIndex = row + 1;
									jlbl[yIndex - 1][xIndex - 1].setIcon(secondColor);
									String win = ICheck.checkwin(jlbl, red,
											green);
									if (win == "none") {
										if (PLAYERNumber == 1) {
											out.writeUTF(yIndex + "x" + xIndex
													+ "x" + 2);
										}
										else if (PLAYERNumber == 2) {
											out.writeUTF(yIndex + "x" + xIndex
													+ "x" + 1);
										}
										File filename = new File("src/sounds/sound6.mp3");// playing mp3 file
                                        MP3 mp3 = new MP3(filename);
                                        mp3.play();
									}
									else {
										out.writeUTF(yIndex + "x" + xIndex
												+ "x" + 0);
										if (PLAYERNumber == 1) {
											clientWin += 1;
											clientScore.setText("Score:  " + clientWin);
										}
										else if (PLAYERNumber == 2) {
											serverWin += 1;
											serverScore.setText("Score:  " + serverWin);
										}
										
										File filename = new File("src/sounds/app-15.mp3");// playing mp3 file 
                                        MP3 mp3 = new MP3(filename);
                                        mp3.play();
										JOptionPane.showMessageDialog(null,
												"you win");
									}
									if (PLAYERNumber == 1) {
										PLAYER = 2;	
									}
									else if (PLAYERNumber == 2) {
										PLAYER = 1;
									}
									out.flush();
								}
							}

						} catch (IOException ie) {
							File filename = new File("src/sounds/alert.mp3");// playing mp3 file 
                            MP3 mp3 = new MP3(filename);
                            mp3.play();
							cleanUp();
							System.exit(1);
						}
					}
				}
			}
		}
	}
}
