package com.cwl.Mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MailSender {

	private String smtpServer = "smtp.mydomain.com";
	private int port = 25;

	public static void main(String[] args) {
		
		Message msg = new Message("1697184654@qq.com","2645093061@qq.com","test","Hi,I miss you very much!");
		new MailSender().sendMail(msg);
	}
	
	public void sendMail(Message msg) {
		Socket socket = null;
		try {
			socket = new Socket(smtpServer,port);//连接到邮件服务器
			BufferedReader br = getReader(socket);
			PrintWriter pw = getWriter(socket);
			String localhost = InetAddress.getLocalHost().getHostName();//客户主机的名字
			
			sendAndReceive(null,br,pw);//仅仅是为了接收服务器的响应数据
			sendAndReceive("Hello"+localhost,br,pw);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendAndReceive(Object object, BufferedReader br, PrintWriter pw) {
		// TODO Auto-generated method stub
		
	}

	private PrintWriter getWriter(Socket socket) {
		// TODO Auto-generated method stub
		return null;
	}

	private BufferedReader getReader(Socket socket) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
