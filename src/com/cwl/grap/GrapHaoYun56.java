package com.cwl.grap;

import java.util.HashMap;
import java.util.Scanner;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.cwl.dao.ISearchDAO;
import com.cwl.factory.DAOFactory;
import com.cwl.parents.Home;
import com.cwl.thread.ThreadService;
import com.cwl.tool.Tools;

public class GrapHaoYun56 extends Home implements ISearchDAO{

	public GrapHaoYun56(String url) {
		super(url);
	}

	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入起始页码：");
		int page = scan.nextInt();
		System.out.println("请输入行数:");
		int li = scan.nextInt();
		GrapHaoYun56 g = new GrapHaoYun56("http://www.haoyun56.com/company");
		g.http.setLi(li);
		g.run(page);

	}
	
	public  void run(int page) {
		http.setFlag(false);
		for (int i=page; i<=932; i++) {
			http.setPage(i);
			String url = "http://www.haoyun56.com/company/list_p"+i+".html";
			first(url);
		}
	}

	@Override
	public boolean first(String url) {
		
		http.setUrl(url);
		Document doc = null;
		this.sleep();
		try {
			doc = DAOFactory.getIHttpDAOInstance(http).getDocument();
		} catch (Exception e) {
			return false;
		}
		Elements ul = null;
		
		ul = doc.select("#form1 .divContain").get(2).select(".divLeft .list_border table").get(1).select("tbody tr");
		
		String company = "";
		String address = "";
		String range = "";
		int li = 0;
		if(!http.getFlag()) {
			li = http.getLi();
			http.setFlag(true);
		}
		
		
		for (int i=li; i< ul.size(); i+=4) {
			http.setLi(i);
			System.out.println("---------"+i/4+"-----------");
			map = new HashMap<String,Object>();
			company = ul.get(i).select("td").get(0).select("a").attr("title");
			range = ul.get(i+1).select("td").get(0).text().replaceAll("\\s+", "");
			address = ul.get(i+2).select("td").get(0).text().replaceAll("\\s+", "");
			map.put("company",company);
			map.put("range",range);
			map.put("address",address);
			String href = "";
			href = ul.get(i).select("td").get(0).select("a").attr("href");
			System.out.println("------"+http.getPage()+"----"+http.getLi());
			if(!second(href)){
				http.setLi(http.getLi()+4);
				run(http.getPage());
			}
		}
		return true;
	}

	@Override
	public boolean second(String url) {
		
		Document doc = null;
		http.setUrl(url);
		try {
			doc = DAOFactory.getIHttpDAOInstance(http).getDocument();
		} catch (Exception e) {
			return false;
		}
		Elements  li =null;
		
		String name = "";
		String phone = "";
		String tel = "";
		try {
			li = doc.select("#div_linkMan div").get(2).select("div div div");
			name = li.get(0).text();
			tel = li.get(1).select(".mob").text();
			phone = li.get(2).html().replace("&nbsp;", "").replace("|", "");
		} catch(Exception e) {
			return false;
		}
		map.put("name", name);
		map.put("tel", tel);
		map.put("phone", phone);
		String json = Tools.toJson(map);
		System.out.println(json);
		ThreadService ts = new ThreadService(json+"\r\n","好运物流网.txt");
		ts.run(ts);
		return true;
	}

	@Override
	public int pageState(String url) {
		// TODO Auto-generated method stub
		return 0;
	}

}
