package com.jdbc.dbcp;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

public class AdbcpTest {

	public static void main(String[] args) throws Exception {
		BasicDataSource dataSource = null;
		//1.����һ�����ݿ����ӳ�
		dataSource = new  BasicDataSource();
		
		//2.���û������������ݿ������(������·)
		  //1.���ļ��л�ȡ���ӵ���Ϣ
		  //2.ͨ��set��������������Ϣ
//		File file = new File("E:\\javastudy\\JDBCStudyNew\\src\\jdbc.properties");
//		InputStream in = new FileInputStream(file);
		
		InputStream in = 
				AdbcpTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
		Properties properties = new Properties();
		properties.load(in);
		
		String driverClass = properties.getProperty("driverClass");
		String url = properties.getProperty("url");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		
		//4.����������һЩ���ݿ����ӳص�����
		//��ʼ��������
		dataSource.setInitialSize(10);
		//��С��ʣ���������
		dataSource.setMinIdle(5);
		//���ĵȴ�ʱ��
		dataSource.setMaxWaitMillis(1000*5);
		
		
		//3.��ȡ���Ӽ���
		Connection connection = dataSource.getConnection();
		
		System.out.println(connection);
		System.out.println(dataSource.getInitialSize());
		

	}

}
