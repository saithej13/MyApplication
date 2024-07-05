package com.vst.myapplication.dataObject;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class userDO implements Serializable {
    @SerializedName("userid")
    public String userid;
    @SerializedName("name")
    public String name;
    @SerializedName("password")
    public String password;
    @SerializedName("mobileno")
    public String mobileno;
    @SerializedName("isactive")
    public int isactive;
    @SerializedName("role")
    public int role;

    public userDO() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public userDO(String userid, String name, String password,String mobileno,int isactive,int role) {
        this.userid = userid;
        this.name = name;
        this.password = password;
        this.mobileno = mobileno;
        this.isactive = isactive;
        this.role = role;
    }
}
