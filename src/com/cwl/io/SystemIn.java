package com.cwl.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SystemIn {

	public static void main(String[] args) throws IOException {
		
		try {
			System.setIn(new java.io.FileInputStream(new File("d:"+File.separator+"hello.txt")));
			byte[] b = new byte[1024];
			int len = 0;
			len = System.in.read(b);
			System.out.println("读入的内容为:"+new String(b,0,len));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
