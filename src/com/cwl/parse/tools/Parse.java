package com.cwl.parse.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ����ֻ���
 * @author Mr.chen
 *
 */
public class Parse {
 
	/**
	 * �Ƿ�Ϊ�绰����
	 * @param str
	 * @return
	 */
	public static boolean isTel(String str) {
		
		boolean flag = false;
		String regex = "1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}";
		Matcher m = Pattern.compile(regex).matcher(str);
		if(m.find()) {
			flag = true;
		}
		return flag;
	}
	
	
	/**
	 * ��ȡ�ֻ���
	 * @param str
	 * @return
	 */
	public static String getTel(String str) {
		
		String tel = "";
		str = str.replace("l", "1").replace("O", "0");
		String regex = "1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}";
		Matcher m = Pattern.compile(regex).matcher(str);
		if(m.find()) {
			tel = m.group();
		}
		return tel;	
	}
	
	public static String getCompany(String str) {
		
		String regex = "([\\u4e00-\\u9fa5\\s]+)";
		Matcher m = Pattern.compile(regex).matcher(str);
		if(m.find()) {
			str = m.group();
		}else{
			str = "";
		}
		return str;
	}
	
	
}
