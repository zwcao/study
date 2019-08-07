//package com.weston.study.boot.elasticsearch.starter;
//
//import java.math.BigDecimal;
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class TestController {
//
//	@Autowired
//	private PersonRepository personRepository;
//
//	@Autowired
//	private ElasticsearchTemplate elasticsearchTemplate;
//
//	@RequestMapping("add")
//	public String add() {
//		Person p = new Person();
//		p.setId(1L);
//		p.setAddress("bei jing");
//		p.setBirthday(new Date());
//		p.setName("jack");
//		p.setSalary(BigDecimal.valueOf(100.1));
//		personRepository.save(p);
//
//		return "success";
//	}
//}
