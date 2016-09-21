package com.cwl.grap;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cwl.dao.ISearchDAO;
import com.cwl.factory.DAOFactory;
import com.cwl.parents.Home;
import com.cwl.thread.ThreadService;
import com.cwl.tool.Tools;

public class Grap51YunLi extends Home implements ISearchDAO{

	public String url = "";
	
	public Grap51YunLi(String url) {
		super(url);
	}

	@Override
	public boolean first(String url) {
		
		int state = pageState(url);
		if( state == -1) {
			return false;
		} else if( state == 1 || state == 0) {//只有一页或者是最后一页
			second(url);//执行一页返回true
			return false;
		} else {
			second(url);
			return true;
		}
	}

	@Override
	public boolean second(String url) {
		
	
		Document doc = null;
		try {
			doc = DAOFactory.getIHttpDAOInstance(http).getDocument();
		} catch (Exception e) {
			run(http.getIndex(),false,http.getPage(),http.getLi());
		}
		Elements ul = doc.select(".ld_list_company li");
		int x = 0;
		if(!http.getFlag()) {
			x = http.getLi();
			http.setFlag(true);
		}
		for (int i=x; i< ul.size(); i++) {
		
			http.setLi(i);
			map = new HashMap<String,Object>();
			Element li = null;
			li = ul.get(i);
			Elements p = null;
			p = li.select("span.ld_name.fl p");
			String href = "";
			href = p.get(0).select("a").get(0).attr("href");
			String company = "";
			company = p.get(0).text();
			String address = "";
			if(Pattern.compile(".+").matcher(company).find()) {
				http.setUrl(href);
				Document d = null;
				try {
					d = DAOFactory.getIHttpDAOInstance(http).getDocument();
				} catch (Exception e) {
					run(http.getIndex(),false,http.getPage(),http.getLi());
				}
				try {
					company = d.select("#company_title_topimage").text();
				} catch(Exception e) {
					company = "";
				}
				try {
					address = d.select("#l_address").text();
				} catch(Exception e) {
					address = "";
				}
				
			}
			String[] tel = null;
			try {
				tel = p.get(1).text().replace("电话：", "").split(",");
			} catch(Exception e) {
				tel = null;
			}
			
			map.put("company", company);
			map.put("tel", tel);
			map.put("address", address);
			String json ="";
			json = Tools.toJson(map);
			System.out.println("-----"+http.getIndex()+"----"+http.getPage()+"----"+http.getLi()+"-----\n"+json);
//			DAOFactory.getIFileDAOInstance("无忧运力网运行日志.txt").FileOutputStream("城市编码索引:"+http.getIndex()+"-->"+"页码:"+http.getPage()+"-->"+"行数:"+http.getLi()+"\r\n", true);
//			DAOFactory.getIFileDAOInstance("无忧运力网.txt").FileOutputStream(json+"\r\n",true);
			new ThreadService(json,"无忧运力网.txt");
		}
		
		return true;
	}
	
	public  void run(int index,boolean flag,int page,int Li) {
		
		List<Integer> list = Tools.getCityCode();
		for ( int i=index; i< list.size(); i++) {
					
			http.setIndex(i);//记录索引
			int Code = list.get(i);
			int p = 1;
			if(!flag) {
				p = page;
			}
			
			while(true) {
				
				
				String url = "http://www.51yunli.com/Company/"+Code+"/"+p+"/0/_0";
				
				
				http.setPage(p);//记录页码
				http.setUrl(url);
				if(!flag) {
					http.setLi(Li);
				}
				http.setFlag(flag);
				flag = true;
				sleep();
				if(!first(url)) {
					break;
				}
				p ++;
			}
		}
	}
	
	public static void main(String[] args){
		
		
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		Grap51YunLi g = new Grap51YunLi("http://www.51yunli.com/Company/110000/1/0/_0");;
		System.out.println("请输入起始城市编码:");
		int index = scan.nextInt();
		System.out.println("请输入起始页码:");
		int page = scan.nextInt();
		System.out.println("请输入异常所在的行数索引:");
		int Li = scan.nextInt();
		boolean flag = false;
		g.run(index,flag,page,Li);

	}

	@Override
	public int pageState(String url) {
		
		http.setUrl(url);
		try {
			Document doc = null;
			doc = DAOFactory.getIHttpDAOInstance(http).getDocument();
			try {
				Elements ul = null;
				ul = doc.select(".cpb");
				try {
					Element li = null;
					li = ul.get(0).nextElementSibling();
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
			DAOFactory.getIFileDAOInstance("无忧运力异常日志.txt").FileOutputStream(url+e+"\r\n", true);
			return -1;//链接不存在
		}
	}

}
