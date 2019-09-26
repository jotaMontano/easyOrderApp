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
 * A ProductByOrder.
 */
@Entity
@Table(name = "product_by_order")
public class ProductByOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "jhi_comment", nullable = false)
    private String comment;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private OrderOk orderOk;

    @OneToMany(mappedBy = "productByOrder")
    private Set<ExtraInLine> extraInLines = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("products")
    private Product products;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductByOrder quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getComment() {
        return comment;
    }

    public ProductByOrder comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isStatus() {
        return status;
    }

    public ProductByOrder status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public OrderOk getOrderOk() {
        return orderOk;
    }

    public ProductByOrder orderOk(OrderOk orderOk) {
        this.orderOk = orderOk;
        return this;
    }

    public void setOrderOk(OrderOk orderOk) {
        this.orderOk = orderOk;
    }

    public Set<ExtraInLine> getExtraInLines() {
        return extraInLines;
    }

    public ProductByOrder extraInLines(Set<ExtraInLine> extraInLines) {
        this.extraInLines = extraInLines;
        return this;
    }

    public ProductByOrder addExtraInLine(ExtraInLine extraInLine) {
        this.extraInLines.add(extraInLine);
        extraInLine.setProductByOrder(this);
        return this;
    }

    public ProductByOrder removeExtraInLine(ExtraInLine extraInLine) {
        this.extraInLines.remove(extraInLine);
        extraInLine.setProductByOrder(null);
        return this;
    }

    public void setExtraInLines(Set<ExtraInLine> extraInLines) {
        this.extraInLines = extraInLines;
    }

    public Product getProducts() {
        return products;
    }

    public ProductByOrder products(Product product) {
        this.products = product;
        return this;
    }

    public void setProducts(Product product) {
        this.products = product;
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
        ProductByOrder productByOrder = (ProductByOrder) o;
        if (productByOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productByOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductByOrder{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", comment='" + getComment() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
