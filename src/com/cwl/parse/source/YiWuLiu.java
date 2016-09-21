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
 * �������ļ�����
 * @author Mr.chen
 * @version
 */
public class YiWuLiu {

	/**
	 * ������      ��������
	 * @param info
	 * @param srouce
	 * @return
	 */
	public static List<Map<String,String>> parse(String info,String source) {
		
		
		if("".equals(source)||source == null) {
			return null;
		}
		
		
		List<Map<String,String>> list = null;
		Map<String,String> map = null;
		
		String str = info;
		String[] strArr_0  = str.split("[$]");
		Matcher mx = Pattern.compile("\\d+").matcher(strArr_0[3]);
		String s = "";
		if(mx.find()) {
			try{
				s = info.substring((strArr_0[0]+strArr_0[1]+strArr_0[2]).length()+3+mx.group().length()).split("[@]")[5]; 
			} catch(Exception e) {
				return null;
			}
		}
		
		String tel =  "";
		String company = "";
		boolean flag = false;
		int telIndex = 0;
		int telCount = 0;
		
		String[] strArr = s.replaceAll("\\s+", "	")
							.replace("����","-")
							.replaceAll("[,]\\s+", ",")
							.replace("��", "(")
							.replace("��",")")
							.replaceAll("[(][(\\u4e00-\\u9fa5)]+[)]", "")
							.trim()
							.split("[,|\\s]");
		list = new ArrayList<Map<String,String>>();
		for(int i=0; i<strArr.length; i++) {
			
			strArr[i] = strArr[i].replace("Tel:", "")
								.replace("l", "1")
								.replace("O", "0")
								.replace("��", "")
								.replace("?","")
								.replace("~", "-")
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
					if(temp.length() >8) {//���ֳ��ȴ���7
						if(!flag) {
							flag = true;
							telIndex = i;
						}
						telCount++;
						strArr[i] = temp;//���õõ��绰
						strArr[i] = Parse.getTel(strArr[i]);
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
						strArr[i] = Parse.getTel(strArr[i]);
						System.out.println("�绰:"+strArr[i]);
					}else{
						if(temp.equals(strArr[i].replace("-", ""))) {
							System.out.println("�绰����С��7��");
							DAOFactory.getIFileDAOInstance(Tools.getPath("�쳣")+"��"+source+"���绰����С��7.txt").FileOutputStream(info+"\r\n", true);
						}else{
							if(!"".equals(strArr[i])&&strArr[i] != null) {
								strArr[i] = strArr[i].replaceAll("[(]\\W+[)]", "");
								strArr[i] = Parse.getCompany(company);
								company += strArr[i] + "	";
								System.out.println("��ҵ:"+strArr[i]);
							}
						}
						
					}
				}
			}else{
				strArr[i] = strArr[i].replaceAll("\\d{3,4}(-|����)?\\d", "");
				if(!"".equals(strArr[i])&&strArr[i] != null) {
					if(Pattern.compile("\\d+").matcher(strArr[i]).matches()) {
						System.out.println("�绰����С��7��");
						DAOFactory.getIFileDAOInstance(Tools.getPath("�쳣")+"��"+source+"���绰����С��7.txt").FileOutputStream(info+"\r\n", true);
					} else{
						strArr[i] = strArr[i].replaceAll("[(]\\W+[)]", "");
						strArr[i] = Parse.getCompany(strArr[i]);
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
					company = Parse.getCompany(company);
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
