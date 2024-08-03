package com.vst.myapplication.dataObject;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tblratesdetails")
public class ratedetailsDO {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("DETAILID")
    public int DETAILID;
    @SerializedName("SLNO")
    public int SLNO;
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
    @SerializedName("BCODE")
    public int BCODE;
}
