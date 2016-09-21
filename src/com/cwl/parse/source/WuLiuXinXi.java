package com.cwl.parse.source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cwl.factory.DAOFactory;
import com.cwl.parse.tools.Parse;
import com.cwl.parse.tools.Tools;

/**
 * ������Ϣ��
 * @author Mr.chen
 *
 */
public class WuLiuXinXi {

	
	
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
				.replace("��ͨ", "")
				.replace("�ƶ�", "")
				.replace("С��", "")
				.replaceAll("[,]", "")
				.replaceAll("^[?]", "")
				.replaceAll("[-]\\s+", "-")
				.replaceAll("\\s+", "")
				.replaceAll("[����]|[��]", "-")
				.trim();
		return tel;
	}
	
	/**
	 * ��ʼ����ʽ
	 * @param str
	 * @return
	 */
	public static String init(String str) {
		
		str = str.replace(" ","	")
				.replace("/", "	")
				.replace("��", "	")
				.replace("��", "	")
				.replace("��", "	")
				.replace(".", "	")
				.replace("����", "-")
				.replace("��", "-")
				.replace("��", "	")
				.replace("��","(")
				.replace("��", ")")
				.replace("��", "[")
				.replace("��", "]")
				.replace(",", "	")
				.replace("?", "	")
				.replaceAll("[-]\\s+", "-")
				.replaceAll("[(][\\W\\s]+[)]", "")
				.replaceAll("\\[\\W+\\]", "")
				.replaceAll("-\\W+ר��", "")
				.replace("����:", "")
				.replaceAll("[-]{2,}", "-")
				.replaceAll("\\s+", "	")
				.trim();//ȥ��β�ո�
		return str;
	}
	
	/**
	 * ����������Ϣ��
	 * @param info
	 * @param source
	 */
	public static List<Map<String,String>> parse(String info,String source) {
		
		if("".equals(source)||source == null) {
			return null;
		}
		
		boolean flag = false;
		int telIndex = 0;
		String str = info;
		List<Map<String,String>> list = null;
		String company = "";
		info = init(info);
		int index = info.lastIndexOf("�绰:");
		if(index > 0) {
			info = info.substring(index+"�绰:".length());//��ȡ�绰�����ֵ���Ϣ
			String[] x = info.replace("��", "	").replaceAll("\\s+", "	").split("\\s");//���ո���
			if (x.length >= 2) {//���ٰ���1���绰��һ����˾����
				list = new ArrayList<Map<String,String>>();
				int telCount =0;
				
				for (int i=0; i<x.length; i++) {
					x[i] = initTel(x[i]).trim();
					Matcher m6 = Pattern.compile("\\d{3,4}-\\d{11}").matcher(x[i]);
					if(m6.find()){
						x[i] = m6.group().replaceAll("\\d{3,4}-","");
					}
					Matcher m = Pattern.compile("(\\d{3,4}(-|����|��)?\\d{7,8})|([(]\\d{3,4}[)]\\d{7,8})|(\\d{7,})").matcher(x[i]);
					if (m.find()) {
						String temp = m.group();
						if(m.matches()) {
							if(temp.length() >=7) {
								x[i] = m.group();
								Matcher m1 = Pattern.compile("\\d{3,4}-\\d{11}").matcher(x[i]);
								if(m1.matches()){
									x[i] = x[i].replaceAll("\\d{3,4}-", "");
								}
								x[i] = x[i].trim();
								x[i] = x[i].replaceAll("\\s+", "");
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
								x[i] = Parse.getTel(x[i]);
								if(!flag) {
									flag = true;
									telIndex = i;
								}
								System.out.println("�绰��"+x[i]);
							} else if(temp.length() > 0) {
								if(Pattern.compile("\\[\\d+\\]").matcher(x[i]).find()){
									DAOFactory.getIFileDAOInstance(Tools.getPath("�쳣")+"��"+source+"����˾��ʽ�쳣.txt").FileOutputStream(str+"\r\n", true);
								}else {
									Matcher m0 = Pattern.compile("\\d{1,6}").matcher(x[i]);
									if(m0.matches()) {
										System.out.println("��Ч���֡�����");
									}else{
										x[i] = x[i].replace("www", "");
										x[i] = Parse.getCompany(x[i]);
										company += x[i] +"	";
										System.out.println("��ҵ:"+x[i]);
									}
									
								}
							}
						}
							
					} else {
						
						x[i] = x[i].replaceAll("^[(]\\W+\\d+[)]$", "	");
						x[i] = x[i].replaceAll("^[(]\\W+[)]$", "");
						if(x[i].length() >0) {
							if(Pattern.compile("\\[\\d+\\]").matcher(x[i]).find()){
								DAOFactory.getIFileDAOInstance(Tools.getPath("�쳣")+"��"+source+"����˾��ʽ�쳣.txt").FileOutputStream(str+"\r\n", true);
							}else {
								Matcher m0 = Pattern.compile("\\d+(-|����)?\\d+|\\d+").matcher(x[i]);
								if(m0.matches()){
									System.out.println("��Ч����....");
								}else{
									x[i] = x[i].replaceAll("[(][\\W\\w]+[)]", "");
									x[i] = x[i].replace("www", "");
									x[i] = Parse.getCompany(x[i]);
									company += x[i] +"	";
									System.out.println("��ҵ:"+x[i]);
								}	
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
