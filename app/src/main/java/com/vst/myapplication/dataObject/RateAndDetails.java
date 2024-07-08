package com.vst.myapplication.dataObject;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class RateAndDetails {
    @Embedded
    public rateDO rate;
    @Relation(
            parentColumn = "SLNO",
            entityColumn = "SLNO"
    )
    public List<ratedetailsDO> rateDetailsList;

    public RateAndDetails(rateDO rate, List<ratedetailsDO> rateDetailsList) {
        this.rate = rate;
        this.rateDetailsList = rateDetailsList;
    }
}
