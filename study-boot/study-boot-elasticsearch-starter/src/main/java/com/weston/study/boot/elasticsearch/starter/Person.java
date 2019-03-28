package com.weston.study.boot.elasticsearch.starter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "person", type = "person")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Integer age;
	private BigDecimal salary;
	private Date birthday;
	private String address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
