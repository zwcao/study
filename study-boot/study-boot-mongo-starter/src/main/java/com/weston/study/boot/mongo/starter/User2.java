package com.weston.study.boot.mongo.starter;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

public class User2 implements Serializable {

	private static final long serialVersionUID = -1L;

	@Id
	private Long id;
	private String username;
	private Integer age;

	public User2(Long id, String username, Integer age) {
		this.id = id;
		this.username = username;
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	@Override
	public String toString() {
		return "\"User2:{\"id\":\"" + id + "\",\"username\":\"" + username + "\",\"age\":\"" + age + "\"}\"";
	}
}