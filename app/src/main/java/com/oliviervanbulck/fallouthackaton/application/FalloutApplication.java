package com.oliviervanbulck.fallouthackaton.application;

/**
 * Created by olivi on 3/12/2015.
 */
import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oliviervanbulck.fallouthackaton.service.FalloutService;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class FalloutApplication extends Application
{
    public static final String BASE_URL = "http://cloud.cozmos.be:2400/api/";
    private FalloutService service;
    private String session;
    private int coins;

    @Override
    public void onCreate()
    {
        super.onCreate();

        Gson gson = new GsonBuilder()
                .setDateFormat("dd-MM-yyyy'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(FalloutService.class);
    }

    public FalloutService getService() {
        return service;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}