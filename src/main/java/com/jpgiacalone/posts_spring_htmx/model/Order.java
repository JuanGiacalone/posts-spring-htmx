package com.jpgiacalone.posts_spring_htmx.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Order {
    @Id
    private String id = UUID.randomUUID().toString();
    private String customerId;
    private Date date;
    private List<String> products;
    private List<String> taxes;
    private Long subtotal;
    private Long total;

    public Order(){}

    public Order(String customerId, Date date, List<String> products, List<String> taxes, Long subtotal, Long total) {
        this.customerId = customerId;
        this.date = date;
        this.products = products;
        this.taxes = taxes;
        this.subtotal = subtotal;
        this.total = total;
    }



    
    public String getId() {
        return id;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public List<String> getProducts() {
        return products;
    }
    public void setProducts(List<String> products) {
        this.products = products;
    }
    public List<String> getTaxes() {
        return taxes;
    }
    public void setTaxes(List<String> taxes) {
        this.taxes = taxes;
    }
    public Long getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(Long subtotal) {
        this.subtotal = subtotal;
    }
    public Long getTotal() {
        return total;
    }
    public void setTotal(Long total) {
        this.total = total;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", customerId=" + customerId + ", date=" + date + ", products=" + products
                + ", taxes=" + taxes + ", subtotal=" + subtotal + ", total=" + total + "]";
    }

    

}
