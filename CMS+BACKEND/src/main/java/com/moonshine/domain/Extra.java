package com.moonshine.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Extra.
 */
@Entity
@Table(name = "extra")
public class Extra implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "extra")
    private Set<ExtraInLine> extraInLines = new HashSet<>();
    @ManyToMany(mappedBy = "extras")
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("extras")
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

    public Extra name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public Extra price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean isStatus() {
        return status;
    }

    public Extra status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<ExtraInLine> getExtraInLines() {
        return extraInLines;
    }

    public Extra extraInLines(Set<ExtraInLine> extraInLines) {
        this.extraInLines = extraInLines;
        return this;
    }

    public Extra addExtraInLine(ExtraInLine extraInLine) {
        this.extraInLines.add(extraInLine);
        extraInLine.setExtra(this);
        return this;
    }

    public Extra removeExtraInLine(ExtraInLine extraInLine) {
        this.extraInLines.remove(extraInLine);
        extraInLine.setExtra(null);
        return this;
    }

    public void setExtraInLines(Set<ExtraInLine> extraInLines) {
        this.extraInLines = extraInLines;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Extra products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Extra addProducts(Product product) {
        this.products.add(product);
        product.getExtras().add(this);
        return this;
    }

    public Extra removeProducts(Product product) {
        this.products.remove(product);
        product.getExtras().remove(this);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Client getClient() {
        return client;
    }

    public Extra client(Client client) {
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
        Extra extra = (Extra) o;
        if (extra.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), extra.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Extra{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
