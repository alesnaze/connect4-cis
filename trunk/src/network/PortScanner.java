package network;

import java.net.*;
import java.io.IOException;

public class PortScanner {
	/**
	 * This class is responsible for Scanning a Specific port on an IP or range
	 * of IPs The default IP is the local IP resulted from the GetOwnIP.java
	 * class.
	 * */
	public PortScanner(int[] IP) throws UnknownHostException, IOException {
		byte[] ipAddress = new byte[4];
		for (int i = 0; i < IP.length; i++) {
			ipAddress[i] = (byte) IP[i];
		}
		// Checking whether the IP is reachable, if so it'll perform the scan
		// operation
		boolean status = InetAddress.getByAddress(ipAddress).isReachable(7);
		if (status == true) {
			try {
				InetAddress ia = InetAddress.getByAddress(ipAddress);
				scan(ia);
			} catch (UnknownHostException ex) {
				System.err.println(ipAddress + " is not a valid host name.");
			}
		}
	}

	public static void scan(InetAddress remote) {
		/**
		 * This method is used for Scanning the given IPs for a specified port
		 * and then tell whether the port is opened or not
		 * **/
		String hostname = remote.getHostAddress();
		int port = 22;
		try {
			Socket s = new Socket(remote, port);
			System.out.println("A server is listening on port " + port + " of "
					+ hostname);
			s.close();
		} catch (IOException ex) {
			System.out.println("A server is not listening on port " + port
					+ " of " + hostname);
		}
	}

	public static void scan(String remote) throws UnknownHostException {
		/** used for Scanning by name*/
		InetAddress ia = InetAddress.getByName(remote);
		scan(ia);
	}
}