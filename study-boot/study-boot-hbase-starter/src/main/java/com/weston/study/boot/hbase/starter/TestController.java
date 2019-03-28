package com.weston.study.boot.hbase.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@Autowired
	private HbaseTemplate hbaseTemplate;

	@RequestMapping("add")
	public String add() {
		return "success";
	}
}
