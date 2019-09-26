package com.moonshine.easyorder.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("price")
    @Expose
    private Double price;

    @SerializedName("waitAverage")
    @Expose
    private int waitAverage;

    @SerializedName("urlImage")
    @Expose
    private String urlImage;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("status")
    @Expose
    private Boolean status;

    @SerializedName("categoriesId")
    @Expose
    private Long categoriesId;

    @SerializedName("clientId")
    @Expose
    private Long clientId;

    public Product() {
    }

    public Product(String name, String description, Double price, int waitAverage, String urlImage, String type, Boolean status, Long categoriesId, Long clientId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.waitAverage = waitAverage;
        this.urlImage = urlImage;
        this.type = type;
        this.status = status;
        this.categoriesId = categoriesId;
        this.clientId = clientId;
    }

    public Product(Long id, String name, String description, Double price, int waitAverage, String urlImage, String type, Boolean status, Long categoriesId, Long clientId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.waitAverage = waitAverage;
        this.urlImage = urlImage;
        this.type = type;
        this.status = status;
        this.categoriesId = categoriesId;
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

    public int getWaitAverage() {
        return waitAverage;
    }

    public void setWaitAverage(int waitAverage) {
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(Long categoriesId) {
        this.categoriesId = categoriesId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
