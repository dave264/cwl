package com.cwl.grap;


import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cwl.dao.ISearchDAO;
import com.cwl.factory.DAOFactory;
import com.cwl.parents.Home;
import com.cwl.tool.ReaderFile;
import com.cwl.tool.Tools;

public class GrapChaWuLiu extends Home implements ISearchDAO {

	private int index = 0;
	private int page = 1;
	private boolean f = false;
	
	public GrapChaWuLiu(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean first(String url) {
		
		http.setUrl(url);
		int i = 1;
		if(f) {
			i = 1;
		} else {
			i = page;
		}
		f = true;
		boolean flag = false;
		while(true) {
			System.out.println("======第"+i+"页======");
			DAOFactory.getIFileDAOInstance("查物流-运行日志.txt").FileOutputStream(index+"---"+i+"---【"+url+"】"+"\r\n", true);
			sleep();
			String href = url+"/?page="+i;
			int state = this.pageState(href);
			if(state !=0 ) {
				if(isLink(url)) {
					Document doc = null;
					
					try {
						doc = DAOFactory.getIHttpDAOInstance(http).getDocument();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Elements ul = doc.select(".lister .tuanitem");
					for (int j=0; j< ul.size(); j++) {
						map = new HashMap<String,Object>();
						Element li = ul.get(j);
						String line = li.select(".tuanitem-meta-title").text();
						String tel = li.select(".nprice").text();
						String address = li.select(".hot_des .fl").text();
						String company = "";
						Matcher m = Pattern.compile("【\\W+】").matcher(line);
						if(m.find()){
							company = m.group().replace("【", "").replace("】", "");
						}
						line = line.replaceAll("【\\W+】", "").replace("专线", "");
						String from = null;
						String[] to = null;
						try {
							from = line.split("至")[0];
							to = line.split("至")[1].split("、");
						} catch(Exception e) {
							try {
								from = line.split("直达")[0];
								to = line.split("直达")[1].split("、");
							} catch (Exception ex) {
								try {
									from = line.split("到")[0];
									to = line.split("到")[1].split("、");
								} catch (Exception et) {
									from = "";
									to = null;
								}
								
							}
							
						}
						
						map.put("company", company);
						map.put("from", from);
						map.put("to",to);
						map.put("address", address);
						map.put("tel", tel);
						String json = Tools.toJson(map);
						System.out.println(json);
						DAOFactory.getIFileDAOInstance("查物流.txt").FileOutputStream(json+"\r\n", true);
					}
					
					if(flag&&state ==1) {
						return true;
					}
					
					if(state ==1) {
						return true;
					}
					
					if(state ==2) {
						flag = true;
					}
				}
				
			}else{
				return false;//页面不存在
			}
			i++;
		}
	}

	@Override
	public boolean second(String url) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isLink(String url) {
		
		http.setUrl(url);
		try {
			Document doc = DAOFactory.getIHttpDAOInstance(http).getDocument();
			Elements ul = doc.select(".lister .tuanitem");
			if (ul.size()>1) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	public int pageState(String url) {
		
		http.setUrl(url);
		try {
			Document doc = DAOFactory.getIHttpDAOInstance(http).getDocument();
			
			try {
				String className = doc.select("#pagation .current").get(0).nextElementSibling().nextElementSibling().nextElementSibling().className();
				if("down_pagination".equals(className)){
					return 2;//倒数第二页
				} else{
					return 6;//页面正常，可以进行下一页
				}
			} catch(Exception e) {
				if(isLink(url)) {
					return 1;//只有一页
				} else {
					return 0;
				}
				
			}
		} catch (Exception e) {
			return 0;//页面异常
		}
		
	}
	
	public static void main(String[] args) {
		
		
		GrapChaWuLiu g = new GrapChaWuLiu("http://shanghai.chawuliu.com/?page=1");
		g.http.setCharset("utf-8");
		List<String> list = ReaderFile.GetChaWuLiuCity();
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入起始索引(0):");
		int m= scan.nextInt();
		System.out.println("请输入起始页码:");
		g.page = scan.nextInt();
		
		for (int i=m; i<list.size(); i++) {
			System.out.println("-----------"+i+"--------【"+list.get(i)+"】");
			g.index = i;
			String city = list.get(i).split("/")[4];
			String url = "http://"+city+".chawuliu.com";
			g.first(url);
		}
		
		
	}



}
