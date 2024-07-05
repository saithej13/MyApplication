package com.vst.myapplication.UI.Farmers;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vst.myapplication.Room.roomRepository;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.Utils.MyApplicationNew;
import com.vst.myapplication.dataObject.farmerDO;

import org.json.JSONObject;

public class famers_VM extends AndroidViewModel {
    private ProjectRepository repository;
    roomRepository roomrepo;
    farmerDO[] farmerDOS;

    public famers_VM(@NonNull Application application) {
        super(application);
        repository = new ProjectRepository();
//        roomrepo = new roomRepository(application);
    }
    public MutableLiveData<JsonObject> insertFarmer(farmerDO farmerDo){
        boolean result = false;
        if(MyApplicationNew.RoomDB){
            roomrepo.insertfarmers(farmerDo);
        }
        else {
            Gson gson = new Gson();
            String jsonString = gson.toJson(farmerDo);
            JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
            repository.InsertFarmer(jsonObject);
        }
        return null;
    }
//    public MutableLiveData<JSONObject> getFarmerdata(){
//        return repository.getfarmers().observe(this, new Observer<JSONObject>() {
//            @Override
//            public void onChanged(JSONObject jsonObject) {
//                if (jsonObject != null) {
//                    Log.d("TAG", jsonObject.toString());
//                    Gson gson = new Gson();
//                    farmerDOS = gson.fromJson(jsonObject.toString(), farmerDO[].class);
//                    if (farmerDOS != null) {
//                        adapter = new OrderListAdapter(this, farmerDOS);
//                    }
//            }
//                }
//        });
//    }


}
