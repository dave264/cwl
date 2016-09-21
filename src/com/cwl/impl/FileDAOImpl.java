package com.cwl.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

import com.cwl.dao.IFileDAO;

public class FileDAOImpl implements IFileDAO {

	private File file = null;
	private String f = "";
	
	public FileDAOImpl(String f){
		
		this.initFile(f);//初始化文件
		
	}
	
	
	
	@Override
	public List<String> BufferedReader() {
		
		FileReader reader = null;
		BufferedReader buf = null;
		List<String> list = null;
		try {
			reader = new FileReader(file);
			list = new ArrayList<String>();
			buf = new BufferedReader(reader);
			String s = "";
			
			while ((s=buf.readLine())!=null) {
				list.add(s);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("---FileReader异常---"+e);
		} catch (IOException e) {
			System.out.println("--BufferedReader异常--"+e);
		} finally {
			try {
				buf.close();
			} catch (IOException e) {
				System.out.println("---文件流关闭异常---"+e);
			}
		}
		return list;
		
	}

	
	
	
	@SuppressWarnings("resource")
	@Override
	public boolean FileOutputStream(String s, boolean flag) {
		
		FileOutputStream fos = null;
		
		try {
			
			fos = new FileOutputStream(file,flag);
			byte[] b = s.getBytes();
			fos.write(b);

		} catch (FileNotFoundException e) {
			System.out.println("--文件不存在--"+e);
			return false;
		} catch (IOException e) {
			System.out.println("--FileOutputStream写入异常--"+e);
			return false;
		} 
		return true;
		
	}



	@Override
	public boolean FileOutputStream(String[] s, boolean flag) {
		
		FileOutputStream fos = null;
		FileChannel fileChannel = null;
		FileLock lock = null;
		
		try {
			fos = new FileOutputStream(file,flag);
		} catch (FileNotFoundException e) {
			System.out.println("文件不存在:"+e);
			return false;
		}
		fileChannel = fos.getChannel();
		while(true){
			try{
				lock = fileChannel.tryLock();
				break;
			}catch(Exception e){
				System.out.println("其它线程正在操作该文件!");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e1) {
					System.out.println("线程中断："+e);
					return false;
				}
				return false;
			}
		}
		
		try {
			
			for(int i=0; i<s.length; i++) {	
				fos.write(s[i].getBytes());
			}
			
		} catch (IOException e) {
			
			System.out.println("文件写入异常:"+e);
			return false;
			
		} finally{
			
			try {
				lock.release();
				fos.close();
			} catch (IOException e) {
				System.out.println("关闭文件失败！"+e);
				return false;
			}
			
		}
		
		return true;
	}



	@Override
	public boolean initFile(String f) {
		
		this.f = f;
		this.file = new File(f);
		
		String filePath = this.getFilePath();
		
		File pathFile = new File(filePath);
		if(!this.file.exists()){
			try {
				pathFile.mkdirs();	
				this.file.createNewFile();
			} catch (IOException e) {
				System.out.println("新建文件异常："+e);
				return false;
			}
		}
		
		return true;
	}



	@Override
	public String getFileFormat() {
		
		String name = "";
		String fileFormat = "";
		name = f.split("/")[f.split("/").length-1];
		String[] str = name.split("\\.");
		fileFormat = str[str.length-1];
		
		return fileFormat;
	}



	@Override
	public String getFileName() {
	
		String name = "";
		String fileName = "";
		name = f.split("/")[f.split("/").length-1];
		String[] str = name.split("\\.");
		fileName = str[str.length-1-1];
		
		return fileName;
	}



	@Override
	public String getFilePath() {

		String filePath = "";
	
		File file = new File(f);
		
		String name = file.getName();
		
		filePath = f.substring(0, f.length()-name.length());
		
		return filePath;
	}



	@SuppressWarnings("resource")
	@Override
	public List<String> Reader() {
		
		InputStreamReader isr = null;
		BufferedReader buf = null;
		List<String> list = null;

		try {
			
			isr = new InputStreamReader(new FileInputStream(this.f), "UTF-8");  
			buf = new BufferedReader(isr);
			list = new ArrayList<String>();
			String s = "";
			
			while ((s=buf.readLine())!=null) {
				list.add(s);
			}
		} catch (FileNotFoundException e) {
			System.out.println("您输入的文件路径有误！");
		} catch (IOException e) {
			System.out.println(e);
		}
		return list;
	}

	@Override
	public boolean OutputStreamWriter(String str,String charset) {
		
		OutputStream os = null;
		Writer out = null;
		BufferedWriter writer = null;
		try {
			os = new FileOutputStream(this.file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			out = new java.io.OutputStreamWriter(os, charset);
			writer = new BufferedWriter(out);
			writer.write(str);
			writer.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

}
