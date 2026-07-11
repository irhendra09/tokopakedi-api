package com.demo.dto;

import jakarta.validation.constraints.Min;

public record OrderDetailRequest(
        Long productId,
        @Min(1)
        Integer quantity
) {
}
