package com.cwl.Mail;

public class Message {//表示邮件

	public String from ;//发送者的邮件地址
	public String to;//接收者的邮件地址
	public String subject;//标题
	public String content;//邮件正文
	public String data;//邮件内容
	
	public Message(String from,String to,String subject,String content) {
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.content = content;
		data = "Subject:"+subject+"\r\n"+content;
	}

}
