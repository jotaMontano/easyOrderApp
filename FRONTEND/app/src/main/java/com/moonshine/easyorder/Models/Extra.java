package com.moonshine.easyorder.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Extra {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("price")
    @Expose
    private double price;

    @SerializedName("clientId")
    @Expose
    private Long clientId;

    public Extra() {
    }

    public Extra(Long id, String name, double price, Long clientId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.clientId = clientId;
    }

    public Extra(String name, double price, Long clientId) {
        this.name = name;
        this.price = price;
        this.clientId = clientId;
    }

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
