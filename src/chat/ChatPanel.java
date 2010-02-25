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
import java.net.Socket;

import javax.swing.*;

public class ChatPanel extends JPanel implements Runnable {
	/**
	 * This class draws the game's Background image and sets its dimension
	 * */

	final Image image = new ImageIcon("src/images/chat.png").getImage();
	// define chat component

	JTextField sendSpace = new JTextField(55);
	JTextArea recieveSpace = new JTextArea(4, 65);
	JScrollPane sp_recieveSpace = new JScrollPane(recieveSpace);
	public JButton send = new JButton("send");
	public JButton replay = new JButton("replay");
	int lineSpace = 30;
	String printString;
	String name = DrawingOvals.name;
	public static String name2;

	// define socket and buffer for connection
	Socket socket;
	BufferedReader in = null;
	BufferedWriter out = null;
	boolean splitOnce;

	// thread method to allow receiving messages from chat
	public void run() {
		while (true) {
			try {
				InetAddress ia = socket.getInetAddress();
				if (splitOnce == true) {
					String str = in.readLine();
					String[] splitted = str.split(":");
					name2 = splitted[0];
					splitOnce = false;
				} else {
					printString = in.readLine() + "\n";
					printText();
				}
			} catch (IOException e) {
			}
		}
	}

	public ChatPanel() {
		repaint();

		// add chat components
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 12));
		sp_recieveSpace
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		recieveSpace.setEditable(false);
		recieveSpace.setLineWrap(true);
		this.add(sp_recieveSpace);
		this.add(sendSpace);
		this.add(send);
		this.add(replay);

		// this.getRootPane().setDefaultButton(send);
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
			Thread t = new Thread(this);
			t.start();

		} catch (IOException ioe) {

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
				}
			}
		});
		replay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DrawingOvals.replayGame();
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