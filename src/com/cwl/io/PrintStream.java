package com.cwl.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PrintStream {
	public static void main(String[] args) throws FileNotFoundException {
		java.io.PrintStream ps = new java.io.PrintStream(new FileOutputStream(new File("d:"+File.separator+"hello.txt")));
		ps.println("ChenWenlong");
		ps.close();
	}
}
