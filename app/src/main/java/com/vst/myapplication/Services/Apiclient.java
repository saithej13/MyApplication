package com.vst.myapplication.Services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vst.myapplication.dataObject.farmerDO;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Apiclient {
    @POST("webservices/api/Scan/Login")
    Call<JsonObject> Login(@Body JsonObject data);

    @POST("webservices/api/Scan/AllOrders")
    Call<JsonArray> loadOrders(@Body JsonObject data);

    @POST
    Call<JsonArray> isValidImei(@Url String url, @Body JsonArray data);

    @POST
    Call<String> SaveImei(@Url String url, @Body JsonObject data);

    @POST("addfarmer")
    Call<JsonObject> InsertFarmer(@Body JsonObject data);

    @GET("getfarmer")
    Call<JsonObject> getFarmers();

    @POST("getfarmerbyid")
    Call<JsonObject> getFarmerbyid(@Body JsonObject data);

    @POST("gettsrate")
    Call<JsonObject> getTsrate(@Body JsonObject data);

    @POST("addrate")
    Call<JsonObject> InsertRate(@Body JsonObject data);

    @GET("getrates")
    Call<JsonObject> getRates();

    @POST("addmilk")
    Call<JsonObject> InsertMData(@Body JsonObject data);

    @POST("getmilk")
    Call<JsonObject> getMData(@Body JsonObject data);

    @POST("getuser")
    Call<JsonObject> getuser(@Body JsonObject data);

    @POST("adduser")
    Call<JsonObject> adduser(@Body JsonObject data);

    @POST("GetTransList")
    Call<String> getTransListData(@Body RequestBody data);

}
