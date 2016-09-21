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
 * 创邦冷藏物流
 * @author Mr.chen
 *
 */
public class ChuangBang {
	
	/**
	 * 创邦冷藏物流   解析
	 * @param info
	 * @param source
	 * @return
	 */
	public static List<Map<String,String>> parse(String info,String source) {
		
		if("".equals(source)||source == null) {
			return null;
		}
		
		boolean flag = false;
		int telIndex = 0;
		
		List<Map<String,String>> list = null;
		String company = "";
		info = Tools.getInfo(info);
		info = init(info);
		
		int index = info.lastIndexOf("电话:");
		
		if(index > 0 ) {
			info = info.substring(index+"电话:".length());//截取电话最后出现的信息
			info = info.replaceAll("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}", "").replace("一", "-");
			info = info.replaceAll("\\s+", "	");
			Matcher mm = Pattern.compile("\\W\\s\\W\\s\\W").matcher(info);
			if(mm.find()) {
				company = mm.group().replaceAll("\\s+", "");
				company = Parse.getCompany(company);
				System.out.println("企业:"+company);
			}
			String[] x = info.split("\\s");//按空格拆分
			if(x.length >=1) {
				list = new ArrayList<Map<String,String>>();
				int telCount =0;
				
				for (int i=0; i< x.length; i++) {
					x[i] = x[i].replace("l", "1")
							.replace("★", "")
							.replace("O","0")
							.replace("--", "-")
							.replaceAll("^[,]", "")
							.replaceAll("^[?]", "")
							.replaceAll("[——]|[—]", "-")
							.replaceAll("\\s+", "	")
							.trim();
					Matcher m = Pattern.compile("(\\d{3,4}(-|——|—)?\\d{7,8})|([(]\\d{3,4}[)]\\d{7,8})|(\\d{7,})").matcher(x[i]);
					if(m.find()) {//找到数字
						String temp = m.group();//获取数字
						if(temp.length() >=7) {//电话
							if(!flag) {
								flag = true;
								telIndex = i;
							}
							temp = Parse.getTel(temp);
							x[i] = temp;
							telCount++;
							System.out.println("电话:"+x[i]);
						}else{//短号或公司
							x[i] = x[i].trim();
							if(!temp.equals(x[i])){
								x[i] = Parse.getCompany(x[i]);
								company +=x[i] + "	";
								System.out.println("企业:"+x[i]);
							}
						}
					}else {//公司
						if(!"".equals(x[i])&&x[i] != null) {
							Matcher mx = Pattern.compile("《([\\u4e00-\\u9fa5\\s]+)》").matcher(x[i]);
							if(mx.find()) {
								x[i] = mx.group().replace("《", "").replace("》", "");
							}
							x[i] = Parse.getCompany(x[i]);
							company += x[i] + "	";
							System.out.println("企业:"+x[i]);
						}	
					}
				}
				company = company.trim();
			
				for(int j=telIndex; j< (telIndex+telCount); j++) {
					Map<String,String> map = new HashMap<String,String>();
					String tel = x[j];
					String company_name = company.trim();
					map.put("tel", tel);
					map.put("source", source);
					map.put("company_name", company_name);
					System.out.println(tel+company_name+source);
					list.add(map);
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
				.replace("；", "	")
				.replace("，", "	")
				.replace(".", "	")
				.replace("——", "-")
				.replace("－", "-")
				.replace("。", "	")
				.replace("（","(")
				.replace("）", ")")
				.replace(",", "	")
				.replace("?", "	")
				.replaceAll("[-]\\s+", "-")
				.trim();
		return str;
	}
}
