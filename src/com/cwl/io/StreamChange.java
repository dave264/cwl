package com.cwl.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * �ֽ������ַ���ת��ʵ��
 * @author Mr.chen
 *
 */
public class StreamChange {

	private static String fileName = "d:"+File.separator+"hello.txt";
	/**
	 * ���ֽ������ת��Ϊ�ַ������
	 */
	public static void byteChangetoString(){
		File file = new File(fileName);
		try {
			Writer out = new OutputStreamWriter(new FileOutputStream(file));
			out.write("hello");
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
