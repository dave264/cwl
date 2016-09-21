package com.cwl.io;

import java.io.File;
import java.io.FileOutputStream;

public class SystemOut {

	public static void main(String[] args)throws Exception {
		
		File f = new File("d:"+File.separator+"hello.txt");
	
		System.setOut(new java.io.PrintStream(new FileOutputStream(f)));
		System.out.println("这些内容会在文件中出现");
	}

}
