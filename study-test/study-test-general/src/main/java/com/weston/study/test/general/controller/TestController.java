package com.weston.study.test.general.controller;

import com.weston.study.test.general.dao.UserDao;
import com.weston.study.test.general.domain.UserDO;
import com.weston.study.test.general.domain.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
public class TestController {

	@Autowired
	private UserDao userDao;

	@RequestMapping("add")
	public String add() {
		UserDO userDO = new UserDO();
		userDO.setAge(1);
		userDO.setAmount(BigDecimal.ZERO);
		userDO.setBirthday(new Date());
		userDO.setUsername("aaa");
		String id = userDao.insert(userDO);

		print(id);
		return "success";
	}

	@RequestMapping("remove")
	public String remove() {
		Integer n = userDao.deleteById("123");
		print(n);
		return "success";
	}

	@RequestMapping("update")
	public String update() {
		return "success";
	}

	@RequestMapping("query")
	public String query() {
		UserQuery query = new UserQuery();
		query.setId("5d4973f32c8cb7bd3ef57ce2");
		query.setGtAge(2);
		List<UserDO> users = this.userDao.query(query);
		return users.toString();
	}

	private void print(Object content) {
		System.out.println(content);
	}
}
