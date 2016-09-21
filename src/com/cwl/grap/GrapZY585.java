package com.cwl.grap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cwl.dao.ISearchDAO;
import com.cwl.factory.DAOFactory;
import com.cwl.image.Image;
import com.cwl.imgIdent.ImgIdent;
import com.cwl.parents.Home;
import com.cwl.tool.Tools;

public class GrapZY585 extends Home implements ISearchDAO{

	public GrapZY585(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		GrapZY585 g = new GrapZY585("http://www.zy585.com/company/list.php?catid=3");
		g.http.setCharset("utf-8");
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.print("请输入起始页码:");
		int curPage = 1;
		try{
			curPage = scan.nextInt();
		} catch(Exception e){
			curPage = 1;
		}
		for(int i=curPage; i<=355; i++) {
			System.out.println("--------------第"+i+"页-----------------");
			String url = "http://www.zy585.com/company/list.php?catid=3&page="+i;
			g.http.setUrl(url);
			
			g.first(url);
		
		}
		
	}

	@Override
	public boolean first(String url) {
		
		Document doc = null;
		try {
			doc = DAOFactory.getIHttpDAOInstance(http).getDocument();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements ul = doc.select(".gsnr");
		Elements li = ul.select(".list");
		for(int i=0; i<li.size();i++) {
			System.out.println("-------第"+(i+1)+"条------");
			map = new HashMap<String,Object>();
			Elements span = li.get(i).select(".gscontents p span");
			String check = span.get(0).text();
			String range = span.get(2).text();
			String company = li.get(i).select(".gstits a").text().replace("*", "");
			String detail = li.get(i).select(".gsall dl dt a").get(0).attr("href");
			String tel = li.get(i).select(".gsall dl dd").html().split(",")[0].replace("公司电话&nbsp;&nbsp; ", "");
			map.put("company", company);
			map.put("tel", tel);
			map.put("check", check);
			map.put("range", range);
			http.setRefer(url);
			second(detail);
			String json = Tools.toJson(map);
			System.out.println(json);
//			DAOFactory.getIFileDAOInstance("中运网.txt").FileOutputStream(json+"\r\n", true);
		}
		return true;
	}

	@Override
	public boolean second(String url){
		
		String details = "";
		String status_person = "";
		String tel = "";
		http.setUrl(url);
		
		Document doc = null;
		try {
			doc = DAOFactory.getIHttpDAOInstance(http).getDocument();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	
		try {
			Elements tr = null;
			tr = doc.select("table").get(0).select("tr");
			for(int i=0; i< tr.size(); i++) {
				Elements td = tr.get(i).select("td");
			
				if("公司地址：".equals(td.get(0).text())){
					details = td.get(1).text();
					continue;
				}
				if("联 系 人：".equals(td.get(0).text())){
					status_person = td.get(1).text();
					continue;
				}
				if("手机号码：".equals(td.get(0).text())){
					String src = td.get(1).select("img").attr("src");
					http.setUrl(src);
					InputStream is = DAOFactory.getIHttpDAOInstance(http).doGet().getEntity().getContent();
					FileOutputStream fos = new FileOutputStream(new File("d://tel.jpg"));
					int len = -1;
					while((len = is.read())!=-1) {
						fos.write(len);
					}
					fos.close();
					is.close();
					System.out.println(src);
					new Image(new File("d://tel.jpg"),src).save();
					Document d = Jsoup.parse(new ImgIdent("d://tel.jpg").postMultipart());
					System.out.println(d.html());
					continue;
				}
				
			}
		} catch (Exception e) {
			if("".equals(details)) {
				Elements ul = doc.select("div.m div.m_r.f_l div.contact_body ul li");
				for(int i=0; i<ul.size(); i++) {
					Element li = ul.get(i);
					if(Pattern.compile("^地址\\W+").matcher(li.text()).matches()) {
						details = li.text();
					}
				}
			}	
		}
		
		if("".equals(details)) {
			Elements ul = doc.select("div.m div.m_r.f_l div.contact_body ul li");
			for(int i=0; i<ul.size(); i++) {
				Element li = ul.get(i);
				if(Pattern.compile("^地址\\W+").matcher(li.text()).matches()) {
					details = li.text();
				}
			}
		}	
		details = "地址="+details;
		map.put("address", details);
		map.put("status_person", status_person);
		map.put("tel", tel);
		return true;
	}


	@Override
	public int pageState(String url) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
}
