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
	 * 从数据库里面读出来图片的信息，并将读出来的图片保存到桌面上
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
				//通过元数据的方式，单纯的是为了练习一下这个东西
				ResultSetMetaData rsmd = resultSet.getMetaData();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					String fieldLable = rsmd.getColumnLabel(i+1);
					Blob picture = resultSet.getBlob(fieldLable);
					//**这是一种写法：
					//String path = "C:\\Users\\North Castle\\Desktop\\图标\\4.jpg";
					//File file= new File(path);
					//FileOutputStream fos = new FileOutputStream(file);
					//fos.write(picture.getBytes(1, (int) picture.length()));
					
					
					//**这是第二中写法：获取一个输入流，然后进行读写操作
					InputStream is = picture.getBinaryStream();
					String pathname = "C:\\Users\\North Castle\\Desktop\\图标\\6.jpg";
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
	 * 往数据库里面写入图片的操作:
	 * 当然需要用到io流的操作
	 * 基本上就都是流的操作
	 */
	public void setBlob(){

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = JDBCUtiles.getConnection();
			String sql = "update my_stu set picture = ? where id = ?";
			preparedStatement = connection.prepareStatement(sql);
			
			//1.通过文件IO 获取到一张要插入的图片
			String path = "C:\\Users\\North Castle\\Desktop\\图标\\2.jpg";
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			//2.调用方法将图片出入进去:实际上是把输入流传入进去当作参数的
			preparedStatement.setBlob(1, fis);
			//关闭流
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
