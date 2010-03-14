package graphics;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

import chat.ChatPanel;
import chat.ChatPanelServer;
import mp3.MP3;

@SuppressWarnings({ "serial", "unused", "deprecation", "static-access" })
/**
 * This class draws Ovals (Circles) on the main frame and checks if any
 * of these Ovals are clicked, then it performs action according to
 * the place of the circle
 * */
public class DrawingOvalsServer extends JFrame implements Runnable {
	// define socket, buffer and InetAddress for connection
	static ServerSocket serverSocket = null;
	static Socket socket;
	static ServerSocket scanSocket = null;
	static DataInputStream in = null;
	static DataOutputStream out = null;
	// Labels to show information about both players
	private JLabel clientLabel, serverLabel, clientPlayer, serverPlayer,
			clientScore, serverScore, redLabel, greenLabel, waitingImageLabel,
			waitingLabel;
	public static String name;
	 // intial value of score for server and client
	int serverWin = 0;
	int clientWin = 0;

	int x, y; // to record mouse position
	static int full = 0; // number of painted circles
	static JLabel[][] jlbl = new JLabel[6][7]; // Labels to store painted images of circles by both players

	static ImageIcon img = new ImageIcon("src/images/empty.png");
	ImageIcon green = new ImageIcon("src/images/green.png");
	ImageIcon red = new ImageIcon("src/images/red.png");

	static int PLAYER = 2; // determine who's turn is to be played
	boolean lestiner, socketAccepted = false;

