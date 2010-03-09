package network;

import java.io.IOException;
import java.net.*;
import java.util.*;

import javax.swing.JOptionPane;

@SuppressWarnings("unused")
/**
 * Scans the local machine for its IP addresses and returns them (ignoring
 * the loopback IP) then passes the IP(s) to the PortScannner.java class to
 * start the scanning process for the specified port. An example of how to
 * scan from command line: java ScanOwnNetwork 22 24 where "22" is port
 * number and "24" is subnet mask
 * @see PortScanner
 * */
public class ScanOwnNetwork {
	public static PortScanner portScanner;
	private String[] scannedIPs;
	public static int port = 8453;
	public static int subnet = 24;

	public static void main(String[] args) {
		if (args.length > 0) {
			// Reading Port from user as the first argument
			try {
				port = Integer.parseInt(args[0]);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Port must be an integer and between 1-65535");
				System.exit(1);
			}

			if (args.length > 1) {
				// Reading Subnet Mask from user as the second argument
				try {
					subnet = Integer.parseInt(args[1]);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Subnet must be an integer and between 8-32");
					System.exit(1);
				}
			}
		}
		try {
			// Calling GetOwnIP Constructor to start searching for available IPs
			new ScanOwnNetwork();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	public ScanOwnNetwork() throws Exception {
		int[] localIP = new int[4];
		String[] splittedStringIP = new String[4];
		String stringIP = null;
		// This loop gets the network interfaces that have assigned IP addresses
		for (Enumeration<NetworkInterface> ifaces = NetworkInterface
				.getNetworkInterfaces(); ifaces.hasMoreElements();) {
			NetworkInterface iface = ifaces.nextElement();
			// This loop gets the Addresses for the interface (IP and MAC
			// Addresses)
			if (ifaces.hasMoreElements() == false)
				break;
			for (Enumeration<InetAddress> addresses = iface.getInetAddresses(); addresses
					.hasMoreElements();) {
				if (iface.isLoopback() == false) {
					InetAddress address = addresses.nextElement();
					stringIP = address.toString();
				}
				if (addresses.hasMoreElements() == false)
					break;
			}
			// omitting the unnecessary characters "/"
			stringIP = stringIP.substring(1);
			// Splitting the IP Address with the character "."
			splittedStringIP = stringIP.split("[.]");
			localIP = toIntegerIP(splittedStringIP);
			portScanner = new PortScanner(localIP);
			scannedIPs = portScanner.resultIPsList.toArray(new String[0]);
		}
	}

	public String[] getScannedIPs() {
		return scannedIPs;
	}

	/**
	 * Converting any IPv4 string address to an integer one by converting
	 * each string octet separately into integer ones
	 * */
	public int[] toIntegerIP(String[] stringIP) {
		int[] integerIP = new int[4];
		for (int i = 0; i < stringIP.length; i++) {
			int convertedOctet = Integer.parseInt(stringIP[i]);
			integerIP[i] = convertedOctet;
		}
		return integerIP;
	}
}
