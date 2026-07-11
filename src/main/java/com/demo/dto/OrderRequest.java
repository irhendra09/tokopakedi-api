package com.demo.dto;

import java.util.List;

public record OrderRequest(
        Long customerId,
        List<OrderDetailRequest> orderDetail
) {
}
