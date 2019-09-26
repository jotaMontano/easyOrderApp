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
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @Column(name = "wait_average", nullable = false)
    private Integer waitAverage;

    @NotNull
    @Column(name = "url_image", nullable = false)
    private String urlImage;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "products")
    private Set<ProductByOrder> productByOrders = new HashSet<>();
    @OneToMany(mappedBy = "products")
    private Set<Top> tops = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "product_extras",
               joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "extras_id", referencedColumnName = "id"))
    private Set<Extra> extras = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "product_combo",
               joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "combo_id", referencedColumnName = "id"))
    private Set<Product> combos = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "product_discounts",
               joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "discounts_id", referencedColumnName = "id"))
    private Set<Discount> discounts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("categories")
    private Category categories;

    @ManyToMany(mappedBy = "combos")
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("products")
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

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public Product price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getWaitAverage() {
        return waitAverage;
    }

    public Product waitAverage(Integer waitAverage) {
        this.waitAverage = waitAverage;
        return this;
    }

    public void setWaitAverage(Integer waitAverage) {
        this.waitAverage = waitAverage;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public Product urlImage(String urlImage) {
        this.urlImage = urlImage;
        return this;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getType() {
        return type;
    }

    public Product type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isStatus() {
        return status;
    }

    public Product status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<ProductByOrder> getProductByOrders() {
        return productByOrders;
    }

    public Product productByOrders(Set<ProductByOrder> productByOrders) {
        this.productByOrders = productByOrders;
        return this;
    }

    public Product addProductByOrder(ProductByOrder productByOrder) {
        this.productByOrders.add(productByOrder);
        productByOrder.setProducts(this);
        return this;
    }

    public Product removeProductByOrder(ProductByOrder productByOrder) {
        this.productByOrders.remove(productByOrder);
        productByOrder.setProducts(null);
        return this;
    }

    public void setProductByOrders(Set<ProductByOrder> productByOrders) {
        this.productByOrders = productByOrders;
    }

    public Set<Top> getTops() {
        return tops;
    }

    public Product tops(Set<Top> tops) {
        this.tops = tops;
        return this;
    }

    public Product addTop(Top top) {
        this.tops.add(top);
        top.setProducts(this);
        return this;
    }

    public Product removeTop(Top top) {
        this.tops.remove(top);
        top.setProducts(null);
        return this;
    }

    public void setTops(Set<Top> tops) {
        this.tops = tops;
    }

    public Set<Extra> getExtras() {
        return extras;
    }

    public Product extras(Set<Extra> extras) {
        this.extras = extras;
        return this;
    }

    public Product addExtras(Extra extra) {
        this.extras.add(extra);
        extra.getProducts().add(this);
        return this;
    }

    public Product removeExtras(Extra extra) {
        this.extras.remove(extra);
        extra.getProducts().remove(this);
        return this;
    }

    public void setExtras(Set<Extra> extras) {
        this.extras = extras;
    }

    public Set<Product> getCombos() {
        return combos;
    }

    public Product combos(Set<Product> products) {
        this.combos = products;
        return this;
    }

    public Product addCombo(Product product) {
        this.combos.add(product);
        product.getProducts().add(this);
        return this;
    }

    public Product removeCombo(Product product) {
        this.combos.remove(product);
        product.getProducts().remove(this);
        return this;
    }

    public void setCombos(Set<Product> products) {
        this.combos = products;
    }

    public Set<Discount> getDiscounts() {
        return discounts;
    }

    public Product discounts(Set<Discount> discounts) {
        this.discounts = discounts;
        return this;
    }

    public Product addDiscounts(Discount discount) {
        this.discounts.add(discount);
        discount.getProducts().add(this);
        return this;
    }

    public Product removeDiscounts(Discount discount) {
        this.discounts.remove(discount);
        discount.getProducts().remove(this);
        return this;
    }

    public void setDiscounts(Set<Discount> discounts) {
        this.discounts = discounts;
    }

    public Category getCategories() {
        return categories;
    }

    public Product categories(Category category) {
        this.categories = category;
        return this;
    }

    public void setCategories(Category category) {
        this.categories = category;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Product products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Product addProducts(Product product) {
        this.products.add(product);
        product.getCombos().add(this);
        return this;
    }

    public Product removeProducts(Product product) {
        this.products.remove(product);
        product.getCombos().remove(this);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Client getClient() {
        return client;
    }

    public Product client(Client client) {
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
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", waitAverage=" + getWaitAverage() +
            ", urlImage='" + getUrlImage() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
