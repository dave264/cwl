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
 * 物流中国
 * @author Mr.chen
 *
 */
public class WuLiuZhongGuo {

	/**
	 * 物流中国  文件解析
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
		String str = "";
		if(m.find()) {
			str = info.substring(sArr[0].length()+sArr[1].length()+sArr[2].length()+3+m.group().length());
			Map<String, Object> map = Tools.toMap(str);
			String tel = (String)map.get("charTel");
			tel = tel.replace("[-]{1,}", "-");
			String[] telArr = tel.split("[,]");
			String company = (String)map.get("oldName");
			Matcher m1 = Pattern.compile("^[\\u4e00-\\u9fa5]+:").matcher(company);
			if(m1.find()){
				company = m1.group().replace(":", "");
				company = Parse.getCompany(company);
			}else{
				company = "";
			}
			for(int i=0; i<telArr.length; i++) {
				Map<String,String> e = new HashMap<String,String>();
				String Tel = Parse.getTel(telArr[i]);
				e.put("tel", Tel);
				e.put("company_name", company);
				e.put("source", source);
				list.add(e);
			}
			
		}
		return list;
	}
}
