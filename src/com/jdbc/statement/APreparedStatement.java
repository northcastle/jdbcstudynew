package com.jdbc.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.jdbc.jdbcUtiles.JDBCUtiles;

public class APreparedStatement {

	public static void main(String[] args) {
		
		/**
		 * PreparedStatement:
		 * 		��Statement���ӽӿ�
		 * 		ͨ�����ݲ����ķ�ʽ����sql�������
		 * 		�ܹ���Ч����֯sqlע���Σ��
		 * 		�ܽ�sql��仺���ڻ����У����ִ�е�Ч��
		 * 					
		 */
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = JDBCUtiles.getConnection();
			//1.׼��sql��䣬������?(Ӣ���ַ����ʺ�)���
			String sql = "insert into my_stu (name,sex,age,height,c_id)"
					+ "values(?,?,?,?,?)";
			//2.����preparedstatement���󣬲��Ұ�sql��Ϊ���������ȥ
			preparedStatement = connection.prepareStatement(sql);
			//3.����setXXX�������ݲ�������1��ʼ
			preparedStatement.setString(1, "��Һ�");
			preparedStatement.setString(2, "��");
			preparedStatement.setInt(3, 22);
			preparedStatement.setFloat(4, 178);
			preparedStatement.setInt(5, 3);
			//4.ִ��sql��䣺���Ǻ�Ͳ���Ҫ��sql����ȥ��
			preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtiles.release(null, preparedStatement, connection);
		}

	}

}
