package com.moonshine.easyorder.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfValue {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("value")
    @Expose
    private String value;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("jhi_type")
    @Expose
    private String jhi_type;

    public ListOfValue() {
    }

    public ListOfValue(Long id, String value, String description, String jhi_type) {
        this.id = id;
        this.value = value;
        this.description = description;
        this.jhi_type = jhi_type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJhi_type() {
        return jhi_type;
    }

    public void setJhi_type(String jhi_type) {
        this.jhi_type = jhi_type;
    }

    @Override
    public String toString() {
        return "ListOfValues{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", description='" + description + '\'' +
                ", jhi_type='" + jhi_type + '\'' +
                '}';
    }
}
