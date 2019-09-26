package com.moonshine.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ListOfValue.
 */
@Entity
@Table(name = "list_of_value")
public class ListOfValue implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_value", nullable = false)
    private String value;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "jhi_type")
    private String type;

    @ManyToMany(mappedBy = "days")
    @JsonIgnore
    private Set<Discount> discounts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public ListOfValue value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public ListOfValue description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public ListOfValue type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Discount> getDiscounts() {
        return discounts;
    }

    public ListOfValue discounts(Set<Discount> discounts) {
        this.discounts = discounts;
        return this;
    }

    public ListOfValue addDiscounts(Discount discount) {
        this.discounts.add(discount);
        discount.getDays().add(this);
        return this;
    }

    public ListOfValue removeDiscounts(Discount discount) {
        this.discounts.remove(discount);
        discount.getDays().remove(this);
        return this;
    }

    public void setDiscounts(Set<Discount> discounts) {
        this.discounts = discounts;
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
        ListOfValue listOfValue = (ListOfValue) o;
        if (listOfValue.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), listOfValue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ListOfValue{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
