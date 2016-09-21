package com.cwl.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cwl.factory.DAOFactory;

public class ReaderFile {
	
	public static List<String> GetChaWuLiuCity() {
		
		List<String> list = DAOFactory.getIFileDAOInstance("file/²éÎïÁ÷.txt").BufferedReader();
		List<String> info = new ArrayList<String>();
		for(int i=0; i<list.size(); i++) {
			Matcher m = Pattern.compile("http://\\w+.\\w+.com/city/\\w+").matcher(list.get(i));
			while(m.find()) {
				String href = m.group();
				info.add(href);
			}
		}
		return info;
	}
}
