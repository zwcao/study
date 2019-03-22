package com.weston.study.boot.mongo.starter;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by nbfujx on 2017-12-08.
 */
public interface UserRepository extends MongoRepository<User, Long> {

	/**
	 * Like（模糊查询） {"username" : name} ( name as regex)
	 */
	List<User> findByUsernameLike(String username);

	/**
	 * Like（模糊查询） {"username" : name}
	 */
	List<User> findByUsername(String username);

	/**
	 * GreaterThan(大于) {"age" : {"$gt" : age}}
	 */
	List<User> findByAgeGreaterThan(int age);

	/**
	 * LessThan（小于） {"age" : {"$lt" : age}}
	 */
	List<User> findByAgeLessThan(int age);

	/**
	 * Between（在...之间） {{"age" : {"$gt" : from, "$lt" : to}}
	 */
	List<User> findByAgeBetween(int from, int to);

	/**
	 * IsNotNull, NotNull（是否非空） {"username" : {"$ne" : null}}
	 */
	List<User> findByUsernameNotNull();

	/**
	 * IsNull, Null（是否为空） {"username" : null}
	 */
	List<User> findByUsernameNull();

	/**
	 * Not（不包含） {"username" : {"$ne" : name}}
	 */
	List<User> findByUsernameNot(String name);
	
	/**
	 * IN (在列表) {"age" : {"$in" : [1,2,3]}}
	 * @param ages
	 * @return
	 */
	List<User> findByAgeIn(List<Integer> ages);
	
	List<User> findByAgeInAndUsernameLike(List<Integer> ages, String username);
	
	List<User> findByBirthdayLessThan(Date date);

	/**
	 * Near（查询地理位置相近的） {"location" : {"$near" : [x,y]}}
	 */
	// findByLocationNear(Point point)

	/**
	 * Within（在地理位置范围内的） {"location" : {"$within" : {"$center" : [ [x, y],
	 * distance]}}}
	 */
	// findByLocationWithin(Circle circle)

	/**
	 * Within（在地理位置范围内的） {"location" : {"$within" : {"$box" : [ [x1, y1], x2, y2]}}}
	 */
	// findByLocationWithin(Box box)

}