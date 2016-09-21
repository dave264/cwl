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
 * 51手机端	文件解析
 * @author Mr.chen
 *
 */
public class Phone51 {

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
			String company =(String)map.get("Linkman");
			String tel = (String)map.get("phone");
			String phone = (String)map.get("custom_phone");
			
			company = company.replaceAll("\\s+", "").trim();
			tel = tel.replace("|", "	").replaceAll("\\s+", "	").trim();
			phone = phone.replace("|", "	").replaceAll("\\s+", "	");
			company = Parse.getCompany(company);
			
			
			boolean flag1 = false;
			boolean flag2 = false;
			if(!"".equals(phone)&& phone != null) {
				String[] phoneArr = phone.split("\\s");
				for (int i=0; i< phoneArr.length; i++) {
					Map<String,String> e  = new HashMap<String,String>();
					phoneArr[i] = Parse.getTel(phoneArr[i]);
					e.put("company_name", company);
					e.put("tel", phoneArr[i]);
					e.put("source", source);
					list.add(e);
				}
				flag1 = true;
			}
			if(!"".equals(tel)&&tel != null) {
				String[] telArr = tel.split("\\s");
				for(int i=0; i<telArr.length; i++) {
					Map<String,String> e  = new HashMap<String,String>();
					telArr[i] = Parse.getTel(telArr[i]);
					e.put("company_name", company);
					e.put("tel", telArr[i]);
					e.put("source", source);
					list.add(e);
				}
				flag2 = true;
			}
			if(!flag1&&!flag2) {
				list = null;
			}
			System.out.println(source+"--->"+company+"--->"+tel);
		}
		return list;
	}
}
