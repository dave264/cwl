package com.cwl.parse.tools;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;



public class Tools {
	
	public static String fileName = "";
	
	public static void setPath(String filePath) {
		
		File file = new File(filePath);
		try{
			fileName = file.getName().split("[.]")[0];
		}catch(Exception e) {
			fileName = file.getName();
		}
	}
	
	/**
	 * ����json��ʽ
	 * @param json
	 * @return
	 */
	public static Map<String,Object> toMap(String json) {
		
		JSONObject object = null;
		Map<String,Object> map = null;
		map = new HashMap<String,Object>();
		try {
			  object = new JSONObject(json);	
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Iterator<?> it = object.keys();
		String key = "";
		String value = "";
		while(it.hasNext()) {
			key = (String) it.next();
			try {
				value = object.getString(key);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			map.put(key, value);
		}
		return map;
	}
	
	/**
	 * ��ҵ������ж��Ƿ�Ϊ��ҵ
	 * @param s
	 * @return
	 */
	public static boolean isCompany(String s) {
		
		boolean flag = false;
		if(Pattern.compile("\\W*��\\W*��").matcher(s).find()) {
			flag = true;
		}
		if(Pattern.compile("\\W*��\\W*��").matcher(s).find()) {
			flag = true;
		}
		if(Pattern.compile("\\W*��\\W*��").matcher(s).find()) {
			flag = false;
		}
		if(Pattern.compile("\\W*��\\W*��").matcher(s).find()) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * ��ȡ��Ҫ����������
	 * @param info
	 * @return
	 */
	public static String getInfo(String info) {
		
		String str = "";
		String[] infoArr = null;
		infoArr =  info.split("[$]");
		try {
			str = infoArr[3];
		} catch(Exception e) {
			str = "";
		}
		return str;
		
	}
	
	/**
	 * ��ȡ·��
	 * @return
	 */
	public static String getPath() {
		
		String path = "";
//		int year = getYear();
//		int month = getMonth();
//		int day = getDay();
//		path = year+"-"+month+"-"+day+File.separator;
		path = fileName+File.separator;
		return path;	
	}
	
	/**
	 * ͨ������ȡ·��
	 * @param kind
	 * @return
	 */
	public static String getPath(String kind) {
		String path = "";
//		int year = getYear();
//		int month = getMonth();
//		int day = getDay();
//		path = kind+File.separator+year+month+day+File.separator;
		path = fileName+File.separator+kind+File.separator;
		return path;
	}
	/**
	 * ��ȡ��ǰ���
	 * @return
	 */
	public static int getYear() {
		
		int year = 2015;
		year = Calendar.getInstance().get(Calendar.YEAR);
		return year;
		
	}
	
	/**
	 * ��ȡ��ǰ��
	 * @return
	 */
	public static int getDay() {
		
		int day = 4;
		day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		return day;
		
	}
	
	/**
	 * ��ȡ��ǰ�·�
	 * @return
	 */
	public static int getMonth() {
		
		int month = 12;
		month = Calendar.getInstance().get(Calendar.MONTH)+1;
		return month;
		
	}
	
	/**
	 * �߳�˯��ʱ��
	 * @param s
	 */
	public static void sleep(long s) {
		try {
			Thread.sleep(s);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
