package com.vst.myapplication.UI.cusotmer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vst.myapplication.Room.roomRepository;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.dataObject.advanceDO;
import com.vst.myapplication.dataObject.customerDO;

public class customerVM extends AndroidViewModel {
    private ProjectRepository repository;
    roomRepository roomrepo;
    advanceDO[] advanceDOS;

    public customerVM(@NonNull Application application) {
        super(application);
        repository = new ProjectRepository();
        roomrepo = new roomRepository();
    }
    public MutableLiveData<JsonObject> insertUpdateCustomer(customerDO cusotmerdo){
        boolean result = false;
        Gson gson = new Gson();
        String jsonString = gson.toJson(cusotmerdo);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        repository.InsertUpdateCustomer(jsonObject);
        return null;
    }
}
