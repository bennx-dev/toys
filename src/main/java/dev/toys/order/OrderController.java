package dev.toys.order;

import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("unshipped")
    List<UnshippedOrdersDto> findUnshippedOrders() {
        return orderService.findUnshippedOrders();
    }

    @GetMapping("{id}")
    OrderDto findOrderDetailsById(@PathVariable @Positive Long id) {
        return orderService.findOrderDetailsById(id);
    }

    @PostMapping("{id}/shippings")
    void shipOrder(@PathVariable @Positive Long id) {
        orderService.shipOrder(id);
    }
}