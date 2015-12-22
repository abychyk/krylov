package com.mkyong.core;

import com.mkyong.config.SpringMongoConfig;
import com.mkyong.model.Product;
//import com.mkyong.model.User;
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
		List<Product> users = new ArrayList<Product>();

		/*Product user1 = new Product("1001", "ant", 10);
		Product user2 = new Product("1002", "bird", 20);
		Product user3 = new Product("1003", "cat", 30);
		Product user4 = new Product("1004", "dog", 40);
		Product user5 = new Product("1005", "elephant", 50);
		Product user6 = new Product("1006", "frog", 60);
		users.add(user1);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		users.add(user5);
		users.add(user6);
		mongoOperation.insert(users, Product.class);*/

		//deleteDoc("elephant","ic","1005");
		//deleteDoc("cat");
		deleteDoc("Pepperoni", "type", "PIZZA");


		System.out.println("\nAll users : ");
		List<Product> allUsers = mongoOperation.findAll(Product.class);
		for (Product user : allUsers) {
			System.out.println(user);
		}

	//	mongoOperation.dropCollection(User.class);

	}

	public static void deleteDoc(String name) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		Product userTest = mongoOperation.findAndRemove(query, Product.class);
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
		Product userTest = mongoOperation.findAndRemove(query, Product.class);
		System.out.println("Deleted document : " + userTest);
	}

}
