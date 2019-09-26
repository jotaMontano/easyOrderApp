package com.moonshine.easyorder.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Category {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("clientId")
    @Expose
    private Long clientId;
    private List<Product> listOfProducts;

    public Category() {
    }

    public Category(Long id, String description, String name, boolean status, Long idCLiente) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.status = status;
        this.clientId = idCLiente;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setClientId(Long idCLiente) {
        this.clientId = idCLiente;
    }

    public void setListOfProducts(List<Product> listOfProducts) {
        this.listOfProducts = listOfProducts;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public boolean isStatus() {
        return status;
    }

    public Long getClientId() {
        return clientId;
    }

    public List<Product> getListOfProducts() {
        return listOfProducts;
    }

    @Override
    public String toString() {
        return "Category2{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", clientId=" + clientId +
                '}';
    }
}
