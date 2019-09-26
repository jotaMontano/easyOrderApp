package com.moonshine.easyorder.expandable.product.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product2 implements Parcelable {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private int waitAverage;

    private String urlImage;

    private String type;

    private Boolean status;

    private Long categoriesId;

    private Long clientId;

    protected Product2(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        name = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        waitAverage = in.readInt();
        urlImage = in.readString();
        type = in.readString();
        byte tmpStatus = in.readByte();
        status = tmpStatus == 0 ? null : tmpStatus == 1;
        if (in.readByte() == 0) {
            categoriesId = null;
        } else {
            categoriesId = in.readLong();
        }
        if (in.readByte() == 0) {
            clientId = null;
        } else {
            clientId = in.readLong();
        }
    }

    public Product2() {
    }

    public Product2(String name, String description, Double price, int waitAverage, String urlImage, String type, Boolean status, Long categoriesId, Long clientId) {
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

    public Product2(Long id, String name, String description, Double price, int waitAverage, String urlImage, String type, Boolean status, Long categoriesId, Long clientId) {
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

    public static final Creator<Product2> CREATOR = new Creator<Product2>() {
        @Override
        public Product2 createFromParcel(Parcel in) {
            return new Product2(in);
        }

        @Override
        public Product2[] newArray(int size) {
            return new Product2[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(name);
        dest.writeString(description);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(price);
        }
        dest.writeInt(waitAverage);
        dest.writeString(urlImage);
        dest.writeString(type);
        dest.writeByte((byte) (status == null ? 0 : status ? 1 : 2));
        if (categoriesId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(categoriesId);
        }
        if (clientId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(clientId);
        }
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
