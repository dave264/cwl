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
 * �����������
 * @author Mr.chen
 *
 */
public class ChuangBang {
	
	/**
	 * �����������   ����
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
		
		int index = info.lastIndexOf("�绰:");
		
		if(index > 0 ) {
			info = info.substring(index+"�绰:".length());//��ȡ�绰�����ֵ���Ϣ
			info = info.replaceAll("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}", "").replace("һ", "-");
			info = info.replaceAll("\\s+", "	");
			Matcher mm = Pattern.compile("\\W\\s\\W\\s\\W").matcher(info);
			if(mm.find()) {
				company = mm.group().replaceAll("\\s+", "");
				company = Parse.getCompany(company);
				System.out.println("��ҵ:"+company);
			}
			String[] x = info.split("\\s");//���ո���
			if(x.length >=1) {
				list = new ArrayList<Map<String,String>>();
				int telCount =0;
				
				for (int i=0; i< x.length; i++) {
					x[i] = x[i].replace("l", "1")
							.replace("��", "")
							.replace("O","0")
							.replace("--", "-")
							.replaceAll("^[,]", "")
							.replaceAll("^[?]", "")
							.replaceAll("[����]|[��]", "-")
							.replaceAll("\\s+", "	")
							.trim();
					Matcher m = Pattern.compile("(\\d{3,4}(-|����|��)?\\d{7,8})|([(]\\d{3,4}[)]\\d{7,8})|(\\d{7,})").matcher(x[i]);
					if(m.find()) {//�ҵ�����
						String temp = m.group();//��ȡ����
						if(temp.length() >=7) {//�绰
							if(!flag) {
								flag = true;
								telIndex = i;
							}
							temp = Parse.getTel(temp);
							x[i] = temp;
							telCount++;
							System.out.println("�绰:"+x[i]);
						}else{//�̺Ż�˾
							x[i] = x[i].trim();
							if(!temp.equals(x[i])){
								x[i] = Parse.getCompany(x[i]);
								company +=x[i] + "	";
								System.out.println("��ҵ:"+x[i]);
							}
						}
					}else {//��˾
						if(!"".equals(x[i])&&x[i] != null) {
							Matcher mx = Pattern.compile("��([\\u4e00-\\u9fa5\\s]+)��").matcher(x[i]);
							if(mx.find()) {
								x[i] = mx.group().replace("��", "").replace("��", "");
							}
							x[i] = Parse.getCompany(x[i]);
							company += x[i] + "	";
							System.out.println("��ҵ:"+x[i]);
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
				.replaceAll("[-]\\s+", "-")
				.trim();
		return str;
	}
}
