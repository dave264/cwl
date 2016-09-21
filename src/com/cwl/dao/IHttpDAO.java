package com.cwl.dao;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.jsoup.nodes.Document;

public interface IHttpDAO {
	
	public void sleep();
	public Document getDocument() throws Exception;
	public CloseableHttpResponse doGet() throws Exception;
	public CloseableHttpResponse doPost() throws Exception;
	public CloseableHttpResponse doHttpsGet() throws Exception;
	public CloseableHttpResponse doHttpsPost() throws Exception;
	public CloseableHttpResponse getResponse() throws Exception;
	public RequestConfig getRequestConfig() throws Exception;
	public void setCharset(String html);
//	public String getHost();
//	public int getPort();
//	public String getProtocol();
//	public String getRef();
//	public String getPath();
//	public String getQuery();
}
