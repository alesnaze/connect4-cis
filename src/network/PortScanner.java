package network;

import java.net.*;
import java.util.ArrayList;
import java.io.IOException;
import network.ScanOwnNetwork;

/**
 * Scanning a Specific port on an IP or range of IPs, The default IP is the
 * local IP resulted from the ScanOwnNetwork.java class. Given the IP
 * address ofthe machine and the SubnetMask, this class concludes the whole
 * IPs within the network so its possible to scan the whole network using
 * this application.
 * @see ScanOwnNetwork
 * */
class PortScanner {
	public static ArrayList<String> resultIPsList = new ArrayList<String>();
	boolean endIP = true;
	private static int port;

	public PortScanner(int[] IP) throws UnknownHostException, IOException {
		int subnetMask = 24;
		if (ScanOwnNetwork.subnet != 0) {
			subnetMask = ScanOwnNetwork.subnet;
		} else {
			System.out.println("Default subnet " + subnetMask + " is used");
		}
		port = ScanOwnNetwork.port;
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
						// number so it'll be 8-bit number
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
								InetAddress ia = InetAddress.getByAddress(ipAddress);
								scan(ia);
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

	/**
	 * This method is used for Scanning the given IPs for a specified port
	 * and then tells whether the port is opened or not.
	 * **/
	public static void scan(InetAddress remote) {
		String hostname = remote.getHostAddress();
		if (port == 0) {
			System.out.println("Scanning the known IPs in the whole network..");
			for (int i = 1 ; i < 1024 ; i ++)
				try {
				Socket s = new Socket(remote, i);
				s.close();
				System.out.println("Port: " + i	+ " is open on: " + hostname);
			} catch (IOException ex) {
			ex.printStackTrace();
			}
		} else {
			try {
				//port is open
				Socket s = new Socket(remote, port);
				s.close();
				resultIPsList.add(hostname);
			} catch (IOException ex) {
			}
		}
	}

	/**
	 * used for Scanning by name
	 * */
	public static void scan(String remote) throws UnknownHostException {
		InetAddress ia = InetAddress.getByName(remote);
		scan(ia);
	}

	/**
	 * This method is used to manage the octets of an IP address when adding
	 * "1" to the current IP
	 * */
	public static int[] IPsWithinRange(int[] networkAddress, int i) {
		if (networkAddress[i] == 255) {
			networkAddress[i] = 1;
			networkAddress = IPsWithinRange(networkAddress, i - 1);
		} else {
			networkAddress[i] = networkAddress[i] + 1;
		}
		return networkAddress;
	}
}
