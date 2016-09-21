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

public class HuoYunWang {

	/**
	 * �й�����������
	 * @param info
	 * @param source
	 * @return
	 */
	public static List<Map<String,String>> parse(String info,String source) {
		
		
		if("".equals(source)||source == null) {
			return null;
		}
		
		List<Map<String,String>> list = null;
		Map<String,String> map = null;
		
		String s = info;
		String telStr = "";
		String tel = "";
		boolean flag = false;
		int telIndex = 0;
		int telCount = 0;
		String company = "";
		
		list = new ArrayList<Map<String,String>>();
		String[] strArr = s.split("[&]");
		int len = strArr.length;
		telStr = strArr[len-2];		
		telStr = telStr.replace(",", "	")
					.replace("��", "	")
					.replace("����", "	")
					.replace("��","	")
					.replace(".","	")
					.replace("��", "	")
					.replaceAll("\\s+", "	")
					.trim();
		
		String[] telArr = telStr.split("\\s");
		for(int i=0; i< telArr.length; i++) {
			map = new HashMap<String,String>();
			telArr[i] = telArr[i].replace("Tel:", "");
			telArr[i] = telArr[i].replace("l", "1");
			telArr[i] = telArr[i].replace("O", "0");
			telArr[i] = telArr[i].replace("��", "");
			telArr[i] = telArr[i].replace("?","");
			telArr[i] = telArr[i].replace("~", "-");
			telArr[i] = telArr[i].replaceAll("[-]{2,}\\W{0,7}", "");
			telArr[i] = telArr[i].replaceAll("[,]", "	");
			telArr[i] = telArr[i].replaceAll("^[?]", "");
			telArr[i] = telArr[i].replaceAll("[-]\\s+", "");
			telArr[i] = telArr[i].replaceAll("--", "");
			telArr[i] = telArr[i].replaceAll("\\s+", "	");
			telArr[i] = telArr[i].replace("Tel:", "");
			telArr[i] = telArr[i].replace("0455o", "");
			telArr[i] = telArr[i].trim();
			
			Matcher m = Pattern.compile("(\\d{3,4}(-|����|��)?\\d{7,8})|([(]\\d{3,4}[)]\\d{7,8})|(\\d{7,})").matcher(telArr[i]);
			if(m.find()) {//�ҵ�����
				String temp = m.group().trim();//��ȡ����
				if(m.matches()) {
					if(temp.length() >=7) {//���ֳ��ȴ���7
						if(!flag) {
							flag = true;
							telIndex = i;
						}
						telCount++;
						temp = Parse.getTel(temp);
						telArr[i] = temp;//���õõ��绰
						System.out.println("�绰:"+telArr[i]);
					}
				}else{//�������ֵ����ǵ绰
					if(temp.length() >=7) {
						if(!flag) {
							flag = true;
							telIndex = i;
						}
						telCount++;
						temp = Parse.getTel(temp);
						telArr[i] = temp;//���õõ��绰
						System.out.println("�绰:"+telArr[i]);
					}else{
						if(temp.equals(telArr[i].replace("-", ""))) {
							System.out.println("�绰����С��7��");
							DAOFactory.getIFileDAOInstance(Tools.getPath("�쳣")+"��"+source+"���绰����С��7.txt").FileOutputStream(info+"\r\n", true);
						}else{
							if(!"".equals(telArr[i])&&telArr[i] != null) {
								strArr[i] = strArr[i].replaceAll("[(]\\W+[)]", "");
								strArr[i] = Parse.getCompany(strArr[i]);
								company += telArr[i] + "	";
								System.out.println("��ҵ:"+telArr[i]);
							}
						}
						
					}
				}
				
			} else{
				if(!"".equals(telArr[i])&&telArr[i] != null) {
					if(Pattern.compile("\\d+").matcher(telArr[i]).matches()) {
						System.out.println("�绰����С��7��");
						DAOFactory.getIFileDAOInstance(Tools.getPath("�쳣")+"��"+source+"���绰����С��7.txt").FileOutputStream(info+"\r\n", true);
					} else{
						try{
							strArr[i] = strArr[i].replaceAll("[(]\\W+[)]", "");
						} catch(Exception e) {
							
						}
						telArr[i] = Parse.getCompany(telArr[i]);
						company += telArr[i] + "	";
						System.out.println("��ҵ:"+telArr[i]);
					}
				}
			}
			
			
		}
		
		company = company.trim();
		
		if(company.split("\\s").length >2) {
			String[] c = company.split("\\s");
			for(int p=0; p<c.length; p++) {
				Matcher m = Pattern.compile("\\W+[����|����|��˾]\\W*").matcher(c[p]);
				if(m.matches()) {
					company = m.group();
					company = company.trim();
					break;
				}
			}
		}
		
		if(company != null && !"".equals(company)) {
			for(int j=telIndex; j< (telIndex+telCount); j++) {
				map = new HashMap<String,String>();
				tel = telArr[j];
				String company_name = company.trim();
				map.put("tel", tel);
				map.put("source", source);
				map.put("company_name", company_name);
				System.out.println(tel+company_name+source);
				list.add(map);
			}
		} else {
			DAOFactory.getIFileDAOInstance(Tools.getPath("�쳣")+"��"+source+"����˾����Ϊ��.txt").FileOutputStream(info+"\r\n", true);
			return null;
		}
		
		return list;
	}
}
