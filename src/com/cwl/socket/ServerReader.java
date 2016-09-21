package com.cwl.socket;

import java.io.DataInputStream;
import java.io.IOException;

public class ServerReader extends Thread {
	public DataInputStream dis;
	public ServerReader(DataInputStream dis) {
		this.dis = dis;
	}
	
	public void run() {
		String str = "";
		while(true) {
			try {
				str = dis.readUTF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("�ͻ���˵:"+str);
			if(str.equals("bye")){
				System.out.println("�Է����ߣ�"+str);
				System.exit(0);
			}
		}
	}
	
}
