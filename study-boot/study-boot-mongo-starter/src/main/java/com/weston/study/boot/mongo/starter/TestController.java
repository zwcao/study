package com.weston.study.boot.mongo.starter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.script.ExecutableMongoScript;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.ReadPreference;

@RestController
public class TestController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@RequestMapping("add")
	public String add() {
		this.mongoTemplate.setReadPreference(ReadPreference.primary());;
		String command = "db.user.find();";
//		String command = "db.user.update({}, { $set: { \"name\":\"bbb\" }},{\"multi\":true });";
		
//		Document doc = (Document) mongoTemplate.scriptOps().execute(new ExecutableMongoScript(command) , "directly execute script");
		Object obj = mongoTemplate.scriptOps().execute(new ExecutableMongoScript("var array = [];var cursor = " + command + "; "
				+ "while(cursor.hasNext()){ array.push(cursor.next());} return array;") , "directly execute script");
//		User user = new User(null, "aaa", 1, new Date(), BigDecimal.valueOf(100));
//		userRepository.insert(user);
//		nInserted=0.0, nUpserted=0.0, nMatched=12.0, nModified=12.0, nRemoved=0.0x
		return "success";
	}

	@RequestMapping("remove")
	public String remove() {
		userRepository.deleteById(1L);
		return "success";
	}

	@RequestMapping("update")
	public String update() {
		User user = new User(null, "aaa11", 1, new Date(), BigDecimal.valueOf(100));
		userRepository.save(user);
		return "success";
	}

	@RequestMapping("query")
	public String query() {
		// List<User> users = userRepository.findByUsernameLike("aa");
		// List<User> users = userRepository.findByAgeIn(Arrays.asList(2));
//		List<User> users = userRepository.findByAgeInAndUsernameLike(Arrays.asList(1), "aa");
		List<User> users = userRepository.findByBirthdayLessThan(new Date());
		return users.toString();
	}

	@RequestMapping("flexQuery")
	public String flexQuery() {
		
		User2 user = mongoTemplate.query(User2.class).inCollection("user").first().get();
		System.out.println(user);
		
		Query query = new Query();
		Criteria criteria = Criteria.where("age").in(Arrays.asList(1));
		query.addCriteria(criteria);
		List<User2> users = mongoTemplate.find(query, User2.class, "user");
		mongoTemplate.executeQuery(query, "user", (document) -> {
			System.out.println(document);
		});
		return users.toString();
	}
}
