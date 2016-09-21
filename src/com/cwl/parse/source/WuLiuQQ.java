package com.cwl.parse.source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cwl.factory.DAOFactory;
import com.cwl.parse.tools.Tools;

/**
 * ����QQ�ļ�����
 * @author Mr.chen
 *
 */
public class WuLiuQQ {
	
	public static List<Map<String,String>> parse(String info,String source) {
		
		
		if("".equals(source)||source == null) {
			return null;
		}
		
		
		
		List<Map<String,String>> list = null;
		Map<String,String> map = null;
		boolean flag = false;
		int telCount = 0;
		int telIndex = 0;
		String company = "";
		String tel = "";
		String str = "";
		String s = info;
		list = new ArrayList<Map<String,String>>();
		try {
			 str= s.split("[$]")[3].split("[&]")[2];
		} catch(Exception e) {
			str = "";
		}
		
		String[] strArr = null;
		strArr = str.replaceAll("\\s+", "	")
						.replace("����","-")
						.replaceAll("[,]\\s+", ",")
						.trim()
						.split("[,|\\s]");
		for(int i=0; i< strArr.length; i++) {
			strArr[i] = strArr[i].replace("Tel:", "")
					.replace("l", "1")
					.replace("O", "0")
					.replace("��", "")
					.replace("?","")
					.replace("~", "-")
					.replace(".", "")
					.replaceAll("[,]", "	")
					.replaceAll("^[?]", "")
					.replaceAll("[-]\\s+", "")
					.replaceAll("--", "")
					.replaceAll("\\s+", "	")
					.trim();
			Matcher m = Pattern.compile("(\\d{3,4}(-|����|��)?\\d{7,8})|([(]\\d{3,4}[)]\\d{7,8})|(\\d{7,})").matcher(strArr[i]);
			if(m.find()) {
				String temp = m.group();
				if(m.matches()) {
					if(temp.length() >=7) {//���ֳ��ȴ���7
						if(!flag) {
							flag = true;
							telIndex = i;
						}
						telCount++;
						strArr[i] = temp;//���õõ��绰
						System.out.println("�绰:"+strArr[i]);
					}
				}else{//�������ֵ����ǵ绰
					if(temp.length() >=7) {
						if(!flag) {
							flag = true;
							telIndex = i;
						}
						telCount++;
						strArr[i] = temp;//���õõ��绰
						System.out.println("�绰:"+strArr[i]);
					}else{
						if(temp.equals(strArr[i].replace("-", ""))) {
							System.out.println("�绰����С��7��");
							DAOFactory.getIFileDAOInstance(Tools.getPath("�쳣")+"��"+source+"���绰����С��7.txt").FileOutputStream(info+"\r\n", true);
						}else{
							if(!"".equals(strArr[i])&&strArr[i] != null) {
								if(!Pattern.compile("\\d{3,4}[-]\\d").matcher(strArr[i]).matches()) {
									company += strArr[i] + "	";
									System.out.println("��ҵ:"+strArr[i]);
								}	
							}
						}
						
					}
				}
			}else{
				if(!"".equals(strArr[i])&&strArr[i] != null) {
					if(Pattern.compile("\\d+").matcher(strArr[i]).matches()) {
						System.out.println("�绰����С��7��");
						DAOFactory.getIFileDAOInstance(Tools.getPath("�쳣")+"��"+source+"���绰����С��7.txt").FileOutputStream(info+"\r\n", true);
					} else{
						company += strArr[i] + "	";
						System.out.println("��ҵ:"+strArr[i]);
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
				tel = strArr[j];
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
