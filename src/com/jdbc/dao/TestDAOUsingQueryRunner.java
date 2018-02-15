package com.jdbc.dao;

import java.sql.Connection;
import java.util.List;

import org.junit.Test;

import com.jdbc.jdbcUtiles.JDBCUtiles;
import com.jdbc.model.Stu;

public class TestDAOUsingQueryRunner {
	
	@Test
	
	public void testGetForValue(){
		Connection connection = null;
		try {
			connection = JDBCUtiles.getConnection();
			String sql = "select name from my_stu where id =  ?";
			String name = DaoUsingDBUtile.getForValue(connection, sql,18);
			
			System.out.println(name);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCUtiles.release(null, null, connection);
		}
		
		
	}
	
	
	public void testGetForList(){
		Connection connection = null;
		try {
			connection = JDBCUtiles.getConnection();
			String sql = "select * from my_stu where id  between ? and ?";
			List<Stu> stus = DaoUsingDBUtile.getForList(Stu.class, connection, sql, 5,18);
			
			for (Stu stu : stus) {
				System.out.println(stu);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCUtiles.release(null, null, connection);
		}
		
		
	}
	public void testGet(){
		Connection connection = null;
		try {
			connection = JDBCUtiles.getConnection();
			String sql = "select * from my_stu where id = ?";
			Stu stu = DaoUsingDBUtile.get(Stu.class, connection, sql, 5);
			System.out.println(stu);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCUtiles.release(null, null, connection);
		}
	}

}
