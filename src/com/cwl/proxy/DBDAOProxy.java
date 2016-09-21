package com.cwl.proxy;

import java.sql.ResultSet;
import java.sql.Statement;

import com.cwl.dao.IDBDAO;
import com.cwl.impl.DBDAOImpl;

public class DBDAOProxy implements IDBDAO{

	private IDBDAO dao = null;
	private String host = "";
	private int port = 0;
	private String dbName = "";
	private String userName = "";
	private String password = "";
	
	public DBDAOProxy() {
		
	}
	
	public DBDAOProxy(String host,int port,String dbName,String userName,String password) {
		
		this.host = host;
		this.port = port;
		this.dbName = dbName;
		this.userName = userName;
		this.password = password;
	
	}

	@Override
	public ResultSet doFind(String sql) {
		
		return this.dao.doFind(sql);
		
	}

	@Override
	public boolean doCreate(String sql) {
		
		return this.dao.doCreate(sql);
		
	}

	@Override
	public boolean doUpdate(String sql) {
		
		return this.dao.doUpdate(sql);
		
	}

	@Override
	public boolean doDelete(String sql) {
		
		return this.dao.doDelete(sql);
		
	}

	@Override
	public ResultSet doFind(String[] field, String tableName, String where) {
		
		return this.dao.doFind(field, tableName, where);
		
	}

	@Override
	public void export(String sql, String fileName) {
		this.dao.export(sql, fileName);	
	}

	@Override
	public Object doMysql() {
		this.dao  = new DBDAOImpl(host,port,dbName,userName,password);
		Object o = this.dao.doMysql();
		DBDAOImpl impl = (DBDAOImpl)o;
		return impl;
		
	}

	@Override
	public Statement MySQLConnection() {
		
		Statement stmt = null;
		stmt = this.dao.MySQLConnection();
		return stmt;
	}

}
