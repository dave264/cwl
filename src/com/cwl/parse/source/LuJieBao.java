package com.cwl.parse.source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cwl.parse.tools.Parse;


/**
 * 路捷宝    
 * @author Mr.chen
 *
 */
public class LuJieBao {

	/**
	 * 路捷宝   文件解析
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
		String company = "";
		String tel = "";
		if(m.find()) {
			String str = info.substring(((sArr[0]+sArr[1]+sArr[2]).length()+3+m.group().length()));
			str = str.replace(".,", "	");
			String[] strArr = str.split("\\s");
			int telIndex = 0;
			int telCount = 0;
			String telRegex = "(\\d{3,4}(-|——|—)?\\d{7,8})|([(]\\d{3,4}[)]\\d{7,8})|(\\d{7,})";
			boolean telFlag = false;
			for(int i=0; i< strArr.length; i++) {
				String temp = strArr[i];
				String[] tempArr = temp.split("[,]");
				if(tempArr.length>1) {
					company = Parse.getCompany(temp);
					tel = Parse.getTel(temp);
					if(!"".equals(tel)&&!"".equals(company)) {
						temp = tel;
					}
					if(Pattern.compile("([\\W|\\w]*[有|求|装][\\W|\\w]*[车|货])|(\\d+)").matcher(company).find()) {
						company = "";
					}
				}
				Matcher m1 = Pattern.compile(telRegex).matcher(temp);
				if(m1.find()) {
					if(!telFlag) {
						telIndex = i;
						telFlag = true;
					}
					strArr[i] = m1.group();
					strArr[i] = Parse.getTel(strArr[i]);
					telCount++;
				}	
			}
			company = company.trim();
			for(int i= telIndex; i< (telIndex+telCount); i++) {
				Map<String,String> e = new HashMap<String,String>();
				tel = strArr[i];
				e.put("tel", tel);
				e.put("company_name", company);
				e.put("source", source);
				list.add(e);
			}
		}
		return list;
	}
}
