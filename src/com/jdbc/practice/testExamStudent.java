package com.jdbc.practice;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import com.jdbc.jdbcUtiles.JDBCUtiles;

public class testExamStudent {

	public static void main(String[] args) {
		/***********************************************************************/		
	/*	Scanner scanner = new Scanner(System.in);
		System.out.println("请输入查询条件：");
		System.out.println("a. 身份证查询");
		System.out.println("b. 准考证号查询");
		
		String condition = scanner.next().toLowerCase();
		
		switch (condition) {
		case "a":{
			System.out.println("请输入身份证号：");
			String idcard = scanner.next();
			
			Connection connection = null;
			Statement statement = null;
			ResultSet resultSet = null;
			
			
			try {
				
				connection = JDBCUtiles.getConnection();
				statement = connection.createStatement();
				String sql = "select *from examstudent where idcard = '"+idcard+"'";
				resultSet = statement.executeQuery(sql);
				if (resultSet.next()) {
					 int flowid = resultSet.getInt("flowid");
					 int type = resultSet.getInt("type");
					 String idcard1 = resultSet.getString("idcard");
					 String examcard = resultSet.getString("examcard");
					 String studentname = resultSet.getString("studentname");
					 String location = resultSet.getString("location");
					 int grade = resultSet.getInt("grade");
					 
					 System.out.println("查询结果如下：");
					 System.out.print("flowid: ");
					 System.out.println(flowid);
					 System.out.print("type: ");
					 System.out.println(type);
					 System.out.print("examcard: ");
					 System.out.println(examcard);
					 System.out.print("idcard: ");
					 System.out.println(idcard1);
					 System.out.print("studentname: ");
					 System.out.println(studentname);
					 System.out.print("location: ");
					 System.out.println(location);
					 System.out.print("grade: ");
					 System.out.println(grade);
					 
				}else{
					System.out.println("查询无果！");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				JDBCUtiles.release(resultSet, statement, connection);
			}
		}break;
		
		
		case "b":{
			System.out.println("请输入准考证号：");
			String examcard = scanner.next();
			
			Connection connection = null;
			Statement statement = null;
			ResultSet resultSet = null;
			
			
			try {
				
				connection = JDBCUtiles.getConnection();
				statement = connection.createStatement();
				String sql = "select *from examstudent where idcard = '"+examcard+"'";
				resultSet = statement.executeQuery(sql);
				if (resultSet.next()) {
					 int flowid = resultSet.getInt("flowid");
					 int type = resultSet.getInt("type");
					 String idcard = resultSet.getString("idcard");
					 String examcard1 = resultSet.getString("examcard");
					 String studentname = resultSet.getString("studentname");
					 String location = resultSet.getString("location");
					 int grade = resultSet.getInt("grade");
					 
					 System.out.println("查询结果如下：");
					 System.out.print("flowid: ");
					 System.out.println(flowid);
					 System.out.print("type: ");
					 System.out.println(type);
					 System.out.print("examcard: ");
					 System.out.println(examcard1);
					 System.out.print("idcard: ");
					 System.out.println(idcard);
					 System.out.print("studentname: ");
					 System.out.println(studentname);
					 System.out.print("location: ");
					 System.out.println(location);
					 System.out.print("grade: ");
					 System.out.println(grade);
					 
				}else{
					System.out.println("查询无果！");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				JDBCUtiles.release(resultSet, statement, connection);
			}
			
		}break;

		
		default:System.out.println("查询条件出入有误，请重新启动程序！");
			break;
		}
		*/
/***********************************************************************/		
//		Scanner scanner =  new Scanner(System.in);
//		System.out.println("请输入考生信息：");
//		
//		System.out.print("Type:");
//		int type = scanner.nextInt();
//		
//		System.out.print("IDCard(18):");
//		String idcard = scanner.next();
//		
//		System.out.print("examcard(15):");
//		String examcard = scanner.next();
//		
//		System.out.print("studentname:");
//		String studentname = scanner.next();
//		
//		System.out.print("location :");
//		String location = scanner.next();
//		
//		System.out.print("grade:");
//		int  grade = scanner.nextInt();
//		
//
//		String sql = "insert into examstudent "
//				+ "values(null,"+type+",'"+idcard+"','"+examcard+"','"+studentname+"','"+location+"',"+grade+")";
//		
//		//直接调用方法来进行相关的操作！
//		JDBCUtiles.update1(sql);
	
	}

}
