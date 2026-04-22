package dev.toys.order;

import dev.toys.product.Product;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "orderdetails")
public class OrderDetail {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    @Id
    private Long id;
    private long ordered;
    private BigDecimal priceEach;

    public OrderDetail(Order order, Product product, long ordered, BigDecimal priceEach) {
        this.order = order;
        this.product = product;
        this.ordered = ordered;
        this.priceEach = priceEach;
    }

    public OrderDetail() {
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    public Long getId() {
        return id;
    }

    public long getOrdered() {
        return ordered;
    }

    public BigDecimal getPriceEach() {
        return priceEach;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetail that = (OrderDetail) o;
        return Objects.equals(order, that.order) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product);
    }
}