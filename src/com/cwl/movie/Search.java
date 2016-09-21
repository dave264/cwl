package com.cwl.movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cwl.factory.DAOFactory;
import com.cwl.parents.Home;
import net.sf.json.JSONObject;

public class Search extends Home {

	public static List<Map<String,Object>> list = null;
	/**
	 * ¥≈¡¶¡¥Ω”
	 * @param url   
	 * http://cililian.me/
	 */
	public Search(String url) {
		super(url);
		
	}
	
	public static void main(String[] args) {
		
		list = new ArrayList<Map<String,Object>>();
		String keywords = "ª–«æ»‘Æ";
		int page = 1;
		String url = "http://cililian.me/list/"+keywords+"/"+page+".html";
		Search g = new Search(url);
		Document doc = null;
		while(true){
			url = "http://cililian.me/list/"+keywords+"/"+page+".html";
			page ++;
			System.out.println(url);
			g.http.setUrl(url);
			try {
				doc = DAOFactory.getIHttpDAOInstance(g.http).getDocument();
				try{
					Elements e = g.getLi(doc);
					if(e==null){
						break;
					}
					for(int i=0; i<e.size(); i++) {
						Element ex = e.get(i);
						list.add(g.getInfo(ex));
					}
				}catch(Exception e){
					break;
				}
				
			} catch (Exception e) {
				break;
			}
		}
		
	}
	
	public Elements getLi(Document doc){
		
		Elements e = null;
		e = doc.select("ul.mlist li");
		return e;
	}
	
	public Map<String,Object> getInfo(Element e) {
		Map<String,Object> map = new HashMap<String,Object>();
		String title = e.select(".T1 a").text();
		map.put("title", title);
		String json = JSONObject.fromObject(map).toString();
		System.out.println(json);
		return map;
	}

}
