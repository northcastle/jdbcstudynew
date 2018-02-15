package com.jdbc.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jdbc.jdbcUtiles.JDBCUtiles;

public class ATransaction {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		 * 事务：
		 *  1.需要使用相同的connection
		 *  
		 *  2.具体的步骤：
		 *  
		 *   (1).开始事务：取消默认提交事务
		 *   (2).操作代码。。。
		 *   (3).操作成功：提交事务
		 *   (4).操作失败：回滚事务
		 */
		Connection connection = null;
		try {
			connection = JDBCUtiles.getConnection();
			//设置数据库的隔离级别的语句：
			//connection.setTransactionIsolation(connection.TRANSACTION_READ_COMMITTED);
			//1.开始事务：关闭自动提交
			connection.setAutoCommit(false);
			//2.下面是正常的操作
			String sql = "update my_stu set sex = ? where id = ?";
			update(connection, sql, "女",5);
			//int b= 10/0;
			update(connection, sql, "女",6);
			
			//3.操作成功：提交事务
			connection.commit();
		
			
		} catch (Exception e) {
			System.out.println("执行事务回滚");
			//4.操作失败：回滚事务
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			JDBCUtiles.release(null, null, connection);
		}

	}
	
	
	//更新版本的update操作:使用从外面传入的connection：可以进行事务操作
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

}
