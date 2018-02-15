package com.jdbc.dao;

import java.util.List;

import org.junit.Test;

import com.jdbc.model.Stu;

public class teatDAO {
	
	DAO dao = new DAO();
	
	@Test
	
	public void testGetForValue(){
		String sql = "select name from my_Stu where id = ?";
		Object object = dao.getForValue(sql, 6);
		System.out.println(object);
	}
	public void testGetForList(){
		String sql = "select *from my_Stu" ;
		List<Stu> list = dao.getForList(Stu.class, sql);
		for(Stu stu : list){
			System.out.println(stu);
		}
		
	}
	
	public void testGet(){
		String sql = "select * from my_stu where id = ?";
		Stu stu = dao.get(Stu.class, sql, 6);
		System.out.println(stu);
	}
	
	
	public void testUpdate(){
		String sql = "update my_stu set name = ? where id = ?";
		dao.update(sql, "¡Ô¡Ôﬂ¬",6);
	}

}
