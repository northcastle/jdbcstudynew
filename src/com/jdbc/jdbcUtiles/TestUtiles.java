package com.jdbc.jdbcUtiles;

import com.jdbc.model.Stu;

public class TestUtiles {

	public static void main(String[] args) {
//		String sql = "delete from my_stu where id = 13";
//		System.out.println(JDBCUtiles.update1(sql));
//		System.out.println("删除成功");
		
		/*String sql = "select *from my_stu";
		List<Stu> stus = JDBCUtiles.query1(sql);
		
		Iterator iterator = stus.iterator();
		while(iterator.hasNext()){
			System.out.println("这里是集合的while循环遍历");
			System.out.println(iterator.next());
		}
		System.out.println("************************");
		for (Stu stu : stus) {
			System.out.println("这里是for循环便利");
			System.out.println(stu);
		}*/
		
//		String sql = "insert into my_stu(name,sex,age,height,c_id)"
//				+ "values(?,?,?,?,?)";
//		
//		System.out.println(JDBCUtiles.update(sql, "吴畅","男",23,170,2));
//		
		
//		
//		String sql = "select * from my_stu where id = ?";
//		Stu stu = JDBCUtiles.getStu(sql, 5);
//		System.out.println(stu);
		
		System.out.println(JDBCUtiles.getConnection());
	}

}
