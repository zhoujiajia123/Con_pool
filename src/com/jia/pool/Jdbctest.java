package com.jia.pool;

import java.sql.Connection;
import java.sql.SQLException;

public class Jdbctest {
	public static void main(String[] args) {
		Connection connection=null;
		try {
			for (int i = 0; i < 20; i++) {
				connection=Jdbcutil.getConnection();
			}
			connection.close();
			//connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
