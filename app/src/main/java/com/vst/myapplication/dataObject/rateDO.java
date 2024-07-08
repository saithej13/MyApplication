package com.vst.myapplication.dataObject;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

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
    @SerializedName("BCODE")
    public String BCODE;
    @SerializedName("ratedetailsDO")
    @Expose
    @Ignore
    public ratedetailsDO ratedetailsDO;
}
