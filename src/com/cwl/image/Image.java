package com.cwl.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.cwl.dao.IImageDAO;

public class Image implements IImageDAO{
	private URL url = null;
	private File file = null;
	
	
	public Image(URL url) {
		this.url = url;
	}
	
	
	public Image(File file) {
		this.file = file;
	}
	
	
	public Image(File file,String url) throws MalformedURLException {
		this.file = file;
		this.url = new URL(url);
	}
	
	@Override
	public boolean save() {
		
		try {
			HttpURLConnection conn = (HttpURLConnection)this.url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(6666);
			InputStream is = conn.getInputStream();
			FileOutputStream fos = new FileOutputStream(this.file);
			int len = -1;
			while ((len = is.read())!=-1) {
				fos.write(len);
			}
			fos.close();
			is.close();	
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	@Override
	public String ident() {
		
		return null;
	}
	
	
	@Override
	public String create() {
		
		return null;
	}
	
	
}
