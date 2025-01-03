package com.vst.myapplication.Room;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.room.Query;

import com.google.gson.JsonObject;
import com.vst.myapplication.Utils.MyApplicationNew;
import com.vst.myapplication.Utils.StringUtils;
import com.vst.myapplication.dataObject.RateAndDetails;
import com.vst.myapplication.dataObject.advanceDO;
import com.vst.myapplication.dataObject.customerDO;
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
    public void Updaterates(RateAndDetails rates){
        new UpdateRatesAsyncTask(roomService).execute(rates);
    }
    public void insertmilkdata(milkDO mdata){
        new InsertMilkDataAsyncTask(roomService).execute(mdata);
    }
    public void insertAdvance(advanceDO advance){
        new InsertAdvanceDataAsyncTask(roomService).execute(advance);
    }
    public void updateAdvance(advanceDO advance){
        new UpdateAdvanceAsyncTask(roomService).execute(advance);
    }
    public void insertCustomer(customerDO cusotmer){
        new InsertCustomerDataAsyncTask(roomService).execute(cusotmer);
    }
    public void updateCustomer(customerDO cusotmer){
        new updateCustomerAsyncTask(roomService).execute(cusotmer);
    }
    public void updateFarmer(farmerDO farmer){
        new updateFarmerAsyncTask(roomService).execute(farmer);
    }
    public List<farmerDO> getfarmerbyid(String farmerCode,int BCODE) {
        int code = Integer.parseInt(farmerCode);
        return roomService.getfarmerbyid(code,BCODE);
    }
    public MutableLiveData<List<farmerDO>> getFarmersData(LifecycleOwner owner,int BCODE) {
        Log.d("URL", "GetFarmersFarmers Request-->");
        final MutableLiveData<List<farmerDO>> data = new MutableLiveData<>();
        try {
            roomService.getfarmers(BCODE).observe(owner, new Observer<List<farmerDO>>() {
                @Override
                public void onChanged(List<farmerDO> orderSummaries) {
                    data.setValue(orderSummaries);
                    Log.d("value", "body " + data.getValue());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
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
    public MutableLiveData<List<RateAndDetails>> getratesData(LifecycleOwner lifecycleOwner,int BCODE) {
        Log.d("URL", "GetFarmersFarmers Request-->");
        final MutableLiveData<List<RateAndDetails>> data = new MutableLiveData<>();
        try {
            roomService.getrates(BCODE).observe(lifecycleOwner, new Observer<List<RateAndDetails>>() {
                @Override
                public void onChanged(List<RateAndDetails> orderSummeries) {
                    data.setValue(orderSummeries);
                    Log.d("value", "body " + data.getValue());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

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
    public MutableLiveData<List<farmerDO>> GetFarmerbycodeAsyncTask(LifecycleOwner owner,int code,int BCODE) {
        Log.d("URL", "GetFarmersFarmers Request-->" );
        final MutableLiveData<List<farmerDO>> data = new MutableLiveData<>();
        try {
            roomService.getFarmerByCode(code,BCODE).observe(owner, new Observer<List<farmerDO>>() {
                @Override
                public void onChanged(List<farmerDO> farmers) {
                    data.setValue(farmers);
                    Log.d("value", "body " + data.getValue());
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return data;
    }

    public MutableLiveData<List<milkDO>> getmilkdata(LifecycleOwner owner, String tdate, String shift,int BCODE) {
        Log.d("URL", "GetMilkData Request--> tdate "+tdate+" shift "+shift );
        final MutableLiveData<List<milkDO>> data = new MutableLiveData<>();
try {
    roomService.getmilkdata(tdate, shift,BCODE).observe(owner, new Observer<List<milkDO>>() {
        @Override
        public void onChanged(List<milkDO> orderSummeries) {
            data.setValue(orderSummeries);
            Log.d("value", "body " + data.getValue());
        }
    });
}catch (Exception e){
    e.printStackTrace();
}

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
            try {
                roomService.insertMilkdata(mdata[0]);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }
    private static class InsertAdvanceDataAsyncTask extends AsyncTask<advanceDO,Void,Void> {
        RoomService roomService;
        private InsertAdvanceDataAsyncTask(RoomService roomService){
            this.roomService = roomService;
        }
        @Override
        protected Void doInBackground(advanceDO... advance) {
            try {
                roomService.insertAdvance(advance[0]);
            }
            catch (Exception e){
                e.printStackTrace();
            }

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
            try {
                roomService.insertFarmer(farmers[0]);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
    private static class InsertRatesAsyncTask extends AsyncTask<RateAndDetails,Void,Void> {
        RoomService roomService;
        private InsertRatesAsyncTask(RoomService roomService){
            this.roomService = roomService;
        }
        @Override
        protected Void doInBackground(RateAndDetails... rateAndDetailsArray) {
            try {
                RateAndDetails rateAndDetails = rateAndDetailsArray[0];
                if (rateAndDetails == null) {
                    Log.e("InsertRatesAsyncTask", "rateAndDetails is null");
                    return null;
                }

                if (rateAndDetails.rate == null) {
                    Log.e("InsertRatesAsyncTask", "rateAndDetails.rate is null");
                    return null;
                }

                long slNo = roomService.insertRates(rateAndDetails.rate);
                if (rateAndDetails.rateDetailsList == null) {
                    Log.e("InsertRatesAsyncTask", "rateAndDetails.rateDetailsList is null");
                    return null;
                }

                for (ratedetailsDO rateDetails : rateAndDetails.rateDetailsList) {
                    if (rateDetails == null) {
                        Log.e("InsertRatesAsyncTask", "rateDetails is null");
                        continue;
                    }
                    rateDetails.SLNO = (int) slNo;
                    rateDetails.BCODE = Integer.parseInt(rateAndDetails.rate.BCODE);
                    roomService.insertRatesdetails(rateDetails);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private static class UpdateRatesAsyncTask extends AsyncTask<RateAndDetails,Void,Void> {
        RoomService roomService;
        private UpdateRatesAsyncTask(RoomService roomService){
            this.roomService = roomService;
        }
        @Override
        protected Void doInBackground(RateAndDetails... rateAndDetailsArray) {
            try {
                RateAndDetails rateAndDetails = rateAndDetailsArray[0];
                if (rateAndDetails == null) {
                    Log.e("UpdateRatesAsyncTask", "rateAndDetails is null");
                    return null;
                }

                if (rateAndDetails.rate == null) {
                    Log.e("UpdateRatesAsyncTask", "rateAndDetails.rate is null");
                    return null;
                }
//            int slno, String milkType, String startDate, String endDate
                roomService.updateRates(rateAndDetails.rate.SLNO, rateAndDetails.rate.MILKTYPE,rateAndDetails.rate.RATETYPE, rateAndDetails.rate.STARTDATE, rateAndDetails.rate.ENDDATE,rateAndDetails.rate.BCODE);
                roomService.deleteRateDetails(rateAndDetails.rate.SLNO, Integer.parseInt(rateAndDetails.rate.BCODE));
                if (rateAndDetails.rateDetailsList == null) {
                    Log.e("UpdateRatesAsyncTask", "rateAndDetails.rateDetailsList is null");
                    return null;
                }
                for (ratedetailsDO rateDetails : rateAndDetails.rateDetailsList) {
                    if (rateDetails == null) {
                        Log.e("UpdateRatesAsyncTask", "rateDetails is null");
                        continue;
                    }
                    rateDetails.SLNO = rateAndDetails.rate.SLNO;
                    rateDetails.BCODE = Integer.parseInt(rateAndDetails.rate.BCODE);
                    roomService.insertRatesdetails(rateDetails);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private static class UpdateAdvanceAsyncTask extends AsyncTask<advanceDO,Void,Void> {
        RoomService roomService;
        private UpdateAdvanceAsyncTask(RoomService roomService){
            this.roomService = roomService;
        }
        @Override
        protected Void doInBackground(advanceDO... advanceArray) {
            try {
                advanceDO advance = advanceArray[0];
                if (advance == null) {
                    Log.e("UpdateAdvanceAsyncTask", "advanceDO is null");
                    return null;
                }
//            String TDATE,String NAME,String CUSTOMERTYPE,String AMOUNT,String REMARKS,String ID,int SLNO
                roomService.updateAdvance(advance.TDATE, advance.NAME, advance.CUSTOMERTYPE, advance.AMOUNT, advance.REMARKS, advance.ID,advance.BCODE, advance.SLNO);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class InsertCustomerDataAsyncTask extends AsyncTask<customerDO,Void,Void> {
        RoomService roomService;
        private InsertCustomerDataAsyncTask(RoomService roomService){
            this.roomService = roomService;
        }
        @Override
        protected Void doInBackground(customerDO... customer) {
            try {
                roomService.insertCustomer(customer[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private static class updateCustomerAsyncTask extends AsyncTask<customerDO,Void,Void> {
        RoomService roomService;
        private updateCustomerAsyncTask(RoomService roomService){
            this.roomService = roomService;
        }
        @Override
        protected Void doInBackground(customerDO... customerDOArray) {
            try {
                customerDO customer = customerDOArray[0];
                if (customer == null) {
                    Log.e("UpdateAdvanceAsyncTask", "advanceDO is null");
                    return null;
                }
//            String CUSTOMERCODE,String CUSTOMERNAME,String MOBILENO,String ISACTIVE,int SLNO
                roomService.updateCustomer(customer.CUSTOMERCODE, customer.CUSTOMERNAME, customer.MOBILENO, customer.ISACTIVE, customer.SLNO,customer.BCODE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class updateFarmerAsyncTask extends AsyncTask<farmerDO,Void,Void> {
        RoomService roomService;
        private updateFarmerAsyncTask(RoomService roomService){
            this.roomService = roomService;
        }
        @Override
        protected Void doInBackground(farmerDO... farmerDOArray) {
            try {
                farmerDO farmer = farmerDOArray[0];
                if (farmer == null) {
                    Log.e("UpdateFarmerAsyncTask", "FarmerDO is null");
                    return null;
                }
//            String CUSTOMERCODE,String CUSTOMERNAME,String MOBILENO,String ISACTIVE,int SLNO
                roomService.updateFarmer(farmer.FARMERNAME, farmer.MOBILENO, farmer.ISACTIVE, farmer.MILKTYPE, farmer.FARMERID,farmer.BCODE, farmer.SLNO);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
