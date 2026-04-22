package dev.toys.order;

import java.time.LocalDate;

record UnshippedOrdersDto(long id, LocalDate ordered
        , LocalDate required, String customerName
        , String Status) {
}