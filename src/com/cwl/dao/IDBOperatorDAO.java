package com.cwl.dao;

import java.sql.ResultSet;

public interface IDBOperatorDAO {
	
	public Object doMysql();
	public ResultSet doFind(String sql);
	public ResultSet doFind(String[] field,String tableName,String where);
	public void export(String sql,String fileName);
	public boolean doCreate(String sql);
	public boolean doUpdate(String sql);
	public boolean doDelete(String sql);
	
}
