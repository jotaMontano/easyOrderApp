package com.moonshine.easyorder.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Discount {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("percentage")
    @Expose
    private double percentage;

    @SerializedName("status")
    @Expose
    private boolean status;

    @Expose
    private Long clientId;

    public Discount() {
    }

    public Discount(Long id, String name, double percentage, boolean status, Long clientId) {
        this.id = id;
        this.name = name;
        this.percentage = percentage;
        this.status = status;
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

    public void setName(String name) { this.name = name; }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", percentage='" + percentage + '\'' +
                ", status=" + status +
                ", clientId=" + clientId +
                '}';
    }
}
