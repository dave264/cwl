package com.cwl.Mail;

public class Message {//��ʾ�ʼ�

	public String from ;//�����ߵ��ʼ���ַ
	public String to;//�����ߵ��ʼ���ַ
	public String subject;//����
	public String content;//�ʼ�����
	public String data;//�ʼ�����
	
	public Message(String from,String to,String subject,String content) {
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.content = content;
		data = "Subject:"+subject+"\r\n"+content;
	}

}
