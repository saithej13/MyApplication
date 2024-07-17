package com.vst.myapplication.UI.Rates;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vst.myapplication.Room.roomRepository;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.dataObject.RateAndDetails;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.dataObject.rateDO;

public class rates_VM extends AndroidViewModel {
    private ProjectRepository repository;

    roomRepository roomrepo;
    farmerDO[] farmerDOS;
    public rates_VM(@NonNull Application application) {
        super(application);
        repository = new ProjectRepository();
//        roomrepo = new roomRepository(application);
    }
    public MutableLiveData<JsonObject> insertRate(RateAndDetails rateDo){
        return repository.InsertRate(rateDo);
    }

    public MutableLiveData<JsonObject> getRates(JsonObject slno){
        return repository.getratebyslno(slno);
    }

}
