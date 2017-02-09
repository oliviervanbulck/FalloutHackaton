package com.oliviervanbulck.fallouthackaton.service;

import com.oliviervanbulck.fallouthackaton.model.Item;
import com.oliviervanbulck.fallouthackaton.model.Login;
import com.squareup.okhttp.Response;

import java.util.List;

import retrofit.Call;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by olivi on 3/12/2015.
 */
public interface FalloutService {
    @FormUrlEncoded
    @POST("users/register")
    Call<Void> register(@Field("email") String email, @Field("password") String pass);

    @POST("users/login")
    Call<Login> login(@Header("Authorization") String authorization);

    @GET("inventory")
    Call<List<Item>> getInventory(@Header("Session") String session);

    @GET("items")
    Call<List<Item>> getItems(@Header("Session") String session);

    @DELETE("users/logout")
    Call<Void> logout(@Header("Session") String session);
}
