package com.weston.study.boot.mongo.starter;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;

public class User implements Serializable {

	private static final long serialVersionUID = -1L;

	@Id
	private String id;
	private String username;
	private Integer age;
	private Date birthday;

	public User(String id, String username, Integer age, Date birthday) {
		this.id = id;
		this.username = username;
		this.age = age;
		this.birthday = birthday;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "\"User:{\"id\":\"" + id + "\",\"username\":\"" + username + "\",\"age\":\"" + age + "\",\"birthday\":\"" + birthday + "\"}\"";
	}
}