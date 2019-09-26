package com.moonshine.easyorder.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExtraInLine {

    @SerializedName("id")
    @Expose
    private Long  id;

    @SerializedName("extraId")
    @Expose
    private Long extraId;

    @SerializedName("productByOrderId")
    @Expose
    private Long productByOrderId;

    public ExtraInLine() {
    }

    public ExtraInLine(Long extraId, Long productByOrderId) {
        this.extraId = extraId;
        this.productByOrderId = productByOrderId;
    }

    public ExtraInLine(Long id, Long extraId, Long productByOrderId) {
        this.id = id;
        this.extraId = extraId;
        this.productByOrderId = productByOrderId;
    }

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
}
