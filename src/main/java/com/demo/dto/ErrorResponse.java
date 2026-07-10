package com.demo.dto;

public record ErrorResponse(
        Integer errCode,
        String errMessage
) {
}
