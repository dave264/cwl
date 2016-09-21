package com.cwl.impl;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.cwl.dao.IHttpDAO;
import com.cwl.pattern.AllPattern;
import com.cwl.vo.Http;

public class HttpDAOImpl implements IHttpDAO {
	
	private Http http = null;

	public HttpDAOImpl(Http http) {
		
		this.http = http;
	
	}

	/**
	 * 获取请求配置文件
	 */
	@Override
	public RequestConfig getRequestConfig(){
		
		RequestConfig requestConfig = null;
		
		Builder builder = RequestConfig.custom();
		builder = builder.setConnectTimeout(this.http.getConnectTimeOut());
		builder = builder.setSocketTimeout(this.http.getSocketTimeOut());
		
		if(!"".equals(http.getCookie())) {
			builder = builder.setCookieSpec(http.getCookie());
		}else{
			builder = builder.setCookieSpec(CookieSpecs.STANDARD_STRICT);
		}
		if(http.getHttpHost() != null) {
			builder = builder.setProxy(http.getHttpHost());
		}
		requestConfig = builder.build();
		return requestConfig;
		
	}

	/**
	 * 获取Jsoup 所需要的节点树
	 */
	@Override
	public Document getDocument() {
		
		String html = "";
		Document doc = null;
		HttpEntity entity = null;
		CloseableHttpResponse response = null;
		try {
			response = this.getResponse();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		int status = 0;
		status = response.getStatusLine().getStatusCode();
		if(status == HttpStatus.SC_OK) {
			entity = response.getEntity();
			
			if( entity != null) {
				try {
					byte[] b = EntityUtils.toByteArray(entity);
					html = new String(b);
					setCharset(html);
					html = new String(b,http.getCharset());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				doc = Jsoup.parse(html);
			}
		}	 
		this.http.setCount(0);
		return doc;
		
	}


	/**
	 * 获取相应结果
	 */
	@Override
	public CloseableHttpResponse getResponse() throws Exception{
		
		CloseableHttpResponse response = null;
		
		if("get".equals(this.http.getMethod().toLowerCase())){
			try {
				response = this.doGet();
			} catch (Exception e) {
				throw e;
			}
		}else{
			try {
				response = this.doPost();
			} catch (Exception e) {
				throw e;
			}
		}
		 
		return response;
		
	}

	/**
	 * http get
	 * @param url  请求地址
	 * @param cookie cookie
	 * @param refer  跳转地址
	 * @return 响应对象
	 */
	@Override
	public CloseableHttpResponse doGet() throws Exception{
		
		HttpGet httpGet = null;
		RequestConfig requestConfig = null;
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		
		//设置全局的标准cookie策略
		requestConfig = this.getRequestConfig();
		//设置可关闭的httpClient
		httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		//发送请求
		httpGet = new HttpGet(http.getUrl());
		httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW24) AppleWebKit/537.32 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.32");
		if(http.getHeader() != null) {
			Map<String,String> headers = http.getHeader();
			for(Map.Entry<String, String> header :headers.entrySet()) {
				httpGet.addHeader(header.getKey(), header.getValue());
			}
		}
		
		try {
			response = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		
		}
		return response;
		
	}

	
	/**
	 * http post
	 * @param  url  请求地址
	 * @param  values  请求参数
	 * @param  cookie  cookie
	 * @param  refer   跳转地址
	 * @return 响应对象
	 */
	@Override
	public CloseableHttpResponse doPost() throws Exception{
		
		HttpPost httpPost = null;
		UrlEncodedFormEntity entity = null;
		RequestConfig  requestConfig = null;
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		
		requestConfig = this.getRequestConfig();
		
		httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		httpPost = new HttpPost(http.getUrl());
		httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW24) AppleWebKit/537.32 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.32");
		if(http.getHeader() != null) {
			Map<String,String> headers = http.getHeader();
			for(Map.Entry<String, String> header :headers.entrySet()) {
				httpPost.addHeader(header.getKey(), header.getValue());
			}
		}
//		entity = new UrlEncodedFormEntity(http.getParameter(),Consts.UTF_8);
		entity = new UrlEncodedFormEntity(http.getParameter(),http.getCharset());
		httpPost.setEntity(entity);
		try {
			response = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
		this.http.setCount(0);
		return response;
		
	}

	/**
	 * https  get请求
	 */
	@Override
	public CloseableHttpResponse doHttpsGet() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * https post 请求
	 */
	@Override
	public CloseableHttpResponse doHttpsPost() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 随机睡眠
	 */
	@Override
	public void sleep() {
		
		long min = 1000;
		long max = 3000;
		long time = (long)(min + Math.random()*(max-min+1));
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 设置编码格式
	 */
	@Override
	public void setCharset(String html) {
		
		String charset = "";
		Matcher metas = AllPattern.meta.matcher(html);
		while(metas.find()) {
			String meta = metas.group();
			Matcher encoding = AllPattern.charset.matcher(meta);
			if(encoding.find()) {
				charset = encoding.group().replace("charset=", "").replaceAll("\"", "").replace(">", "").replace("/","").trim();
				http.setCharset(charset);
				break;
			}
		}
	}

}
