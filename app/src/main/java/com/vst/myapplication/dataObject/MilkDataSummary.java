package com.vst.myapplication.dataObject;

import androidx.room.ColumnInfo;

public class MilkDataSummary {
    @ColumnInfo(name = "TDATE")
    public String tdate;

    @ColumnInfo(name = "QTY")
    public double qty;

    public MilkDataSummary(String tdate, double qty) {
        this.tdate = tdate;
        this.qty = qty;
    }
}
