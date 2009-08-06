package chat;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.net.*;

import java.io.*;

public class Server  extends JFrame implements Runnable {
	
	JTextField sendSpace;
	JTextArea recieveSpace;
	JScrollPane sp_recieveSpace;
	JScrollPane sp_sendSpace;
	JButton send;
	
	ServerSocket serverSocket = null;
	Socket socket;
	BufferedReader in = null;
	BufferedWriter out = null;

	
	public void run() {
		
		while(true){
			try{
				InetAddress ia = socket.getInetAddress();
				recieveSpace.append(ia.getHostName()  + " : " + in.readLine() + "\n");
			}catch(IOException e){

			}
		}
	}

	public Server() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
		
		recieveSpace = new JTextArea(9,28);
		sp_recieveSpace = new JScrollPane(recieveSpace);
        add(sp_recieveSpace);
        recieveSpace.setEditable(false);
		
        sendSpace = new JTextField(20);
		sp_sendSpace = new JScrollPane(sendSpace);
        add(sp_sendSpace);
		
		send = new JButton("send");
		add(send);
		send.setToolTipText("");
		
		this.getRootPane().setDefaultButton(send);

        try {
			serverSocket = new ServerSocket(8000);
			socket = serverSocket.accept();
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF8"));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF8"));
			
			Thread t = new Thread(this);
			t.start();
			
		}catch(IOException ioe) {

		}
		

		send.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
			
			recieveSpace.append(" you : " + sendSpace.getText() + "\n");
			
			try {
						
				out.write(sendSpace.getText());
				out.newLine();
				out.flush();
				sendSpace.setText("");

			}catch(IOException ie){

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
		Server s = new Server();
		s.setVisible(true);
		s.setTitle("server");
		s.setResizable(false);
		s.setSize(350, 300);
		
	}
}