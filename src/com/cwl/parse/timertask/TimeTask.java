package com.cwl.parse.timertask;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import com.cwl.parse.export.Export;
import com.cwl.parse.reader.Reader;

public class TimeTask extends TimerTask {

	@Override
	public void run() {
		
		long time = 0;
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 00:00:00");
		} catch (ParseException e3) {
			e3.printStackTrace();
		}
		time = date.getTime();
		long current = 0;
		try {
			current = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).getTime();
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		System.out.println("��������ʱ��:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time)));
		System.out.println("��ǰʱ��:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current)));
		if(((current-time)/(86400*1000))>0&&((current-time)/(86400*1000))<1) {
	
			String fileName = "";
			
			String beginTime = "";
			beginTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 00:00:00";
			
			long begintime = 0;
			try {
				begintime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beginTime).getTime();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			fileName = new SimpleDateFormat("yyyyMMdd").format(new Date(begintime));
			fileName = fileName + ".txt";
			if(!new File(fileName).exists()) {
				System.out.println("���������С�������");
				new Export().export(time);
			}
			
			System.out.println("���ݼ�����ɡ�����");
			Reader reader = new Reader(fileName+".txt",0);
			try {
				reader.reader();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("���ݽ������.....");
			try {
				time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 00:00:00").getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

	}

}
