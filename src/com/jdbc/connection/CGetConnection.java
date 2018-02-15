package com.jdbc.connection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class CGetConnection {

	public static void main(String[] args) throws Exception {
		CGetConnection c = new CGetConnection();
		System.out.println(c.getConnection());

	}
	/**
	 * 1.通过DriverManager的方式获取数据库连接
	 * 2.同样是通过jdbc.properties的方式来获取对应的参数
	 * 
	 * @return
	 * @throws Exception 
	 */
	public Connection getConnection() throws Exception{
		
		Connection connection = null;
		
		String driverClass = null;
		String url = null;
		String user = null;
		String password = null;
		
		InputStream in = 
		getClass().getClassLoader().getResourceAsStream("jdbc.properties");
		
		Properties properties = new Properties();
		properties.load(in);
		
		driverClass = properties.getProperty("driverClass");
		url = properties.getProperty("url");
		user = properties.getProperty("user");
		password = properties.getProperty("password");
		
		//1.加载驱动
		Class.forName(driverClass);
		//2.创建连接
		connection = 
				DriverManager.getConnection(url, user, password);
		
		return connection;
	}

}
