package com.cwl.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JEditorPane;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.cwl.factory.DAOFactory;
import com.cwl.imgIdent.ImgIdent;
import com.cwl.parents.Home;
import com.cwl.parse.tools.Parse;
import com.cwl.tool.Tools;



public class GrapTest extends Home{

	public GrapTest(String url) {
		super(url);
		
	}

	public static void xingzhengCode() {
		List<String> list = DAOFactory.getIFileDAOInstance("file/城市编码.txt").BufferedReader();
		List<Map> info = null;
		Map<String,Object> map = null;
		for (int i=0; i< list.size(); i++) {
			
			info = new ArrayList<Map>();
			String Name = "";
			Integer Code = 0;
			
			String idRegex = "\\d+";
			String nameRegex = "\'\\W+";
			
			String city = list.get(i).split("：")[0];
			String cityName = city.split(",")[1];
			String cityCode = city.split(",")[0];
			Matcher mCityName = Pattern.compile(nameRegex).matcher(cityName);
			Matcher mCityCode = Pattern.compile(idRegex).matcher(cityCode);
			if(mCityName.find()) {
				Name = mCityName.group().replace("':", "").replace("'", "");
			}
			if(mCityCode.find()) {
				Code = Integer.parseInt(mCityCode.group());
			}
			map = new HashMap<String,Object>();
			map.put(Name, Code);
			
			
			try {
				String s = list.get(i).split("：\\s+")[1];
				String[] str = s.split(",");
				for (int j=0; j<str.length; j+=2) {
					
					String cityId = str[j];
					String cName = str[j+1].replace("':", "");
					
					Matcher mId = Pattern.compile(idRegex).matcher(cityId);
					Matcher mName = Pattern.compile(nameRegex).matcher(cName);
					Integer id = 0;
					String name = "";
					if(mId.find()) {
						id = Integer.parseInt(mId.group());
						if(id==0) {
							continue;
						} 
					}
					if(mName.find()) {
						name = mName.group().replace("'", "").replaceAll("\\s+", "");
					}
					map.put(name, id);
					info.add(map);
				}
			} catch(Exception e) {
				continue;
			}
			
			System.out.println(Tools.toJson(map));
			DAOFactory.getIFileDAOInstance("d://行政编码.txt").FileOutputStream(Tools.toJson(map)+"\r\n", true);
		}
	}
	public static void main(String[] args) {
	
		GrapTest g = new GrapTest("http://www.baidu.com");
		try {
			Document doc = DAOFactory.getIHttpDAOInstance(g.http).getDocument();
			System.out.println(doc.html());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void URLConnection_(){
		try {
			URL url = new URL("http://www.zy585.com/company/list.php?catid=3");
			URLConnection conn = url.openConnection();
			System.out.println("正文类型:"+conn.getContentType());
			InputStream is = conn.getInputStream();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while ((len = is.read(b))!=-1) {
				buffer.write(b,0,len);
			}
			String s = new String(buffer.toByteArray());
			s = new String(s.getBytes("gbk"),"UTF-8");
			System.out.println(s);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void URLOpenStream(){
		try {
			URL url = new URL("http://www.zy585.com/company/list.php?catid=3");
			InputStream in = url.openStream();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while((len = in.read(b))!=-1) {
				buffer.write(b,0,len);
			}
			String s = new String(buffer.toByteArray());
			s = new String(s.getBytes("gbk"),"UTF-8");
			Document doc = Jsoup.parse(s);
			System.out.println(doc.html());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void grap(){
		ImgIdent img = new ImgIdent("d://");
		String s = img.postMultipart();
		System.out.println(s);
	}

}
