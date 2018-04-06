package com.example.hp_pc.serverrajeev;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Hp-Pc on 10/6/2017.
 */
public class Photo implements Serializable {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("url")
    private String mUrl;

    @SerializedName("username")
    private String UserName;

    @SerializedName("type")
    private int type;

    @SerializedName("description")
    private String des;

    @SerializedName("price")
    private int price;

    private String[] types = {"Books", "Electronics", "Cycles", "Others"};

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getType() {
        return type;
    }

    public String getTypeName() {
        return types[type];
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
