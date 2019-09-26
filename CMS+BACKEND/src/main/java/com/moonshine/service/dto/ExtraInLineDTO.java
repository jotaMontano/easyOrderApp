package com.moonshine.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ExtraInLine entity.
 */
public class ExtraInLineDTO implements Serializable {

    private Long id;


    private Long extraId;

    private Long productByOrderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExtraId() {
        return extraId;
    }

    public void setExtraId(Long extraId) {
        this.extraId = extraId;
    }

    public Long getProductByOrderId() {
        return productByOrderId;
    }

    public void setProductByOrderId(Long productByOrderId) {
        this.productByOrderId = productByOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExtraInLineDTO extraInLineDTO = (ExtraInLineDTO) o;
        if (extraInLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), extraInLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExtraInLineDTO{" +
            "id=" + getId() +
            ", extra=" + getExtraId() +
            ", productByOrder=" + getProductByOrderId() +
            "}";
    }
}
