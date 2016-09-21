package com.cwl.proxy;

import java.util.List;

import com.cwl.dao.IFileDAO;
import com.cwl.impl.FileDAOImpl;

public class FileDAOProxy implements IFileDAO {

	private IFileDAO dao = null;
	
	public FileDAOProxy(String f){
		this.dao = new FileDAOImpl(f);
	}
	
	
	@Override
	public List<String> BufferedReader() {
		
		List<String> list =null;
		list = this.dao.BufferedReader();
		
		return list;
	}

	@Override
	public boolean FileOutputStream(String s, boolean flag) {
		
		boolean f = true;
		f = this.dao.FileOutputStream(s, flag);
		
		return f;
		
	}

	@Override
	public boolean FileOutputStream(String[] s, boolean flag) {
		
		boolean f = true;
		f = this.dao.FileOutputStream(s, flag);
		
		return f;
		
	}


	@Override
	public String getFileFormat() {
		
		String fileFormat = "";
		fileFormat = this.dao.getFileFormat();
		
		return fileFormat;
		
	}


	@Override
	public String getFileName() {
		
		String fileName = "";
		fileName = this.dao.getFileName();
		
		return fileName;
		
	}


	@Override
	public String getFilePath() {
		
		String filePath = "";
		filePath = this.dao.getFilePath();
		
		return filePath;
		
	}


	@Override
	public boolean initFile(String f) {
		
		boolean flag = false;
		flag = this.dao.initFile(f);
		
		return flag;
		
	}


	@Override
	public List<String> Reader() {
		
		List<String> list = null;
		list = this.dao.Reader();
		
		return list;
	}


	@Override
	public boolean OutputStreamWriter(String s,String charset) {
		
		boolean flag= false;
		flag = this.dao.OutputStreamWriter(s,charset);
		return flag;
	}

}
