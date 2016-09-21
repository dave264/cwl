package com.cwl.factory;

import com.cwl.dao.IDBOperatorDAO;
import com.cwl.dao.IFileDAO;
import com.cwl.dao.IHttpDAO;
import com.cwl.proxy.DBDAOProxy;
import com.cwl.proxy.FileDAOProxy;
import com.cwl.proxy.HttpDAOProxy;
import com.cwl.vo.Http;

public class DAOFactory {

	/**
	 * 文件操作
	 * @param f
	 * @return
	 */
	public static IFileDAO getIFileDAOInstance(String f){
		return new FileDAOProxy(f);
	}
	
	/**
	 * http操作
	 * @param http
	 * @return
	 */
	public static IHttpDAO getIHttpDAOInstance(Http http) {
		return new HttpDAOProxy(http);
	}
	
	/**
	 * 数据库操作
	 * @return
	 */
	public static IDBOperatorDAO getIDBDAOInstance(String host,int port,String dbName,String userName,String password){
		return new DBDAOProxy(host,port,dbName,userName,password);
	}
	
	
}
