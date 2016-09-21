package com.cwl.parents;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.httpclient.NameValuePair;
import com.cwl.vo.Http;

public class Home {

	public Map<String,Object> map = null;
	

	public Http http = null;
	
	public Home(String url){
		http = new Http();
		initHttp(url);
	}
	
	/**
	 * 初始化 url 链接
	 * @param url
	 */
	public void initHttp(String url) {
		
		if(!isUrl(url)){
			url = http.getScheme() + "://"+ http.getHost() + url;
		} 
			
		http.setUrl(url);
		http.setAddress(this.getAddress(url));
		http.setScheme(this.getScheme(url));
		http.setHost(this.getHost(url));
		
	}

	/**
	 * 程序随机睡眠
	 */
	public void sleep(){
		
		long min = 500;
		long max = 1500;
		long time = (long)(min + Math.random()*(max-min+1));
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	/**
	 * GET 传参，获取参数列表
	 * @param url
	 * @return
	 */
	public List<NameValuePair> getParameter(String url) {
		
		List<NameValuePair> list = null;
		
		try{
			String str = url.split("[?]")[1];
			
			list = new ArrayList<NameValuePair>();
			
			String[] params = str.split("[&]");
			
			for(int i=0; i<params.length; i++) {
				String key = params[i].split("[=]")[0];
				String value = "";
				try {
					value = params[i].split("[=]")[1];
				} catch(Exception e) {
					value = "";
				}
				
				list.add(new NameValuePair(key,value));
			}
		} catch(Exception e) {
			return null;
		}
			
		return list;
		
	}
	
	/**
	 * 获取 访问 url 地址
	 * @param url
	 * @return
	 */
	public String getAddress(String url) {
		
		
		String address = "";
	
		try{
			
			address = url.split("[?]")[0];
			
		}catch(Exception e){
			address = url;	
		}
		
		return address ;
	
	}

	/**
	 * 获取通讯协议
	 * @param url
	 * @return
	 */
	public String getScheme(String url) {
		
		String address = "";
		String scheme = "";
		if(isUrl(url)) {
			try {
				address = this.getAddress(url);
				scheme = address.split("://")[0];
				if(scheme.toLowerCase() == "https" || "https".equals(scheme.toLowerCase())) {
					scheme = "http";
				}
			} catch(Exception e) {
				scheme = "http";
			}
		}
		
		return scheme ;
	}

	/**
	 * 获取文件路径
	 * @param url
	 * @return
	 */
	public String getPath(String url) {
		
		String address = "" ;
		String scheme = "" ;
		String host = "" ;
		String path = "" ;
		
		if( isUrl(url)) {
			try {
				
				address = this.getAddress(url);
				scheme = this.getScheme(url);
				host = this.getHost(url);
				path = address.substring(new String(scheme+"://"+host).length());
				
			}catch(Exception e){
				path = "";
			}
		} else {
			path = url;
		}
		
		return path;
	}


	/**
	 * 获取主机
	 * @param url
	 * @return
	 */
	public String getHost(String url) {
		
		String address = "" ;
		String host = "";
		
		if(isUrl(url)) {
			try {
				address = this.getAddress(url);
				host = address.split("://")[1].split("/")[0];
			} catch(Exception e) {
				host = "";
			}
		}
		return host;
		
	}
	
	/**
	 * 判断是否为标准url 地址
	 * @param url
	 * @return
	 */
	public boolean isUrl(String url) {
		
		try {
			if(url.split("http://").length < 2){
				return false;
			}
		} catch(Exception e) {
			return false;
		}
		
		return true;
		
	}

}
