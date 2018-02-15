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
	//1.insert，update，delete 增删改  三种基本操作
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

	//2.查询操作

	//1.查询一条记录，返回一个对象
	public <T> T get(Class<T> clazz,String sql,Object ... args) {
		T entity = null;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			
			//1.常规操作
			connection = JDBCUtiles.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for (int pr = 0; pr < args.length; pr++) {
				preparedStatement.setObject(pr+1, args[pr]);
			}
			
			resultSet = preparedStatement.executeQuery();
			
			//2.ResultSetMetaDate元数据的操作
			ResultSetMetaData rsmd = resultSet.getMetaData();
			//3.使用map集合保存数据
			Map<String, Object> map = new HashMap<>();
			//4.把查询到的值保存到map中
			if (resultSet.next()) {
				
				for(int i=0;i<rsmd.getColumnCount();i++){
					String columnLable = rsmd.getColumnLabel(i+1);
					Object columnValue = resultSet.getObject(i+1);
					map.put(columnLable, columnValue);
				}
			}
			
			//5.通过反射创建对象并返回
			if (map.size() > 0) {
				entity = clazz.newInstance();
				
				//循环便利map为对象的属性赋值
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					String fieldName = entry.getKey();
					Object fieldValue = entry.getValue();
					
					//通过反射来获取相应的属性进行赋值（跳过了set方法）
//					Field field = clazz.getDeclaredField(fieldName);
//					field.setAccessible(true);
//					field.set(entity, fieldValue);
					//通过BeanUtiles来给对象的对应的属性赋值
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

	//2.查询一组记录，返回对象的集合：这个要思路清晰
	public <T> List<T> getForList(Class<T> clazz,String sql,Object ... args){
		//准备盛放查询得到的数据的集合
		List<T> list = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try{
			//1.常规操作
			connection = JDBCUtiles.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for(int i = 0;i <args.length;i++){
				preparedStatement.setObject(i+1, args[i]);
			}
			resultSet = preparedStatement.executeQuery();
			//需要得到许多的数据元的操作
			ResultSetMetaData rsmd = resultSet.getMetaData();
			//一个map对应一个对象的信息，多个对象就有多个map,把每一个map 都保存在一个list中
			List<Map<String, Object>> maps = new ArrayList<>();
			//2.循环得到的结果集合，一条一条的处理
			while(resultSet.next()){
				//把每一列对应的数据保存到一个map中
				Map<String, Object> map = new HashMap<>();
				//遍历循环得到的数据元，将每一列的数据提取出来
				for(int i = 0;i<rsmd.getColumnCount();i++){
					String fieldName = rsmd.getColumnLabel(i+1);
					Object fieldValue = resultSet.getObject(i+1);
					map.put(fieldName, fieldValue);
				}
				//把得到的一个对象的map添加到list中
				maps.add(map);
			}
			
			//3.经过2的步骤，已经将每一条的数据都保存在一个map中，然后又将每一个map保存在了list中
			    //下面就是进行遍历list将内容获取出来
			
			Iterator iterator = maps.iterator();
			while(iterator.hasNext()){
				//准备一个T的对象，用来保存查询到的对象
				T entity = clazz.newInstance();
				Map<String, Object> map = (Map<String, Object>) iterator.next();
				//然后再遍历map对象，将属性名和属性值一一求出来
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					String fieldName = entry.getKey();
					Object fieldValue = entry.getValue();
					//使用BeanUtiles工具包来进行属性的赋值操作
					BeanUtils.setProperty(entity, fieldName, fieldValue);
				}
				//每一个循环结束之后，证明完成了一个对象的属性赋值，将对象放入到list中准备返回
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

	//3.返回某一条记录的一个字段的值，或者一个统计的值（一共多少条记录等等。。。）
	//这个返回的结果一定是一行一列
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
