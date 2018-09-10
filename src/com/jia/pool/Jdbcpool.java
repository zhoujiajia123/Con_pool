package com.jia.pool;

import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class Jdbcpool implements DataSource {
	private static String driver="com.mysql.cj.jdbc.Driver";
	private static String dbuser="root";
	private static String dbpasswd="zj1314";
	private static String url="jdbc:mysql://localhost:3306/mydb1?useSSL=false&serverTimezone=GMT";
	private static int poolSize=20; 
	private static LinkedList<Connection> pool=new LinkedList<>();
	static {
		try {
			Class.forName(driver);
			for (int i = 0; i < poolSize; i++) {
				Connection conn=DriverManager.getConnection(url,dbuser,dbpasswd);
				pool.addLast(conn);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		if (pool.size()>0) {
			final Connection conn=pool.removeFirst();
			System.err.println(pool.size());
			return (Connection)Proxy.newProxyInstance(Jdbcpool.class.getClassLoader(), new Class[] {Connection.class}, new InvocationHandler() {
				
				@Override
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					// TODO Auto-generated method stub
					if (!method.getName().equals("close")) {
						return method.invoke(conn, args);
					}
					else {
						pool.addLast(conn);
						System.out.println("还给连接池了");
						return null;
					}
				}
			});
		}
		else {
			throw new RuntimeException("数据库忙");
		}
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
