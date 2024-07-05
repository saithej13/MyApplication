package com.vst.myapplication.dataObject;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class rateDO implements Serializable {
    @SerializedName("SLNO")
    public int SLNO;
    @SerializedName("MILKTYPE")
    public String MILKTYPE;
    @SerializedName("STARTDATE")
    public String STARTDATE;
    @SerializedName("ENDDATE")
    public String ENDDATE;
    @SerializedName("FATMIN")
    public double FATMIN;
    @SerializedName("FATMAX")
    public double FATMAX;
    @SerializedName("SNFMIN")
    public double SNFMIN;
    @SerializedName("SNFMAX")
    public double SNFMAX;
    @SerializedName("RATE")
    public double RATE;
}
