package com.jdbc.jdbcUtiles;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import com.jdbc.model.Stu;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtiles {
	
	/**
	 * 关于事务的三个方法：开始事务，提交事务，回滚事务
	 */
	public static void beginTX(Connection connection){
		if (connection != null) {
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	public static void commitTX(Connection connection){
		if (connection != null) {
			try {
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static void rollBackTX(Connection connection){
		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * 查询方法：返回一个Stu对象
	 */
	public static Stu getStu(String sql,Object...args){
		Stu stu = null;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JDBCUtiles.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for(int i = 0;i<args.length;i++){
				preparedStatement.setObject(i+1, args[i]);
			}
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String sex = resultSet.getString("sex");
				int age = resultSet .getInt("age");
				double height = resultSet.getDouble("height");
				int c_id = resultSet.getInt("c_id");
				
				stu = new Stu(id, name, sex, age, height, c_id);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCUtiles.release(resultSet, preparedStatement, connection);
		}
		
		return stu;
	}
	
	
	
	/**
	 * 查询的方法：
	 * 返回的是一个集合：这个方法可以查询一部分的对象，但是不够灵活
	 */
	public static List<Stu> query1(String sql){
		List<Stu> stus = new ArrayList<>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JDBCUtiles.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()){
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String sex = resultSet.getString("sex");
				int age = resultSet .getInt("age");
				double height = resultSet.getDouble("height");
				int c_id = resultSet.getInt("c_id");
				
				Stu stu = 
						new Stu(id, name, sex, age, height, c_id);
				
				stus.add(stu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCUtiles.release(resultSet, statement, connection);
		}
		
		return stus;
	}
	/**
	 * 版本三：支持事务的update操作
	 * 更新版本的update操作:使用从外面传入的connection：可以进行事务操作
	 * 
	*/
	public static void update(Connection connection,String sql,Object...args){
			
			PreparedStatement preparedStatement = null;
			
			try {
				//开始操作
				preparedStatement = connection.prepareStatement(sql);
				for (int i = 0; i < args.length; i++) {
					preparedStatement.setObject(i+1, args[i]);
				}
				preparedStatement.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//因为是外面传入的参数所以可以不用关闭connection
				JDBCUtiles.release(null, preparedStatement, null);
			}
		}

	/**
	 * 更新方法：版本2：使用preparestatement传递参数
	 * 这也是个比较通用的方法：使用可变参数的方式
	 * 返回值是操作的条数
	 */
	
	public static int update(String sql,Object...args){
		int num = 0;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = JDBCUtiles.getConnection();
			
			preparedStatement= connection.prepareStatement(sql);
			//使用for循环的方式将参数传递进去
			for(int i = 0;i<args.length;i++){
				preparedStatement.setObject(i+1, args[i]);
			}
			
			num = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtiles.release(null, preparedStatement, connection);
		}
		return num;
	}
	/**
	 * 更新的方法（版本1）：insert  update   delete
	 */
	
	public  static int update(String sql){
		
		int rowsInfect = 0;
		Connection connection = null;
		Statement  statement = null;
		
		try {
			connection = JDBCUtiles.getConnection();
			statement = connection.createStatement();
			
			rowsInfect = statement.executeUpdate(sql);
			
		} catch (Exception e) {
			System.out.println("执行更新失败！");
			e.printStackTrace();
		}finally{
			JDBCUtiles.release(null,statement, connection);
		}
		
		return rowsInfect;
	}
	/**
	 * 释放连接的方法
	 */
	
	public static void release(ResultSet resultSet,Statement statement,Connection connection){
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (connection != null) {
			try {
				connection .close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 获取连接的方法
	 * 重构之后的获取链接的方法：
	 * 通过数据库连接池获取数据库链接的方法
	 */
	
	private static DataSource dataSource = null;
	static{
		dataSource = 
				new ComboPooledDataSource("myc3p0pool");
	}
	public static Connection getConnection(){
		
		Connection connection = null;
		
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
	}

}
