package com.moonshine.easyorder.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProductByOrder {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("status")
    @Expose
    private Boolean status;

    @SerializedName("orderOkId")
    @Expose
    private Long orderOkId;

    @SerializedName("productsId")
    @Expose
    private Long productsId;

    private List<Extra> extrasInLine = new ArrayList<>();

    public ProductByOrder() {
    }

    public ProductByOrder(Integer quantity, String comment, Boolean status, Long orderOkId, Long productsId) {
        this.quantity = quantity;
        this.comment = comment;
        this.status = status;
        this.orderOkId = orderOkId;
        this.productsId = productsId;
    }

    public ProductByOrder(Long id, Integer quantity, String comment, Boolean status, Long orderOkId, Long productsId) {
        this.id = id;
        this.quantity = quantity;
        this.comment = comment;
        this.status = status;
        this.orderOkId = orderOkId;
        this.productsId = productsId;
    }

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

    public Boolean getStatus() {
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

    public void setProductsId(Long productsId) {
        this.productsId = productsId;
    }

    public List<Extra> getExtrasInLine() {
        return extrasInLine;
    }

    public void setExtrasInLine(List<Extra> extrasInLine) {
        this.extrasInLine = extrasInLine;
    }
}
