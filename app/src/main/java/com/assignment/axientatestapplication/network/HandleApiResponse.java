package com.assignment.axientatestapplication.network;

import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.assignment.axientatestapplication.data.UserModel;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HandleApiResponse {
    private IpService mClient = null;

    public HandleApiResponse(Context context, String url) {
        init(context,url);
    }

    private void init(Context context,String url) {
        mClient = new RetrofitClient().getClient(context,url).create(IpService.class);
    }

    public interface CallBackDelegate {
        void onResponseSuccess(String result);
        void onFailure(String error);
    }



    public interface CallBackUsersDelegate {
        void onResponseSuccess(List<UserModel> userModelList);
        void onFailure(String error);
    }

    public void login(String userName, String password, String macID , String versionNo , String deviceID, CallBackDelegate delegate){

        mClient.loginService(userName,password,macID,versionNo,deviceID).enqueue(new Callback<JsonArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (response.code() == 200) {
                    try {
                        if (response.body() != null) {
                            JSONArray jsonArray = new JSONArray(String.valueOf(response.body()));
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String object = jsonObject.getJSONArray("LoginStatus").getJSONObject(0).get("Status").toString();
                            delegate.onResponseSuccess(object);

                        } else {
                            Log.e("login", "SOMETHING WENT WRONG");
                            delegate.onFailure("SOMETHING WENT WRONG");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("login", "SOMETHING WENT WRONG");
                    try {
                        Log.e("login", "response "+response.errorBody().string());
                        delegate.onFailure(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("login", "onFailure: " + t);
                delegate.onFailure(t.toString());
            }
        });
    }

    public void getAllUsers(CallBackUsersDelegate delegate){
        mClient.getAllUsers().enqueue(new Callback<JsonArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                //Log.e("getAllUsers", "response "+response.body().toString());

                if (response.code() == 200) {
                    try {
                        JSONArray jsonArray = new JSONArray(String.valueOf(response.body()));
                        List<UserModel> userModelList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            UserModel userModel = new UserModel();

                            JSONObject users = jsonArray.getJSONObject(i);
                            userModel.setId(users.getInt("id"));
                            userModel.setName(users.getString("name"));
                            userModel.setUsername(users.getString("username"));
                            userModel.setEmail(users.getString("email"));
                            userModel.setPhone(users.getString("phone"));
                            userModel.setWebsite(users.getString("website"));

                            userModel.setCompany_name(users.getJSONObject("company").getString("name"));
                            userModel.setCompany_catchPhrase(users.getJSONObject("company").getString("catchPhrase"));
                            userModel.setCompany_bs(users.getJSONObject("company").getString("bs"));

                            userModel.setStreet(users.getJSONObject("address").getString("street"));
                            userModel.setSuite(users.getJSONObject("address").getString("suite"));
                            userModel.setZipcode(users.getJSONObject("address").getString("zipcode"));
                            userModel.setLat(users.getJSONObject("address").getJSONObject("geo").getString("lat"));
                            userModel.setLng(users.getJSONObject("address").getJSONObject("geo").getString("lng"));

                            userModelList.add(userModel);
                        }
                        delegate.onResponseSuccess(userModelList);

                    } catch (Exception e) {
                        e.printStackTrace();
                        //delegate.onResponseException(e);
                    }
                } else {
                    Log.e("getAllUsers", "SOMETHING WENT WRONG");
                    try {
                        Log.e("getAllUsers", "response "+response.errorBody().string());
                        delegate.onFailure(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("getAllUsers", "onFailure: " + t);
                delegate.onFailure(t.toString());
            }
        });
    }
}
