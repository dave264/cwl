package com.cwl.socket;

import java.io.DataInputStream;
import java.io.IOException;

public class ClientReader extends Thread {
	private DataInputStream  dis = null;
	public ClientReader(DataInputStream dis) {
		this.dis = dis;
	}
	
	public void run(){
		String str = "";
		
		while(true) {
			try {
				str = dis.readUTF();
			} catch (IOException e) {
				
			}
			System.out.println("������˵:"+str);
			if(str.equals("bye")) {
				System.out.println("�Է����ߣ�"+str);
				System.exit(0);
			}
		}
	}
}
