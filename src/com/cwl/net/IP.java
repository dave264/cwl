package com.cwl.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IP {

	public static void main(String[] args) {
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		System.out.println(inetAddress.getHostAddress());
		System.out.println(inetAddress.getHostName());
		
	}

}
