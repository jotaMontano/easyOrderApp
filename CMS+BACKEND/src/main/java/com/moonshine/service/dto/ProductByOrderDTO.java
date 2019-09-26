package com.moonshine.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ProductByOrder entity.
 */
public class ProductByOrderDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer quantity;

    @NotNull
    private String comment;

    @NotNull
    private Boolean status;


    private Long orderOkId;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getOrderOkId() {
        return orderOkId;
    }

    public void setOrderOkId(Long orderOkId) {
        this.orderOkId = orderOkId;
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

        ProductByOrderDTO productByOrderDTO = (ProductByOrderDTO) o;
        if (productByOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productByOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductByOrderDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", comment='" + getComment() + "'" +
            ", status='" + isStatus() + "'" +
            ", orderOk=" + getOrderOkId() +
            ", products=" + getProductsId() +
            ", products='" + getProductsName() + "'" +
            "}";
    }
}
