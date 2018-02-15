package com.jdbc.statement;

import java.sql.Connection;
import java.sql.Statement;

import com.jdbc.connection.CGetConnection;
import com.jdbc.jdbcUtiles.JDBCUtiles;

public class AStatement {

	public static void main(String[] args) throws Exception {
		
		/**
		 * Statement : 用于执行sql语句的对象
		 * 1.获得statement对象：Statement statement = connection.createStatement();
		 * 2.准备sql语句：可以使insert，update，delete三种形式
		 * 3.执行操作：statement.executeUpdate(sql);
		 * 
		 * 4.最后关闭对象和连接：statement.close();
		 *				   connection.close();
		 */
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = JDBCUtiles.getConnection();
			
			//String sql = "insert into my_stu values (null,'名人','男',26,170,3)";
			String sql = "update my_stu  set sex = '女' where id = 13";
			
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			//statement.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("111");
		}finally{
			JDBCUtiles.release(null,statement, connection);
			//System.out.println("连接已关闭");
		}
		
		

	}

}
