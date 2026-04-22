package dev.toys.country;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    private Long id;
    private String name;
    @Version
    private long version;

    public Country(String name, long version) {
        this.name = name;
        this.version = version;
    }

    public Country() {
    }

    public Long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }
}