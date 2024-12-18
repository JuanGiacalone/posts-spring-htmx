package com.jpgiacalone.posts_spring_htmx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

@EnableReactiveMongoRepositories
public class DatabaseConfig extends AbstractReactiveMongoConfiguration {
    
    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create();
    }

    @SuppressWarnings("null")
    @Override
    protected String getDatabaseName() {
        return("testDB");
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(mongoClient(), getDatabaseName());
    }


}
