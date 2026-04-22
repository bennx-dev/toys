package dev.toys.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

record OrderDto(long id, LocalDate ordered
        , LocalDate required, String customerName, String country
        , BigDecimal value, List<OrderDetailDto> details) {
}