/**
 * 
 */
package com.cwl.socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Mr.chen
 *
 */
public class ServerWriter extends Thread {
	public DataOutputStream dos = null;
	public ServerWriter(DataOutputStream dos) {
		this.dos = dos;
	}
	public void run(){
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String str ="";
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
			System.out.println("服务器说："+str+"\n");
			if(str.equals("bye")) {
				System.out.println("自己下线");
				System.exit(0);
			}
		}
	}
}
