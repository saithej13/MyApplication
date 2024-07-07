package com.vst.myapplication.UI.MCollection;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vst.myapplication.Room.roomRepository;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.dataObject.milkDO;
import com.vst.myapplication.dataObject.rateDO;

import java.util.List;

public class milkCollection_VM extends AndroidViewModel {
    private ProjectRepository repository;
    roomRepository roomrepo;
    milkDO[] milkDOs;
    public milkCollection_VM(@NonNull Application application) {
        super(application);
        repository = new ProjectRepository();
        roomrepo = new roomRepository();
    }
    public MutableLiveData<JsonObject> insertMdata(milkDO milkDo){
        Gson gson = new Gson();
        String jsonString = gson.toJson(milkDo);
        JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        return repository.InsertMilk(jsonObject);
    }
    public MutableLiveData<List<farmerDO>> getFarmerbycodeRoom(LifecycleOwner owner, int code){
        return roomrepo.GetFarmerbycodeAsyncTask(owner,code);
    }
    public MutableLiveData<List<rateDO>> getGetrates(String mtype, String tdate, double fat, double snf){
        return roomrepo.getratesdata(mtype,tdate,fat,snf);
    }
    public MutableLiveData<List<milkDO>> getmilkdata(LifecycleOwner owner,String tdate, String shift){
        return roomrepo.getmilkdata(owner,tdate,shift);
    }
    public void insertMdataRoom(milkDO mdata){
        roomrepo.insertmilkdata(mdata);
    }
}
