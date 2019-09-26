package com.moonshine.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Discount.
 */
@Entity
@Table(name = "discount")
public class Discount implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "percentage", nullable = false)
    private Double percentage;

    @NotNull
    @Column(name = "star_date", nullable = false)
    private Instant starDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @Column(name = "start_hour")
    private Instant startHour;

    @Column(name = "end_hour")
    private Instant endHour;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "discount_days",
               joinColumns = @JoinColumn(name = "discount_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "days_id", referencedColumnName = "id"))
    private Set<ListOfValue> days = new HashSet<>();

    @ManyToMany(mappedBy = "discounts")
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("discounts")
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Discount name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPercentage() {
        return percentage;
    }

    public Discount percentage(Double percentage) {
        this.percentage = percentage;
        return this;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Instant getStarDate() {
        return starDate;
    }

    public Discount starDate(Instant starDate) {
        this.starDate = starDate;
        return this;
    }

    public void setStarDate(Instant starDate) {
        this.starDate = starDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public Discount endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getStartHour() {
        return startHour;
    }

    public Discount startHour(Instant startHour) {
        this.startHour = startHour;
        return this;
    }

    public void setStartHour(Instant startHour) {
        this.startHour = startHour;
    }

    public Instant getEndHour() {
        return endHour;
    }

    public Discount endHour(Instant endHour) {
        this.endHour = endHour;
        return this;
    }

    public void setEndHour(Instant endHour) {
        this.endHour = endHour;
    }

    public Boolean isStatus() {
        return status;
    }

    public Discount status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<ListOfValue> getDays() {
        return days;
    }

    public Discount days(Set<ListOfValue> listOfValues) {
        this.days = listOfValues;
        return this;
    }

    public Discount addDays(ListOfValue listOfValue) {
        this.days.add(listOfValue);
        listOfValue.getDiscounts().add(this);
        return this;
    }

    public Discount removeDays(ListOfValue listOfValue) {
        this.days.remove(listOfValue);
        listOfValue.getDiscounts().remove(this);
        return this;
    }

    public void setDays(Set<ListOfValue> listOfValues) {
        this.days = listOfValues;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Discount products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Discount addProducts(Product product) {
        this.products.add(product);
        product.getDiscounts().add(this);
        return this;
    }

    public Discount removeProducts(Product product) {
        this.products.remove(product);
        product.getDiscounts().remove(this);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Client getClient() {
        return client;
    }

    public Discount client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Discount discount = (Discount) o;
        if (discount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), discount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Discount{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", percentage=" + getPercentage() +
            ", starDate='" + getStarDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", startHour='" + getStartHour() + "'" +
            ", endHour='" + getEndHour() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
