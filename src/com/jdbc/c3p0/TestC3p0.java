package com.jdbc.c3p0;

import java.sql.Connection;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;



public class TestC3p0 {

	public static void main(String[] args) throws Exception {

		/**
		 * 1.����c3p0��jar��(����һ��mchange�İ�)
		 * 2.�ο�c3p0�İ����ĵ����������ļ��ı�д���Ƽ�ʹ��xml�����ļ�
		 * 3.�ο�c3p0�İ����ĵ��������ݿ����ӳصĴ���
		 */
		
		ComboPooledDataSource cpds =
				new ComboPooledDataSource("myc3p0pool");
		
		Connection connection = cpds.getConnection();
		System.out.println(connection);
		System.out.println(cpds.getInitialPoolSize());
		
	}

}
