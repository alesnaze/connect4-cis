
package chat;

import graphics.Client;
import graphics.Drawing;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import mp3.MP3;
import network.ConnectForm;

@SuppressWarnings( { "serial" })
/**
 * Defines Chat components in the Client side and draws them to the main
 * frame. then it connects to the server and starts I/O streaming, then
 * begin sending and receiving text and update the chat receive space each
 * time.
 * */
public class ChatPanel extends Chat {
	InetAddress serverIP;
	/**
	 * A constructor which draws the actual chat components, try to connect to
	 * the server, then run the thread to handle the chatting
	 * @see #run()
	 * */
	public ChatPanel() {
		PLAYER = 1;
		serverIP = ConnectForm.ia;
		initComponents();
		sendlbl.setForeground(new Color(85, 0, 0));

		// connection to the server socket
		try {
			socket = new Socket(serverIP, 8452);
			clientName = Client.clientName;
			in = new BufferedReader(new InputStreamReader(socket
					.getInputStream(), "UTF8"));
			out = new BufferedWriter(new OutputStreamWriter(socket
					.getOutputStream(), "UTF8"));
			splitOnce = true;
			isSplitted = false;
			sentName = false;
			
			t = new Thread(this);
			t.start();

		} catch (IOException ioe) {
			File filename = new File("src/sounds/alert.mp3");// playing mp3 file
            MP3 mp3 = new MP3(filename);
            mp3.play();
			Drawing.cleanUp();
			System.exit(1);
		}

		buttonsLestiners();
	}
}