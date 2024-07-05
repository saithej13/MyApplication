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
import com.vst.myapplication.Utils.MyApplicationNew;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.dataObject.rateDO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectRepository {
    Apiclient apiClient = AppModule.getClient().create(Apiclient.class);

    LifecycleOwner lifecycleOwner = MyApplicationNew.getLifecycleOwner();
    RoomService roomService = Database.getInstance().roomService();

    roomRepository roomrepo;

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

        apiClient.getTsrate(payload).enqueue(new Callback<JsonObject>() {
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
                Log.d("Log", "getTSRATE payload Failed-->" + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
//  #Rates
public MutableLiveData<JsonObject> getrates() {
    final MutableLiveData<JsonObject> data = new MutableLiveData<>();
    if(MyApplicationNew.RoomDB){
        roomService.getrates().observe(lifecycleOwner, new Observer<List<rateDO>>() {
            @Override
            public void onChanged(List<rateDO> rateDOS) {
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
    public MutableLiveData<JsonObject> InsertRate(rateDO rateDO) {
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();
        if(MyApplicationNew.RoomDB)
        {
            Log.d("STARTDATE",""+rateDO.STARTDATE);
            Log.d("ENDDATE",""+rateDO.ENDDATE);
            Log.d("FATMIN",""+rateDO.FATMIN);
            Log.d("FATMAX",""+rateDO.FATMAX);
            Log.d("SNFMIN",""+rateDO.SNFMIN);
            Log.d("SNFMAX",""+rateDO.SNFMAX);
            Log.d("RATE",""+rateDO.RATE);

                roomrepo.insertrates(rateDO);
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
    public MutableLiveData<JsonObject> getMData(JsonObject payload) {
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();

        apiClient.getMData(payload).enqueue(new Callback<JsonObject>() {
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

    public MutableLiveData<JsonObject> getUser(JsonObject payload) {
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();

        apiClient.getuser(payload).enqueue(new Callback<JsonObject>() {
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
}
