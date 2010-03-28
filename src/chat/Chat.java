package chat;

import graphics.DrawingOvals;
import graphics.DrawingOvalsServer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mp3.MP3;

@SuppressWarnings({ "unused", "serial" })
public class Chat extends JPanel implements Runnable{
	final Image image = new ImageIcon("src/images/chat.png").getImage();

	// define chat components
	public static JTextField sendSpace = new JTextField(53);
	JTextArea recieveSpace = new JTextArea(4, 63);
	JScrollPane sp_recieveSpace = new JScrollPane(recieveSpace);
	JLabel sendlbl, chatSeparator;
	public static JButton send = new JButton("send");
	public static JButton replay = new JButton("replay");
	public JButton exit = new JButton("Exit");
	int lineSpace = 30;
	String printString;
	public static String serverName;
	public static String clientName;
	public static Thread t;

	// define socket and buffer for connection
	public static Socket socket;
	public static BufferedReader in = null;
	public static BufferedWriter out = null;
	public static boolean splitOnce, isSplitted, sentName;
	
	public static int PLAYER;
	
	public void run() {
		if (PLAYER == 2) {
			ChatPanelServer.initiateServer();
		}
		while (true) {
			try {
				InetAddress ia = socket.getInetAddress();
				File filename = new File("src/sounds/receive.mp3");// playing mp3 file
                MP3 mp3 = new MP3(filename);
                mp3.play();
				if (splitOnce == true) {
					// getting an empty message from the server to discover the server's name
					String str = in.readLine();
					String[] splitted = str.split(":");
					if (PLAYER == 1) {
						serverName = splitted[0];
						DrawingOvals.serverPlayer.setText(serverName);
					}
					else if (PLAYER == 2) {
						clientName = splitted[0];
						if (DrawingOvalsServer.nameFieldStatus == true) {
							writeName(DrawingOvalsServer.serverName);
						}
					}
					splitOnce = false;
					isSplitted = true;
					sentAndReceiveName();
				} else {
					printString = in.readLine() + "\n";
					printText();
				}
			} catch (IOException e) {
				File filename = new File("src/sounds/alert.mp3");// playing mp3 file
                MP3 mp3 = new MP3(filename);
                mp3.play();
				DrawingOvals.cleanUp();
				System.exit(1);
			}
		}
	}
	
	public void initComponents() {
		repaint();

		// add chat components
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 7));
		sp_recieveSpace
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		recieveSpace.setEditable(false);
		recieveSpace.setLineWrap(true);
		recieveSpace.setBackground(new Color(108, 114, 146));
		recieveSpace.setFont(new Font(null, 1, 12));
		recieveSpace.setForeground(new Color(0, 0, 58));
		sp_recieveSpace.setBorder(null);
		sendSpace.setOpaque(false);
		sendSpace.setForeground(new Color(0, 0, 58));
		sendSpace.setBorder(null);
		sendSpace.setFont(new Font(null, 1, 12));
		sendSpace
				.setToolTipText("Write here then press \"send\" to chat with the other player");
		sendlbl  = new JLabel(">");
		sendlbl.setFont(new Font(null, 1, 12));
		chatSeparator = new JLabel();
		chatSeparator.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/images/chatSeparator.png")));

		this.add(sp_recieveSpace);
		this.add(chatSeparator);
		this.add(sendlbl);
		this.add(sendSpace);
		this.add(send);
		this.add(replay);
		this.add(exit);
	}
	
	String playerName;
	public void buttonsLestiners() {
		// the action of sending message to the client
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (PLAYER == 1) {
					playerName = clientName;
				}
				else if (PLAYER == 2) {
					playerName = serverName;
				}
				File filename = new File("src/sounds/send.mp3");// playing mp3 file
                MP3 mp3 = new MP3(filename);
                mp3.play();
				printString = playerName + ": " + sendSpace.getText() + "\n";
				printText();
				try {
					out.write(playerName + ": " + sendSpace.getText());
					out.newLine();
					out.flush();
					sendSpace.setText("");
				} catch (IOException ie) {
					File filename2 = new File("src/sounds/alert.mp3");// playing mp3 file
		            MP3 mp32 = new MP3(filename2);
		            mp32.play();
		            if (PLAYER == 1) {
						DrawingOvals.cleanUp();
		    		}
		    		else if (PLAYER == 2) {
						DrawingOvalsServer.cleanUp();
		    		}
					System.exit(1);
				}
			}
		});
		replay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File filename = new File("src/sounds/sound11.mp3");// playing mp3 file
                MP3 mp3 = new MP3(filename);
                mp3.play();
                if (PLAYER == 1) {
    				DrawingOvals.replayGame();
	    		}
	    		else if (PLAYER == 2) {
					DrawingOvalsServer.replayGame();
	    		}
			}
		});

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] options = { "Yes", "No" };
				int option = JOptionPane.showOptionDialog(null,
						"Are you sure you want to exit?", "Exit?",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, options,
						options[1]);
				if (option == 0) {
					if (PLAYER == 1) {
						DrawingOvals.cleanUp();
		    		}
		    		else if (PLAYER == 2) {
						DrawingOvalsServer.cleanUp();
		    		}
					System.exit(0);
				}
			}
		});
	}
	

	/** Drawing the background image */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}

	/** Setting the JPanel's Size */
	public Dimension getPreferredSize() {
		return new Dimension(800, 150);
	}
	
	/**
	 * printing the written text in the local receive space and scrolling down
	 * after writing the text
	 * */
	public void printText() {
		recieveSpace.append(printString);
		int printNewLine = (printString.length()/80) + 1;
		recieveSpace.setPreferredSize(new Dimension(400, lineSpace += 15*printNewLine));
		recieveSpace.getCaret().setDot(recieveSpace.getText().length());
		sp_recieveSpace.scrollRectToVisible(recieveSpace.getVisibleRect());
	}
	
	public static void writeName(String serverName) {
		try {
			out.write(serverName + ": ");
			out.newLine();
			out.flush();
			sentName = true;
			sentAndReceiveName();
		} catch (IOException e) {

		}
	}
	public static void sentAndReceiveName() {
		if (isSplitted == true && sentName == true) {
			if (PLAYER == 2) {
				DrawingOvalsServer.controlComponents(true);
				DrawingOvalsServer.serverPlayer.setText(serverName);
				DrawingOvalsServer.clientPlayer.setText(clientName);
			}
			else if (PLAYER == 1) {
				DrawingOvals.controlComponents(true);
				DrawingOvals.serverPlayer.setText(serverName);
				DrawingOvals.clientPlayer.setText(clientName);
			}
			sendSpace.requestFocus();
		}
	}
}