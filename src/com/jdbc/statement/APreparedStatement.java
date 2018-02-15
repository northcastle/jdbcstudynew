package com.jdbc.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.jdbc.jdbcUtiles.JDBCUtiles;

public class APreparedStatement {

	public static void main(String[] args) {
		
		/**
		 * PreparedStatement:
		 * 		是Statement的子接口
		 * 		通过传递参数的方式进行sql语句的完成
		 * 		能够有效的组织sql注入的危险
		 * 		能将sql语句缓存在缓存中，提高执行的效率
		 * 					
		 */
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = JDBCUtiles.getConnection();
			//1.准备sql语句，参数用?(英文字符的问号)填充
			String sql = "insert into my_stu (name,sex,age,height,c_id)"
					+ "values(?,?,?,?,?)";
			//2.创建preparedstatement对象，并且把sql作为参数传入进去
			preparedStatement = connection.prepareStatement(sql);
			//3.调用setXXX方法传递参数，从1开始
			preparedStatement.setString(1, "大家好");
			preparedStatement.setString(2, "男");
			preparedStatement.setInt(3, 22);
			preparedStatement.setFloat(4, 178);
			preparedStatement.setInt(5, 3);
			//4.执行sql语句：这是后就不需要吧sql传进去了
			preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtiles.release(null, preparedStatement, connection);
		}

	}

}
