package dev.toys.product;

import dev.toys.order.OrderConflictException;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "productlineId")
    private Productline productline;

    @Id
    private Long id;
    private String name;
    private String scale;
    private String description;
    private long inStock;
    private long inOrder;
    private BigDecimal price;
    @Version
    private long version;

    public Product(Productline productline, String name, String scale, String description, long inStock, long inOrder, BigDecimal price, long version) {
        this.productline = productline;
        this.name = name;
        this.scale = scale;
        this.description = description;
        this.inStock = inStock;
        this.inOrder = inOrder;
        this.price = price;
        this.version = version;
    }

    public Product() {
    }

    public Productline getProductline() {
        return productline;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getScale() {
        return scale;
    }

    public String getDescription() {
        return description;
    }

    public long getInStock() {
        return inStock;
    }

    public long getInOrder() {
        return inOrder;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getVersion() {
        return version;
    }

    public void reserveOrder(long ordered) {
        if (!(inStock >= ordered)) {
            throw new OrderConflictException("Niet genoeg voorraad: " + this.getName());
        }
        ;
        this.inStock -= ordered;
        this.inOrder -= ordered;
    }
}