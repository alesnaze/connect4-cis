
package chat;

import graphics.DrawingOvals;
import graphics.DrawingOvalsServer;
import graphics.Play;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.*;
import mp3.MP3;

@SuppressWarnings( { "serial", "unused" })
/**
 * Defines Chat components in the Client side and draws them to the main
 * frame. then it connects to the server and starts I/O streaming, then
 * begin sending and receiving text and update the chat receive space each
 * time.
 * */
public class ChatPanel extends JPanel implements Runnable {

	final Image image = new ImageIcon("src/images/chat.png").getImage();

	// define chat components
	public static JTextField sendSpace = new JTextField(53);
	JTextArea recieveSpace = new JTextArea(5, 62);
	JScrollPane sp_recieveSpace = new JScrollPane(recieveSpace);
	JLabel sendlbl = new JLabel(">");
	public JButton send = new JButton("send");
	public JButton replay = new JButton("replay");
	public JButton exit = new JButton("Exit");
	int lineSpace = 30;
	String printString;
	String name = DrawingOvals.name;
	public static String name2;
	public static Thread t;

	// define socket and buffer for connection
	public static Socket socket;
	public static BufferedReader in = null;
	public static BufferedWriter out = null;
	boolean splitOnce;

	/** thread method to allow receiving messages from chat */
	public void run() {
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
					name2 = splitted[0];
					splitOnce = false;
				} else {
					printString = in.readLine() + "\n";
					printText();
				}
			} catch (IOException e) {
				File filename = new File("src/sounds/alert.mp3");// playing mp3 file
                MP3 mp3 = new MP3(filename);
                mp3.play();
				DrawingOvals.cleanUp();
				System.exit(0);
			}
		}
	}

	/**
	 * A constructor which draws the actual chat components, try to connect to
	 * the server, then run the thread to handle the chatting
	 * @see #run()
	 * */
	public ChatPanel() {
		repaint();
		// add chat components
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 8));
		sp_recieveSpace
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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
		sendlbl.setFont(new Font(null, 1, 12));
		sendlbl.setForeground(new Color(85, 0, 0));

		this.add(sp_recieveSpace);
		this.add(sendlbl);
		this.add(sendSpace);
		this.add(send);
		this.add(replay);
		this.add(exit);

		// connection to the server socket
		try {
			socket = new Socket("localhost", 8000);
			in = new BufferedReader(new InputStreamReader(socket
					.getInputStream(), "UTF8"));
			out = new BufferedWriter(new OutputStreamWriter(socket
					.getOutputStream(), "UTF8"));
			splitOnce = true;
			out.write(name + ": ");
			out.newLine();
			out.flush();
			t = new Thread(this);
			t.start();

		} catch (IOException ioe) {
			File filename = new File("src/sounds/alert.mp3");// playing mp3 file
            MP3 mp3 = new MP3(filename);
            mp3.play();
			DrawingOvals.cleanUp();
			System.exit(1);
		}

		// the action of sending message to the server
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printString = name + ": " + sendSpace.getText() + "\n";
				printText();
				try {
					out.write(name + ": " + sendSpace.getText());
					out.newLine();
					out.flush();
					sendSpace.setText("");
				} catch (IOException ie) {
					File filename = new File("src/sounds/alert.mp3");// playing mp3 file
                    MP3 mp3 = new MP3(filename);
                    mp3.play();
					DrawingOvals.cleanUp();
					System.exit(1);
				}
			}
		});
		replay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File filename = new File("src/sounds/sound11.mp3");// playing mp3 file
                MP3 mp3 = new MP3(filename);
                mp3.play();
				DrawingOvals.replayGame();
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
					DrawingOvals.cleanUp();
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
		recieveSpace.setPreferredSize(new Dimension(400, lineSpace += 15));
		recieveSpace.getCaret().setDot(recieveSpace.getText().length());
		sp_recieveSpace.scrollRectToVisible(recieveSpace.getVisibleRect());
	}
}
