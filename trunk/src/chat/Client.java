package chat;

import java.net.*;
import java.io.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Client extends JFrame{
	
	Socket socket;
	DataInputStream in;
	DataOutputStream out;
	
	JTextArea sendSpace;
	JTextArea recieveSpace;
	JScrollPane sp_recieveSpace;
	JScrollPane sp_sendSpace;
	JButton send;
	JButton connect;
	
	public Client() {
		
		setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
		
		recieveSpace = new JTextArea(9,20);
		add(recieveSpace);
		
		connect = new JButton("connect");
		add(connect);
		
		sendSpace = new JTextArea(4,20);
		add(sendSpace);
		
		send = new JButton("send");
		add(send);

		connect.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
			try {
			
				socket = new Socket("localhost", 8000);
				in = new DataInputStream(socket.getInputStream());
				out =new DataOutputStream(socket.getOutputStream());
			
			}catch(IOException ex){
				System.err.println(e);
			}
		}
		});
		
		send.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
			
			recieveSpace.append("Client : " + sendSpace.getText() + "\n");
			
			try {
				
				out.writeUTF(sendSpace.getText());
				sendSpace.setText("");
			
			}catch(IOException ex){
				System.err.println(ex);
			}
			
			
		}
		});
		
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
	}
	public static void main(String[] args){
		Client c = new Client();
		c.setVisible(true);
		c.setSize(350,300);
		c.setTitle("gggg");
	}
}
