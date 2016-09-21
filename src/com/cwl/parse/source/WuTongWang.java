package com.cwl.parse.source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cwl.parse.tools.Parse;

/**
 * ÎïÍ¨Íø
 * @author Mr.chen
 *
 */
public class WuTongWang {

	/**
	 * 
	 * @param info
	 * @param source
	 * @return
	 */
	public static List<Map<String,String>> parse(String info,String source) {
		
		if("".equals(source)||source == null) {
			return null;
		}
		
		List<Map<String,String>> list = null;
		Map<String,String> map = null;
		String s = info.split("[$]")[3];
		String[] strArr = s.split("[&]");
		
		String tel = "";
		String company_name = "";
		String contact = "";
		Pattern p0 = Pattern.compile("Tel");
		Pattern p1 = Pattern.compile("CompanyName");
		Pattern p2 = Pattern.compile("Contact");
		
		for(int i=0; i< strArr.length; i++) {
			if(p0.matcher(strArr[i]).find()) {
				tel = strArr[i].substring("Tel=".length());
				tel = Parse.getTel(tel);
				continue;
			}
			if(p1.matcher(strArr[i]).find()) {
				company_name = strArr[i].substring("CompanyName=".length());
				company_name = company_name.trim();
				company_name = Parse.getCompany(company_name);
				continue;
			}
			if(p2.matcher(strArr[i]).find()) {
				contact = strArr[i].substring("Contact=".length());
				contact = contact.trim();
				contact = Parse.getCompany(contact);
				continue;
			}
		}
		Matcher m = Pattern.compile("[(\\u4e00-\\u9fa5)]+").matcher(company_name);
		if(m.find()) {
			company_name = m.group();
			company_name = Parse.getCompany(company_name);
		}
		company_name = company_name+"	"+contact;
		company_name = company_name.trim();
		map = new HashMap<String,String>();
		list = new ArrayList<Map<String,String>>();
		map.put("tel", tel);
		map.put("company_name", company_name);
		map.put("source", source);
		list.add(map);
		return list;
	}
}
