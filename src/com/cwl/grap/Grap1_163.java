package com.cwl.grap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cwl.factory.DAOFactory;
import com.cwl.parents.Home;
import com.cwl.tool.Tools;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Grap1_163 extends Home {

	public Grap1_163(String url) {
		super(url);
		
	}

	public static void main(String[] args) {
		
		Grap1_163 g = new Grap1_163("http://1.163.com");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = null;
		for(int m=1; m<=12; m++) {
			String url = "http://1.163.com/list/0-0-1-"+m+".html";
			g.http.setUrl(url);
			Document doc = null;
			try {
				doc = DAOFactory.getIHttpDAOInstance(g.http).getDocument();
				Elements ul = doc.select("#goodsList").select(".w-quickBuyList-item");
				for(int i=0; i<ul.size(); i++) {
					map = new HashMap<String,Object>();
					Element e = ul.get(i);
					try{
						String href = e.select(".w-goods-pic a").get(0).attr("href");
						href = href.replace("/detail/", "").replace(".html", "");
						String[] params = href.split("[-]");
						String gid = params[0];
						String period = params[1];
						map.put("gid", gid);
						map.put("period", period);
						list.add(map);
						System.out.println(JSONObject.fromObject(map).toString());
					}catch(Exception ex) {
						continue;
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String json = JSONArray.fromObject(list).toString();
		DAOFactory.getIFileDAOInstance("e://1.163.txt").FileOutputStream(json, true);
	}

}
