package com.cwl.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.cwl.dao.IDBConnectionDAO;
import com.cwl.dao.IDBDAO;
import com.cwl.db.MySQL;
import com.cwl.factory.DAOFactory;

public class DBDAOImpl implements IDBDAO {

	private Statement  stmt = null;
	private String host = "";
	private int port = 3306;
	private String dbName = "";
	private String userName = "";
	private String password = "";
	
	public DBDAOImpl(){
		
	}
	
	public DBDAOImpl(String host,int port,String dbName,String userName,String password){
		
		this.host = host;
		this.port = port;
		this.dbName = dbName;
		this.userName = userName;
		this.password = password;
		
	}

	
	/**
	 * 查找操作
	 */
	@Override
	public ResultSet doFind(String sql) {
		
		ResultSet rs = null;
		try {
			
			rs = this.stmt.executeQuery(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 插入操作
	 */
	@Override
	public boolean doCreate(String sql) {
		
		try {
			
			this.stmt.executeUpdate(sql);//执行数据库更新操作
			this.stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
		
	}

	/**
	 * 更新操作
	 */
	@Override
	public boolean doUpdate(String sql) {
		
		try {
			
			this.stmt.executeUpdate(sql);
			this.stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * 删除操作
	 */
	@Override
	public boolean doDelete(String sql) {
		
		try {
			
			this.stmt.executeUpdate(sql);
			this.stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	@Override
	public ResultSet doFind(String[] field, String tableName, String where) {
		
		ResultSet rs = null;
		String str = "";
		for (int i=0; i< field.length; i++) {
			if (i != (field.length-1)) {
				str += field[i]+",";
			} else {
				str += field[i];
			}
		}
		String sql = "SELECT "+str+"FROM "+tableName+" where "+where;
		try {
			rs = this.stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	@Override
	public void export(String sql,String fileName) {
		
		ResultSet rs = null;
		try {
			rs = this.stmt.executeQuery(sql);
			while(rs.next()) {
				String source = rs.getString("name");
				String isPass = rs.getString("ispass");
				String passCount = rs.getString("passcount");
				String hyStr = rs.getString("hystr");
				String str = source+"$"+isPass+"$"+passCount+"$"+hyStr;
				System.out.println(str);
				DAOFactory.getIFileDAOInstance(fileName).FileOutputStream(str+"\r\n", true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public DBDAOImpl doMysql() {
		
		this.stmt = this.MySQLConnection();
		return this;
		
	}

	@Override
	public Statement MySQLConnection() {
		
		Statement stmt = null;
		IDBConnectionDAO dao = new MySQL(this.host,this.port,this.dbName,this.userName,this.password);
		stmt = dao.MySQLConnection();
		
		return stmt;
		
	}

}
