package com.example.simplechatandroid.internet_service;

import android.content.Context;

import com.example.simplechatandroid.data.ChatItem;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static final String BASE_URL="Your Server Base Url";
    private RetrofitApiService apiService;

    public ApiService(Context context){



        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(RetrofitApiService.class);
    }

    public Single<List<ChatItem>> getMessages(String token){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("token",token);
        return apiService.getMessages(jsonObject);
    }

}
