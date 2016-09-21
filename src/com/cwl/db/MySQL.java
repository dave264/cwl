package com.cwl.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.cwl.dao.IDBConnectionDAO;

public class MySQL implements IDBConnectionDAO {

	public static  String DBDRIVER = "com.mysql.jdbc.Driver"; //mysql数据库驱动程序
	public static  String DBURL = "jdbc:mysql://127.0.0.1:3306/cwl";//定义mysql数据库连接地址
	public static  String DBUSER = "root";//mysql数据库连接名
	public static  String DBPASS = "root";//mysql数据库连接密码
	
	public MySQL(){
		
	}
	
	public MySQL(String host,int port,String dbName,String user,String password) {
		
		DBURL = "jdbc:mysql://"+host+":"+port+"/"+dbName;
		DBUSER = user;
		DBPASS = password;
		
	}
	
	@Override
	public Statement MySQLConnection() {
		
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(DBDRIVER);
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			stmt = conn.createStatement();//实例化Statement对象
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLException e) {
		}
		
		return stmt;
		
	}
	
}
