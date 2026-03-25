package com.deverytime.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class BasicDao {
	
	private DBUtil util;
	public Connection conn;
	public Statement stat;
	public PreparedStatement pstat;
	public ResultSet rs;
	
	public BasicDao() {		
		try {
			util = new DBUtil();
			conn = util.open();
			stat = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	protected void close(AutoCloseable ac) {
        try {
            if (ac != null) ac.close();
        } catch (Exception e) {}
    }

    protected void closeAll() {
        close(rs);
        close(pstat);
        close(stat);
        close(conn);
    }

}
