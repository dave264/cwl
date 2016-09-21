package com.cwl.net;

import java.net.MalformedURLException;
import java.net.URL;

public class Url {

	public static void main(String[] args) {
		
		try {
			URL url = new URL("http://www.163.com");
			System.out.println(url.getProtocol());
			System.out.println(url.getHost());
			System.out.println(url.getPort());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
