package com.jdbc.dao;

import java.lang.reflect.Field;
import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.text.html.parser.Entity;

import org.apache.commons.beanutils.BeanUtils;

import com.jdbc.jdbcUtiles.JDBCUtiles;

public class DAO {
	//1.insert��update��delete ��ɾ��  ���ֻ�������
	public void update(String sql,Object ... args){
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = JDBCUtiles.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			
			for(int i = 0;i<args.length;i++){
				preparedStatement.setObject(i+1, args[i]);
			}
			
			preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally{
			JDBCUtiles.release(null, preparedStatement, connection);
			
		}
		
	}

	//2.��ѯ����

	//1.��ѯһ����¼������һ������
	public <T> T get(Class<T> clazz,String sql,Object ... args) {
		T entity = null;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			
			//1.�������
			connection = JDBCUtiles.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for (int pr = 0; pr < args.length; pr++) {
				preparedStatement.setObject(pr+1, args[pr]);
			}
			
			resultSet = preparedStatement.executeQuery();
			
			//2.ResultSetMetaDateԪ���ݵĲ���
			ResultSetMetaData rsmd = resultSet.getMetaData();
			//3.ʹ��map���ϱ�������
			Map<String, Object> map = new HashMap<>();
			//4.�Ѳ�ѯ����ֵ���浽map��
			if (resultSet.next()) {
				
				for(int i=0;i<rsmd.getColumnCount();i++){
					String columnLable = rsmd.getColumnLabel(i+1);
					Object columnValue = resultSet.getObject(i+1);
					map.put(columnLable, columnValue);
				}
			}
			
			//5.ͨ�����䴴�����󲢷���
			if (map.size() > 0) {
				entity = clazz.newInstance();
				
				//ѭ������mapΪ��������Ը�ֵ
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					String fieldName = entry.getKey();
					Object fieldValue = entry.getValue();
					
					//ͨ����������ȡ��Ӧ�����Խ��и�ֵ��������set������
//					Field field = clazz.getDeclaredField(fieldName);
//					field.setAccessible(true);
//					field.set(entity, fieldValue);
					//ͨ��BeanUtiles��������Ķ�Ӧ�����Ը�ֵ
					BeanUtils.setProperty(entity, fieldName, fieldValue);
					
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JDBCUtiles.release(resultSet, preparedStatement, connection);
		}
		
		
		return entity;
		
	}

	//2.��ѯһ���¼�����ض���ļ��ϣ����Ҫ˼·����
	public <T> List<T> getForList(Class<T> clazz,String sql,Object ... args){
		//׼��ʢ�Ų�ѯ�õ������ݵļ���
		List<T> list = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try{
			//1.�������
			connection = JDBCUtiles.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for(int i = 0;i <args.length;i++){
				preparedStatement.setObject(i+1, args[i]);
			}
			resultSet = preparedStatement.executeQuery();
			//��Ҫ�õ���������Ԫ�Ĳ���
			ResultSetMetaData rsmd = resultSet.getMetaData();
			//һ��map��Ӧһ���������Ϣ�����������ж��map,��ÿһ��map ��������һ��list��
			List<Map<String, Object>> maps = new ArrayList<>();
			//2.ѭ���õ��Ľ�����ϣ�һ��һ���Ĵ���
			while(resultSet.next()){
				//��ÿһ�ж�Ӧ�����ݱ��浽һ��map��
				Map<String, Object> map = new HashMap<>();
				//����ѭ���õ�������Ԫ����ÿһ�е�������ȡ����
				for(int i = 0;i<rsmd.getColumnCount();i++){
					String fieldName = rsmd.getColumnLabel(i+1);
					Object fieldValue = resultSet.getObject(i+1);
					map.put(fieldName, fieldValue);
				}
				//�ѵõ���һ�������map��ӵ�list��
				maps.add(map);
			}
			
			//3.����2�Ĳ��裬�Ѿ���ÿһ�������ݶ�������һ��map�У�Ȼ���ֽ�ÿһ��map��������list��
			    //������ǽ��б���list�����ݻ�ȡ����
			
			Iterator iterator = maps.iterator();
			while(iterator.hasNext()){
				//׼��һ��T�Ķ������������ѯ���Ķ���
				T entity = clazz.newInstance();
				Map<String, Object> map = (Map<String, Object>) iterator.next();
				//Ȼ���ٱ���map���󣬽�������������ֵһһ�����
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					String fieldName = entry.getKey();
					Object fieldValue = entry.getValue();
					//ʹ��BeanUtiles���߰����������Եĸ�ֵ����
					BeanUtils.setProperty(entity, fieldName, fieldValue);
				}
				//ÿһ��ѭ������֮��֤�������һ����������Ը�ֵ����������뵽list��׼������
				list.add(entity);
			}
			
			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			JDBCUtiles.release(resultSet, preparedStatement, connection);
		}
		
		return list;
		
	}

	//3.����ĳһ����¼��һ���ֶε�ֵ������һ��ͳ�Ƶ�ֵ��һ����������¼�ȵȡ�������
	//������صĽ��һ����һ��һ��
	public <E> E getForValue(String sql,Object ...args){
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JDBCUtiles.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i+1, args[i]);
			}
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				ResultSetMetaData rsmd = resultSet.getMetaData();
				
				return (E) resultSet.getObject(rsmd.getColumnLabel(1));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally{
			JDBCUtiles.release(resultSet, preparedStatement, connection);
		}
		return null;
		
	}

}
