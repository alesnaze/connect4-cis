/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chat;

import graphics.DrawingOvals;
import graphics.DrawingOvalsServer;
import graphics.Play;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;

public class ChatPanelServer extends JPanel implements Runnable {
	/**
	 * This class draws the game's Background image and sets its dimension
	 * */
	
	final Image image = new ImageIcon("src/images/chat.png").getImage();

	// define chat components
	JTextField sendSpace = new JTextField(55);
	JTextArea recieveSpace = new JTextArea(4, 65);
	JScrollPane sp_recieveSpace = new JScrollPane(recieveSpace);
	public JButton send = new JButton("send");
	public JButton replay = new JButton("replay");
	int lineSpace = 0;
	String printString;

	// define socket and buffer for connection
	ServerSocket serverSocket = null;
	Socket socket;
	BufferedReader in = null;
	BufferedWriter out = null;

	// thread method to allow receiving messages from chat
	public void run() {
		while (true) {
			try {
				InetAddress ia = socket.getInetAddress();
				printString = ia.getHostName() + " : " + in.readLine() + "\n";
				printText();
			} catch (IOException e) {
			}
		}
	}

	public ChatPanelServer() {
		repaint();

		// add chat components
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 12));
		sp_recieveSpace.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		recieveSpace.setEditable(false);
		recieveSpace.setLineWrap(true);
		this.add(sp_recieveSpace);
		this.add(sendSpace);
		this.add(send);
		this.add(replay);

		// opening the socket and accepting connection from client
		try {
			serverSocket = new ServerSocket(8000);
			socket = serverSocket.accept();

			in = new BufferedReader(new InputStreamReader(socket
					.getInputStream(), "UTF8"));
			out = new BufferedWriter(new OutputStreamWriter(socket
					.getOutputStream(), "UTF8"));

			Thread t = new Thread(this);
			t.start();

		} catch (IOException ioe) {

		}

		// the action of sending message to the client
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				printString = " you : " + sendSpace.getText() + "\n" ; 
				printText();

				try {
					out.write(sendSpace.getText());
					out.newLine();
					out.flush();
					sendSpace.setText("");
				} catch (IOException ie) {

				}
			}
		});
		replay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DrawingOvalsServer.replayGame();
			}
		});
	}

	// Drawing the image
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}

	// Setting the JPanel's Size
	public Dimension getPreferredSize() {
		return new Dimension(800, 150);
	}
	
	public void printText() {
		recieveSpace.append(printString);
		recieveSpace.setPreferredSize(new Dimension(400, lineSpace += 15));
		recieveSpace.getCaret().setDot(recieveSpace.getText().length());
		sp_recieveSpace.scrollRectToVisible(recieveSpace.getVisibleRect());
	}
}