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
	 * 批量操作：
	 * 1.事务操作：要么都成功，要么都不成功
	 * 2.在MySQL中的这种操作果然是效果不行的
	 * 
	 * 3，本次实验有三个test来测试batch批量操作
	 *   其中最上面这个中包含着批量操作的具体步骤（三部曲）
	 *   ①。积攒
	 *   ②。执行一次batch，清空batch
	 *   ③。把最后剩余的部分执行掉
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
				
				//在这个地方积攒
				preparedStatement.addBatch();
				//积攒到一定程度的时候在执行
				if ((i+1) % 300 == 0) {//每三百条执行一次
					//执行batch
					preparedStatement.executeBatch();
					//清空batch
					preparedStatement.clearBatch();
					
				}
			}
			
			//执行最后一次batch,把最后一车烤鸭发送出去
			if (100000 % 300 != 0) {
				//执行batch
				preparedStatement.executeBatch();
				//清空batch
				preparedStatement.clearBatch();
			}
			
			
			JDBCUtiles.commitTX(connection);
			long end = System.currentTimeMillis();
			System.out.println("插入用时：" + (end - begin));//22369
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
			System.out.println("插入用时：" + (end - begin));//22429,22293
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
			System.out.println("插入用时："+(end - begin));//19365,21649
			
		} catch (Exception e) {
			JDBCUtiles.rollBackTX(connection);
			e.printStackTrace();
		} finally {
			JDBCUtiles.release(null, statement, connection);
		}
		
	}

}
