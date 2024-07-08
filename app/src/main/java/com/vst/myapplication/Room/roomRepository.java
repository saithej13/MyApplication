package com.vst.myapplication.Room;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.gson.JsonObject;
import com.vst.myapplication.Utils.MyApplicationNew;
import com.vst.myapplication.dataObject.RateAndDetails;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.dataObject.milkDO;
import com.vst.myapplication.dataObject.rateDO;
import com.vst.myapplication.dataObject.ratedetailsDO;

import java.util.List;

public class roomRepository {
//    RoomService roomService = Database.getInstance().roomService();
//RoomService roomService = MyApplicationNew.getRoomService();
RoomService roomService;
    public roomRepository(){
        Database database = Database.getInstance();
        roomService = database.roomService();
    }
    public void insertfarmers(farmerDO farmers){
        new InsertFarmersAsyncTask(roomService).execute(farmers);
    }
    public void insertrates(RateAndDetails rates){
        new InsertRatesAsyncTask(roomService).execute(rates);
    }
    public void insertmilkdata(milkDO mdata){
        new InsertMilkDataAsyncTask(roomService).execute(mdata);
    }
    public List<farmerDO> getfarmerbyid(String farmerCode) {
        int code = Integer.parseInt(farmerCode);
        return roomService.getfarmerbyid(code);
    }
    public MutableLiveData<List<farmerDO>> getFarmersData(LifecycleOwner owner) {
        Log.d("URL", "GetFarmersFarmers Request-->");
        final MutableLiveData<List<farmerDO>> data = new MutableLiveData<>();

        roomService.getfarmers().observe(owner,new Observer<List<farmerDO>>() {
            @Override
            public void onChanged(List<farmerDO> orderSummaries) {
                data.setValue(orderSummaries);
                Log.d("value", "body " + data.getValue());
            }
        });

        return data;
    }

    //    public MutableLiveData<List<tblrates>> getratesData() {
//        Log.d("URL", "GetFarmersFarmers Request-->" );
//        final MutableLiveData<List<tblrates>> data = new MutableLiveData<>();
//
//        fadservices.getrates().observeForever(new Observer<List<tblrates>>() {
//            @Override
//            public void onChanged(List<tblrates> orderSummeries) {
//                data.setValue(orderSummeries);
//                Log.d("value", "body " + data.getValue());
//            }
//        });
//
//        return data;
//    }
    public MutableLiveData<List<RateAndDetails>> getratesData(LifecycleOwner lifecycleOwner) {
        Log.d("URL", "GetFarmersFarmers Request-->");
        final MutableLiveData<List<RateAndDetails>> data = new MutableLiveData<>();

        roomService.getrates().observe(lifecycleOwner, new Observer<List<RateAndDetails>>() {
            @Override
            public void onChanged(List<RateAndDetails> orderSummeries) {
                data.setValue(orderSummeries);
                Log.d("value", "body " + data.getValue());
            }
        });

        return data;
    }

    public MutableLiveData<List<rateDO>> getValidateFat(LifecycleOwner lifecycleOwner,String mtype,double fatmin,double fatmax) {
        Log.d("URL", "GetValidateRate Request-->");
        final MutableLiveData<List<rateDO>> data = new MutableLiveData<>();
//        roomService.validateFat(mtype,fatmin,fatmax).observe(lifecycleOwner, new Observer<List<rateDO>>() {
//            @Override
//            public void onChanged(List<rateDO> orderSummeries) {
//                data.setValue(orderSummeries);
//                Log.d("value", "body " + data.getValue());
//            }
//        });
        return data;
    }
    //GetFarmerbycodeAsyncTask
    public MutableLiveData<List<farmerDO>> GetFarmerbycodeAsyncTask(LifecycleOwner owner,int code) {
        Log.d("URL", "GetFarmersFarmers Request-->" );
        final MutableLiveData<List<farmerDO>> data = new MutableLiveData<>();

        roomService.getFarmerByCode(code).observe(owner,new Observer<List<farmerDO>>() {
            @Override
            public void onChanged(List<farmerDO> farmers) {
                data.setValue(farmers);
                Log.d("value", "body " + data.getValue());
            }
        });

        return data;
    }

    public MutableLiveData<List<milkDO>> getmilkdata(LifecycleOwner owner, String tdate, String shift) {
        Log.d("URL", "GetMilkData Request--> tdate "+tdate+" shift "+shift );
        final MutableLiveData<List<milkDO>> data = new MutableLiveData<>();

        roomService.getmilkdata(tdate,shift).observe(owner,new Observer<List<milkDO>>() {
            @Override
            public void onChanged(List<milkDO> orderSummeries) {
                data.setValue(orderSummeries);
                Log.d("value", "body " + data.getValue());
            }
        });

        return data;
    }
    public MutableLiveData<List<rateDO>> getratesdata(String mtype, String tdate, double fat, double snf) {
        Log.d("URL", "GetMilkData Request-->" );
        final MutableLiveData<List<rateDO>> data = new MutableLiveData<>();

//        roomService.gettsrate(mtype,tdate,fat,snf).observeForever(new Observer<List<rateDO>>() {
//            @Override
//            public void onChanged(List<rateDO> orderSummeries) {
//                data.setValue(orderSummeries);
//                Log.d("value", "body " + data.getValue());
//            }
//        });

        return data;
    }

    private static class InsertMilkDataAsyncTask extends AsyncTask<milkDO,Void,Void> {
        RoomService roomService;
        private InsertMilkDataAsyncTask(RoomService roomService){
            this.roomService = roomService;
        }
        @Override
        protected Void doInBackground(milkDO... mdata) {
            roomService.insertMilkdata(mdata[0]);
            return null;
        }
    }
    private static class InsertFarmersAsyncTask extends AsyncTask<farmerDO,Void,Void> {
        RoomService roomService;
        private InsertFarmersAsyncTask(RoomService roomService){
            this.roomService = roomService;
        }
        @Override
        protected Void doInBackground(farmerDO... farmers) {
            roomService.insertFarmer(farmers[0]);
            return null;
        }
    }
    private static class InsertRatesAsyncTask extends AsyncTask<RateAndDetails,Void,Void> {
        RoomService roomService;
//        private MutableLiveData<JsonObject> data;
//        private InsertRatesAsyncTask(RoomService roomService, MutableLiveData<JsonObject> data){
//            this.roomService = roomService;
//            this.data = data;
//        }
        private InsertRatesAsyncTask(RoomService roomService){
            this.roomService = roomService;
        }
        @Override
        protected Void doInBackground(RateAndDetails... rateAndDetailsArray) {
            RateAndDetails rateAndDetails = rateAndDetailsArray[0];
//            roomService.insertRates(rates[0]);
            long slNo = roomService.insertRates(rateAndDetails.rate);
//            rateAndDetails.rateDetails.SLNO = (int) slNo;
//            roomService.insertRatesdetails(rateAndDetails.rateDetails);
            for (ratedetailsDO rateDetails : rateAndDetails.rateDetailsList) {
                rateDetails.SLNO = (int) slNo;
                roomService.insertRatesdetails(rateDetails);
            }

            return null;
        }
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            JsonObject successResponse = new JsonObject();
//            successResponse.addProperty("success", true);
//            data.setValue(successResponse);
//        }
    }
}
