package com.cwl.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.cwl.factory.DAOFactory;

/**
 * ����ץȡ������
 * @author Mr.chen
 *
 */
public class Filter {
	
	public String path = "";
	
	public Filter(String path) {
		this.path = path;
	}
	
	public List<Map<String,String>> check(List<Map<String,String>> list) {
		
		if(list != null) {
			Map<String,String> map =  null;
			for(int i=0; i< list.size(); i++) {
				
				String msg = "";
				boolean flag0 = false;
				boolean flag1 = false;
				boolean flag2 = false;
				map = list.get(i);
				String tel = map.get("tel");
				String source = map.get("source");
				String company_name = map.get("company_name");
				if(Pattern.compile("[-\\d]+|[(]\\d+[)]\\d+").matcher(tel).matches()) {
					flag0 = true;
				} else {
					msg += "---�绰�����ʽ����ȷ��----";
				}
				if(tel.length() >= 7) {
					flag1 = true;//�̺ţ��������ֻ���
				} else {
					msg +="---�绰��������С��7��---";
				}
				if(!"".equals(company_name)&&company_name != null) {
					flag2 = true;
				} else{
					msg += "---��˾����Ϊ�գ�---";
				}
				String info = source+"$"+tel+"$"+company_name+"$$$$$$";
				
				if(!flag0) {
					System.out.println("----------�绰�����ʽ����ȷ��-----------");
					System.out.println(info);
					System.out.println(msg);
					DAOFactory.getIFileDAOInstance(path+source+"�绰�����ʽ����ȷ.txt").FileOutputStream(info+"\r\n"+msg+"\r\n", true);
					try {
						Thread.sleep(6666);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(flag0&&flag1&&flag2) {
//					DAOFactory.getIFileDAOInstance(path+source+".txt").FileOutputStream(info+"\r\n", true);
				} else {
					System.out.println("----------����--------------");
					System.out.println(info);
					System.out.println(msg);
					DAOFactory.getIFileDAOInstance(path+source+"error.txt").FileOutputStream(info+"\r\n"+msg+"\r\n", true);
					try {
						Thread.sleep(3333);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
			}
		}
		return list;
	}
	
	/**
	 * �ļ�д��
	 */
	public void writer(Map<String,String> map) {
			
		String source = map.get("source");
		String tel = map.get("tel");
		String company_name = map.get("company_name");
		String info = source+"$"+tel+"$"+company_name+"$$$$$$";
		System.out.println(info);
		DAOFactory.getIFileDAOInstance(this.path+source+".txt").FileOutputStream(info+"\r\n", true);
	
		
	}
	
	public  List<Map<String,String>> filter(List<Map<String,String>> list,List<Map<String,String>> data) {
		
		data = this.check(data);
		if(list != null) {
			if(data != null) {
				for(int i=0; i<data.size(); i++) {
					String n_tel = data.get(i).get("tel");
					boolean flag = false;
					for(int j=0; j< list.size(); j++) {
						String tel = list.get(j).get("tel");
						if(n_tel.equals(tel)) {
							flag = true;
							break;
						}
					}
					if(!flag) {
						list.add(data.get(i));
						this.writer(data.get(i));
					}
				}
			}
			
		} else {
			if(data != null) {
				list = new ArrayList<Map<String,String>>();
				list.addAll(data);
				for (int i=0; i< data.size(); i++) {
					this.writer(data.get(i));
				}
			}
		}
		return list;
	}
}
