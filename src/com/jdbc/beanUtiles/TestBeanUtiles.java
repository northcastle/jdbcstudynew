package com.jdbc.beanUtiles;

import org.apache.commons.beanutils.BeanUtils;

import com.jdbc.model.Stu;

public class TestBeanUtiles {
	
	public static void main(String ...args) throws Exception{
		Class clazz = Stu.class;
		
		Stu stu = (Stu) clazz.newInstance();
		
		System.out.println(stu);
		
		
		BeanUtils.setProperty(stu, "name", "Íõºê²ý");
		System.out.println(stu);
		
		String name = BeanUtils.getProperty(stu, "name");
		System.out.println(name);
		
		
	}
	

}
