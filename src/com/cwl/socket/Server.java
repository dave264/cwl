package com.cwl.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		
		ServerSocket server = null;
		try {
			server = new ServerSocket(6666);
			Socket socket = server.accept();//�����ȴ��ͻ�������
			System.out.println("�ͻ������ӳɹ���");
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			new ServerReader(dis).start();
			new ServerWriter(dos).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
