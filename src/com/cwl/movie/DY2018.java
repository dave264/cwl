package com.cwl.movie;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.cwl.dao.IDBOperatorDAO;
import com.cwl.dao.ISearchDAO;
import com.cwl.factory.DAOFactory;
import com.cwl.parents.Home;

/**
 * 电影天堂
 * http://www.dy2018.com/
 * @author Mr.chen
 *
 */
public class DY2018 extends Home implements ISearchDAO {

	public DY2018(String url) {
		super(url);
	}

	@Override
	public boolean first(String url) {
		
		int pageSize = this.pageState(url);
		String href = "";
		for (int j=1; j<=pageSize; j++) {
			if (j == 1) {
				href = url;
			} else {
				href = url+"index_"+j+".html";
			}
			second(href);
		}
		return false;
	}

	@Override
	public boolean second(String url) {
		
		http.setUrl(url);
		try {
			Document doc = DAOFactory.getIHttpDAOInstance(http).getDocument();
			Elements ul = doc.select(".co_content8 ul table");
			for(int i=0; i< ul.size(); i++) {
				String href = ul.get(i).select("tbody tr .ulink").get(1).attr("href");
				initHttp(href);
				String link = DAOFactory.getIHttpDAOInstance(http).getDocument().select("#Zoom table tbody tr td").text();
				String source = "电影天堂";
				String[] links = link.split("\\s+");
				for (int x=0; x<links.length; x++) {
					String regex ="^ftp://[\\w\\W]+";
					Matcher m = Pattern.compile(regex).matcher(links[x]);
					if(m.find()) {
						System.out.println(links[x]);
//						String sql = "insert into movie(link,source) values('"+links[x]+"','"+source+"')";
//						((IDBOperatorDAO) DAOFactory.getIDBDAOInstance("127.0.0.1",3306,"cwl","root","123456").doMysql()).doCreate(sql);
						DAOFactory.getIFileDAOInstance("e://电影天堂.txt").FileOutputStream(links[x]+"\r\n", true);
					}	
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int pageState(String url) {
		
		int pageSize = 0;
		try {
			String regex = "\\d+/\\d+";
			String s = DAOFactory.getIHttpDAOInstance(http).getDocument().select(".co_content8 .x").get(0).html();
			Matcher m = Pattern.compile(regex).matcher(s);
			if(m.find()) {
				pageSize = Integer.parseInt(m.group().split("/")[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageSize;
	}

	public static void main(String[] args) {
		DY2018 g = new DY2018("http://www.dy2018.com");
		
		try {
			Elements ul = DAOFactory.getIHttpDAOInstance(g.http).getDocument().select("#menu").select("li");
			for (int i=1; i< ul.size()-3; i++) {
				String href = ul.get(i).select("a").attr("href");
				g.initHttp(href);
				String url = g.http.getUrl();
				g.first(url);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
