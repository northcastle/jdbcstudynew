package com.jdbc.statement;

import java.sql.Connection;
import java.sql.Statement;

import com.jdbc.connection.CGetConnection;
import com.jdbc.jdbcUtiles.JDBCUtiles;

public class AStatement {

	public static void main(String[] args) throws Exception {
		
		/**
		 * Statement : ����ִ��sql���Ķ���
		 * 1.���statement����Statement statement = connection.createStatement();
		 * 2.׼��sql��䣺����ʹinsert��update��delete������ʽ
		 * 3.ִ�в�����statement.executeUpdate(sql);
		 * 
		 * 4.���رն�������ӣ�statement.close();
		 *				   connection.close();
		 */
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = JDBCUtiles.getConnection();
			
			//String sql = "insert into my_stu values (null,'����','��',26,170,3)";
			String sql = "update my_stu  set sex = 'Ů' where id = 13";
			
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			//statement.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("111");
		}finally{
			JDBCUtiles.release(null,statement, connection);
			//System.out.println("�����ѹر�");
		}
		
		

	}

}
