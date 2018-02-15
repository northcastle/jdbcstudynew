package com.jdbc.model;

public class Stu {
	
	private int id;
	private String name;
	private String sex;
	private int age;
	private double height;
	private int c_id;
	
	
	public Stu() {
		super();
	}
	
	public Stu(int id, String name, String sex, int age, double height, int c_id) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.height = height;
		this.c_id = c_id;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public int getC_id() {
		return c_id;
	}
	public void setC_id(int c_id) {
		this.c_id = c_id;
	}

	@Override
	public String toString() {
		return "Stu [id=" + id + ", name=" + name + ", sex=" + sex + ", age=" + age + ", height=" + height + ", c_id="
				+ c_id + "]";
	}
	
	
	

}
