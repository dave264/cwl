package com.cwl.parse;

import java.io.File;
import java.util.Scanner;

import com.cwl.parse.reader.Reader;

/**
 * �ļ�����
 * @author Mr.chen
 *
 */
public class Parse {

	
	private static Scanner scan = null;

	public static void main(String[] args) {

				
		scan = new Scanner(System.in);
		String fileName = "";
		System.out.println("��������ҵ�����ļ���:");
		fileName = scan.next();
		String path = new File("").getAbsolutePath();
		fileName = path+File.separator+fileName+".txt";
		Reader reader = new Reader(fileName,0);
		try {
			reader.reader();
		} catch (Exception e) {
			System.out.println("�Բ���û�ҵ��ļ�!\n"+e);
		}
				
	}
}


