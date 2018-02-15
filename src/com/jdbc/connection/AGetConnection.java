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
		 * 1.���ǵ�һ�ַ�ʽ����ȡһ�����ӣ���������һ���̶���
		 * 		�����
		 *������ֱ��ʹ��Driver�ķ�ʽ��ȡ����
		 */
		//1.����driver����
		Driver driver = new com.mysql.jdbc.Driver();
		//2.׼������ url user  password
		String url = "jdbc:mysql://localhost:3306/zzznewstudy";
		Properties info = new Properties();
		info.put("user", "root");
		info.put("password", "123456");
		//3.����connect������ȡ����
		Connection connection = driver.connect(url, info);
		System.out.println(connection);
		
	}

}
