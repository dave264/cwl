package com.cwl.parse.source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cwl.parse.tools.Parse;
import com.cwl.parse.tools.Tools;

/**
 * 林安物流
 * @author Mr.chen
 *
 */
public class LinAnWuLiu {

	public static List<Map<String,String>> parse(String info,String source) {
		
		List<Map<String,String>> list = null;
		Map<String,Object> map = null;
		
		list = new ArrayList<Map<String,String>>();
		
		String s = info.split("[$]")[3];
		Matcher m = Pattern.compile("\\d+").matcher(s);
		if(m.find()) {
			String json = info.substring(info.split("[$]")[0].length()+3+info.split("[$]")[1].length()+info.split("[$]")[2].length()+m.group().length());
			map = Tools.toMap(json);
			String company = (String)map.get("customerName");
			String tel = (String)map.get("mobile");
			
			company = company.replace("、", "	").replace("，",",").replace(",", "	");
			company = Parse.getCompany(company);
			try{
				String[] telArr = tel.split("\\s");
				for(int i=0; i<telArr.length; i++) {
					Map<String,String> e = new HashMap<String,String>();
					telArr[i] = Parse.getTel(telArr[i]);
					e.put("company_name", company);
					e.put("tel", telArr[i]);
					e.put("source", source);
					list.add(e);
				}
			} catch(Exception ex) {
				Map<String,String> e = new HashMap<String,String>();
				tel = Parse.getTel(tel);
				e.put("company_name", company);
				e.put("tel", tel);
				e.put("source", source);
				list.add(e);
			}	
		}
		
		return list;
		
	}
}
