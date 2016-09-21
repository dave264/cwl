package com.cwl.parse.source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cwl.parse.tools.Parse;

/**
 * 万吉物流
 * @author Mr.chen
 *
 */
public class WanJiWuLiu {

	
	/**
	 * 万吉物流   文件解析
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
			String[] strArr = str.split("\\s");
			int telIndex = 0;
			int telCount = 0;
			String telRegex = "(\\d{3,4}(-|——|—)?\\d{7,8})|([(]\\d{3,4}[)]\\d{7,8})|(\\d{7,})";
			boolean telFlag = false;
			for(int i=0; i< strArr.length; i++) {
				String temp = strArr[i];
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
			String company = "";
			String tel = "";
			for(int i=(telIndex+telCount); i< strArr.length; i++) {
				String temp = strArr[i].replaceAll("\\s+", "	");
				temp = Parse.getCompany(temp);
				company += temp+"	";
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
