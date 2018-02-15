package com.jdbc.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jdbc.jdbcUtiles.JDBCUtiles;

public class ATransaction {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		 * ����
		 *  1.��Ҫʹ����ͬ��connection
		 *  
		 *  2.����Ĳ��裺
		 *  
		 *   (1).��ʼ����ȡ��Ĭ���ύ����
		 *   (2).�������롣����
		 *   (3).�����ɹ����ύ����
		 *   (4).����ʧ�ܣ��ع�����
		 */
		Connection connection = null;
		try {
			connection = JDBCUtiles.getConnection();
			//�������ݿ�ĸ��뼶�����䣺
			//connection.setTransactionIsolation(connection.TRANSACTION_READ_COMMITTED);
			//1.��ʼ���񣺹ر��Զ��ύ
			connection.setAutoCommit(false);
			//2.�����������Ĳ���
			String sql = "update my_stu set sex = ? where id = ?";
			update(connection, sql, "Ů",5);
			//int b= 10/0;
			update(connection, sql, "Ů",6);
			
			//3.�����ɹ����ύ����
			connection.commit();
		
			
		} catch (Exception e) {
			System.out.println("ִ������ع�");
			//4.����ʧ�ܣ��ع�����
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
	
	
	//���°汾��update����:ʹ�ô����洫���connection�����Խ����������
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

}
