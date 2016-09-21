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
	 * �����ļ���С������ָ���ռ䣬���ֽڶ�ȡ
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
	 * �����ļ���С������ָ���ռ�,��ȡ�ֽ�����
	 * @throws Exception
	 */
	public static void doSecondMethod() throws Exception
	{
		File f = new File(fileName);
		InputStream is = new java.io.FileInputStream(f);
		byte[] b = new byte[(int)f.length()];
		is.read(b);
		is.close();
		System.out.println("�ļ�����Ϊ:"+f.length());
		System.out.println("�ļ�����:"+new String(b));
	}
	
	/**
	 * ������ָ����С�ռ䣬��ȡ�ֽ����飬����֮���ٽ�ȡ
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
