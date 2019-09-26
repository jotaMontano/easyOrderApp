package com.moonshine.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the OrderOk entity.
 */
public class OrderOkDTO implements Serializable {

    private Long id;

    @NotNull
    private Double total;

    @NotNull
    private Boolean status;


    private Long clientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

        OrderOkDTO orderOkDTO = (OrderOkDTO) o;
        if (orderOkDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderOkDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderOkDTO{" +
            "id=" + getId() +
            ", total=" + getTotal() +
            ", status='" + isStatus() + "'" +
            ", client=" + getClientId() +
            "}";
    }
}
