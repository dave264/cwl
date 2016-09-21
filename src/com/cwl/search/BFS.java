package com.cwl.search;

import java.util.List;
import java.util.Map;

import com.cwl.factory.DAOFactory;


/**
 * ¹ã¶ÈËÑË÷Ëã·¨
 * @author Mr.chen
 *
 */
public class BFS {
	
	public static List<Map<String,String>> link = null;
	
	public static void find(String url) {
		List<Map<String,String>> list = null;
		list = HttpRequest.get(url);
		if( list != null) {
			for(Map<String,String> map : list) {
				String title = map.get("title");
				String href = map.get("href");
				String a = "<a href=\""+href+"\">"+title+"</a><br />";
				if(title != null && !"".equals(title)) {
					DAOFactory.getIFileDAOInstance("d://news.html").FileOutputStream(a, true);
					System.out.println("title--->"+title);
					System.out.println("href---->"+href);
				}
				
			}
		}	
	}
}
