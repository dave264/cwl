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
 * 特运通
 * @author Mr.chen
 *
 */
public class TeYunTong {
	
	/**
	 * 特运通文件解析
	 * @param info
	 * @param source
	 * @return
	 */
	public static List<Map<String,String>> parse(String info,String source) {
		
		
		if("".equals(source)||source == null) {
			return null;
		}
		
		List<Map<String,String>> list = null;
		
		String s = info;
		s = s.split("[$]")[3];
		
		Matcher m = Pattern.compile("\\d+").matcher(s);
		if(m.find()) {
			list = new ArrayList<Map<String,String>>();
			String json = s.substring(m.group().length());
			Map<String,Object> map = Tools.toMap(json);
			String company =(String)map.get("nickName");
			String tel = (String)map.get("tel");
			String phone = (String)map.get("uploadCellPhone");
			company = init(company);
			company = company.replaceAll("[(]\\d{7,}[)]", "");
			company = company.replaceAll("\\s+", "	").trim();
			Matcher ma = Pattern.compile("<\\w+@\\w+\\scom>").matcher(company);
			Matcher mat = Pattern.compile("[(]\\W+[)]").matcher(company);
			if(mat.find()) {
				company = company.replace(mat.group(), "");
			}
			if(ma.find()){
				company = company.replace(ma.group(),"");
			}
			Matcher m0 = Pattern.compile("[(\\u4e00-\\u9fa5)]+").matcher(company);
			if(m0.find()) {
				company = m0.group().replace("(", "").replace(")", "").replace("电话", "");
			}
			
			company = Parse.getCompany(company);
			tel = tel.replaceAll("\\s+", "	").trim();
			phone = phone.replaceAll("\\s+", "	").trim();
			String[] telArr = tel.split("\\s");
			String[] phoneArr = phone.split("\\s");
			if(telArr.length > 1) {
				for(int i=0; i< telArr.length; i++) {
					Map<String,String> e  = new HashMap<String,String>();
					Matcher m1 = Pattern.compile("\\d{3,4}(-|——)?\\d{11}").matcher(telArr[i]);
					if(m1.matches()){
						telArr[i] = telArr[i].replaceAll("\\d{3,4}(-|——)?", "");
					}
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
					Matcher m1 = Pattern.compile("\\d{3,4}(-|——)?\\d{11}").matcher(tel);
					if(m1.matches()){
						tel = tel.replaceAll("\\d{3,4}(-|——)?", "");
					}
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
					Matcher m1 = Pattern.compile("\\d{3,4}(-|——)?\\d{11}").matcher(phoneArr[i]);
					if(m1.matches()){
						phoneArr[i] = phoneArr[i].replaceAll("\\d{3,4}(-|——)?", "");
					}
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
					Matcher m1 = Pattern.compile("\\d{3,4}(-|——)?\\d{11}").matcher(phone);
					if(m1.matches()){
						phone = phone.replaceAll("\\d{3,4}(-|——)?", "");
					}
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
	
	
	/**
	 * 初始化格式
	 * @param str
	 * @return
	 */
	public static String init(String str) {
		
		str = str.replace("/", "	")
				.replace("、", "	")
				.replace("；", ";")
				.replace(";", "	")
				.replace("“", "")
				.replace("==", "")
				.replace("！", "!")
				.replace("，", ",")
				.replace("-","")
				.replace("＜", "<")
				.replace("《", "<")
				.replace("【", "[")
				.replace("】", "]")
				.replace("◆", "")
				.replace("ヾ", "")
				.replace("╮", "")
				.replace("》", ">")
				.replace("︶", "")
				.replace("ㄣ", "")
				.replace("︵", "")
				.replace("~", "")
				.replace("ε", "")
				.replace("の", "")
				.replace("╰--", "")
				.replace("╰", "")
				.replace("╮", "")
				.replace("ぃ", "")
				.replace("→", "")
				.replace("〆", "")
				.replace("ぅ","")
				.replace("け","")
				.replace("♡", "")
				.replace("★", "")
				.replace("^_^", "")
				.replace("·", "")
				.replace("〓", "")
				.replace("゛", "")
				.replace("ˇ", "")
				.replace("～", "")
				.replace("┍", "")
				.replace("w","")
				.replace("♀", "")
				.replace("ñ", "")
				.replace("ヽ", "")
				.replace("ㄟ","")
				.replace("つ","")
				.replace("™", "")
				.replace("@", "")
				.replace("ω", "")
				.replace("ǒ", "")
				.replace("＊", "")
				.replace("ㄲ","")
				.replace("☆", "")
				.replace("む", "")
				.replace("ゎ", "")
				.replace("┄", "")
				.replace("&", "")
				.replace("：", "")
				.replace(":","")
				.replace("？","?")
				.replace("? ", "")
				.replaceAll("<[\\w.]+@qq.com>", "")
				.replaceAll("<\\w+qq\\scom>", "")
				.replace(".", "	")
				.replace("——", "-")
				.replace("－", "-")
				.replace("。", "	")
				.replace("‘", "'")
				.replace("’", "'")
				.replace("（","(")
				.replace("）", ")")
				.replace(",", "	")
				.replace("?", "")
				.replaceAll("[?]", "	")
				.replace("!", "")
				.replace("'", "")
				.replace("((null))", "")
				.replaceAll("(\\d+)", "")
				.replace("2","")
				.replace("*", "")
				.replace("老二;", "")
				.replace("――高", "")
				.replace("f", "")
				.replaceAll("[\\s]+", "	")
				.replaceAll("[(][\\W\\d]+[)]", "")
				.replace("[", "")
				.replace("]", "")
				.trim();//去首尾空格
		return str;
	}
	
}
