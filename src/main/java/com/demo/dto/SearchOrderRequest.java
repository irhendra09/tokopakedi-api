package com.demo.dto;

public record SearchOrderRequest(
        Integer page,
        Integer size,
        Long customerId
) {
}
