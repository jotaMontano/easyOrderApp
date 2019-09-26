package com.moonshine.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "client")
    private Set<Product> products = new HashSet<>();
    @OneToMany(mappedBy = "client")
    private Set<Category> categories = new HashSet<>();
    @OneToMany(mappedBy = "client")
    private Set<Extra> extras = new HashSet<>();
    @OneToMany(mappedBy = "client")
    private Set<Discount> discounts = new HashSet<>();
    @OneToMany(mappedBy = "client")
    private Set<OrderOk> orders = new HashSet<>();
    @OneToMany(mappedBy = "client")
    private Set<OrderHistory> orderHistories = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public Client email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public Client name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public Client user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Client products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Client addProducts(Product product) {
        this.products.add(product);
        product.setClient(this);
        return this;
    }

    public Client removeProducts(Product product) {
        this.products.remove(product);
        product.setClient(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Client categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Client addCategories(Category category) {
        this.categories.add(category);
        category.setClient(this);
        return this;
    }

    public Client removeCategories(Category category) {
        this.categories.remove(category);
        category.setClient(null);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Extra> getExtras() {
        return extras;
    }

    public Client extras(Set<Extra> extras) {
        this.extras = extras;
        return this;
    }

    public Client addExtras(Extra extra) {
        this.extras.add(extra);
        extra.setClient(this);
        return this;
    }

    public Client removeExtras(Extra extra) {
        this.extras.remove(extra);
        extra.setClient(null);
        return this;
    }

    public void setExtras(Set<Extra> extras) {
        this.extras = extras;
    }

    public Set<Discount> getDiscounts() {
        return discounts;
    }

    public Client discounts(Set<Discount> discounts) {
        this.discounts = discounts;
        return this;
    }

    public Client addDiscounts(Discount discount) {
        this.discounts.add(discount);
        discount.setClient(this);
        return this;
    }

    public Client removeDiscounts(Discount discount) {
        this.discounts.remove(discount);
        discount.setClient(null);
        return this;
    }

    public void setDiscounts(Set<Discount> discounts) {
        this.discounts = discounts;
    }

    public Set<OrderOk> getOrders() {
        return orders;
    }

    public Client orders(Set<OrderOk> orderOks) {
        this.orders = orderOks;
        return this;
    }

    public Client addOrder(OrderOk orderOk) {
        this.orders.add(orderOk);
        orderOk.setClient(this);
        return this;
    }

    public Client removeOrder(OrderOk orderOk) {
        this.orders.remove(orderOk);
        orderOk.setClient(null);
        return this;
    }

    public void setOrders(Set<OrderOk> orderOks) {
        this.orders = orderOks;
    }

    public Set<OrderHistory> getOrderHistories() {
        return orderHistories;
    }

    public Client orderHistories(Set<OrderHistory> orderHistories) {
        this.orderHistories = orderHistories;
        return this;
    }

    public Client addOrderHistory(OrderHistory orderHistory) {
        this.orderHistories.add(orderHistory);
        orderHistory.setClient(this);
        return this;
    }

    public Client removeOrderHistory(OrderHistory orderHistory) {
        this.orderHistories.remove(orderHistory);
        orderHistory.setClient(null);
        return this;
    }

    public void setOrderHistories(Set<OrderHistory> orderHistories) {
        this.orderHistories = orderHistories;
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
        Client client = (Client) o;
        if (client.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), client.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
