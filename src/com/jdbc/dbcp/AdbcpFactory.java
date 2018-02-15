package com.jdbc.dbcp;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

public class AdbcpFactory {

	public static void main(String[] args) throws Exception {

		//1.����һ������
		BasicDataSourceFactory basicDataSourceFactory = 
				new BasicDataSourceFactory();
		
		//2.ͨ���������һ�����ݿ����ӳ�
		InputStream inStream = 
				AdbcpFactory.class.getClassLoader().getResourceAsStream("dbcp.properties");
		Properties properties = new Properties();
		properties.load(inStream);
		BasicDataSource basicDataSource =
				basicDataSourceFactory.createDataSource(properties);
		Connection connection = basicDataSource.getConnection();
		System.out.println(connection);
		System.out.println(basicDataSource.getInitialSize());
	}

}
