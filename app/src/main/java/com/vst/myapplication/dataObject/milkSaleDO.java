package com.vst.myapplication.dataObject;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
@Entity(tableName = "tblmilksaledata")
public class milkSaleDO {
        @PrimaryKey(autoGenerate = true)
        @SerializedName("SLNO")
        public int SLNO;
        @SerializedName("TDATE")
        public String TDATE;
        @SerializedName("SHIFT")
        public String SHIFT;
        @SerializedName("MILKTYPE")
        public String MILKTYPE;
        @SerializedName("CUSTOMERID")
        public String FARMERID;
        @SerializedName("CUSTOMERNAME")
        public String FARMERNAME;
        @SerializedName("QUANTITY")
        public double QUANTITY;
        @SerializedName("FAT")
        public double FAT;
        @SerializedName("SNF")
        public double SNF;
        @SerializedName("RATE")
        public double RATE;
        @SerializedName("AMOUNT")
        public double AMOUNT;
        @SerializedName("CRDATE")
        public String CRDATE;
}
