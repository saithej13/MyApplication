package com.vst.myapplication.dataObject;

import java.util.List;

public class RateAndDetails {
    public rateDO rate;
    public List<ratedetailsDO> rateDetailsList;

    public RateAndDetails(rateDO rate, List<ratedetailsDO> rateDetailsList) {
        this.rate = rate;
        this.rateDetailsList = rateDetailsList;
    }
}
