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
		//1.创建一个数据库链接池
		dataSource = new  BasicDataSource();
		
		//2.设置基本的连接数据库的属性(都是套路)
		  //1.从文件中获取链接的信息
		  //2.通过set方法设置链接信息
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
		
		//4.还可以设置一些数据库连接池的属性
		//初始化连接数
		dataSource.setInitialSize(10);
		//最小的剩余的链接数
		dataSource.setMinIdle(5);
		//最大的等待时间
		dataSource.setMaxWaitMillis(1000*5);
		
		
		//3.获取链接即可
		Connection connection = dataSource.getConnection();
		
		System.out.println(connection);
		System.out.println(dataSource.getInitialSize());
		

	}

}
