package graphics;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

import network.ConnectForm;

import chat.Chat;
import chat.ChatPanel;
import chat.ChatPanelServer;
import mp3.MP3;

@SuppressWarnings({ "unused", "serial", "deprecation", "static-access" })
/**
 * This class draws Ovals (Circles) on the main frame and checks if any
 * of these Ovals are clicked, then it performs action according to
 * the place of the circle
 * */
public class Client extends Drawing {
	public static InetAddress ia;

	public static String clientName="";
	InetAddress serverIP;

	/**
	 * Constructor to draw the main frame and all the components needed, then it
	 * creates server sockets and threads and wait for the client to connect
	 * 
	 * @see #run()
	 * */
	public Client() {
		PLAYERNumber = 1;
		color = green;
		secondColor = red;
		serverIP = ConnectForm.ia;
		this.setTitle("Connect 4");
		// labels for information about the players
		serverLabel = new JLabel("Player 1");
		clientLabel = new JLabel("Player 2");
		clientPlayer = new JLabel(clientName);
		serverPlayer = new JLabel();
		
		initComponents();
		
		// adding chat panel to the the main panel
		ChatPanel cp = new ChatPanel();
		this.add(cp, BorderLayout.SOUTH);
		// make send button the default button when pressing enter
		cp.getRootPane().setDefaultButton(cp.send);
		ChatPanel.sendSpace.requestFocus();
		try {
			// connect to the server socket
			socket = new Socket(serverIP, 8451);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());

			Thread t = new Thread(this);
			t.start();

		} catch (IOException ioe) {
			ChatPanel.t.stop();
			File filename = new File("src/sounds/alert.mp3");// playing mp3 file 
            MP3 mp3 = new MP3(filename);
            mp3.play();
			cleanUp();
			System.exit(1);
		}

		Lestiners();
		
		nameFieldButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (clientName.length() == 0) {
					clientName = nameField.getText();
					clientPlayer.setText(clientName);
					nameField.setVisible(false);
					nameFieldButton.setVisible(false);
					Chat.clientName = clientName;
					Chat.writeName(clientName);
				}
			}
		});
		
		controlComponents(false);
	}
}