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
	 * 1.ͨ��DriverManager�ķ�ʽ��ȡ���ݿ�����
	 * 2.ͬ����ͨ��jdbc.properties�ķ�ʽ����ȡ��Ӧ�Ĳ���
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
		
		//1.��������
		Class.forName(driverClass);
		//2.��������
		connection = 
				DriverManager.getConnection(url, user, password);
		
		return connection;
	}

}
