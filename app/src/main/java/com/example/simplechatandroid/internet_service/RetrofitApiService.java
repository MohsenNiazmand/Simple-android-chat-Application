package com.example.simplechatandroid.internet_service;

import com.example.simplechatandroid.data.ChatItem;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitApiService {

    @POST("Chat List Address")
    Single<List<ChatItem>> getMessages(@Body JsonObject body);
}
