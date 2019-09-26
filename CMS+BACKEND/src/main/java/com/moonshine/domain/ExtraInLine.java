package com.moonshine.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ExtraInLine.
 */
@Entity
@Table(name = "extra_in_line")
public class ExtraInLine implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("extraInLines")
    private Extra extra;

    @ManyToOne
    @JsonIgnoreProperties("extraInLines")
    private ProductByOrder productByOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Extra getExtra() {
        return extra;
    }

    public ExtraInLine extra(Extra extra) {
        this.extra = extra;
        return this;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }

    public ProductByOrder getProductByOrder() {
        return productByOrder;
    }

    public ExtraInLine productByOrder(ProductByOrder productByOrder) {
        this.productByOrder = productByOrder;
        return this;
    }

    public void setProductByOrder(ProductByOrder productByOrder) {
        this.productByOrder = productByOrder;
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
        ExtraInLine extraInLine = (ExtraInLine) o;
        if (extraInLine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), extraInLine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExtraInLine{" +
            "id=" + getId() +
            "}";
    }
}
