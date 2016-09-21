package com.cwl.parse.source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cwl.parse.tools.Parse;

public class TianJiao {
	
	public static String company_name = "";
	public static String tel = "";
	public static String source = "";
	
	public static String init(String str,String source) {
		str = str.replace("/", "	")
				.replace("、", "	")
				.replace("；", "	")
				.replace("，", "	")
				.replace(".", "	")
				.replace("—", "	")
				.replace("－", "-")
				.replace("。", "	")
				.replace("（","(")
				.replace("）", ")")
				.replace(",", "	")
				.replace("?", "	")
				.replace("：", ":")
				.replaceAll("[(][\\W\\s]+[)]", "")
				.replaceAll("[-]{2,}", "-")
				.replaceAll("[\\s]+", "	")
				.trim();//去首尾空格
		TianJiao.source = source;
		return str;
	}
	
	/**
	 * 初始化电话
	 * @param s
	 * @return
	 */
	public static String initTel(String s) {
	
		String tel = "";
		tel = s.replace("l", "1")
				.replace("★", "")
				.replace("O","0")
				.replaceAll("[,]", "")
				.replaceAll("^[?]", "")
				.replaceAll("[-]\\s+", "-")
				.replaceAll("\\s+", "")
				.trim();
		return tel;
	}
	
	/**
	 * 天骄解析
	 * @param s
	 */
	public static List<Map<String,String>> parse(String info,String source) {
		
		if("".equals(source)||source == null) {
			return null;
		}
		
		
		List<Map<String,String>> list = null;
		info = init(info,source);
		int index = info.lastIndexOf("电话:");
		if(index > 0 ) {
			info = info.substring(index+"电话:".length());//截取电话最后出现的信息
			String[] x = info.trim().split("\\s");//按空格拆分
			list = parseInfo(x);
		}
		return list;
	}
	
	/**
	 * 解析信息
	 * @param x
	 */
	public static List<Map<String,String>> parseInfo(String[] x) {
		
		boolean flag = false;
		int telIndex = 0;
		
		List<Map<String,String>> list = null;
		if (x.length >= 2) {//至少包含1个电话和一个公司名称
			list = new ArrayList<Map<String,String>>();
			int telCount =0;
			String company = "";
			for (int i=0; i<x.length; i++) {
				x[i] = initTel(x[i]);//初始化电话
				Matcher m = Pattern.compile("(\\d{3,4}(-|——|—)?\\d{7,8})|([(]\\d{3,4}[)]\\d{7,8})|(\\d{7,})").matcher(x[i]);
				if(m.find()) {//当字符中存在电话格式,数字,-
					String li = m.group().trim();
					if(li.length() >=7) {//电话长度大于7
						x[i] = li;
						Matcher m1 = Pattern.compile("\\d{3,4}(-|——)?\\d{11}").matcher(x[i]);
						if(m1.matches()){
							x[i] = x[i].replaceAll("\\d{3,4}(-|——)?", "");
						}
						x[i] = x[i].replaceAll("\\s+", "");
						x[i] = x[i].replaceAll("[-|——]", "");
						x[i] = Parse.getTel(x[i]);
						telCount ++;
						if(!flag) {
							flag = true;
							telIndex = i;
						}
						System.out.println("电话："+x[i]);
					} else{//电话长度小于7
						x[i] = Parse.getCompany(x[i]);
						company += x[i] +"	";
						System.out.println("企业:"+x[i]);
					}
					
//						String temp = x[i].replaceAll("[(\\u4e00-\\u9fa5)]", "").replaceAll("\\s+", "").trim();//过滤中文和空格，那么剩下的只剩纯数字
				} else{
					x[i] = Parse.getCompany(x[i]);
					company += x[i] +"	";
					System.out.println("企业:"+x[i]);
				}
			}
			company = company.trim();
			for (int j=telIndex; j<(telIndex+telCount); j++) {
				Map<String,String> map = new HashMap<String,String>();
				tel = x[j];
				company_name = company.trim();
				map.put("tel", tel);
				map.put("source", source);
				map.put("company_name", company_name);
				list.add(map);
			}
		}
		return list;
	}
	
}
