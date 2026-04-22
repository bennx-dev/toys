package dev.toys.order;

import java.math.BigDecimal;

record OrderDetailDto(long ordered, BigDecimal priceEach
        , BigDecimal value, String productName) {
}