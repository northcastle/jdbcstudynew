package com.jdbc.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.jdbc.jdbcUtiles.JDBCUtiles;
import com.jdbc.model.Stu;

public class AResultSet {

	public static void main(String[] args) {
		/**
		 * 执行查询操作，并且返回结果的集合 
		 */
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JDBCUtiles.getConnection();
			statement = connection.createStatement();
			
			String sql = "select  id,name,sex,age,height,c_id "
					+ "from my_stu";
			
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()){
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String sex = resultSet.getString("sex");
				int age = resultSet.getInt("age");
				double height = resultSet.getDouble("height");
				int c_id = resultSet.getInt("c_id");
				
				
				Stu stu = new Stu(id, name, sex, age, height, c_id);
				
				System.out.println(stu);
						
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtiles.release(resultSet, statement, connection);
		}
	}
	


}
