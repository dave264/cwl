package com.cwl.proxy;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.jsoup.nodes.Document;

import com.cwl.dao.IHttpDAO;
import com.cwl.impl.HttpDAOImpl;
import com.cwl.vo.Http;

public class HttpDAOProxy implements IHttpDAO {

	private IHttpDAO dao = null;
	
	
	public HttpDAOProxy(Http http) {
		
		this.dao = new HttpDAOImpl(http);
		
	}
	

	@Override
	public Document getDocument() throws Exception {
		
		Document doc = null;
		try {
			doc = this.dao.getDocument();
		} catch(Exception e) {
			System.out.println(e);
			throw e;
		}
		return doc;
	}




	@Override
	public CloseableHttpResponse doGet() throws Exception{
		
		CloseableHttpResponse response = null;
		try {
			response = this.dao.doGet();
		} catch(Exception e) {
			System.out.println(e);
			throw e;	
		}
		
		return response;
		
	}

	@Override
	public CloseableHttpResponse doPost() throws Exception {
		
		CloseableHttpResponse response = null;
		try {
			response = this.dao.doPost();
		} catch (Exception e) {
			System.out.println(e);
			throw e;
		}
		
		
		return response;
		
	}

	

	@Override
	public RequestConfig getRequestConfig() throws Exception {
		
		RequestConfig requestConfig = null;
		try {
			requestConfig = this.dao.getRequestConfig();
		} catch (Exception e) {
			System.out.println(e);
			throw e;
		}
		
		
		return requestConfig;
		
	}


	@Override
	public CloseableHttpResponse getResponse() throws Exception {
		
		CloseableHttpResponse response = null;
		try {
			response = this.dao.getResponse();
		} catch(Exception e) {
			System.out.println(e);
			throw e;
		}
		
		return response;
		
	}



	@Override
	public CloseableHttpResponse doHttpsGet() throws Exception {
		
		CloseableHttpResponse response = null;
		try {
			response = this.dao.doHttpsGet();
		} catch (Exception e) {
			System.out.println(e);
			throw e;
		}
		
		
		return response;
		
	}



	@Override
	public CloseableHttpResponse doHttpsPost() throws Exception {
		
		CloseableHttpResponse response = null;
		try {
			response = this.dao.doHttpsPost();
		} catch (Exception e ){
			System.out.println(e);
			throw e;
		}	
		return response;
		
	}



	@Override
	public void sleep() {
		this.dao.sleep();
	}


	@Override
	public void setCharset(String html) {
		
		this.dao.setCharset(html);
		
	}


}
