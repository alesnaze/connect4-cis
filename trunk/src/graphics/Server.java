package graphics;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

import chat.Chat;
import chat.ChatPanel;
import chat.ChatPanelServer;
import mp3.MP3;

@SuppressWarnings( { "serial", "unused", "deprecation", "static-access" })
/**
 * This class draws Ovals (Circles) on the main frame and checks if any
 * of these Ovals are clicked, then it performs action according to
 * the place of the circle
 * */
public class Server extends Drawing {
	static ServerSocket serverSocket = null;
	static ServerSocket scanSocket = null;

	public static String serverName = "";

	static boolean socketAccepted = false;
	public static boolean nameFieldStatus = false;

	/**
	 * Constructor to draw the main frame and all the components needed, then it
	 * connects to server sockets, uses threads after establishing the
	 * connection.
	 * 
	 * @see #run()
	 * */
	public Server() {
		PLAYERNumber = 2;
		PLAYER = 2;
		color = red;
		secondColor = green;
		this.setTitle("Connect 4 Server");
		// labels for information about the players
		serverLabel = new JLabel("Player 1");
		clientLabel = new JLabel("Player 2");
		serverPlayer = new JLabel(serverName);
		clientPlayer = new JLabel();

		initComponents();
		// adding chat panel to the the main panel
		ChatPanelServer cps = new ChatPanelServer();
		this.add(cps, BorderLayout.SOUTH);
		// make send button the default button when pressing enter
		cps.getRootPane().setDefaultButton(cps.send);
		// opening the socket and accepting connection from client
		try {
			// Creating a socket and waiting for connection
			serverSocket = new ServerSocket(8451);
			scanSocket = new ServerSocket(8453);

			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						scanSocket.accept();
					} catch (IOException e) {
					}
				}
			});
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
		Lestiners();

		nameFieldButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (serverName.length() == 0) {
					serverName = nameField.getText();
					serverPlayer.setText(serverName);
					nameField.setVisible(false);
					nameFieldButton.setVisible(false);
					Chat.serverName = serverName;
					nameFieldStatus = true;
				}
				if (socketAccepted == true) {
					Chat.writeName(serverName);
					nameFieldStatus = false;
				}
			}
		});
		
		controlComponents(false);
	}

	/**
	 * Method for waiting for a client to connect so that the game can start.
	 * */
	public static void initiateServer() {
		if (socketAccepted == false) {
			try {
				socket = serverSocket.accept();
				scanSocket.close();
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
				socketAccepted = true;
				ChatPanelServer.sendSpace.requestFocus();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Contains the code that the general {@link #cleanUp()} won't use everytime it's called
	 * */
	public static void serverCleanUp() {
		try {
			if (serverSocket != null) {
				serverSocket.close();
				serverSocket = null;
			}
		} catch (IOException e) {
			serverSocket = null;
		}
	}
}