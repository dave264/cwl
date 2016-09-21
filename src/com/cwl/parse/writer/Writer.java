package com.cwl.parse.writer;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cwl.factory.DAOFactory;
import com.cwl.parse.tools.Tools;

public class Writer {
	
	public static void writer(Map<String,String> map) {
		
		String source = map.get("source");
		String tel = map.get("tel");
		String company_name = map.get("company_name");
		
		String info = "";
		try{
			String[] companyArr = company_name.split("\\s");
			
			if(companyArr.length > 2) {
				String s = "";
				int count =0;
				for(int i=0; i< companyArr.length; i++) {
					if(companyArr[i].length() >=1 && companyArr[i].length()<=2){
						s +=companyArr[i];
						count++;
					}
				}
				if(!"".equals(s)&& s!= null) {
					if(count == companyArr.length) {
						company_name = s;
						info = source+"$"+tel+"$"+company_name+"$$$$$$";
						if(tel.length()>0&&tel.length()<9) {
							DAOFactory.getIFileDAOInstance(Tools.getPath("异常")+"电话号码无区号【"+source+"】.txt").FileOutputStream(info+"\r\n", true);
						}else if(tel.length()>=9&&tel.length()<=14) {
							DAOFactory.getIFileDAOInstance(Tools.getPath()+source+".txt").FileOutputStream(info+"\r\n", true);
						}else if(tel.length()>14){
							DAOFactory.getIFileDAOInstance(Tools.getPath("异常")+"人工处理----电话【"+source+"】.txt").FileOutputStream(info+"\r\n", true);
						}
					} else{
						DAOFactory.getIFileDAOInstance(Tools.getPath("异常")+"人工处理----企业【"+source+"】.txt").FileOutputStream(info+"\r\n", true);
					}
				}
				
			}else {
				if(company_name.length()<2 && company_name.length()>0) {
					info = source+"$"+tel+"$"+company_name+"$$$$$$";
					DAOFactory.getIFileDAOInstance(Tools.getPath("异常")+"不完整企业信息【"+source+"】.txt").FileOutputStream(info+"\r\n", true);
				} else{
					Matcher m0 = Pattern.compile("[\\w\\s]+").matcher(company_name);
					if(companyArr.length==2) {

						if(companyArr[0].equals(companyArr[1])||companyArr[0]==companyArr[1]) {
							company_name = companyArr[0];
						}else{
							if(companyArr[0].length()<2&&companyArr[1].length()>=2) {
								company_name = companyArr[1];
							}else if(companyArr[1].length()<2&&companyArr[0].length()>=2) {
								company_name = companyArr[0];
							}
						}	
					}
					info = source+"$"+tel+"$"+company_name+"$$$$$$";
					if(m0.matches()&&!"".equals(company_name) && company_name != null) {//如果不存在中文
						DAOFactory.getIFileDAOInstance(Tools.getPath("异常")+"不完整企业信息【"+source+"】.txt").FileOutputStream(info+"\r\n", true);
					} else {
						if(!"".equals(company_name) && company_name != null) {
							if(tel.length() ==0 ) {
								DAOFactory.getIFileDAOInstance(Tools.getPath("异常")+"电话号码为空【"+source+"】.txt").FileOutputStream(info+"\r\n", true);
							}else if(tel.length() >0 && tel.length() <7) {
								DAOFactory.getIFileDAOInstance(Tools.getPath("异常")+"电话号码格式不正确【"+source+"】.txt").FileOutputStream(info+"\r\n", true);
							}else if(tel.length()>=7&&tel.length()<9){
								DAOFactory.getIFileDAOInstance(Tools.getPath("异常")+"电话号码无区号【"+source+"】.txt").FileOutputStream(info+"\r\n", true);
							}else if(tel.length() >=9 && tel.length() <=14) {
								DAOFactory.getIFileDAOInstance(Tools.getPath()+source+".txt").FileOutputStream(info+"\r\n", true);
							}else if(tel.length() >14) {
								DAOFactory.getIFileDAOInstance(Tools.getPath("异常")+"人工处理----电话【"+source+"】.txt").FileOutputStream(info+"\r\n", true);
							}
						}else{
							DAOFactory.getIFileDAOInstance(Tools.getPath("异常")+"公司名字为空【"+source+"】.txt").FileOutputStream(info+"\r\n", true);
						} 	
					}
				}
			}	
		} catch(Exception e) {
			
		}
		

	}
}
