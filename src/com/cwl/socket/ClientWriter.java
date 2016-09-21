package com.cwl.socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientWriter extends Thread {
	private DataOutputStream dos = null;
	
	public ClientWriter(DataOutputStream dos) {
		this.dos = dos;
	}
	public void run(){
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String str = "";
		while(true) {
			try {
				str = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				dos.writeUTF(str);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("�ͻ���˵:"+str+"\n");
			if(str.equals("bye")) {
				System.out.println("�Լ�����!");
				System.exit(0);
			}
		}
	}
}
