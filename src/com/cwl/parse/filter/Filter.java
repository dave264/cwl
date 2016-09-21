package com.cwl.parse.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cwl.factory.DAOFactory;
import com.cwl.parse.check.Check;
import com.cwl.parse.tools.Tools;
import com.cwl.parse.writer.Writer;

/**
 * 过滤，踢重
 * @author Mr.chen
 *
 */
public class Filter {
	
	/**
	 * 踢重，过滤多余数据
	 * @param list
	 * @param data
	 * @return
	 */
	public static List<Map<String,String>> filter(List<Map<String,String>> list,List<Map<String,String>> data) {
		
		data = Check.check(data);//检查数据正确性
		
		if(list != null) {
			if(data != null) {
				for(int i=0; i<data.size(); i++) {
					
					String n_tel = data.get(i).get("tel");
					String n_source = data.get(i).get("source");
//					if("路捷宝".equals(n_source)) {
//						Tools.sleep(3333);
//					}
					boolean flag = false;
					for(int j=0; j< list.size(); j++) {
						String tel = list.get(j).get("tel");
						String source = list.get(j).get("source");
						if(n_source.equals(source)&& n_tel.equals(tel)) {//对同来源踢重

							System.out.println("*-*-*-*-*-*-*-*-*---------源数据信息--------*-*-*-*-*-*-*-*-*-*");
							System.out.println("【"+list.get(j).get("source")+"】"+list.get(j).get("tel")+"-------"+list.get(j).get("company_name"));
							System.out.println("*-*-*-*-*-*-*-*-*---------新数据信息--------*-*-*-*-*-*-*-*-*-*");
							System.out.println("【"+data.get(i).get("source")+"】"+data.get(i).get("tel")+"-------"+data.get(i).get("company_name"));		
							flag = true;
							break;
						}
					}
					if(!flag) {
						if(!"".equals(data.get(i).get("company_name"))) {
							list.add(data.get(i));
							Writer.writer(data.get(i));
						}else{
							String info = "【"+data.get(i).get("source")+"】"+data.get(i).get("tel");
							DAOFactory.getIFileDAOInstance(Tools.getPath("异常")+"公司名字为空【"+data.get(i).get("source")+"】.txt").FileOutputStream(info+"\r\n", true);
						}	
					}
				}
			}
			
		} else {
			if(data != null) {
				list = new ArrayList<Map<String,String>>();
				for (int i=0; i< data.size(); i++) {
					if(!"".equals(data.get(i).get("company_name"))){
						list.add(data.get(i));
						Writer.writer(data.get(i));
					}else{
						String info = "【"+data.get(i).get("source")+"】"+data.get(i).get("tel");
						DAOFactory.getIFileDAOInstance(Tools.getPath("异常")+"公司名字为空【"+data.get(i).get("source")+"】.txt").FileOutputStream(info+"\r\n", true);
					}
				}
			}
		}
		return list;
	}
}
