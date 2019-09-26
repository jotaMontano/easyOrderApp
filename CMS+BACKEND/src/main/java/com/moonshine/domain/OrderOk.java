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
 * A OrderOk.
 */
@Entity
@Table(name = "order_ok")
public class OrderOk implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "total", nullable = false)
    private Double total;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Client client;

    @OneToMany(mappedBy = "orderOk")
    private Set<ProductByOrder> orders = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public OrderOk total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Boolean isStatus() {
        return status;
    }

    public OrderOk status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public OrderOk client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<ProductByOrder> getOrders() {
        return orders;
    }

    public OrderOk orders(Set<ProductByOrder> productByOrders) {
        this.orders = productByOrders;
        return this;
    }

    public OrderOk addOrders(ProductByOrder productByOrder) {
        this.orders.add(productByOrder);
        productByOrder.setOrderOk(this);
        return this;
    }

    public OrderOk removeOrders(ProductByOrder productByOrder) {
        this.orders.remove(productByOrder);
        productByOrder.setOrderOk(null);
        return this;
    }

    public void setOrders(Set<ProductByOrder> productByOrders) {
        this.orders = productByOrders;
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
        OrderOk orderOk = (OrderOk) o;
        if (orderOk.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderOk.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderOk{" +
            "id=" + getId() +
            ", total=" + getTotal() +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
