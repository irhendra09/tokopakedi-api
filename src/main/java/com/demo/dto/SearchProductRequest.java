package com.demo.dto;

import java.math.BigDecimal;

public record SearchProductRequest(
        Integer page,
        Integer size,
        String name,
        Double minPrize,
        Double maxPrize
) {

    public SearchProductRequest{
        page = page<1 ? 1 : page;
    }
}
