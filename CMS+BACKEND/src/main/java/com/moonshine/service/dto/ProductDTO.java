package com.moonshine.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Product entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Double price;

    @NotNull
    private Integer waitAverage;

    @NotNull
    private String urlImage;

    @NotNull
    private String type;

    @NotNull
    private Boolean status;


    private Set<ExtraDTO> extras = new HashSet<>();

    private Set<ProductDTO> combos = new HashSet<>();

    private Set<DiscountDTO> discounts = new HashSet<>();

    private Long categoriesId;

    private String categoriesName;

    private Long clientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getWaitAverage() {
        return waitAverage;
    }

    public void setWaitAverage(Integer waitAverage) {
        this.waitAverage = waitAverage;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<ExtraDTO> getExtras() {
        return extras;
    }

    public void setExtras(Set<ExtraDTO> extras) {
        this.extras = extras;
    }

    public Set<ProductDTO> getCombos() {
        return combos;
    }

    public void setCombos(Set<ProductDTO> products) {
        this.combos = products;
    }

    public Set<DiscountDTO> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Set<DiscountDTO> discounts) {
        this.discounts = discounts;
    }

    public Long getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(Long categoryId) {
        this.categoriesId = categoryId;
    }

    public String getCategoriesName() {
        return categoriesName;
    }

    public void setCategoriesName(String categoryName) {
        this.categoriesName = categoryName;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (productDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            ", waitAverage=" + getWaitAverage() +
            ", urlImage='" + getUrlImage() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + isStatus() + "'" +
            ", categories=" + getCategoriesId() +
            ", categories='" + getCategoriesName() + "'" +
            ", client=" + getClientId() +
            "}";
    }
}
