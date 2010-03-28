
package chat;

import graphics.Client;
import graphics.Drawing;
import graphics.Play;
import graphics.Server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;

import mp3.MP3;

@SuppressWarnings( { "unused", "serial" })
/**
 * Defines Chat components in the Client side and draws them to the main
 * frame. then it connects to the server and starts I/O streaming, then
 * begin sending and receiving text and update the chat receive space each
 * time.
 * */
public class ChatPanelServer extends Chat{

	// define socket and buffer for connection
	static ServerSocket serverSocket = null;
	static boolean socketAccepted = false;

	/** thread method to allow receiving messages from chat */
	public static void initiateServer() {
		if (socketAccepted == false) {
			try {
				socket = serverSocket.accept();
				serverName = Server.serverName;
				in = new BufferedReader(new InputStreamReader(socket
						.getInputStream(), "UTF8"));
				out = new BufferedWriter(new OutputStreamWriter(socket
						.getOutputStream(), "UTF8"));
				splitOnce = true;
				isSplitted = false;
				sentName = false;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			socketAccepted = true;
		}
	}

	/**
	 * A constructor which draws the actual chat components, Creates the server
	 * socket then runs the thread to handle the chatting
	 * @see #run()
	 * */
	public ChatPanelServer() {
		PLAYER = 2;
		initComponents();
		sendlbl.setForeground(new Color(0, 85, 0));
		
		// opening the socket and accepting connection from client
		try {
			serverSocket = new ServerSocket(8452);
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