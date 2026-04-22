package dev.toys.customer;

import dev.toys.country.Country;
import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "countryId")
    private Country country;

    @Id
    private Long id;
    private String name;
    private String streetAndNumber;
    private String city;
    private String state;
    private String postalCode;
    @Version
    private Long version;

    public Customer(Country country, String name, String streetAndNumber, String city, String state, String postalCode, Long version) {
        this.country = country;
        this.name = name;
        this.streetAndNumber = streetAndNumber;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.version = version;
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Long getVersion() {
        return version;
    }

    public Country getCountry() {
        return country;
    }
}