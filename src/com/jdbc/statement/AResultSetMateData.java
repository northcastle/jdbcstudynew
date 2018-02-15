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
		 * ResultSetMateData:Ԫ����
		 * 		���Ի�ȡ���ؽ�����ĵ�Ԫ������Ϣ��
		 * 			�������ݵ����ݣ��������һ�£�
		 * 			һ�������ѯ��sql�����
		 * 	  1.getColumnCount():��ȡ�еĸ���
		 * 	  2.getColumnLable(index):��ȡ�еı�����ע���Ǳ���,������1��ʼ�������
		 * дsql����ʱ����Ҫע�⣺�еı�����Ҫ������������һ�£��������Ĳ���
		 * �������sql������Ϊ�Ѿ����������һ���ˣ���û���������
		 * 		String sql = "select id,name,sex,age,height,c_id from my_stu"
							+ "where id = ?";
		 * 
		 */
		String sql = "select id ,name , sex ,age ,height ,c_id  from my_stu where id = ?";
		Stu stu = AResultSetMateData.get(Stu.class, sql, 6);
		System.out.println(stu);
		
	}
	
	/**
	 * Я��һ��ͨ�õķ�������ȡĳ�����һ������һ������
	 * @param clazz��ĳ��ʵ����
	 * @param sql����ѯ��sql ���
	 * @param args��sql����Ҫ����Ĳ���
	 * @return
	 */
	public static <T> T get(Class<T> clazz,String sql,Object...args){
		
		T entity = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			//�����ĳ������
			connection = JDBCUtiles.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			for(int i = 0;i < args.length;i++){
				preparedStatement.setObject(i+1, args[i]);
			}
			resultSet = preparedStatement.executeQuery();
			
			//�²�������ȡ�������Ԫ���ݣ�׼������
			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			//׼��һ��map,ͨ����ֵ�Եķ��������ȡ��������
			Map<String, Object> map = new HashMap<>();
			
			if (resultSet.next()) {
				//ͨ��ѭ������ʽ��ȡ���������Ϣ,���Ұ���Ϣ���浽map�ļ�ֵ����
				for(int i = 0;i < rsmd.getColumnCount();i++){
					String columnLable = rsmd.getColumnLabel(i+1);
					Object columnValue = resultSet.getObject(i+1);
					map.put(columnLable, columnValue);
				}
			}
			
			//�жϵ����ϲ�Ϊ��ʱ���Ž�����һ������
			if (map.size() > 0) {
				//entity������Ҫ���صĶ���
				entity = clazz.newInstance();
				//Ȼ��ѭ������map��stu����ֵ(��ȻҪ������������ķ���)
				for(Map.Entry<String, Object> entry : map.entrySet()){
					
					String fieldName = entry.getKey();
					Object fieldValue = entry.getValue();
					
					//System.out.println(fieldName+" : "+fieldValue);
					//����һ������ط��ǵ�����һ��д�õķ���
					//ReflectionUtils.setFieldValue(entity, fieldName, fieldValue);
					
					//���������ҿ����Լ�дһ�����丳ֵ��˼·����ȡ���ԣ������Բ���
					//ֱ��ͨ�����似���������Ը�ֵ
					//���������������setter������
					
					Field field = clazz.getDeclaredField(fieldName);
					field.setAccessible(true);
					field.set(entity,fieldValue);
				}
				
			}else{
				System.out.println("����Ϊ��");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtiles.release(resultSet, preparedStatement, connection);
		}
		
		return entity;
	}

}
