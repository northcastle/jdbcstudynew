package com.jdbc.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import com.jdbc.jdbcUtiles.JDBCUtiles;

public class AgetPrimaryKey {
	
	public static void main(String[] args) {
		/**
		 * 获取插入时候自动生成的主键值
		 * 使用的是重载的方法：	//使用重载的方法
			preparedStatement = 
				connection.prepareStatement(sql, 
				Statement.RETURN_GENERATED_KEYS);
				
		 * 得到的是一个结果集：只有一行一列的结果集
		  
			//结果集在这里用到了:.getGeneratedKeys();这个方法返回的是结果集 
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				//获取这个结果集的元数据
				ResultSetMetaData rsmd = resultSet.getMetaData();
				//遍历元数据，常规操作：得到的一行一列的数据
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					String columnLable = rsmd.getColumnLabel(i+1);
					Object columnValue = resultSet.getObject(columnLable);
					System.out.println(columnLable +" : "+columnValue);
				}
				
			}
		 * 
		 */
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		ResultSet resultSet = null;
		
		try {
			connection = JDBCUtiles.getConnection();
			String sql = "insert into my_stu(name,sex,age,height,c_id) "
					+ "values(?,?,?,?,?)";
			//preparedStatement = connection.prepareStatement(sql);
			//使用重载的方法
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, "苹果");
			preparedStatement.setString(2, "女");
			preparedStatement.setInt(3, 11);
			preparedStatement.setFloat(4, (float) 167.9);
			preparedStatement.setInt(5, 2);
			
			preparedStatement.executeUpdate();
			
			//结果集在这里用到了:.getGeneratedKeys();这个方法返回的是结果集 
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				
				ResultSetMetaData rsmd = resultSet.getMetaData();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					String columnLable = rsmd.getColumnLabel(i+1);
					Object columnValue = resultSet.getObject(columnLable);
					System.out.println(columnLable +" : "+columnValue);
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCUtiles.release(resultSet, preparedStatement, connection);
		}
	}

}
