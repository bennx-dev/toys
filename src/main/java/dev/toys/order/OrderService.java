package dev.toys.order;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    List<UnshippedOrdersDto> findUnshippedOrders() {
        var orders = orderRepository.findAllByStatusNotInOrderById(List.of(OrderStatus.CANCELLED, OrderStatus.SHIPPED));

        return orders.stream().map(order -> new UnshippedOrdersDto(
                        order.getId()
                        , order.getOrdered()
                        , order.getRequired()
                        , order.getCustomer().getName()
                        , order.getStatus().toString()
                ))
                .toList();
    }

    OrderDto findOrderDetailsById(Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);

        var orderDetailDto = order
                .getDetails()
                .stream()
                .map(detail -> new OrderDetailDto(
                        detail.getOrdered()
                        , detail.getPriceEach()
                        , BigDecimal.valueOf(detail.getOrdered()).multiply(detail.getPriceEach())
                        , detail.getProduct().getName()))
                .toList();

        var value = orderDetailDto
                .stream()
                .map(OrderDetailDto::value)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new OrderDto(
                order.getId()
                , order.getOrdered()
                , order.getRequired()
                , order.getCustomer().getName()
                , order.getCustomer().getCountry().getName()
                , value
                , orderDetailDto);
    }

    @Transactional
    void shipOrder(Long id) {
        try {
            var order = orderRepository.findAndLockById(id)
                    .orElseThrow(OrderNotFoundException::new);

            if (order.getStatus().equals(OrderStatus.SHIPPED)) {
                throw new OrderConflictException("Order is al verzonden");
            }

            order.setStatus(OrderStatus.SHIPPED);
            order.setShippedDate(LocalDate.now());
            order.getDetails()
                    .forEach(detail ->
                            detail.getProduct()
                                    .reserveOrder(detail.getOrdered()));
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw new OrderConflictException("Order kan op dit moment niet verzonden worden omdat het tijdelijk in gebruik is door iemand anders");

        }
    }
}