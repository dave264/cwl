package com.cwl.grap;

import java.util.HashMap;
import java.util.Scanner;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.cwl.factory.DAOFactory;
import com.cwl.parents.Home;
import com.cwl.tool.Tools;

public class GrapQD56 extends Home {

	public GrapQD56(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	public boolean first(String url) {
		
		sleep();
		Document doc = null;
		try {
			doc = DAOFactory.getIHttpDAOInstance(http).getDocument();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements tr = doc.select(".div_body_right_txt .div_coms_list .div_coms_list_title");
		
		for(int j=0; j<tr.size(); j++) {
			System.out.println("------------"+j+"-----------------");
			String company = tr.get(j).select("a").html();
			String detail = tr.get(j).select("a").attr("href");
			detail = "http://www.qd56.cn/"+detail;
			map = new HashMap<String,Object>();
			map.put("company", company);
			http.setUrl(detail);
			second(detail);
			String json = Tools.toJson(map);
			System.out.println(json);
			DAOFactory.getIFileDAOInstance("青岛物流.txt").FileOutputStream(json+"\r\n", true);
		}
		return false;

	}
	
	public boolean second(String url) {
		
		Document doc = null;
		try {
			doc = DAOFactory.getIHttpDAOInstance(http).getDocument();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements li = doc.select(".div_show_all .div_show_coms_list").get(0).select("ul li");
	
		Elements td = doc.select(".div_show_all .div_show_coms_list").get(1).select("ul li");
		String range = li.get(0).text();
		String address = li.get(1).text();
		String name = td.get(0).text().replace("姓名：", "");
		String tel = td.get(2).text();
		map.put("range", range);
		map.put("address", address);
		map.put("name", name);
		map.put("tel", tel);
		return false;
	}
	
	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.print("请输入i的起始值:");
		int m = scan.nextInt();
		for (int i=m; i<=2960; i+=20) {
			System.out.println("----------i="+i+"\t第"+((i/20)+1)+"页---------");
			String url = "http://www.qd56.cn/coms.html?sid=4&word=&setid="+i;
			GrapQD56 g = new GrapQD56(url);
			g.http.setCharset("gb2312");
			g.http.setUrl(url);
			try {
				g.first(url);
			} catch (Exception e) {
				i -= 20;
				System.out.println("第"+(i/2)+1+"页："+url+e);
			}
		}

	}

}
