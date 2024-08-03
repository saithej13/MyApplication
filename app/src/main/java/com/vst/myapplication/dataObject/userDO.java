package com.vst.myapplication.dataObject;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
@Entity(tableName = "tbluser")
public class userDO implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int slno;
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
    @SerializedName("BCODE")
    public int BCODE;
}
