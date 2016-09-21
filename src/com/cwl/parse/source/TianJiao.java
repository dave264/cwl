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
				.replace("��", "	")
				.replace("��", "	")
				.replace("��", "	")
				.replace(".", "	")
				.replace("��", "	")
				.replace("��", "-")
				.replace("��", "	")
				.replace("��","(")
				.replace("��", ")")
				.replace(",", "	")
				.replace("?", "	")
				.replace("��", ":")
				.replaceAll("[(][\\W\\s]+[)]", "")
				.replaceAll("[-]{2,}", "-")
				.replaceAll("[\\s]+", "	")
				.trim();//ȥ��β�ո�
		TianJiao.source = source;
		return str;
	}
	
	/**
	 * ��ʼ���绰
	 * @param s
	 * @return
	 */
	public static String initTel(String s) {
	
		String tel = "";
		tel = s.replace("l", "1")
				.replace("��", "")
				.replace("O","0")
				.replaceAll("[,]", "")
				.replaceAll("^[?]", "")
				.replaceAll("[-]\\s+", "-")
				.replaceAll("\\s+", "")
				.trim();
		return tel;
	}
	
	/**
	 * �콾����
	 * @param s
	 */
	public static List<Map<String,String>> parse(String info,String source) {
		
		if("".equals(source)||source == null) {
			return null;
		}
		
		
		List<Map<String,String>> list = null;
		info = init(info,source);
		int index = info.lastIndexOf("�绰:");
		if(index > 0 ) {
			info = info.substring(index+"�绰:".length());//��ȡ�绰�����ֵ���Ϣ
			String[] x = info.trim().split("\\s");//���ո���
			list = parseInfo(x);
		}
		return list;
	}
	
	/**
	 * ������Ϣ
	 * @param x
	 */
	public static List<Map<String,String>> parseInfo(String[] x) {
		
		boolean flag = false;
		int telIndex = 0;
		
		List<Map<String,String>> list = null;
		if (x.length >= 2) {//���ٰ���1���绰��һ����˾����
			list = new ArrayList<Map<String,String>>();
			int telCount =0;
			String company = "";
			for (int i=0; i<x.length; i++) {
				x[i] = initTel(x[i]);//��ʼ���绰
				Matcher m = Pattern.compile("(\\d{3,4}(-|����|��)?\\d{7,8})|([(]\\d{3,4}[)]\\d{7,8})|(\\d{7,})").matcher(x[i]);
				if(m.find()) {//���ַ��д��ڵ绰��ʽ,����,-
					String li = m.group().trim();
					if(li.length() >=7) {//�绰���ȴ���7
						x[i] = li;
						Matcher m1 = Pattern.compile("\\d{3,4}(-|����)?\\d{11}").matcher(x[i]);
						if(m1.matches()){
							x[i] = x[i].replaceAll("\\d{3,4}(-|����)?", "");
						}
						x[i] = x[i].replaceAll("\\s+", "");
						x[i] = x[i].replaceAll("[-|����]", "");
						x[i] = Parse.getTel(x[i]);
						telCount ++;
						if(!flag) {
							flag = true;
							telIndex = i;
						}
						System.out.println("�绰��"+x[i]);
					} else{//�绰����С��7
						x[i] = Parse.getCompany(x[i]);
						company += x[i] +"	";
						System.out.println("��ҵ:"+x[i]);
					}
					
//						String temp = x[i].replaceAll("[(\\u4e00-\\u9fa5)]", "").replaceAll("\\s+", "").trim();//�������ĺͿո���ôʣ�µ�ֻʣ������
				} else{
					x[i] = Parse.getCompany(x[i]);
					company += x[i] +"	";
					System.out.println("��ҵ:"+x[i]);
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
