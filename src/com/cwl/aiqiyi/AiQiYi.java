package com.cwl.aiqiyi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.cwl.factory.DAOFactory;
import com.cwl.parents.Home;

public class AiQiYi extends Home{

	public static List<Map<String,String>> list = null;
	
	public AiQiYi(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		list = new ArrayList<Map<String,String>>();
//		int i=0;
//		while(true) {
			toDo();
//			i++;
//			Tools.sleep(120000);
//			if(list.size()>=10) {
//				break;
//			}
//		}
//		for(int m=0; m<list.size(); m++) {
//			String loginId = list.get(m).get("loginId");
//			String password = list.get(m).get("password");
//			String info = loginId+"\r\n"+password+"\r\n";
//			DAOFactory.getIFileDAOInstance("∞Æ∆Ê“’.txt").FileOutputStream(info, true);
//		}
		
	}
	
	public static  boolean isExist(String loginId) {
		
		for(int i=0; i<list.size(); i++) {
			String id = list.get(i).get("loginId");
			if(id == loginId || loginId.equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	public static void toDo() {
		AiQiYi g = new AiQiYi("http://www.aiqiyi8.com/Controller/adminInfoController.php?do=tiqu");
		g.http.setMethod("POST");
		Map<String,String> header = new HashMap<String,String>();
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("code","113355"));
		header.put("Content-Type","application/x-www-form-urlencoded");
		g.http.setParameter(param);
		g.http.setHeader(header);
		Document doc = null;
		try {
			doc = DAOFactory.getIHttpDAOInstance(g.http).getDocument();
			Elements tr = doc.select("tbody tr");
			for(int i=0; i<tr.size(); i++) {
				Elements td = tr.get(i).select("td");
				Map<String,String> map = new HashMap<String,String>();
				String loginId = td.get(0).text();
				String password = td.get(1).text();
				map.put("loginId", loginId);
				map.put("password", password);
				String info = loginId+"\r\n"+password+"\r\n";
				System.out.println(info);
				DAOFactory.getIFileDAOInstance("∞Æ∆Ê“’.txt").FileOutputStream(info, true);
				if(!isExist(loginId)) {
					list.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
