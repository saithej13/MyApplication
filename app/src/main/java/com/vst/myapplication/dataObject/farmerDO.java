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
    @SerializedName("FARMERID")
    public int FARMERID;
    @SerializedName("FARMERNAME")
    public String FARMERNAME="";
    @SerializedName("MOBILENO")
    public String MOBILENO="";
    @SerializedName("MILKTYPE")
    public String MILKTYPE="";
    @SerializedName("ISACTIVE")
    public boolean ISACTIVE;
    @SerializedName("BCODE")
    public int BCODE;
}
