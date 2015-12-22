package com.mkyong.core;

import com.mkyong.config.SpringMongoConfig;
import com.mkyong.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Delete example
 *
 * @author mkyong
 *
 */

public class DeleteApp {

	public static void main(String[] args) {
		// For Annotation
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

		// insert 6 users for testing
		List<User> users = new ArrayList<User>();

		User user1 = new User("1001", "ant", 10);
		User user2 = new User("1002", "bird", 20);
		User user3 = new User("1003", "cat", 30);
		User user4 = new User("1004", "dog", 40);
		User user5 = new User("1005", "elephant", 50);
		User user6 = new User("1006", "frog", 60);
		users.add(user1);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		users.add(user5);
		users.add(user6);
		mongoOperation.insert(users, User.class);

		deleteDoc("elephant","ic","1005");
		deleteDoc("cat");

		System.out.println("\nAll users : ");
		List<User> allUsers = mongoOperation.findAll(User.class);
		for (User user : allUsers) {
			System.out.println(user);
		}

		mongoOperation.dropCollection(User.class);

	}

	public static void deleteDoc(String name) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		User userTest = mongoOperation.findAndRemove(query, User.class);
		System.out.println("Deleted document : " + userTest);
	}

	public static void deleteDoc(String name, String parameter, String id) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

		Query query = new Query();
		query.addCriteria(
				Criteria.where("name").is(name)
						.andOperator(
								Criteria.where(parameter).is(id)
						)
		);
		User userTest = mongoOperation.findAndRemove(query, User.class);
		System.out.println("Deleted document : " + userTest);
	}

}
