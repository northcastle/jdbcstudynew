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
		 * ��ȡ����ʱ���Զ����ɵ�����ֵ
		 * ʹ�õ������صķ�����	//ʹ�����صķ���
			preparedStatement = 
				connection.prepareStatement(sql, 
				Statement.RETURN_GENERATED_KEYS);
				
		 * �õ�����һ���������ֻ��һ��һ�еĽ����
		  
			//������������õ���:.getGeneratedKeys();����������ص��ǽ���� 
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				//��ȡ����������Ԫ����
				ResultSetMetaData rsmd = resultSet.getMetaData();
				//����Ԫ���ݣ�����������õ���һ��һ�е�����
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
			//ʹ�����صķ���
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, "ƻ��");
			preparedStatement.setString(2, "Ů");
			preparedStatement.setInt(3, 11);
			preparedStatement.setFloat(4, (float) 167.9);
			preparedStatement.setInt(5, 2);
			
			preparedStatement.executeUpdate();
			
			//������������õ���:.getGeneratedKeys();����������ص��ǽ���� 
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
