package com.vst.myapplication.dataObject;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tblcustomer", indices = {@Index(value = "SLNO", unique = true)})
public class customerDO {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("SLNO")
    public int SLNO;
    @SerializedName("CUSTOMERCODE")
    public String CUSTOMERCODE="";
    @SerializedName("CUSTOMERNAME")
    public String CUSTOMERNAME="";
    @SerializedName("MOBILENO")
    public String MOBILENO="";
    @SerializedName("ISACTIVE")
    public boolean ISACTIVE;
}
