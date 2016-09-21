package com.cwl.parse;

import java.io.File;
import java.util.Scanner;

import com.cwl.parse.reader.Reader;

/**
 * 文件解析
 * @author Mr.chen
 *
 */
public class Parse {

	
	private static Scanner scan = null;

	public static void main(String[] args) {

				
		scan = new Scanner(System.in);
		String fileName = "";
		System.out.println("请输入企业解析文件名:");
		fileName = scan.next();
		String path = new File("").getAbsolutePath();
		fileName = path+File.separator+fileName+".txt";
		Reader reader = new Reader(fileName,0);
		try {
			reader.reader();
		} catch (Exception e) {
			System.out.println("对不起，没找到文件!\n"+e);
		}
				
	}
}


