package dev.toys.order;

import dev.toys.customer.Customer;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> details;

    @Id
    private Long id;
    private LocalDate ordered;
    private LocalDate required;
    private LocalDate shipped;
    private String comments;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Version
    private long version;

    public Order(Customer customer, LocalDate ordered, LocalDate required, LocalDate shipped, String comments, long version, OrderStatus status) {
        this.customer = customer;
        this.ordered = ordered;
        this.required = required;
        this.shipped = shipped;
        this.comments = comments;
        this.status = status;
        this.version = version;

        details = new LinkedHashSet<>();
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public LocalDate getOrdered() {
        return ordered;
    }

    public LocalDate getRequired() {
        return required;
    }

    public LocalDate getShipped() {
        return shipped;
    }

    public String getComments() {
        return comments;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public long getVersion() {
        return version;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Set<OrderDetail> getDetails() {
        return details;
    }

    public void setStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }

    public void setShippedDate(LocalDate date) {
        this.shipped = date;
    }
}