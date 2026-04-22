package dev.toys.product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "productlines")
public class Productline {

    @Id
    private Long id;
    private String name;
    private String description;
    @Version
    private long version;

    public Productline(String name, String description, long version) {
        this.name = name;
        this.description = description;
        this.version = version;
    }

    public Productline() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getVersion() {
        return version;
    }
}