package com.moonshine.easyorder.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderOk {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("total")
    @Expose
    private Double total;

    @SerializedName("status")
    @Expose
    private Boolean status;

    @SerializedName("clientId")
    @Expose
    private Long clientId;

    public OrderOk() {
    }

    public OrderOk(Double total, Boolean status, Long clientId) {
        this.total = total;
        this.status = status;
        this.clientId = clientId;
    }

    public OrderOk(Long id, Double total, Boolean status, Long clientId) {
        this.id = id;
        this.total = total;
        this.status = status;
        this.clientId = clientId;
    }

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

    public Boolean getStatus() {
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
}
