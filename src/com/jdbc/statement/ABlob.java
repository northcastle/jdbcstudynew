package com.jdbc.statement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import com.jdbc.jdbcUtiles.JDBCUtiles;

public class ABlob {
	public static void main(String[] args) {
		ABlob aBlob = new ABlob();
		aBlob.getBlob();
		
	}
	
	/**
	 * �����ݿ����������ͼƬ����Ϣ��������������ͼƬ���浽������
	 * 
	 * 
	 * 
	 */
	public void getBlob(){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JDBCUtiles.getConnection();
			String sql = "select picture from my_stu where id = 18";
			preparedStatement = connection.prepareStatement(sql);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				//ͨ��Ԫ���ݵķ�ʽ����������Ϊ����ϰһ���������
				ResultSetMetaData rsmd = resultSet.getMetaData();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					String fieldLable = rsmd.getColumnLabel(i+1);
					Blob picture = resultSet.getBlob(fieldLable);
					//**����һ��д����
					//String path = "C:\\Users\\North Castle\\Desktop\\ͼ��\\4.jpg";
					//File file= new File(path);
					//FileOutputStream fos = new FileOutputStream(file);
					//fos.write(picture.getBytes(1, (int) picture.length()));
					
					
					//**���ǵڶ���д������ȡһ����������Ȼ����ж�д����
					InputStream is = picture.getBinaryStream();
					String pathname = "C:\\Users\\North Castle\\Desktop\\ͼ��\\6.jpg";
					File file = new File(pathname);
					FileOutputStream fos = new FileOutputStream(file);
					
					byte[] b = new byte[1024];
					int len = 0;
					while((len = is.read(b)) != -1){
						fos.write(b, 0, len);
					}
					
					is.close();
					fos.close();
					
				}
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally{
			JDBCUtiles.release(resultSet, preparedStatement, connection);
		}
		
	}
	
	/**
	 * �����ݿ�����д��ͼƬ�Ĳ���:
	 * ��Ȼ��Ҫ�õ�io���Ĳ���
	 * �����ϾͶ������Ĳ���
	 */
	public void setBlob(){

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = JDBCUtiles.getConnection();
			String sql = "update my_stu set picture = ? where id = ?";
			preparedStatement = connection.prepareStatement(sql);
			
			//1.ͨ���ļ�IO ��ȡ��һ��Ҫ�����ͼƬ
			String path = "C:\\Users\\North Castle\\Desktop\\ͼ��\\2.jpg";
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			//2.���÷�����ͼƬ�����ȥ:ʵ�����ǰ������������ȥ����������
			preparedStatement.setBlob(1, fis);
			//�ر���
			fis.close();
			
			preparedStatement.setInt(2, 18);
			
			preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			
			
			JDBCUtiles.release(null, preparedStatement, connection);
		}
	}

}
