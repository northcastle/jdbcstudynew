package com.jdbc.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.jdbc.jdbcUtiles.JDBCUtiles;

public class DaoUsingDBUtile {
	
	private static QueryRunner queryRunner = new QueryRunner();
	//1.��ȡһ������
	public static <T> T get(Class<T> clazz,Connection connection,String sql,Object ... args){
		T entity = null;
		try{
			//ֱ�ӵ��ò�ѯ�������в�ѯ��һ�仰�㶨��
			entity =  queryRunner.query(connection, sql, new BeanHandler<T>(clazz), args);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return entity;
	}
	
	//2.��ȡһ�����ϵĶ���
	
	public static <T> List<T> getForList(Class<T> clazz,Connection connection,String sql,Object...args){
		List<T> list = new ArrayList<>();
		
		try {
			//����һ�仰�㶨��
			list = queryRunner.query(connection, sql, new BeanListHandler<>(clazz), args);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return list;
	}
	//3.��ȡһ������
	
	public static <E> E getForValue(Connection connection,String sql,Object...args){
		E e = null;
		try {
			e = (E) queryRunner.query(connection, sql, new ScalarHandler<>(), args);
			
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}
		return e;
	}
	//4.����
	public static void update(Connection connection,String sql,Object...args){
		try {
			queryRunner.update(connection, sql, args);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
