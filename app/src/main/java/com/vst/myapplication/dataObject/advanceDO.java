package com.vst.myapplication.dataObject;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tbladvances", indices = {@Index(value = "SLNO", unique = true)})
public class advanceDO {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("SLNO")
    public int SLNO;
    @SerializedName("TDATE")
    public String TDATE="";
    @SerializedName("CUSTOMERTYPE")
    public String CUSTOMERTYPE="";
    @SerializedName("ID")
    public String ID="";
    @SerializedName("NAME")
    public String NAME="";
    @SerializedName("AMOUNT")
    public String AMOUNT="";
    @SerializedName("REMARKS")
    public String REMARKS="";
    @SerializedName("BCODE")
    public int BCODE;
}
