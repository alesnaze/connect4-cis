package chat;

import java.net.*;
import java.io.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class server extends JFrame{
	
	JButton send;
	JTextArea recieveSpace;
	JTextArea sendSpace;
	
	ServerSocket server;
	Socket socket;
	DataInputStream in;
	DataOutputStream out;
	
	
	public server() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
		
		recieveSpace = new JTextArea(9,20);
		add(recieveSpace);
		
		sendSpace = new JTextArea(4,20);
		add(sendSpace);
		
		send = new JButton("send");
		add(send);
		
		try {
			
			server = new ServerSocket(8000);
			socket = server.accept();
			in = new DataInputStream(socket.getInputStream());
			out =new DataOutputStream(socket.getOutputStream());
			
		}catch(IOException e){
			System.err.println(e);
		}
		
		try {
			
			recieveSpace.append("Client : " + in.readUTF() + "\n");
			
		}catch(IOException e){
			System.err.println(e);
		}

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
	}
	public static void main(String[] args){
		server s = new server();
		s.setVisible(true);
		s.setTitle("oooo");
		s.setSize(350, 300);
	}
}
