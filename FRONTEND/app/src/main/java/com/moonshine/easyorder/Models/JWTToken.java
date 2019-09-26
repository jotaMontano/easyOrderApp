package com.moonshine.easyorder.Models;

import com.google.gson.annotations.SerializedName;

public class JWTToken {
    private String id_token;
    @SerializedName("body")


    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }
}
