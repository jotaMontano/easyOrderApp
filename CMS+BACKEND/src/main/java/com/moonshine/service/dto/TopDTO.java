package com.moonshine.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Top entity.
 */
public class TopDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer quantity;

    @NotNull
    private String type;


    private Long productsId;

    private String productsName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getProductsId() {
        return productsId;
    }

    public void setProductsId(Long productId) {
        this.productsId = productId;
    }

    public String getProductsName() {
        return productsName;
    }

    public void setProductsName(String productName) {
        this.productsName = productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TopDTO topDTO = (TopDTO) o;
        if (topDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), topDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TopDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", type='" + getType() + "'" +
            ", products=" + getProductsId() +
            ", products='" + getProductsName() + "'" +
            "}";
    }
}
