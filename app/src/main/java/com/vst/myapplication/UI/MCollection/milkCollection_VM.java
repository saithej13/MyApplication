package com.vst.myapplication.UI.MCollection;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.dataObject.milkDO;
import com.vst.myapplication.dataObject.rateDO;

public class milkCollection_VM extends AndroidViewModel {
    private ProjectRepository repository;
    milkDO[] milkDOs;
    public milkCollection_VM(@NonNull Application application) {
        super(application);
        repository = new ProjectRepository();
    }
    public MutableLiveData<JsonObject> insertMdata(milkDO milkDo){
        Gson gson = new Gson();
        String jsonString = gson.toJson(milkDo);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        return repository.InsertMilk(jsonObject);
    }
}
