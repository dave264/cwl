package com.cwl.vo;

import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;

public class Http {
	
	private String method = "get";//请求方式
	private String charset = "utf-8";
	private String url = "";//url地址
	private String cookie = null;
	private String refer = null;
	private int socketTimeOut = 666666;//超时
	private int connectTimeOut =666666;
	private String address = "";//请求服务器地址
	private int state = 200;//响应状态
	private String userAgent = "";
	private String scheme = "http";//协议
	private String host = "";//主机
	private String path = "";//路径
	private List<NameValuePair> parameter = null;
	private CloseableHttpResponse response = null;
	private int count = 0;
	private int index = 0;
	private int page = 1;
	private int li=0;
	private boolean flag = false;
	private HttpHost httpHost = null;
	private Map<String,String> header = null;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLi() {
		return li;
	}
	public void setLi(int li) {
		this.li = li;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	public String getRefer() {
		return refer;
	}
	public void setRefer(String refer) {
		this.refer = refer;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}



	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	
	public List<NameValuePair> getParameter() {
		return parameter;
	}
	public void setParameter(List<NameValuePair> parameter) {
		this.parameter = parameter;
	}
	public int getSocketTimeOut() {
		return socketTimeOut;
	}
	public void setSocketTimeOut(int socketTimeOut) {
		this.socketTimeOut = socketTimeOut;
	}
	public int getConnectTimeOut() {
		return connectTimeOut;
	}
	public void setConnectTimeOut(int connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public CloseableHttpResponse getResponse() {
		return response;
	}
	public void setResponse(CloseableHttpResponse response) {
		this.response = response;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public boolean getFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public HttpHost getHttpHost() {
		return httpHost;
	}
	public void setHttpHost(HttpHost httpHost) {
		this.httpHost = httpHost;
	}
	public Map<String,String> getHeader() {
		return header;
	}
	public void setHeader(Map<String,String> header) {
		this.header = header;
	}
	
	
}
