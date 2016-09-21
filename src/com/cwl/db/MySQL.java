package com.cwl.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.cwl.dao.IDBConnectionDAO;

public class MySQL implements IDBConnectionDAO {

	public static  String DBDRIVER = "com.mysql.jdbc.Driver"; //mysql���ݿ���������
	public static  String DBURL = "jdbc:mysql://127.0.0.1:3306/cwl";//����mysql���ݿ����ӵ�ַ
	public static  String DBUSER = "root";//mysql���ݿ�������
	public static  String DBPASS = "root";//mysql���ݿ���������
	
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
			stmt = conn.createStatement();//ʵ����Statement����
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLException e) {
		}
		
		return stmt;
		
	}
	
}
