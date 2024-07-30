package com.vst.myapplication.dataObject;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
@Entity(tableName = "tblfarmers", indices = {@Index(value = "FARMERID", unique = true)})
public class farmerDO implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("SLNO")
    public int SLNO;
//{
//    "farmerid":"1",
//        "farmername":"1",
//        "mobileno":"1",
//        "milktype":"1",
//        "isactive":"1"
//}
    @SerializedName("FARMERID")
    public int FARMERID;
    @SerializedName("FARMERNAME")
    public String FARMERNAME="";
//    @SerializedName("lastname")
//    public String lastname="";
    @SerializedName("MOBILENO")
    public String MOBILENO="";
    @SerializedName("MILKTYPE")
    public String MILKTYPE="";
    @SerializedName("ISACTIVE")
    public boolean ISACTIVE;
}
