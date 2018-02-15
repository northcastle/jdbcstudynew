package com.jdbc.c3p0;

import java.sql.Connection;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;



public class TestC3p0 {

	public static void main(String[] args) throws Exception {

		/**
		 * 1.导入c3p0的jar包(还有一个mchange的包)
		 * 2.参考c3p0的帮助文档进行配置文件的编写：推荐使用xml配置文件
		 * 3.参考c3p0的帮助文档进行数据库链接池的创建
		 */
		
		ComboPooledDataSource cpds =
				new ComboPooledDataSource("myc3p0pool");
		
		Connection connection = cpds.getConnection();
		System.out.println(connection);
		System.out.println(cpds.getInitialPoolSize());
		
	}

}
