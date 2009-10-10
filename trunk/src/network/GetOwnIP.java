package network;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class GetOwnIP {
	/**
	 * This class scans the local machine for its IP addresses and returns them
	 * then passes the IP(s) to the PortScannner.java class to scan the IP for
	 * the specified port
	 * */
	public static PortScanner x;
	private String[] scannedIPs;
	
	public GetOwnIP() throws Exception {
		int[] localIP = new int[4];
		String[] temp = new String[4];
		String myStr = null;
		// This loop gets the network interfaces that have assigned IP addresses
		for (Enumeration<NetworkInterface> ifaces = NetworkInterface
				.getNetworkInterfaces(); ifaces.hasMoreElements();) {
			NetworkInterface iface = ifaces.nextElement();
			// This loop gets the Addresses for the interface (IP and MAC
			// Addresses)
			if (ifaces.hasMoreElements() == false) break;
			for (Enumeration<InetAddress> addresses = iface.getInetAddresses(); addresses.hasMoreElements()/*hasOneElement*/;) {
				/*
				 * ERROR: hasMoreElements() is always true if there's one or
				 * more element in iface
				 */

				// As we won't be working on the localhost only, we'll ignore
				// the Loop Back address
				if (iface.isLoopback() == false) {
					InetAddress address = addresses.nextElement();
					myStr = address.toString();
				}
				if (addresses.hasMoreElements() == false) break;
			}
			// omitting the unnecessary character "/"
			myStr = myStr.substring(1);
			// Splitting the IP Address with the character "."
			temp = myStr.split("[.]");
			for (int i = 0; i < temp.length; i++) {
				int Obj2 = Integer.parseInt(temp[i]);
				localIP[i] = Obj2;
			}
			x = new PortScanner(localIP);
			scannedIPs = x.IPsList.toArray(new String[0]);
		}
	}

	public String[] getScannedIPs() {
		return scannedIPs;
	}
}