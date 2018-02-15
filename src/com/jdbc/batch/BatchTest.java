package com.jdbc.batch;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.junit.Test;

import com.jdbc.jdbcUtiles.JDBCUtiles;

public class BatchTest {
	/**
	 * ����������
	 * 1.���������Ҫô���ɹ���Ҫô�����ɹ�
	 * 2.��MySQL�е����ֲ�����Ȼ��Ч�����е�
	 * 
	 * 3������ʵ��������test������batch��������
	 *   ��������������а��������������ľ��岽�裨��������
	 *   �١�����
	 *   �ڡ�ִ��һ��batch�����batch
	 *   �ۡ������ʣ��Ĳ���ִ�е�
	 * 
	 */

	@Test
	public void testBatch(){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		 Date date = new Date(new java.util.Date().getTime());
		
		try {
			connection = JDBCUtiles.getConnection();
			String sql = "insert into my_batch values(?,?,?,?)";
			JDBCUtiles.beginTX(connection);
			
			preparedStatement = connection.prepareStatement(sql);
			long begin = System.currentTimeMillis();
			for (int i = 0; i < 100000; i++) {
				preparedStatement.setInt(1, i+1);
				preparedStatement.setString(2, "name_"+i);
				preparedStatement.setInt(3, i);
				preparedStatement.setDate(4,date);
				
				//������ط�����
				preparedStatement.addBatch();
				//���ܵ�һ���̶ȵ�ʱ����ִ��
				if ((i+1) % 300 == 0) {//ÿ������ִ��һ��
					//ִ��batch
					preparedStatement.executeBatch();
					//���batch
					preparedStatement.clearBatch();
					
				}
			}
			
			//ִ�����һ��batch,�����һ����Ѽ���ͳ�ȥ
			if (100000 % 300 != 0) {
				//ִ��batch
				preparedStatement.executeBatch();
				//���batch
				preparedStatement.clearBatch();
			}
			
			
			JDBCUtiles.commitTX(connection);
			long end = System.currentTimeMillis();
			System.out.println("������ʱ��" + (end - begin));//22369
		} catch (Exception e) {
			// TODO: handle exception
			JDBCUtiles.rollBackTX(connection);
			e.printStackTrace();
		} finally {
			JDBCUtiles.release(null, preparedStatement, connection);
		}
	}
	
	public void testPreparedStatement(){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		 Date date = new Date(new java.util.Date().getTime());
		
		try {
			connection = JDBCUtiles.getConnection();
			String sql = "insert into my_batch values(?,?,?,?)";
			JDBCUtiles.beginTX(connection);
			
			preparedStatement = connection.prepareStatement(sql);
			long begin = System.currentTimeMillis();
			for (int i = 0; i < 100000; i++) {
				preparedStatement.setInt(1, i+1);
				preparedStatement.setString(2, "name_"+i);
				preparedStatement.setInt(3, i);
				preparedStatement.setDate(4,date);
				
				preparedStatement.executeUpdate();
			}
			
			
			JDBCUtiles.commitTX(connection);
			long end = System.currentTimeMillis();
			System.out.println("������ʱ��" + (end - begin));//22429,22293
		} catch (Exception e) {
			// TODO: handle exception
			JDBCUtiles.rollBackTX(connection);
			e.printStackTrace();
		} finally {
			JDBCUtiles.release(null, preparedStatement, connection);
		}
	}
	
	
	public void testStatement() {
		Connection connection = null;
		Statement statement = null;
		String sql = null;
		Date date = new Date(new java.util.Date().getTime());
		try {
			connection = JDBCUtiles.getConnection();
			JDBCUtiles.beginTX(connection);
			
			statement = connection.createStatement();
			
			long begin = System.currentTimeMillis();
			for(int i = 0;i<100000;i++){
				sql = "insert into my_batch values("+(i+1)+",'"
								+("name_"+i)+"',"+(i)+",'"+date+"')";
				
				//System.out.println(sql);
				statement.executeUpdate(sql);
				
			}
			long end = System.currentTimeMillis();
			
			
			JDBCUtiles.commitTX(connection);
			System.out.println("������ʱ��"+(end - begin));//19365,21649
			
		} catch (Exception e) {
			JDBCUtiles.rollBackTX(connection);
			e.printStackTrace();
		} finally {
			JDBCUtiles.release(null, statement, connection);
		}
		
	}

}
