package com.cwl.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cwl.factory.DAOFactory;
import com.cwl.parents.Home;

public class HttpRequest extends Home{
	
	public HttpRequest(String url) {
		super(url);
	}

	public static List<Map<String,String>> get(String url) {
		
		List<Map<String,String>> list = null;
		Map<String,String> map = null;
		Document doc = null;
		Elements elements = null;
		try {
			doc = DAOFactory.getIHttpDAOInstance(new HttpRequest(url).http).getDocument();
		} catch (Exception e) {
			doc = null;
		}
		list = new ArrayList<Map<String,String>>();
		if(doc != null) {
			elements = doc.select("a");
			if(elements != null) {
				for(Element e : elements) {
					map = new HashMap<String,String>();
					String href = e.attr("href");
					String title = e.text();
					map.put("href", href);
					map.put("title", title);
					list.add(map);
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
		return list;
	}
}
