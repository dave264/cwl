package com.cwl.parse.source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cwl.parse.tools.Parse;

/**
 * ����ͨ����
 * @author Mr.chen
 *
 */
public class TianXiaTong {

	/**
	 * ��ʼ����ʽ
	 * @param str
	 * @return
	 */
	public static String init(String str) {
		
		str = str.replace("/", "	")
				.replace("��", "	")
				.replace("��", "	")
				.replace("��", "	")
				.replace(".", "	")
				.replace("����", "-")
				.replace("��", "-")
				.replace("��", "	")
				.replace("��","(")
				.replace("��", ")")
				.replace(",", "	")
				.replace("?", "	")
				.replace("^_^", "")
				.replaceAll("�绰:8888888", "")
				.replaceAll("[-]\\s+", "-")
				.replaceAll("\\W+��\\W+��", "	")
				.replaceAll("\\d+:\\d+|����\\d+:\\d+","")
				.replaceAll("[(][\\W\\s]+[)]", "")
				.replaceAll("[-]{2,}", "-")
				.replaceAll("���ź�\\d+", "	")
				.replaceAll("��ͨ\\d+", "	")
				.replaceAll("\\d{2,6}:", "")
				.replaceAll("\\d{2,6};", "	")
				.replaceAll("\\d{2,6}[)]", "	")
				.replaceAll("С��\\d+", "	")
				.replaceAll("����\\d+", "	")
				.replaceAll("����:\\d+", "	")
				.replaceAll("[\\s]+", "	")
				.trim();//ȥ��β�ո�
		return str;
	}
	
	public static List<Map<String, String>> parse(String info,String source) {
		
		if("".equals(source)||source == null) {
			return null;
		}
		
		boolean flag = false;
		int telIndex = 0;
		
		List<Map<String,String>> list = null;
		String company = "";
		info = init(info);
		int index = info.lastIndexOf("�绰:");
		
		if(index > 0) {
			info = info.substring(index+"�绰:".length());//��ȡ�绰�����ֵ���Ϣ
			
			String[] x = info.split("\\s");//���ո���
			if (x.length >= 1) {//���ٰ���1���绰��һ����˾����
				list = new ArrayList<Map<String,String>>();
				int telCount =0;
				
				for (int i=0; i<x.length; i++) {
					x[i] = x[i].replace("l", "1");//��l�滻��1
					x[i] = x[i].replace("��", "");//�����Ǻ�
					x[i] = x[i].replace("O","0");//��O�滻��0
					x[i] = x[i].replaceAll("^[,]", "");//���˿�ͷ,
					x[i] = x[i].replaceAll("^[?]", "");//���˿�ͷ?
					x[i] = x[i].replaceAll("[����]|[��]", "-");
					x[i] = x[i].replaceAll("\\s+", "");
//					x[i] = x[i].replaceAll("\\W+QQ\\d+\\s\\d+", "");
					x[i] = x[i].replaceAll("[QQ|qq][\\d]+","");
					x[i] = x[i].replaceAll("\\W+:\\d+", "");
					x[i] = x[i].trim();
					if(i==0) {
						x[i] = x[i].replaceAll("^\\W+", "");
					}
//					Matcher m = Pattern.compile("[-\\d]+|[(]\\d+[)]\\d+").matcher(x[i]);
					Matcher m = Pattern.compile("(\\d{3,4}(-|����|��)?\\d{7,8})|([(]\\d{3,4}[)]\\d{7,8})|(\\d{7,})").matcher(x[i]);
					if (m.find()) {
						String temp = x[i].replaceAll("\\W+", "").replaceAll("[a-zA-Z]", "");
						if(m.matches()) {
							if(temp.length() >=7) {
								x[i] = m.group();
								Matcher m1 = Pattern.compile("\\d{3,4}-\\d{11}").matcher(x[i]);
								if(m1.matches()){
									x[i] = x[i].replaceAll("\\d{3,4}-", "");
								}
								x[i] = x[i].trim();
								x[i] = x[i].replaceAll("\\s+", "");
								x[i] = x[i].replaceAll("[-|����]", "");
								x[i] = Parse.getTel(x[i]);
								telCount ++;
								if(!flag) {
									flag = true;
									telIndex = i;
								}
								System.out.println("�绰��"+x[i]);
							}
						} else{
							if(temp.length()>=7) {
								x[i] = temp;
								telCount ++;
								x[i] = x[i].replaceAll("[-|����]", "");
								x[i] = Parse.getTel(x[i]);
								if(!flag) {
									flag = true;
									telIndex = i;
								}
								System.out.println("�绰��"+x[i]);
							} else if(temp.length() > 0 && "".equals(company)) {
								Matcher m0 = Pattern.compile("[(\\u4e00-\\u9fa5)]+").matcher(x[i]);
								if(m0.find()){
									x[i] = m0.group();
									x[i] = Parse.getCompany(x[i]);
									company += x[i] +"	";
									System.out.println("��ҵ:"+x[i]);
								}
								
							}
						}
							
					} else {
						
						x[i] = x[i].replaceAll("^[(]\\W+\\d+[)]$", "	");
						x[i] = x[i].replaceAll("^[(]\\W+[)]$", "");
						if(x[i].length() >0 &&"".equals(company)) {
							Matcher m0 = Pattern.compile("[(\\u4e00-\\u9fa5)]+").matcher(x[i]);
							if(m0.find()){
								x[i] = m0.group();
								x[i] = Parse.getCompany(x[i]);
								Matcher m1 = Pattern.compile("[(\\u4e00-\\u9fa5)]+��˾").matcher(x[i]);
								if(m1.find()){
									x[i] = m1.group();
									x[i] = x[i].substring(0, x[i].indexOf("��˾")+2);
								}
								company += x[i] +"	";
								System.out.println("��ҵ:"+x[i]);
							}
						}	
					}
				}
				company = company.trim();
				
				for (int i=telIndex; i<(telIndex+telCount); i++) {
					Map<String,String> map = new HashMap<String,String>();
					String tel = x[i];
					String company_name = company.trim();
					map.put("tel", tel);
					map.put("source", source);
					map.put("company_name", company_name);
					System.out.println(tel+company_name+source);
					list.add(map);
				}
			}
		} else {
			list = null;
		}
		
		return list;
	}
}
