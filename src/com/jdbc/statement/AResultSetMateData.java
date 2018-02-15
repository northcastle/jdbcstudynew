package com.jdbc.statement;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

import com.jdbc.jdbcUtiles.JDBCUtiles;
import com.jdbc.model.Stu;

public class AResultSetMateData {

	public static void main(String[] args) {

		/**
		 * ResultSetMateData:元数据
		 * 		可以获取返回结果集的的元数据信息：
		 * 			操作数据的数据（可以理解一下）
		 * 			一般是与查询的sql语句用
		 * 	  1.getColumnCount():获取列的个数
		 * 	  2.getColumnLable(index):获取列的别名（注意是别名,索引从1开始）：因此
		 * 写sql语句的时候需要注意：列的别名需要与对象的属性名一致，方便后面的操作
		 * （下面的sql例子因为已经与对象属性一致了，就没有起别名）
		 * 		String sql = "select id,name,sex,age,height,c_id from my_stu"
							+ "where id = ?";
		 * 
		 */
		String sql = "select id ,name , sex ,age ,height ,c_id  from my_stu where id = ?";
		Stu stu = AResultSetMateData.get(Stu.class, sql, 6);
		System.out.println(stu);
		
	}
	
	/**
	 * 携程一个通用的方法：获取某个类的一个对象！一个对象！
	 * @param clazz：某个实体类
	 * @param sql：查询的sql 语句
	 * @param args：sql中需要传入的参数
	 * @return
	 */
	public static <T> T get(Class<T> clazz,String sql,Object...args){
		
		T entity = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			//基本的常规操作
			connection = JDBCUtiles.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for(int i = 0;i < args.length;i++){
				preparedStatement.setObject(i+1, args[i]);
			}
			resultSet = preparedStatement.executeQuery();
			
			//新操作：获取结果集的元数据，准备操作
			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			//准备一个map,通过键值对的方法保存获取到的数据
			Map<String, Object> map = new HashMap<>();
			
			if (resultSet.next()) {
				//通过循环的形式获取结果集的信息,并且把信息保存到map的键值对中
				for(int i = 0;i < rsmd.getColumnCount();i++){
					String columnLable = rsmd.getColumnLabel(i+1);
					Object columnValue = resultSet.getObject(i+1);
					map.put(columnLable, columnValue);
				}
			}
			
			//判断当集合不为空时，才进行下一步操作
			if (map.size() > 0) {
				//entity是我们要返回的对象
				entity = clazz.newInstance();
				//然后循环便利map给stu对象赋值(当然要掌握这个便利的方法)
				for(Map.Entry<String, Object> entry : map.entrySet()){
					
					String fieldName = entry.getKey();
					Object fieldValue = entry.getValue();
					
					//System.out.println(fieldName+" : "+fieldValue);
					//方法一：这个地方是调用了一个写好的方法
					//ReflectionUtils.setFieldValue(entity, fieldName, fieldValue);
					
					//方法二：我可以自己写一个反射赋值的思路：获取属性，对属性操作
					//直接通过反射技术来给属性赋值
					//并且这个是跳过了setter方法的
					
					Field field = clazz.getDeclaredField(fieldName);
					field.setAccessible(true);
					field.set(entity,fieldValue);
				}
				
			}else{
				System.out.println("集合为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtiles.release(resultSet, preparedStatement, connection);
		}
		
		return entity;
	}

}
