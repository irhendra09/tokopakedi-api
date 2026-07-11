package com.demo.dto;

import jakarta.validation.constraints.Min;

public record ProductRequest(
        String name,
        @Min(1)
        Double price,
        @Min(value = 1, message = "Stock must be greater than or equal to 1")
        Integer stock
) {
}
