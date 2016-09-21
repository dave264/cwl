package com.cwl.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Save {
	public static void main(String[] args) {
		try {
			URL url = new URL("http://nos.netease.com/mail-userthumb/e41341ba1cfff2374f83dbf86575c3b6_40.jpeg");
			URLConnection conn = url.openConnection(); 
		
			conn.setConnectTimeout(6666);
			InputStream is = conn.getInputStream();
			
			FileOutputStream fos = new FileOutputStream(new File("d://1.jpg"));
			int len = -1;
			while((len = is.read())!=-1) {
				fos.write(len);
			}
			fos.close();
			is.close();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
