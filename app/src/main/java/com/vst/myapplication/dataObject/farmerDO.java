package com.vst.myapplication.dataObject;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class farmerDO implements Serializable {
//    @SerializedName("slno")
//    public int slno;
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
