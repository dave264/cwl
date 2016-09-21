package com.cwl.grap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.cwl.factory.DAOFactory;
import com.cwl.parents.Home;
import com.cwl.tool.Tools;

public class GrapWuliuz extends Home {

	public GrapWuliuz(String url) {
		super(url);
	}

	public static void main(String[] args) {
		
		String beginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		GrapWuliuz g = null;
		for(int i=1; i<= 71; i++) {
			String url = "http://www.wuliuz.com/gongsi/list_9_"+i+".html";
			g = new GrapWuliuz(url);
			System.out.println("------��"+i+"ҳ----");
			
			g.grap(url);
		}
		String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		Tools.timeCalculate(beginTime, endTime);
		
	}
	
	
	public boolean grap(String url) {
		
		String uri ="";
		Document doc = null;
		try {
			doc = DAOFactory.getIHttpDAOInstance(http).getDocument();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements tr = doc.select(".m-newlist .u-post");
	
		for(int i=0; i<tr.size(); i++) {
			System.out.println("----------"+i+"----------");
			uri= tr.get(i).select(".u-hd h3 a").attr("href");
//			run = new RunThread(gx, uri);
//			gx.fixedThreadPool.execute(run);//���̲߳���
//			run.run();//���߳�
			grapDetail(uri);
		}
		return true;
	}

	public boolean grapDetail(String url) {
		Document doc = null;
		Map<String,Object> map = null;
		try{
			http.setUrl(url);
			doc = DAOFactory.getIHttpDAOInstance(http).getDocument();
			map = new HashMap<String,Object>();
			String company = "";
			String address = "";
			String tel = "";
			String range = "";
			String line = "";
			
			Elements er = doc.select(".text #baseInfoWrapDom .baseInfoRight .biItem");
			Elements el = doc.select(".text #baseInfoWrapDom .baseInfoLeft .biItem");
			company = doc.select(".text h1.title").text();
			if("".equals(company)){
				return false;
			}
			
			for(int i=0; i<er.size(); i++){
				String title = er.get(i).select(".biTitle").text();
				String content = er.get(i).select(".biContent").html().replaceAll("<p>", "").replaceAll("</p>", "");
				if("��Ӫ��Χ".equals(title)){
					if("".equals(content)){
						range = "";
					}else{
						range = "��Ӫ��Χ��"+content;
					}	
				}
				if("��Ӫҵ��".equals(title)){
					if("".equals(content)){
						range = "";
					}else{
						range = "��Ӫҵ��"+content;
					}
					
				}
				if("��Ӫ��·".equals(title)){
					if("".equals(content)){
						line = "";
					}else{
						line = "��Ӫ��·��"+content;
					}
				}
			}

			for(int j=0; j<el.size(); j++) {
				String title = el.get(j).select(".biTitle").text();
				String content = el.get(j).select(".biContent").html().replaceAll("<p>", "").replaceAll("</p>", "");
				if("��˾��ַ".equals(title)||"�ܲ��ص�".equals(title)) {
					address = "��ַ="+content;
				}
				if("��˾�绰".equals(title)) {
					tel = content;
				}
			}
			
			map.put("company", company);
			map.put("address", address);
			map.put("tel", tel);
			map.put("line", line);
			map.put("range", range);
			String json  = Tools.toJson(map);
			DAOFactory.getIFileDAOInstance("d://Program Files/grap/wuliuz//���ջ�����Ϣ.txt").FileOutputStream(json+"\r\n", true);
			System.out.println(json);
		}catch(Exception e){
			return false;
		}

		return true;
	}

}
