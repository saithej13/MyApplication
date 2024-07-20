package com.vst.myapplication.UI.Advance;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vst.myapplication.Room.roomRepository;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.Utils.MyApplicationNew;
import com.vst.myapplication.dataObject.advanceDO;
import com.vst.myapplication.dataObject.farmerDO;

public class advancesVM extends AndroidViewModel {
    private ProjectRepository repository;
    roomRepository roomrepo;
    advanceDO[] advanceDOS;

    public advancesVM(@NonNull Application application) {
        super(application);
        repository = new ProjectRepository();
        roomrepo = new roomRepository();
    }
    public MutableLiveData<JsonObject> insertAdvance(advanceDO advanceDo){
        boolean result = false;
        Gson gson = new Gson();
        String jsonString = gson.toJson(advanceDo);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        repository.InsertAdvance(jsonObject);
        return null;
    }
}
