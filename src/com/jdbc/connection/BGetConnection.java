package com.jdbc.connection;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import java.sql.Driver;

public class BGetConnection {

	public static void main(String[] args) {
		BGetConnection b = new BGetConnection();
		try {
			System.out.println(b.getConnection());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("³ÌÐò³ö´íÁË");
		}
	}
	
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
		
		
		Driver driver =
				(Driver)Class.forName(driverClass).newInstance();
		Properties info = new Properties();
		info.put("user", user);
		info.put("password", password);
		
		connection = driver.connect(url, info);
		
		
		return connection;
	}

}
