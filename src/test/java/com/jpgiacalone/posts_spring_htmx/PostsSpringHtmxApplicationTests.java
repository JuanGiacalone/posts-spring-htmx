package com.jpgiacalone.posts_spring_htmx;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ContextConsumer;

import com.jpgiacalone.posts_spring_htmx.controller.DataController;
import com.jpgiacalone.posts_spring_htmx.model.Customer;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.main.web-application-type=reactive")
class PostsSpringHtmxApplicationTests {

	@Autowired
	private DataController controller;

	@Test
	void contextLoads() {

		Customer costumer = new Customer("jp", "it supp");
		


	}

}
