package com.vst.myapplication.Services;

import android.app.Application;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.vst.myapplication.Room.Database;
import com.vst.myapplication.Room.RoomService;
import com.vst.myapplication.Room.roomRepository;
import com.vst.myapplication.Utils.CalendarUtils;
import com.vst.myapplication.Utils.MyApplicationNew;
import com.vst.myapplication.dataObject.RateAndDetails;
import com.vst.myapplication.dataObject.advanceDO;
import com.vst.myapplication.dataObject.customerDO;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.dataObject.milkDO;
import com.vst.myapplication.dataObject.rateDO;
import com.vst.myapplication.dataObject.ratedetailsDO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectRepository {
    Apiclient apiClient = AppModule.getClient().create(Apiclient.class);

    LifecycleOwner lifecycleOwner = MyApplicationNew.getLifecycleOwner();
    RoomService roomService = Database.getInstance().roomService();

    roomRepository roomrepo = new roomRepository();

    public MutableLiveData<JsonObject> login(JsonObject payload) {
        Log.d("URL","Login Request-->"+payload);
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();

        apiClient.Login(payload).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("URL", "Login Request-->" + payload);
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("Login", "payload Failed-->" + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
    public MutableLiveData<JsonObject> InsertFarmer(JsonObject payload) {
        Log.d("URL","getFarmerbycode Request-->"+payload);
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();

        apiClient.InsertFarmer(payload).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("URL", "getFarmerbycode Request-->" + payload);
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("Log", "getFarmerbycode payload Failed-->" + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
    public MutableLiveData<JsonObject> getfarmerbyid(JsonObject payload) {
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();

        apiClient.getFarmerbyid(payload).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject =response.body();
                    Log.d("Log Response", "" + response.body());
                    data.setValue(jsonObject);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("Log", "getFarmerbycode payload Failed-->" + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<JsonObject> getfarmers() {
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();

        apiClient.getFarmers().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject =response.body();
                    Log.d("Log Response", "" + response.body());
                    data.setValue(jsonObject);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("Log", "getFarmerbycode payload Failed-->" + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
    public MutableLiveData<JsonObject> getTSrate(JsonObject payload) {
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();
        if(MyApplicationNew.RoomDB){
//            jsonObject1.addProperty("MILKTYPE", binding.textmtypec.getText().toString());
//            jsonObject1.addProperty("TDATE", binding.textdate.getText().toString());
//            jsonObject1.addProperty("FAT", fat);
//            jsonObject1.addProperty("SNF", snf);
            String MILKTYPE = payload.get("MILKTYPE").getAsString();
            String TDATE = payload.get("TDATE").getAsString();
            Double FAT = payload.get("FAT").getAsDouble();
            Double SNF = payload.get("SNF").getAsDouble();
            Log.d("MILKTYPE",""+MILKTYPE);
            Log.d("TDATE",""+TDATE);
            Log.d("FAT",""+FAT);
            Log.d("SNF",""+SNF);
            roomService.gettsrate(MILKTYPE,TDATE,FAT,SNF).observe(lifecycleOwner, new Observer<Double>() {
                @Override
                public void onChanged(Double aDouble) {
                    Gson gson = new Gson();
                    JsonObject jsonObject = new JsonObject();
                    String jsonArrayString = gson.toJson(aDouble);
                    JsonArray finalJsonArray = new JsonArray();
                    JsonObject rateAndDetailsObject = new JsonObject();
                    rateAndDetailsObject.add("rate", gson.toJsonTree(jsonArrayString));
                    finalJsonArray.add(rateAndDetailsObject);
                    jsonObject.add("Data", finalJsonArray);
                    Log.d("PRINTjsonArray",""+finalJsonArray);
                    data.setValue(jsonObject);
                }
            });
        }
        else {
            apiClient.getTsrate(payload).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        JsonObject jsonObject = response.body();
                        Log.d("Log Response", "" + response.body());
                        data.setValue(jsonObject);
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.d("Log", "getTSRATE payload Failed-->" + t.getMessage());
                    data.setValue(null);
                }
            });
        }
        return data;
    }
//  #Rates
public MutableLiveData<JsonObject> getrates() {
    final MutableLiveData<JsonObject> data = new MutableLiveData<>();
    if(MyApplicationNew.RoomDB){
        roomService.getrates().observe(lifecycleOwner, new Observer<List<RateAndDetails>>() {
            @Override
            public void onChanged(List<RateAndDetails> rateDOS) {
                Gson gson = new Gson();
                JsonObject jsonObject = new JsonObject();
                String jsonArrayString = gson.toJson(rateDOS);
                try {
                    JsonArray jsonArray = gson.fromJson(jsonArrayString, JsonArray.class);
                    Map<Integer, RateAndDetails> rateAndDetailsMap = new HashMap<>();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JsonObject rateObject = jsonArray.get(i).getAsJsonObject();
                        JsonObject rate = rateObject.getAsJsonObject("rate");
                        int slno = rate.get("SLNO").getAsInt();
                        RateAndDetails rateAndDetails = rateAndDetailsMap.get(slno);

                        if (rateAndDetails == null) {
                            rateAndDetails = new RateAndDetails();
                            rateAndDetails.rate = new rateDO();
                            rateAndDetails.rate.ENDDATE = rate.get("ENDDATE").getAsString();
                            rateAndDetails.rate.MILKTYPE = rate.get("MILKTYPE").getAsString();
                            rateAndDetails.rate.SLNO = slno;
                            rateAndDetails.rate.STARTDATE = rate.get("STARTDATE").getAsString();
                            rateAndDetails.rateDetailsList = new ArrayList<>();

                            rateAndDetailsMap.put(slno, rateAndDetails);
                        }

                        JsonArray rateDetailsArray = rateObject.getAsJsonArray("rateDetailsList");
                        for (int j = 0; j < rateDetailsArray.size(); j++) {
                            JsonObject rateDetail = rateDetailsArray.get(j).getAsJsonObject();
                            ratedetailsDO rateDetailDO = new ratedetailsDO();
                            rateDetailDO.DETAILID = rateDetail.get("DETAILID").getAsInt();
                            rateDetailDO.FATMAX = rateDetail.get("FATMAX").getAsDouble();
                            rateDetailDO.FATMIN = rateDetail.get("FATMIN").getAsDouble();
                            rateDetailDO.RATE = rateDetail.get("RATE").getAsDouble();
                            rateDetailDO.SNFMAX = rateDetail.get("SNFMAX").getAsDouble();
                            rateDetailDO.SNFMIN = rateDetail.get("SNFMIN").getAsDouble();

                            // Check for existing DETAILID in rateDetailsList for the current SLNO
                            boolean isDuplicate = false;
                            for (ratedetailsDO existingDetail : rateAndDetails.rateDetailsList) {
                                if (existingDetail.DETAILID == rateDetailDO.DETAILID && existingDetail.SLNO == rateDetailDO.SLNO) {
                                    isDuplicate = true;
                                    break;
                                }
                            }

                            // Add only if not a duplicate
                            if (!isDuplicate) {
                                rateAndDetails.rateDetailsList.add(rateDetailDO);
                            }
                        }
                    }
                    JsonArray finalJsonArray = new JsonArray();
                    for (RateAndDetails rateAndDetails : rateAndDetailsMap.values()) {
                        JsonObject rateAndDetailsObject = new JsonObject();
                        rateAndDetailsObject.add("rate", gson.toJsonTree(rateAndDetails.rate));
                        rateAndDetailsObject.add("rateDetailsList", gson.toJsonTree(rateAndDetails.rateDetailsList));
                        finalJsonArray.add(rateAndDetailsObject);
                    }
                    jsonObject.add("Data", finalJsonArray);
                    Log.d("PRINTjsonArray",""+finalJsonArray);
                } catch (JsonSyntaxException e) {
                    Log.e("Log Response", "Error parsing JSON array", e);
                    jsonObject.add("Data", new JsonArray());
                }
                Log.d("Log Response", "" + jsonObject);
                data.setValue(jsonObject);
            }
        });
    }
    else {
        apiClient.getRates().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject =response.body();
                    Log.d("Log Response", "" + response.body());
                    data.setValue(jsonObject);
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("Log", "getRATES payload Failed-->" + t.getMessage());
                data.setValue(null);
            }
        });
    }
    return data;
}
    public MutableLiveData<JsonObject> getratebyslno(JsonObject payload) {
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();
        if(MyApplicationNew.RoomDB){
            String SLNO = payload.get("SLNO").getAsString();
            roomService.getratesbyslno(SLNO).observe(lifecycleOwner, new Observer<List<RateAndDetails>>() {
                @Override
                public void onChanged(List<RateAndDetails> rateDOS) {
                    Gson gson = new Gson();
                    JsonObject jsonObject = new JsonObject();
                    String jsonArrayString = gson.toJson(rateDOS);
                    try {
                        JsonArray jsonArray = gson.fromJson(jsonArrayString, JsonArray.class);
                        jsonObject.add("Data", jsonArray);
                    } catch (JsonSyntaxException e) {
                        Log.e("Log Response", "Error parsing JSON array", e);
                        jsonObject.add("Data", new JsonArray());
                    }
                    Log.d("Log Response", "" + jsonObject);
                    data.setValue(jsonObject);
                }
            });
        }
        else {
            apiClient.getRates().enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        JsonObject jsonObject =response.body();
                        Log.d("Log Response", "" + response.body());
                        data.setValue(jsonObject);
                    }
                }
                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.d("Log", "getRATES payload Failed-->" + t.getMessage());
                    data.setValue(null);
                }
            });
        }
        return data;
    }
    public MutableLiveData<JsonObject> InsertRate(RateAndDetails rateDO) {
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();
        if(MyApplicationNew.RoomDB)
        {
            if(rateDO.rate.SLNO==0)
            {
                roomrepo.insertrates(rateDO);
            }
            else {
                roomrepo.Updaterates(rateDO);
            }
        }
        else {
            Gson gson = new Gson();
            String jsonString = gson.toJson(rateDO);
            JsonObject payload = new JsonParser().parse(jsonString).getAsJsonObject();
            Log.d("URL", "getFarmerbycode Request-->" + payload);
            apiClient.InsertRate(payload).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.d("URL", "INSERTRATE Request-->" + payload);
                    if (response.isSuccessful()) {
                        data.setValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.d("Log", "INSERTRATE payload Failed-->" + t.getMessage());
                    data.setValue(null);
                }
            });
        }
        return data;
    }


    //#Milk Collection

    public MutableLiveData<JsonObject> InsertMilk(JsonObject payload) {
        Log.d("URL","INSERTMILK Request-->"+payload);
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();

        apiClient.InsertMData(payload).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("URL", "INSERTMILK Request-->" + payload);
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("Log", "INSERTMILK payload Failed-->" + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
    public MutableLiveData<JsonObject> getMData(LifecycleOwner owner,JsonObject payload) {
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();
        if(MyApplicationNew.RoomDB){
            String TDATE = payload.get("TDATE").getAsString();
            String SHIFT = payload.get("SHIFT").getAsString();
            roomService.getmilkdata(TDATE,SHIFT).observe(owner,new Observer<List<milkDO>>() {
                @Override
                public void onChanged(List<milkDO> orderSummeries) {
                    Gson gson = new Gson();
                    JsonObject jsonObject = new JsonObject();
                    String jsonArrayString = gson.toJson(orderSummeries);
                    try {
                        JsonArray jsonArray = gson.fromJson(jsonArrayString, JsonArray.class);
                        jsonObject.add("Data", jsonArray);
                    } catch (JsonSyntaxException e) {
                        Log.e("Log Response", "Error parsing JSON array", e);
                        jsonObject.add("Data", new JsonArray());
                    }
                    Log.d("Log Response", "" + jsonObject);
                    data.setValue(jsonObject);
                }
            });
//            roomrepo.getmilkdata(owner,payload.get("TDATE").toString(),payload.get("SHIFT").toString()).observe(lifecycleOwner, new Observer<List<milkDO>>() {
//                @Override
//                public void onChanged(List<milkDO> milkDOS) {
//                    Gson gson = new Gson();
//                    JsonObject jsonObject = new JsonObject();
//                    String jsonArrayString = gson.toJson(milkDOS);
//                    try {
//                        JsonArray jsonArray = gson.fromJson(jsonArrayString, JsonArray.class);
//                        jsonObject.add("Data", jsonArray);
//                    } catch (JsonSyntaxException e) {
//                        Log.e("Log Response", "Error parsing JSON array", e);
//                        jsonObject.add("Data", new JsonArray());
//                    }
//                    Log.d("Log Response", "" + jsonObject);
//                    data.setValue(jsonObject);
//                }
//            });
        }
        else {
            apiClient.getMData(payload).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        JsonObject jsonObject = response.body();
                        Log.d("Log Response", "" + response.body());
                        data.setValue(jsonObject);
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.d("Log", "getFarmerbycode payload Failed-->" + t.getMessage());
                    data.setValue(null);
                }
            });
        }
        return data;
    }

    public MutableLiveData<JsonObject> getMData2(LifecycleOwner owner,JsonObject payload) {
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();
        if(MyApplicationNew.RoomDB){
            String STARTDATE = payload.get("STARTDATE").getAsString();
            String ENDDATE = payload.get("ENDDATE").getAsString();
            roomService.getmilkdata2(STARTDATE,ENDDATE).observe(owner,new Observer<List<milkDO>>() {
                @Override
                public void onChanged(List<milkDO> orderSummeries) {
                    Gson gson = new Gson();
                    JsonObject jsonObject = new JsonObject();
                    String jsonArrayString = gson.toJson(orderSummeries);
                    try {
                        JsonArray jsonArray = gson.fromJson(jsonArrayString, JsonArray.class);
                        jsonObject.add("Data", jsonArray);
                    } catch (JsonSyntaxException e) {
                        Log.e("Log Response", "Error parsing JSON array", e);
                        jsonObject.add("Data", new JsonArray());
                    }
                    Log.d("Log Response", "" + jsonObject);
                    data.setValue(jsonObject);
                }
            });
//            roomrepo.getmilkdata(owner,payload.get("TDATE").toString(),payload.get("SHIFT").toString()).observe(lifecycleOwner, new Observer<List<milkDO>>() {
//                @Override
//                public void onChanged(List<milkDO> milkDOS) {
//                    Gson gson = new Gson();
//                    JsonObject jsonObject = new JsonObject();
//                    String jsonArrayString = gson.toJson(milkDOS);
//                    try {
//                        JsonArray jsonArray = gson.fromJson(jsonArrayString, JsonArray.class);
//                        jsonObject.add("Data", jsonArray);
//                    } catch (JsonSyntaxException e) {
//                        Log.e("Log Response", "Error parsing JSON array", e);
//                        jsonObject.add("Data", new JsonArray());
//                    }
//                    Log.d("Log Response", "" + jsonObject);
//                    data.setValue(jsonObject);
//                }
//            });
        }
        else {
            apiClient.getMData(payload).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        JsonObject jsonObject = response.body();
                        Log.d("Log Response", "" + response.body());
                        data.setValue(jsonObject);
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.d("Log", "getFarmerbycode payload Failed-->" + t.getMessage());
                    data.setValue(null);
                }
            });
        }
        return data;
    }

    public MutableLiveData<JsonObject> getUser(JsonObject payload) {
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();
            apiClient.getuser(payload).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        JsonObject jsonObject = response.body();
                        Log.d("Log Response", "" + response.body());
                        data.setValue(jsonObject);
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.d("Log", "getFarmerbycode payload Failed-->" + t.getMessage());
                    data.setValue(null);
                }
            });

        return data;
    }
    public MutableLiveData<String> getTransList(RequestBody payload) {
        final MutableLiveData<String> data = new MutableLiveData<>();
        apiClient.getTransListData(payload).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("Response Code", "" + response.code());
                if (response.isSuccessful()) {
                    String resp =response.body();
                    Log.d("Log Response", "" + response.body());
                    data.setValue(resp);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Log", "getTransList payload Failed-->" + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
    /* #Advances */
    public MutableLiveData<JsonObject> getAdvances() {
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();
        if(MyApplicationNew.RoomDB) {
            roomService.getadvances().observe(lifecycleOwner, new Observer<List<advanceDO>>() {
                @Override
                public void onChanged(List<advanceDO> advanceDOS) {
                    Gson gson = new Gson();
                    JsonObject jsonObject = new JsonObject();
                    String jsonArrayString = gson.toJson(advanceDOS);
                    try {
                        JsonArray jsonArray = gson.fromJson(jsonArrayString, JsonArray.class);
                        jsonObject.add("Data", jsonArray);
                    } catch (JsonSyntaxException e) {
                        Log.e("Log Response", "Error parsing JSON array", e);
                        jsonObject.add("Data", new JsonArray());
                    }
                    Log.d("Log Response", "" + jsonObject);
                    data.setValue(jsonObject);
                }
            });
        }
        else{
            //API
        }
        return data;
    }
    public MutableLiveData<JsonObject> getAdvancesbyslno(int SLNO) {
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();
        if(MyApplicationNew.RoomDB) {
            roomService.getadvancesbyslno(SLNO).observe(lifecycleOwner, new Observer<List<advanceDO>>() {
                @Override
                public void onChanged(List<advanceDO> advanceDOS) {
                    Gson gson = new Gson();
                    JsonObject jsonObject = new JsonObject();
                    String jsonArrayString = gson.toJson(advanceDOS);
                    try {
                        JsonArray jsonArray = gson.fromJson(jsonArrayString, JsonArray.class);
                        jsonObject.add("Data", jsonArray);
                    } catch (JsonSyntaxException e) {
                        Log.e("Log Response", "Error parsing JSON array", e);
                        jsonObject.add("Data", new JsonArray());
                    }
                    Log.d("Log Response", "" + jsonObject);
                    data.setValue(jsonObject);
                }
            });
        }
        else{
            //API
        }
        return data;
    }
    public MutableLiveData<JsonObject> getcustomers() {
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();
        if(MyApplicationNew.RoomDB) {
            roomService.getcustomers().observe(lifecycleOwner, new Observer<List<customerDO>>() {
                @Override
                public void onChanged(List<customerDO> customerDOS) {
                    Gson gson = new Gson();
                    JsonObject jsonObject = new JsonObject();
                    String jsonArrayString = gson.toJson(customerDOS);
                    try {
                        JsonArray jsonArray = gson.fromJson(jsonArrayString, JsonArray.class);
                        jsonObject.add("Data", jsonArray);
                    } catch (JsonSyntaxException e) {
                        Log.e("Log Response", "Error parsing JSON array", e);
                        jsonObject.add("Data", new JsonArray());
                    }
                    Log.d("Log Response", "" + jsonObject);
                    data.setValue(jsonObject);
                }
            });
        }
        else{
            //API
        }
        return data;
    }
    public MutableLiveData<JsonObject> getcustomerbyslno(int SLNO) {
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();
        if(MyApplicationNew.RoomDB) {
            roomService.getcustomerbyslno(SLNO).observe(lifecycleOwner, new Observer<List<customerDO>>() {
                @Override
                public void onChanged(List<customerDO> customerDOS) {
                    Gson gson = new Gson();
                    JsonObject jsonObject = new JsonObject();
                    String jsonArrayString = gson.toJson(customerDOS);
                    try {
                        JsonArray jsonArray = gson.fromJson(jsonArrayString, JsonArray.class);
                        jsonObject.add("Data", jsonArray);
                    } catch (JsonSyntaxException e) {
                        Log.e("Log Response", "Error parsing JSON array", e);
                        jsonObject.add("Data", new JsonArray());
                    }
                    Log.d("Log Response", "" + jsonObject);
                    data.setValue(jsonObject);
                }
            });
        }
        else{
            //API
        }
        return data;
    }
    public MutableLiveData<JsonObject> getfarmerbyslno(int SLNO) {
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();
        if(MyApplicationNew.RoomDB) {
            roomService.getFarmerByCode(SLNO).observe(lifecycleOwner, new Observer<List<farmerDO>>() {
                @Override
                public void onChanged(List<farmerDO> farmerDOS) {
                    Gson gson = new Gson();
                    JsonObject jsonObject = new JsonObject();
                    String jsonArrayString = gson.toJson(farmerDOS);
                    try {
                        JsonArray jsonArray = gson.fromJson(jsonArrayString, JsonArray.class);
                        jsonObject.add("Data", jsonArray);
                    } catch (JsonSyntaxException e) {
                        Log.e("Log Response", "Error parsing JSON array", e);
                        jsonObject.add("Data", new JsonArray());
                    }
                    Log.d("Log Response", "" + jsonObject);
                    data.setValue(jsonObject);
                }
            });
        }
        else{
            //API
        }
        return data;
    }
    public MutableLiveData<JsonObject> deleteFarmerID(int FARMERID) {
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();
        if(MyApplicationNew.RoomDB) {
            roomService.deleteFarmerId(FARMERID);
        }
        else{
            //API
        }
        return data;
    }
    public MutableLiveData<JsonObject> InsertAdvance(JsonObject payload) {
        Log.d("URL","getFarmerbycode Request-->"+payload);
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();
        if(MyApplicationNew.RoomDB)
        {
            int SLNO = payload.get("SLNO").getAsInt();
            advanceDO advance = new advanceDO();
            advance.ID = payload.get("ID").getAsString();
            advance.TDATE = payload.get("TDATE").getAsString();
            advance.NAME = payload.get("NAME").getAsString();
            advance.CUSTOMERTYPE = payload.get("CUSTOMERTYPE").getAsString();
            advance.AMOUNT = payload.get("AMOUNT").getAsString();
            advance.REMARKS = payload.get("REMARKS").getAsString();
            advance.SLNO = SLNO;
            if(SLNO==0)
            {
                roomrepo.insertAdvance(advance);
            }
            else {
                roomrepo.updateAdvance(advance);
            }
//            roomService.InsertAdvanceDataAsyncTask
        }
        else {


        }
        return data;
    }
    public MutableLiveData<JsonObject> InsertUpdateCustomer(JsonObject payload) {
        Log.d("URL","CUSTOMERCODE Request-->"+payload);
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();
        if(MyApplicationNew.RoomDB)
        {
            int SLNO = payload.get("SLNO").getAsInt();
            customerDO customer = new customerDO();
            customer.CUSTOMERCODE = payload.get("CUSTOMERCODE").getAsString();
            customer.CUSTOMERNAME = payload.get("CUSTOMERNAME").getAsString();
            customer.MOBILENO = payload.get("MOBILENO").getAsString();
            customer.ISACTIVE = payload.get("ISACTIVE").getAsString();
            customer.SLNO = SLNO;
            if(SLNO==0)
            {
                roomrepo.insertCustomer(customer);
            }
            else {
                roomrepo.updateCustomer(customer);
            }
//            roomService.InsertAdvanceDataAsyncTask
        }
        else {


        }
        return data;
    }
    public MutableLiveData<JsonObject> InsertUpdateFarmer(JsonObject payload) {
        Log.d("URL","getFarmerbycode Request-->"+payload);
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();
        if(MyApplicationNew.RoomDB)
        {
            int SLNO = payload.get("SLNO").getAsInt();
            farmerDO farmer = new farmerDO();
            farmer.FARMERID = payload.get("FARMERID").getAsInt();
            farmer.FARMERNAME = payload.get("FARMERNAME").getAsString();
            farmer.MOBILENO = payload.get("MOBILENO").getAsString();
            farmer.ISACTIVE = Boolean.parseBoolean(payload.get("ISACTIVE").getAsString());
            farmer.MILKTYPE = payload.get("MILKTYPE").getAsString();
            if(SLNO==0)
            {
                roomrepo.insertfarmers(farmer);
            }
            else {
                roomrepo.updateFarmer(farmer);
            }
//            roomService.InsertAdvanceDataAsyncTask
        }
        else {


        }
        return data;
    }
}
