package com.cwl.dao;

import java.util.List;

public interface IFileDAO {
	
	public String getFileFormat();
	public String getFileName();
	public String getFilePath();
	public boolean initFile(String f);
	public List<String> BufferedReader();
	public List<String> Reader();
	public boolean FileOutputStream(String s,boolean flag);
	public boolean FileOutputStream(String[] s,boolean flag);
	public boolean OutputStreamWriter(String s,String charset);
}
