package network;

import java.net.*;
import java.util.ArrayList;
import java.io.IOException;

public class PortScanner {
	/**
	 * This class is responsible for Scanning a Specific port on an IP or range
	 * of IPs The default IP is the local IP resulted from the GetOwnIP.java
	 * class.
	 * */
	public ArrayList<String> IPsList = new ArrayList<String>();
	boolean endIP = true ;

	public PortScanner(int[] IP) throws UnknownHostException, IOException {
		/**
		 * Given the IP address of the machine and the SubnetMask, this
		 * constructor concludes the whole IPs within the network so its
		 * possible to scan the whole network using this constructor
		 * */
		int subnetMask = 24;
		int count = 0;
		byte[] ipAddress = new byte[4];
		int[] networkAddress = new int[4];
		int[] broadcastAddress = new int[4];
		int[] scannedIP = new int[4];
		if (subnetMask >= 1 && subnetMask <= 30) {
			for (int i = 0; i < IP.length; i++) {
				// converting the IPs to the Binary form so we can conclude the
				// network IP and the broadcast IP
				String binaryIP = Integer.toBinaryString(IP[i]);
				String binaryBroadcastIP = binaryIP;
				if (binaryIP.length() <= 8) {
					int diff = 8 - binaryIP.length();
					for (int j = 1; j <= diff; j++) {
						// This loop is for adding the 0 bits to the binary
						// number
						// so it'll be 8-bit number
						binaryIP = "0" + binaryIP;
						binaryBroadcastIP = binaryIP;
					}
					for (int k = 0; k < binaryIP.length(); k++) {
						// This loop is for getting the binary values of the
						// networkIP and the broadcastIP
						char ch = binaryIP.charAt(k);
						String temp = "" + ch;
						if (temp.isEmpty() == false) {
							count++;
							if (count > subnetMask) {
								// This limits the search to the 32 bits only
								// The following code computes the network and
								// broadcast IPs
								binaryIP = binaryIP.substring(0, k)
										+ "0"
										+ binaryIP.substring(k, binaryIP
												.length() - 1);
								binaryBroadcastIP = binaryBroadcastIP
										.substring(0, k)
										+ "1"
										+ binaryBroadcastIP.substring(k,
												binaryBroadcastIP.length() - 1);
							}
						}
					}
				}
				// Converting code from Binary to Decimal
				broadcastAddress[i] = Integer.parseInt(binaryBroadcastIP, 2);
				networkAddress[i] = Integer.parseInt(binaryIP, 2);
			}
			while (endIP) {
				// This loop is for scanning all the network IPs
				boolean check = true;
				int counter = 0;
				for (int i = 0; i < 4; i++) {
					// This loop is for checking whether we've reached the last
					// IP or not (which is the broadcast IP)
					if (networkAddress[i] == broadcastAddress[i]) {
						check = true;
						counter += 1;
						if (counter == 4) {
							// if the current IP is the broadcast IP, then Exit
							endIP = false;
							break;
//							System.exit(0);
						}
					} else {
						// if current IP is not the broadcast IP
						check = false;
					}
					if (check == false) {
						scannedIP = IPsWithinRange(networkAddress, 3);
						for (int k = 0; k < 4; k++) {
							// Converting the Integer IP to a Byte one so we can
							// scan it
							ipAddress[k] = (byte) scannedIP[k];
						}
						// Checking whether the IP is reachable, if so it'll
						// perform the scan operation
						boolean status = InetAddress.getByAddress(ipAddress)
								.isReachable(10);
						if (status == true) {
							try {
								InetAddress ia = InetAddress
										.getByAddress(ipAddress);
								String x = scan(ia);
								if (x != "None") {
									System.out.println(x);
									IPsList.add(x);
								}
							} catch (UnknownHostException ex) {
								System.err.println(ipAddress
										+ ": IP not valid.");
							}
						}
					}
				}
			}
		}
	}
	
	public static String scan(InetAddress remote) {
		/**
		 * This method is used for Scanning the given IPs for a specified port
		 * and then tell whether the port is opened or not
		 * **/
		String hostname = remote.getHostAddress();
		int port = 22;
		try {
			Socket s = new Socket(remote, port);
			s.close();
			return hostname;
		} catch (IOException ex) {
			return "None";
		}
	}

	public static void scan(String remote) throws UnknownHostException {
		/**
		 * used for Scanning by name
		 * */
		InetAddress ia = InetAddress.getByName(remote);
		scan(ia);
	}

	public static int[] IPsWithinRange(int[] networkAddress, int i) {
		/**
		 * This method is used to manage the octets of an IP address when adding
		 * "1" to the current IP
		 * */
		if (networkAddress[i] == 255) {
			networkAddress[i] = 1;
			networkAddress = IPsWithinRange(networkAddress, i - 1);
		} else {
			networkAddress[i] = networkAddress[i] + 1;
		}
		return networkAddress;
	}
}