package com.cwl.grap;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.cwl.factory.DAOFactory;
import com.cwl.parents.Home;
import com.cwl.tool.Tools;

/**
 * 全国物流货运名录
 * @author Mr.chen
 *
 */
public class GrapWltag extends Home {
	
	public GrapWltag(String index){
		super(index);
	}
	
	public static void main(String[] args) {
		
		Scanner s = null;
		try{
			s = new Scanner(System.in);
		}catch(Exception e){
			
		}
		
		System.out.print("请输入起始页码：");
		int indexPage = s.nextInt();
		for(int i=indexPage; i<=5030; i++){
			
			System.out.println("----------------第"+i+"页-----------------");
			String url = "http://www.wltag.com/list.php?key=%E8%B4%A7%E8%BF%90&condition=corporatename&page="+i;
			GrapWltag g = new GrapWltag(url);
			try {
				g.detail(url);
				g.sleep();
			} catch (Exception e) {
				DAOFactory.getIFileDAOInstance(g.getClass().getName()+"/log.txt").FileOutputStream(url+"\r\n", true);
				continue;
			}
		}
	}

	
	public boolean detail(String url) throws Exception{
		
		Document doc = null;
		
		Map<String,Object> map = null;
		
		map = new HashMap<String,Object>();
		doc = DAOFactory.getIHttpDAOInstance(http).getDocument();
		Elements tr = null;
		tr = doc.select(".tab_change tbody tr");
		for(int j=1; j< tr.size()-1; j++){
			System.out.println("--------------"+j+"--------------");
			Elements td = null;
			td = tr.get(j).select("td");
			String company = td.get(0).select("a").html();
			String location = td.get(1).text();
			String address= "";
			String range = "";
			String s = td.get(2).select("a").attr("onclick");
			String tel = getTel(s);
			try{
				
				String detail = td.get(3).select("a").attr("href");
				
				GrapWltag gw = new GrapWltag(detail);
				Document d = null;
				d = DAOFactory.getIHttpDAOInstance(gw.http).getDocument();
				Elements p = null;
				p = d.select(".left_links .left_box p");
				
				address = p.get(1).select("b label").text();
				try{
					if(d.select(".left_links .left_box").select("fieldset").size()>1){
						range += "主营产品:";
						range += d.select(".left_links .left_box").select("fieldset").get(0).select(".typesList p").text();
					}
					
				}catch(Exception e){
					range = "";
				}
				
				try{
					if("".equals(location)){
						location = p.get(5).select("b label").text();
						if(location.contains("经营类型")){
							location = p.get(6).select("b label").text();
						}
						location = location.substring("所在地区:".length());
					}
				}catch(Exception e){
					location = "";
				}
				
			}catch(Exception e){
				address="";
			}
			
			
			map.put("company", company);
			map.put("address", address);
			map.put("tel", tel);
			map.put("location", location);
			map.put("range", range);
			String json = Tools.toJson(map);
			System.out.println(json);
			DAOFactory.getIFileDAOInstance(getClass().getName()+"/全国物流货运名录.txt").FileOutputStream(json+"\r\n", true);
		
		}
		return true;
	}
	
	public static String getTel(String s){
		
		s = s.replace("getTels(", "").replace(");", "");
		int id = Integer.parseInt(s.split(",")[0]);
		int type = Integer.parseInt(s.split(",")[1]);
		String url = "http://www.wltag.com/common/getTels.php?lang=cn&id="+id+"&type="+type;
		String tel = "";
		try{
			GrapWltag gx = new GrapWltag(url);
			gx.http.setUrl(url);
			tel = DAOFactory.getIHttpDAOInstance(gx.http).getDocument().select("body").html();
		}catch(Exception e){
			DAOFactory.getIFileDAOInstance("/获取电话异常.txt").FileOutputStream(s, true);
		}
		tel.replaceAll("\\s+", "");
	
		return tel;
	}
	
	
	
}
