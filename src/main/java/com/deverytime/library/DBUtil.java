package com.deverytime.library;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
	
	private Connection conn;
	
	public Connection open() {
		
		String url = "jdbc:oracle:thin:@192.168.10.108:1521:xe";
		String id = "board";
		String pw = "java1234";
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(url, id, pw);
						
			return conn;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public Connection open(boolean autocommit) { //false
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "server";
		String pw = "java1234";
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(url, id, pw);
			
			conn.setAutoCommit(autocommit);
						
			return conn;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public Connection open(String server, String id, String pw) {
		
		String url = "jdbc:oracle:thin:@" + server + ":1521:xe";
			
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(url, id, pw);
			
			return conn;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public Connection open(String server, String id, String pw, boolean autocommit) {
		
		String url = "jdbc:oracle:thin:@" + server + ":1521:xe";
			
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(url, id, pw);
			
			conn.setAutoCommit(autocommit);
			
			return conn;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	

}













