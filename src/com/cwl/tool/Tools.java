package com.cwl.tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.cwl.factory.DAOFactory;

import net.sf.json.JSONObject;

public class Tools {

	
	public static List<Integer> getCityCode() {
		
		List<String> list = DAOFactory.getIFileDAOInstance("file/行政编码.txt").BufferedReader();
		List<Integer> Code = new ArrayList<Integer>();
		
		
		for (int i=0; i< list.size(); i++ ) {
			String str = list.get(i);
			String[] strArr = str.replace("{", "").replace("}", "").split(",");
			for(int j=0; j<strArr.length; j++) {
				int code = Integer.parseInt(strArr[j].split(":")[1]);
				Code.add(code);	
			}
		}
		return Code;
	}
	
	/**
	 * 时间计算
	 * @param beginTime
	 * @param endTime
	 */
	public static void timeCalculate(String beginTime,String endTime){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
		try {
			long second = (dateFormat.parse(endTime).getTime()-dateFormat.parse(beginTime).getTime())/(1000);//除以1000换算成秒
			long minute = second / 60;
			long hour = minute / 60;
			long day = hour/24;
			long s = second - day*24*60*60-hour*60*60-minute*60;
			System.out.println("开始时间:"+beginTime+",结束时间:"+endTime+",共耗时："+day+"天 "+hour+"小时 "+minute+"分钟 "+s+"秒 ");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * map集合转换成json
	 * @param map
	 */
	public static String toJson(Map<String,Object> map){
		
		JSONObject json = null;
		json = JSONObject.fromObject(map);
		return json.toString();
		
	}
	
	/**
	 * 过滤&nbsp;
	 * @param html
	 * @return
	 */
	public static String filterSpace(String html){
		for(int i=6; i>0; i--) {
			String s = "";
			for(int j=i; j>0; j--) {
				s += "&nbsp;";
			}
			html = html.replace(s, "\t");
		}
		html = html.replace("\t", " ");
		return html;
	}
	
	
	/**
	 * list集合转换成json
	 * @param list
	 * @return
	 */
	public static String toJson(List<Map<String,Object>> list){
		
		JSONObject json = null;
		json =  JSONObject.fromObject(list);
	
		return json.toString();
		
	}
	
	/**
	 * 将url转换为uri
	 * @param url
	 */
	public static Map<String,String> toUri(String url){
		Map<String,String> map = null;
		map = new HashMap<String,String>();
		
		String[] urlArr = null;
		String[] params = null;
		String address = "";
		String param = "";
		String parameter = "";
		String scheme = "";
		String host = "";
		String path = "";
		String key = "";
		String value = "";
		
		
		try{
			urlArr = url.split("[?]");
			address = urlArr[0];
		
			param = urlArr[1];
			try{
				params = param.split("[&]");
				for(int i=0; i<params.length; i++) {
					parameter = params[i];
					key = parameter.split("=")[0];
					try{
						value = parameter.split("=")[1];	
					}catch(Exception e){
						value = "";
					}
					
					map.put(key,value);
				}
			}catch(Exception e){
				parameter = param;
				key = parameter.split("=")[0];
				try{
					value = parameter.split("=")[1];	
				}catch(Exception ex){
					value = "";
				}
				map.put(key,value);
			}
		}catch(Exception e){
			address = url;
		}
		scheme = address.split("://")[0];
		host = address.split("://")[1].split("/")[0];
		path = address.substring(scheme.length()+3+host.length());
	
		map.put("path", path);
		
		map.put("scheme", scheme);
		map.put("host", host);
		
		return map;
	}
	
	
	public static List<String> getCity(){
		
		String f = "file/中国各省份城市.txt";
		List<String>  list = DAOFactory.getIFileDAOInstance(f).BufferedReader();
		List<String> city = new ArrayList<String>();
		String[] x = null;
		for(int i=0; i<list.size(); i++){
			x = list.get(i).split("、");
			for(int j=0; j<x.length; j++){
				String s = x[j].replaceAll("\\s+", "");
				if((!Pattern.compile("\\s+").matcher(s).matches())&&(s !="")) {
					city.add(x[j]);
				}
			}
		}
		return city;
	}
}
