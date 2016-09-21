package com.cwl.grap;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cwl.dao.ISearchDAO;
import com.cwl.factory.DAOFactory;
import com.cwl.parents.Home;
import com.cwl.tool.Tools;

public class Grap91156Line extends Home implements ISearchDAO {

	private String from = "";
	private String to = "";
	private int i=0;
	private int j=0;
	private int page = 1;
	private boolean link = true;
	public static boolean flag = true;
	
	public Grap91156Line(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean first(String url) {
	
		if(isLastPage(url)) {
			if(link) {
				this.second(url);
			}
			return false;
		}
		if(this.second(url)) {
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public boolean second(String url) {
		
		http.setUrl(url);
		try{
			Document doc = DAOFactory.getIHttpDAOInstance(http).getDocument();
			Elements ul = doc.select(".content_wrapper2 .left_content .zx_list .zx_inner");
			for (int i=0; i< ul.size(); i++) {
				
				System.out.println("============第"+(i+1)+"条数据========");
				map = new HashMap<String,Object>();
				map.put("from", from);
				map.put("to", to);
				Element li = ul.get(i);
				String company = li.select("dt").text();
				String address = li.select("dd").get(0).text();
				String[]  tel = li.select("dd").get(1).html().replace("&nbsp;", "").replace("联系电话：", "").split("\\s+");
				map.put("company", company);
				map.put("address", address);
				map.put("tel", tel);
				String json = Tools.toJson(map);
				System.out.println(json);
				DAOFactory.getIFileDAOInstance("物流导航网专线.txt").FileOutputStream(json+"\r\n", true);
			}
		} catch(IndexOutOfBoundsException e) {
			System.out.println("没数据....");
			return false;
		} catch (Exception e) {
			Grap91156Line gx = new Grap91156Line(url);
			gx.map = new HashMap<String,Object>();
			gx.map.put("from", i);
			gx.map.put("to", j);
			gx.map.put("page", page);
			System.out.println(e);
			DAOFactory.getIFileDAOInstance("物流导航网专线-异常日志.txt").FileOutputStream(Tools.toJson(gx.map)+"\r\n", true);
			return false;
		}
		
		return true;
	}


	public boolean isLastPage(String url) {
		
		try {
			http.setUrl(url);
			Document doc = DAOFactory.getIHttpDAOInstance(http).getDocument();
			String s = doc.select(".isNow").get(0).nextElementSibling().className();
			if("Disabled".equals(s)) {
				link = true;
				return true;
			}
		} catch (IndexOutOfBoundsException e) {
			link = false;
			System.out.println("数组溢出。。。");
			return true;
		} catch (Exception e) {
			Grap91156Line gx = new Grap91156Line(url);
			gx.map = new HashMap<String,Object>();
			gx.map.put("from", i);
			gx.map.put("to", j);
			gx.map.put("page", page);
			System.out.println(e);
			DAOFactory.getIFileDAOInstance("物流导航网专线-异常日志.txt").FileOutputStream(Tools.toJson(gx.map)+"\r\n", true);
			return true;
		}
		return false;
	}
	public static void main(String[] args) {
		
		Grap91156Line g = new Grap91156Line("http://www.91156.net/");
		Grap91156Line gt = new Grap91156Line("http://www.91156.net/");
		g.http.setCharset("gb2312");
		g.http.setCookie("safedog-flow-item=58AA6B2814E1FE5DC40290FBF8AF5C24; dizhicookie=%2Fwh%2F; citycookie=%CE%E4%BA%BA; ASPSESSIONIDSSRATCTR=FDFKCEKAIHBKAGPJJILLLOKI; AJSTAT_ok_pages=1; AJSTAT_ok_times=1");
		List<String> city = Tools.getCity();
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入起始城市索引(0)：");
		int f = 0;
		f = scan.nextInt();
		System.out.println("请输入目的地城市索引(0)：");
		int t = 0;
		t = scan.nextInt();
		System.out.println("请输入起始页码:");
		int p = scan.nextInt();
		
		for (g.i=f; g.i<city.size(); g.i++) {
			g.from = city.get(g.i);
			for (g.j=t; g.j<city.size(); g.j++) {
				g.to = city.get(g.j);
				if (g.i==g.j) {
					continue;
				}
				System.out.println("----"+g.i+"--"+g.from+"---->"+g.to+"----"+g.j);
				String From = "";
				String To = "";
				try {
					From = URLEncoder.encode(g.from,"gb2312");
					To = URLEncoder.encode(g.to, "gb2312");
				} catch (Exception e) {
					System.out.println("----------编码错误！--------");
				}
				
				if(flag) {
					g.page = p;
				} else {
					g.page = 1;
				}
				flag = false;
				
				
				while (true) {
					
					gt.map = new HashMap<String,Object>();
					gt.map.put("from",g.i);
					gt.map.put("to", g.j);
					gt.map.put("page", g.page);
					DAOFactory.getIFileDAOInstance("物流导航网-运行日志.txt").FileOutputStream(Tools.toJson(gt.map)+"\r\n", true);
					System.out.println("------------第"+g.page+"页---------");

					g.sleep();
					String url = "http://www.91156.net/zx.asp?id=&city="+From+"&city3="+To+"&page="+g.page;
					if(!g.first(url)) {
						break;
					}
					g.page ++;
				}
				
			}
		}
	}

	@Override
	public int pageState(String url) {
		
		http.setUrl(url);
		try {
			Document doc = DAOFactory.getIHttpDAOInstance(http).getDocument();
			try {
				Elements ul = doc.select(".cpb");
				try {
					Element li = ul.get(0).nextElementSibling();
					if (li == null) {
						return 1;//只有一页
					} else {
						return 6;//页码不是最后一页
					}
				} catch(Exception e) {
					return 1;//最后一页
				}
				
			} catch(Exception e) {
				return 0;//没有页码
			}
			
		} catch (Exception e) {
			DAOFactory.getIFileDAOInstance("物流导航网异常日志.txt").FileOutputStream(url+e+"\r\n", true);
			return -1;//链接不存在
		}
	}


}
