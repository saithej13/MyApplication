package com.vst.myapplication.dataObject;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
@Entity(tableName = "tblrates")
public class rateDO implements Serializable {
    @PrimaryKey(autoGenerate = true)
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
