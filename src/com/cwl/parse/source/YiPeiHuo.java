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
 * Ò×Åä»õ½âÎö
 * @author Mr.chen
 *
 */
public class YiPeiHuo {
	
	public static List<Map<String,String>> parse(String info,String source) {
		
		
		if("".equals(source)||source == null) {
			return null;
		}
		
		
		
		List<Map<String,String>> list = null;
		
		list = new ArrayList<Map<String,String>>();
		String s = info;
		String str = s.split("[$]")[3];
		Matcher m = Pattern.compile("\\d+").matcher(str);
		if(m.find()) {
			String json = s.substring(s.split("[$]")[0].length()+s.split("[$]")[1].length()+s.split("[$]")[2].length()+3+m.group().length());
			Map<String,Object> map = Tools.toMap(json);
			String company =(String)map.get("realName");
			String tel = (String)map.get("tradeMobileNumber");
			String phone = (String)map.get("tradeTelephoneNumber");
			
			company = company.replaceAll("\\s+", "	").trim();
			tel = tel.replaceAll("\\s+", "	").trim();
			phone = phone.replaceAll("\\s+", "	").trim();
			company = Parse.getCompany(company);
			String[] telArr = tel.split("\\s");
			String[] phoneArr = phone.split("\\s");
			if(telArr.length > 1) {
				for(int i=0; i< telArr.length; i++) {
					Map<String,String> e  = new HashMap<String,String>();
					telArr[i] = Parse.getTel(telArr[i]);
					e.put("company_name", company);
					e.put("tel", telArr[i]);
					e.put("source", source);
					list.add(e);
					System.out.println(source+"--->"+company+"--->"+tel);
				}
			} else {
				if(!"".equals(tel) && tel != null) {
					Map<String,String> e  = new HashMap<String,String>();
					tel = Parse.getTel(tel);
					e.put("company_name", company);
					e.put("tel", tel);
					e.put("source", source);
					list.add(e);
					System.out.println(source+"--->"+company+"--->"+tel);
				}
			}
			
			if(phoneArr.length >1) {
				for(int i=0; i< phoneArr.length; i++) {
					Map<String,String> e  = new HashMap<String,String>();
					phoneArr[i] = Parse.getTel(phoneArr[i]);
					e.put("company_name", company);
					e.put("tel", phoneArr[i]);
					e.put("source", source);
					list.add(e);
					System.out.println(source+"--->"+company+"--->"+tel);
				}
			} else{
				if(!"".equals(phone)&& phone != null) {
					Map<String,String> e  = new HashMap<String,String>();
					phone = Parse.getTel(phone);
					e.put("company_name", company);
					e.put("tel", phone);
					e.put("source", source);
					list.add(e);
					System.out.println(source+"--->"+company+"--->"+tel);
				}
			}

			
		}
		return list;
	}
}
