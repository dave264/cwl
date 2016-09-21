package com.cwl.parse.export;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cwl.dao.IDBOperatorDAO;
import com.cwl.factory.DAOFactory;

public class Export {
	
	public boolean export(long time) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
		String beginTime = date+" 00:00:00";
		String endTime = date + " 23:59:59";
		String fileName = "";
		long begintime = 0L;
		long endtime = 0L;
		try {
			begintime = sdf.parse(beginTime).getTime()/1000;//当天
			begintime = (begintime -86400)*1000;
			endtime = sdf.parse(endTime).getTime()/1000;//当天
			endtime = (endtime-86400)*1000;
			fileName = new SimpleDateFormat("yyyyMMdd").format(new Date(Long.valueOf(begintime)));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		String sql = "SELECT sys_sourcemap.`name`,isPass,passCount,hyStr "+
				"from sys_originalstock "+ 
				"JOIN sys_sourcemap on sys_sourcemap.type=sys_originalstock.type "+
				"where time between "+begintime+" AND "+endtime;
		String host = "120.31.131.195";
		int port = 3306;
		String dbName = "stock";
		String userName = "dev";
		String password = "dev";
		try {
			((IDBOperatorDAO) DAOFactory.getIDBDAOInstance(host, port, dbName, userName, password).doMysql()).export(sql, fileName+".txt");
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
