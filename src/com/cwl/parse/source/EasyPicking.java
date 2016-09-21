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
 * 配货易
 * @author Mr.chen
 *
 */
public class EasyPicking {

	
	/**
	 * 配货易   文件解析
	 * @param info
	 * @param source
	 * @return
	 */
	public static List<Map<String,String>> parse(String info,String source) {
		List<Map<String,String>> list = null;
		list = new ArrayList<Map<String,String>>();
		
		String s = info;
		String[] sArr = s.split("[$]");
		Matcher m = Pattern.compile("\\d+").matcher(sArr[3]);
		if(m.find()) {
			String str = info.substring(((sArr[0]+sArr[1]+sArr[2]).length()+3+m.group().length()));
			Map<String,Object> map = Tools.toMap(str);
			String tel = (String)map.get("phone");
			String company_name = (String)map.get("name");
			tel = Parse.getTel(tel);
			company_name = Parse.getCompany(company_name);
			Map<String,String> e = new HashMap<String,String>();
			e.put("tel", tel);
			e.put("company_name", company_name);
			e.put("source", source);
			list.add(e);
		}
		
		return list;
	}
}
