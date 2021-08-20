package com.assignment.axientatestapplication.network;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IpService {

    @GET("avLogin/Get")
    Call<JsonArray> loginService(@Query("id") String id, @Query("password") String password, @Query("macaddress")
            String macaddress, @Query("versionnumber") String versionnumber, @Query("deviceid") String deviceid);

    @GET("users")
    Call<JsonArray> getAllUsers();

}

