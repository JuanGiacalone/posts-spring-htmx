package com.jpgiacalone.posts_spring_htmx.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpgiacalone.posts_spring_htmx.model.Customer;
import com.jpgiacalone.posts_spring_htmx.model.Order;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
public class DataController {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;
    
    @PostMapping("/customer/create")
    public Mono<Customer> createCustomer(@RequestBody Customer customer){

        return reactiveMongoTemplate.save(customer);

    }
    
    @GetMapping("/customer/find")
    public Mono<Customer> findCustomerId(@RequestParam("customerId") String costumerId){

        return findCustomerbyId(costumerId);

    }

    @GetMapping("/customer/findAll")
    public Flux<Customer> findCustomers(){

        return reactiveMongoTemplate.findAll(Customer.class);

    }

    private Mono<Customer> findCustomerbyId(String id){
        Criteria criteria = Criteria.where("id").is(id);
        Query query = Query.query(criteria);

        return reactiveMongoTemplate.findOne(query,Customer.class).log();
    }

    @PostMapping("/order/")
    public Mono<Order> saveOrder(@RequestBody Order order){
            return reactiveMongoTemplate.save(order);
        
    }

    @GetMapping("/order/")
    public Mono<Order> getOrder(@RequestParam("orderId") String orderId ) {
        
        Query query = new Query(Criteria.where("id").is(orderId));
        
        return reactiveMongoTemplate.findOne(query, Order.class);
    }

    @GetMapping("/customer/orders")
    public Flux<Order> getCustomerOrders (@RequestParam("customerId") String customerId) {

        Query query = new Query(Criteria.where("customerId").is(customerId));

        return reactiveMongoTemplate.find(query, Order.class).log();

    }

    @GetMapping("/sales/summary")

    public Mono<Map<String, Long>> calculateSummary() {

        return reactiveMongoTemplate.findAll(Customer.class)
            .flatMap(customer -> Mono.zip(Mono.just(customer), sumEachOrder(customer.getId())))
            .collectMap(t-> t.getT1().getName(), Tuple2::getT2).log();

    }

    private Mono<Long> sumEachOrder(String customerId) {

        Query query = new Query(Criteria.where("customerId").is(customerId));

        return reactiveMongoTemplate.find(query, Order.class)
                .map(Order::getTotal)
                .reduce(0l, Long::sum);

    }

}

