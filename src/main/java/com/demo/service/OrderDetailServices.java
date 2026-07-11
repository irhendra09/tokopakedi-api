package com.demo.service;

import com.demo.dto.OrderDetailRequest;
import com.demo.entity.OrderDetail;
import com.demo.entity.Orders;
import com.demo.entity.Product;
import com.demo.repository.OrderDetailRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;

@ApplicationScoped
public class OrderDetailServices {
    @Inject
    OrderDetailRepository orderDetailRepository;
    @Inject
    ProductService productService;
    @Transactional
    public OrderDetail create(OrderDetailRequest request, Orders orders){
        OrderDetail orderDetail = new OrderDetail();
        Product product = productService.getById(request.productId());
        if (product.getStock()- request.quantity() < 0){
            throw new BadRequestException("quantity exceeds");
        }
        product.setStock(product.getStock()-request.quantity());
        orderDetail.setProductPrice(product.getPrice());
        orderDetail.setQuantity(request.quantity());
        orderDetail.setOrder(orders);
        orderDetail.setProduct(product);
        orderDetailRepository.persist(orderDetail);
        return orderDetail;
    }
}
