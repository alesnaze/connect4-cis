package network;

import java.net.*;
import java.io.IOException;

public class PortScanner {
	public static void main(String[] args) {
		byte[] ip = {10, 0, 0, 1};
		try {
			InetAddress ia = InetAddress.getByAddress(ip);
			scan(ia);
		}
		catch (UnknownHostException ex) {
			System.err.println( ip + " is not a valid host name.");
		}
	}

	public static void scan(InetAddress remote) {
		String hostname = remote.getHostAddress();
		for (int port = 0; port < 1024; port++) {
			try {
				Socket s = new Socket(remote, port); 
				System.out.println("A server is listening on port " + port + " of " + hostname);
				s.close();
			}
			catch (IOException ex) {
			}
		}
	}

	public static void scan(String remote) throws UnknownHostException {
		InetAddress ia = InetAddress.getByName(remote);
		scan(ia);
	}
}