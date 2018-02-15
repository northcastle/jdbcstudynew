package com.jdbc.connection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;



public class AGetConnection {
	@Test

	public void testDriver() throws SQLException{
		
		
		/**
		 * 1.这是第一种方式来获取一个连接，不过这是一个固定的
		 * 		不灵活
		 *不建议直接使用Driver的方式获取连接
		 */
		//1.创建driver对象
		Driver driver = new com.mysql.jdbc.Driver();
		//2.准备参数 url user  password
		String url = "jdbc:mysql://localhost:3306/zzznewstudy";
		Properties info = new Properties();
		info.put("user", "root");
		info.put("password", "123456");
		//3.调用connect方法获取连接
		Connection connection = driver.connect(url, info);
		System.out.println(connection);
		
	}

}
