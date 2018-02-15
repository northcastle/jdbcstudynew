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
	 * ���������������������ʼ�����ύ���񣬻ع�����
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
	 * ��ѯ����������һ��Stu����
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
	 * ��ѯ�ķ�����
	 * ���ص���һ�����ϣ�����������Բ�ѯһ���ֵĶ��󣬵��ǲ������
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
	 * �汾����֧�������update����
	 * ���°汾��update����:ʹ�ô����洫���connection�����Խ����������
	 * 
	*/
	public static void update(Connection connection,String sql,Object...args){
			
			PreparedStatement preparedStatement = null;
			
			try {
				//��ʼ����
				preparedStatement = connection.prepareStatement(sql);
				for (int i = 0; i < args.length; i++) {
					preparedStatement.setObject(i+1, args[i]);
				}
				preparedStatement.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//��Ϊ�����洫��Ĳ������Կ��Բ��ùر�connection
				JDBCUtiles.release(null, preparedStatement, null);
			}
		}

	/**
	 * ���·������汾2��ʹ��preparestatement���ݲ���
	 * ��Ҳ�Ǹ��Ƚ�ͨ�õķ�����ʹ�ÿɱ�����ķ�ʽ
	 * ����ֵ�ǲ���������
	 */
	
	public static int update(String sql,Object...args){
		int num = 0;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = JDBCUtiles.getConnection();
			
			preparedStatement= connection.prepareStatement(sql);
			//ʹ��forѭ���ķ�ʽ���������ݽ�ȥ
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
	 * ���µķ������汾1����insert  update   delete
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
			System.out.println("ִ�и���ʧ�ܣ�");
			e.printStackTrace();
		}finally{
			JDBCUtiles.release(null,statement, connection);
		}
		
		return rowsInfect;
	}
	/**
	 * �ͷ����ӵķ���
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
	 * ��ȡ���ӵķ���
	 * �ع�֮��Ļ�ȡ���ӵķ�����
	 * ͨ�����ݿ����ӳػ�ȡ���ݿ����ӵķ���
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
