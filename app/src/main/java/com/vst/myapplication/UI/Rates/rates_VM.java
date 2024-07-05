package com.vst.myapplication.UI.Rates;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.dataObject.rateDO;

public class rates_VM extends AndroidViewModel {
    private ProjectRepository repository;
    farmerDO[] farmerDOS;
    public rates_VM(@NonNull Application application) {
        super(application);
        repository = new ProjectRepository();
    }
    public MutableLiveData<JsonObject> insertRate(rateDO rateDo){
        Gson gson = new Gson();
        String jsonString = gson.toJson(rateDo);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        return repository.InsertRate(jsonObject);
    }

}
