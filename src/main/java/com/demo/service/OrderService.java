package com.demo.service;

import com.demo.dto.OrderDetailRequest;
import com.demo.dto.OrderRequest;
import com.demo.dto.PageResponse;
import com.demo.dto.SearchOrderRequest;
import com.demo.entity.Customer;
import com.demo.entity.OrderDetail;
import com.demo.entity.Orders;
import com.demo.repository.OrderRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class OrderService {

    @Inject
    OrderDetailServices orderDetailServices;
    @Inject
    OrderRepository orderRepository;
    @Transactional
    public Orders create(OrderRequest request){
        Customer customer = (Customer) Customer.findByIdOptional(request.customerId()).orElseThrow(()-> new NotFoundException("Customer Not Found"));
        Orders orders = new Orders();
        orders.setCustomer(customer);
        orders.setTransdate(new Date());
        orderRepository.persist(orders);

        List<OrderDetail> orderDetails = new ArrayList<>();

        for (OrderDetailRequest orderDetailRequest : request.orderDetail()){
            OrderDetail orderDetail = orderDetailServices.create(orderDetailRequest, orders);
            orderDetails.add(orderDetail);
        }
        orders.setOrderDetails(orderDetails);
        return orders;
    }

    public Orders getById(long id){
        return orderRepository.findByIdOptional(id).orElseThrow(()->new NotFoundException("Order not found"));
    }

    public PageResponse<Orders> getAll(SearchOrderRequest request){
        int page = request.page()-1;
        PanacheQuery<Orders> query = orderRepository.getByCustomerId(request);
        query.page(page, request.size());
        return new PageResponse<>(query.list(), query.count(), query.pageCount(), request.page(), request.size());
    }
}
