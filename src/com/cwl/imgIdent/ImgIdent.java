package com.cwl.imgIdent;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ImgIdent {

	// the url below can not be changed !!
	public static final String apiUrl = "http://lab.ocrking.com/ok.html";
	// replace the word KEY below with your apiKey obtained by Email
	public static final String apiKey = "d5318ae3ade8ffa46fU4NyBvRaaIset9DRkTtw0N5ABttC0pL2nT53PNjjAkOciTpkgHd2bbEJbA";
	public static final String service = "OcrKingForPhoneNumber";//手机电话
//	public static final String service = "OcrKingForPassages";//长篇内容
//	public static final String service = "OcrKingForPDF";//PDF识别
//	public static final String service = "OcrKingForPrice";//商城价格
//	public static final String service = "OcrKingForNumber";//纯数字类
//	public static final String service = "OcrKingForCaptcha";//验证码类
//	public static final String service = "BarcodeDecode";//条形二维码
//	public static final String service = "PDFToImage";//PDF转图片
	
	public String language = "eng";//英文
//	public String language = "sim";//简体
	public String charset = "7";
	public Map<String, String> dataMap = null;
	public Map<String, String> fileMap = null;
	
	public ImgIdent(String filePath) {
		this.init(filePath);
	}
	
	public void init(String filePath) {

		this.dataMap = new HashMap<String, String>();
		this.fileMap = new HashMap<String, String>();
		
		dataMap.put("service", service);
		dataMap.put("language", this.language);
		dataMap.put("charset", this.charset);
		dataMap.put("type",apiUrl);
		dataMap.put("apiKey", apiKey);
		fileMap.put("ocrfile", filePath);
		
		
	}
	
	/**
	 * post data with file uploading
	 * @param urlStr  the address to upload
	 * @param dataMap post data
	 * @param fileMap file to upload
	 * @return xml result
	 */
	public	String postMultipart( ) {
		
		
		String res = "";
		HttpURLConnection conn = null;
		String boundary = "----------------------------OcrKing_Client_Aven_s_Lab";
		try {
			URL url = new URL(apiUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Referer", "http://lab.ocrking.com/?javaclient0.1)");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1; zh-CN; rv:1.9.1.3) Gecko/20100101 Firefox/8.0");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

			OutputStream out = new DataOutputStream(conn.getOutputStream());
			// data   
			if (dataMap != null) {
				StringBuffer strBuf = new StringBuffer();
				Iterator<Map.Entry<String, String>> iter = dataMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String, String> entry = iter.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null) {
						continue;
					}
					strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
					strBuf.append(inputValue);
				}
				out.write(strBuf.toString().getBytes());
			}

			// file  
			if (fileMap != null) {
				Iterator<Map.Entry<String, String>> iter = fileMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String, String> entry = iter.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null) {
						continue;
					}
					File file = new File(inputValue);
					String filename = file.getName();

					StringBuffer strBuf = new StringBuffer();
					strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");
					strBuf.append("Content-Type:application/octet-stream\r\n\r\n");

					out.write(strBuf.toString().getBytes());

					DataInputStream in = new DataInputStream(new FileInputStream(file));
					int bytes = 0;
					byte[] bufferOut = new byte[1024];
					while ((bytes = in.read(bufferOut)) != -1) {
						out.write(bufferOut, 0, bytes);
					}
					in.close();
				}
			}

			byte[] endData = ("\r\n--" + boundary + "--\r\n").getBytes();
			out.write(endData);
			out.flush();
			out.close();

			// handle the response 
			StringBuffer strBuf = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuf.append(line).append("\n");
			}
			res = strBuf.toString();
			reader.close();
			
		} catch (Exception e) {
			System.out.println("error " + apiUrl);
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return res;
	}
}
