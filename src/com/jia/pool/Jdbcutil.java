package com.jia.pool;

import java.sql.Connection;
import java.sql.SQLException;

public class Jdbcutil {
	private static Jdbcpool jdbcpool=new Jdbcpool();
	
	public static Connection getConnection() throws SQLException {
		return jdbcpool.getConnection();
	}
	
	public static void relese(Connection conn) {
		if (conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
