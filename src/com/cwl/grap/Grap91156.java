package com.cwl.grap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cwl.factory.DAOFactory;
import com.cwl.parents.Home;
import com.cwl.tool.Tools;

public class Grap91156 extends Home{

	
	public Grap91156(String url) {
		super(url);
		
	}


	public static void main(String[] args) {
		
		Grap91156 g = null;
		String url = "";
		
		
		String beginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		
		for(int i=1; i<= 11930; i++) {
			
			System.out.println("-----第"+i+"页-----");
			
			url = "http://www.91156.net/s.asp?cityid=&cityname=&s=&action=&page="+i;
			g = new Grap91156(url);
			g.grapDetail(url);
			
		}
		
		String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		Tools.timeCalculate(beginTime, endTime);
		
	}
	 
	
	public boolean grap(String url){
			
		grapDetail(url);
	
		return true;
	}
	
	public boolean grapDetail(String url) {
		
		String name = "d://Program Files/grap/91156/物流导航网-物流公司.txt";
		Map<String,Object> map = null;
		Document doc = null;
		map = new HashMap<String,Object>();
		try {
			doc  = DAOFactory.getIHttpDAOInstance(http).getDocument();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements es = doc.select(".zx_list dl");
		
		
		for(int i=0; i<es.size(); i++) {
			System.out.println("-------------"+i+"----------------");
			String company = es.get(i).select("dt").text();
			String address = es.get(i).select("dd").get(0).html().replaceAll("&nbsp;", "").replaceAll("<dd>", "").replaceAll("</dd>", "");
			String tel = es.get(i).select("dd").get(1).html().replaceAll("&nbsp;", "").replaceAll("<dd>", "").replaceAll("</dd>", "").replace("联系电话：", "");
			String uri = es.get(i).select("dd").get(2).select("a").attr("href");
			Document d = null;
			List<Object> list = null;
			Grap91156 gxx = new Grap91156(uri);
			try {
				d = DAOFactory.getIHttpDAOInstance(gxx.http).getDocument();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list = getDetail(d);
			
			map.put("company", company);
			map.put("address", address);
			map.put("tel", tel.split("\\s+"));
			map.put("web", list);
			String json  = Tools.toJson(map);
			System.out.println(json);
			DAOFactory.getIFileDAOInstance(name).FileOutputStream(json+",", true);
		}
		
		return true;
	}

	
	public  List<Object> getDetail(Document doc) {
		
		List<Object> list = null;
		list = new ArrayList<Object>();
		Element td = null;
		try{
			td = doc.select("#_ctl5_dlLine tbody tr").get(0).select("td").get(0);
		}catch(Exception e){
			return null;
		}
		String html = td.html();
		for(int i=6; i>0; i--) {
			String s = "";
			for(int j=i; j>0; j--) {
				s += "&nbsp;";
			}
			html = html.replace(s, "\t");
		}
		html = html.replace("\t", " ");
		String[] s = html.split("<br>");
		for(int i=0; i<s.length; i++) {
			if(s[i] !=null){
				list.add(s[i].trim());
			}
			
		}
		return list;
	}

}
