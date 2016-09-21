package com.cwl.io;

import java.io.DataInputStream;
import java.io.File;
import java.io.InputStream;

public class FileInputStream {

	private static String fileName = "D:"+File.separator+"hello.txt";
	
	
	public static void main(String[] args) throws Exception {
		doThirdMethod();
	}
	
	public static void doFourthMethod() throws Exception {
		File f = new File(fileName);
		@SuppressWarnings("resource")
		DataInputStream dis =new DataInputStream(new java.io.FileInputStream(f));
		byte[] b = new byte[(int)f.length()];
		int temp =0;
		int count  =0;
		while((temp = dis.readChar())!=-1){
			b[count++] = (byte)temp;
		}
	}
	
	/**
	 * 计算文件大小，申请指定空间，逐字节读取
	 */
	public static void doThirdMethod() throws Exception
	{
		File f = new File(fileName);
		InputStream is = new java.io.FileInputStream(f);
		byte[] b = new byte[(int)f.length()];
		for(int i=0; i<b.length; i++) {
			b[i] = (byte)is.read();
		}
		is.close();
		System.out.println(new String(b));
	}
	
	/**
	 * 计算文件大小，申请指定空间,读取字节数组
	 * @throws Exception
	 */
	public static void doSecondMethod() throws Exception
	{
		File f = new File(fileName);
		InputStream is = new java.io.FileInputStream(f);
		byte[] b = new byte[(int)f.length()];
		is.read(b);
		is.close();
		System.out.println("文件长度为:"+f.length());
		System.out.println("文件内容:"+new String(b));
	}
	
	/**
	 * 先申请指定大小空间，读取字节数组，多了之后再截取
	 * @throws Exception
	 */
	public static void doFirstMethod() throws Exception
	{
		File f = new File(fileName);
		InputStream  is = new java.io.FileInputStream(f);
		byte[] b = new byte[1024];
		int len = is.read(b);
		is.close();
		String s = new String(b,0,len);
		System.out.println(s);
	}

}
