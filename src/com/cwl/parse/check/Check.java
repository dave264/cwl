package com.cwl.parse.check;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Check {

	
	public static List<Map<String,String>> check(List<Map<String,String>> data) {
		
		List<Map<String,String>> list = null;
		if(data != null) {
			Map<String,String> map =  null;
			list = new ArrayList<Map<String,String>>();
			String company_name = "";
			String source = "";
			try {
				company_name = data.get(0).get("company_name");
			} catch(Exception e) {
				company_name = "";
			}
			try {
				source = data.get(0).get("source");
			} catch(Exception e) {
				source = "";
			}
			
			
			for(int i=0; i< data.size(); i++) {
				
				boolean flag = false;
		
				map = data.get(i);
				String tel = map.get("tel");
				
				if(Pattern.compile("\\d+[-]\\d+|\\d+|[(]\\d+[)]\\d+").matcher(tel).matches()&&tel.length()>=7) {
					flag = true;
				}
				
				String info = source+"$"+tel+"$"+company_name+"$$$$$$";
				System.out.println(info);
			
				if(flag) {
					list.add(data.get(i));
				}
			}
		}
		return list;
	}

}