	/**
	 * Thread method to start reading input from client and split it to know if
	 * there's any specified signal (i.e: win signal, replay signal) and then
	 * perform an action according to that signal
	 * @see ICheck
	 * */
	public void run() {
		if (socketAccepted == false) {
			try {
//				scanSocket = new ServerSocket(8453);
//				scanSocket.accept();
				socket = serverSocket.accept();
				controlComponents(true);
				repaint();
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			socketAccepted = true;
			ChatPanelServer.sendSpace.requestFocus();
		}
		while (true) {
			clientPlayer.setText(ChatPanelServer.name2);
			try {
				InetAddress ia = socket.getInetAddress();
				String s = in.readUTF();
				String[] split = s.split("\\D");
				PLAYER = Integer.parseInt(split[2]);
				if (PLAYER == 3) {
					// replay game if the Client send the raplay signal
					replayGame();
					PLAYER = 2;
				} else {
					// indexes of the last painted circle by the Client
					String yy = split[0];
					String xx = split[1];
					int xIndex2 = Integer.parseInt(xx);
					int yIndex2 = Integer.parseInt(yy);
					if (PLAYER == 0) {
						// PLAYER equals 0 if the other player wins
						getLbl(yIndex2, xIndex2);
						clientWin += 1;
						clientScore.setText("Score:  " + clientWin);
						File filename = new File("src/sounds/boo-03.mp3");// playing mp3 file
                        MP3 mp3 = new MP3(filename);
                        mp3.play();
					} else {
						getLbl(yIndex2, xIndex2);
						File filename = new File("src/sounds/sound6.mp3");// playing mp3 file
                        MP3 mp3 = new MP3(filename);
                        mp3.play();
					}
				}
			} catch (IOException e) {
				ChatPanelServer.t.stop();
				File filename = new File("src/sounds/alert.mp3");// playing mp3 file
                MP3 mp3 = new MP3(filename);
                mp3.play();
				cleanUp();
				System.exit(1);
			}
		}
	}
	static Panel panel;

	/**
	 * Constructor to draw the main frame and all the components needed, then it
	 * connects to server sockets, uses threads after establishing the connection
	 * @see #run()
	 * */
	public DrawingOvalsServer() {
		// Creating the Frame and setting it's properties [visibility, size,
		// resizability and closing]
		this.setVisible(true);
		this.setSize(800, 600);
		this.setTitle("Connect 4 Server");
		while (name == null || name.length() == 0) {
			name = JOptionPane.showInputDialog(null, "enter your name");
		}
		// labels for information about the players
		serverPlayer = new JLabel(name);
		serverPlayer.setForeground(new java.awt.Color(254, 254, 254));
		clientPlayer = new JLabel();
		clientPlayer.setForeground(new java.awt.Color(254, 254, 254));
		clientScore = new JLabel("Score:  " + clientWin);
		clientScore.setForeground(new java.awt.Color(254, 254, 254));
		serverScore = new JLabel("Score:  " + serverWin);
		serverScore.setForeground(new java.awt.Color(254, 254, 254));
        clientLabel = new JLabel("Player 2");
        clientLabel.setForeground(new java.awt.Color(254, 254, 254));
        clientLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 25));
        serverLabel = new JLabel("Player 1");
        serverLabel.setForeground(new java.awt.Color(254, 254, 254));
        serverLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 25));
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
		this.setResizable(false);
		this.setDefaultCloseOperation(cleanUpOnClose());
		final Image icon = new ImageIcon("src/images/Connect4Logo.png")
				.getImage();
		this.setIconImage(icon);
		
		waitingImageLabel = new JLabel();
		waitingImageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/images/waitingtoconnect.png")));
		waitingImageLabel.setMaximumSize(new java.awt.Dimension(600, 250));
		waitingImageLabel.setMinimumSize(new java.awt.Dimension(600, 250));
		
		waitingLabel = new JLabel("Waiting for a client to connect..");
        waitingLabel.setForeground(new java.awt.Color(254, 254, 254));
        waitingLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 25));

		// Adding the JPanel to the JFrame and edit their properties
		panel = new Panel(false);
		panel.setOpaque(false);
		Panel p = new Panel(true);
		p.setLayout(new FlowLayout(FlowLayout.LEFT, 185, 28));
		p.add(panel);
		
		this.add(waitingLabel);
		waitingLabel.setBounds(150, 87, 600, 250);
		this.add(waitingImageLabel);
		waitingImageLabel.setBounds(100, 87, 600, 250);
		this.add(serverPlayer);
		serverPlayer.setBounds(45, 140, 135, 35);
		this.add(clientPlayer);
		clientPlayer.setBounds(665, 140, 135, 35);
		this.add(clientScore);
		clientScore.setBounds(665, 185, 135, 35);
		this.add(serverScore);
		serverScore.setBounds(45, 185, 135, 35);
		this.add(greenLabel);
		greenLabel.setBounds(45, 250, 60, 60);
		this.add(redLabel);
		redLabel.setBounds(665, 250, 60, 60);
		this.add(clientLabel);
		clientLabel.setBounds(645, 100, 135, 35);
		this.add(serverLabel);
		serverLabel.setBounds(25, 100, 135, 35);
		this.add(p);
		this.pack();

		// adding chat panel to the the main panel
		ChatPanelServer cps = new ChatPanelServer();
		this.add(cps, BorderLayout.SOUTH);
		// make send button the default button when pressing enter
		cps.getRootPane().setDefaultButton(cps.send);
		// opening the socket and accepting connection from client
		try {
			// Creating a socket and waiting for connection
			serverSocket = new ServerSocket(8451);
			Thread tP = new Thread(this);
			tP.start();
		} catch (IOException ioe) {
			ChatPanelServer.t.stop();
			File filename = new File("src/sounds/alert.mp3");// playing mp3 file
            MP3 mp3 = new MP3(filename);
            mp3.play();
			cleanUp();
			System.exit(1);
		}

		panel.setLayout(new GridLayout(6, 7));
		// setting a grid to draw the circles on it
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				jlbl[i][j] = new JLabel();
				jlbl[i][j].setIcon(img);
				panel.add(jlbl[i][j]);
			}
		}

		// getting the mouse position when clicked
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseClick) {
				if (lestiner == true) {
					x = mouseClick.getX();
					y = mouseClick.getY();
					if (PLAYER == 2) {
						repaint();
					}
				}
			}
		});
		controlComponents(false);
	}

	/**
	 * Drawing the circles in the grid, filling them with the right color if
	 * they're clicked, then check if anyone won the game
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
								File filename = new File("src/sounds/alert.mp3");// playing mp3 file 
                                MP3 mp3 = new MP3(filename);
                                mp3.play();
								String[] options = {"Replay", "Exit"};
								int option = JOptionPane.showOptionDialog(null, "The board is full.", "Replay?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
								if (option == 0) {
									replayGame();
								}
								else {
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
									jlbl[yIndex - 1][xIndex - 1].setIcon(green);
									String win = ICheck.checkwin(jlbl, red,
											green);
									if (win == "none") {
										out.writeUTF(yIndex + "x" + xIndex
												+ "x" + 1);
										File filename = new File("src/sounds/sound6.mp3");// playing mp3 file
                                        MP3 mp3 = new MP3(filename);
                                        mp3.play();
									}
									else {
										out.writeUTF(yIndex + "x" + xIndex
												+ "x" + 0);
										serverWin += 1;
										serverScore.setText("Score:  "
												+ serverWin);
										File filename = new File("src/sounds/app-15.mp3");// playing mp3 file 
                                        MP3 mp3 = new MP3(filename);
                                        mp3.play();
										JOptionPane.showMessageDialog(null,
												"you win");
									}
									PLAYER = 1;
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

	/** getting the last played label and check if somebody won */
	public void getLbl(int yIndex2, int xIndex2) {
		jlbl[yIndex2 - 1][xIndex2 - 1].setIcon(red);
		String winn = ICheck.checkwin(jlbl, red, green);
	}

	/** Setting the Frame's Size */
	public Dimension getPreferredSize() {
		return new Dimension(800, 600);
	}

	/**
	 * A method for controlling the frame components, enable, disable, hide and
	 * show them. this is important to make sure the server player won't do
	 * anything wrong by using components to send for example when the socket is
	 * not yet created.
	 * */
	public void controlComponents(boolean enabled) {
		lestiner = enabled;
		ChatPanelServer.sendSpace.setEnabled(enabled);
		ChatPanelServer.send.setEnabled(enabled);
		ChatPanelServer.replay.setEnabled(enabled);
		waitingImageLabel.setVisible(!enabled);
		waitingLabel.setVisible(!enabled);
		serverPlayer.setVisible(enabled);
		clientPlayer.setVisible(enabled);
		clientScore.setVisible(enabled);
		serverScore.setVisible(enabled);
		greenLabel.setVisible(enabled);
		redLabel.setVisible(enabled);
		clientLabel.setVisible(enabled);
		serverLabel.setVisible(enabled);
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
		
		// informing the Client with the Replay status
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
					cleanUp();
					cleanUp();
					System.exit(1);
				}
			}
			else if (option == 2) {
				File filename2 = new File("src/sounds/alert.mp3");// playing mp3 file 
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
	 * Cleaning up by closing all the open sockets and streams before
	 * terminating the game to make sure it won't cause any problems
	 * */
	public static void cleanUp() {
		try {
			if (serverSocket != null) {
				serverSocket.close();
				serverSocket = null;
			}
		} catch (IOException e) { serverSocket = null; }
		
		try {
			if (in != null) {
				in.close();
				in = null;
			}
		} catch (IOException e) { in = null; }
		
		try {
			if (out != null) {
				out.close();
				out = null;
			}
		} catch (IOException e) { out = null; }
		
		try {
			if (socket != null) {
				socket.close();
				socket = null;
			}
		} catch (IOException e) { socket = null; }
		
		try {
			if (ChatPanelServer.socket != null) {
				ChatPanelServer.socket.close();
				ChatPanelServer.socket = null;
			}
		} catch (IOException e) { ChatPanelServer.socket = null; }
		
		try {
			if (ChatPanelServer.in != null) {
				ChatPanelServer.in.close();
				ChatPanelServer.in = null;
			}
		} catch (IOException e) { ChatPanelServer.in = null; }
		
		try {
			if (ChatPanelServer.out != null) {
				ChatPanelServer.out.close();
				ChatPanelServer.out = null;
			}
		} catch (IOException e) { ChatPanelServer.out = null; }
	}

	/** Cleaning up if the user exits using the default close operation */
	private static int cleanUpOnClose() {
		cleanUp();
		return 3;
	}
}