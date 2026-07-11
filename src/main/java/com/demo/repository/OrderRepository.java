package com.demo.repository;

import com.demo.dto.SearchOrderRequest;
import com.demo.entity.Orders;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.hibernate.query.Order;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Orders> {
    public PanacheQuery<Orders> getByCustomerId(SearchOrderRequest request){
        return request.customerId() == null ? findAll():find("customer.id", request.customerId());
    }
}
