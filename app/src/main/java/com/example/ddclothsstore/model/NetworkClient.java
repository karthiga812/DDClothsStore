package com.example.ddclothsstore.model;

import android.util.Log;

import com.example.ddclothsstore.model.database.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

    public static final String TAG = "NetworkClient";
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://2klqdzs603.execute-api.eu-west-2.amazonaws.com";
    private List<Product> productList = null;

    private static NetworkClient networkClientInstance = null;
    // static method to create instance of Singleton class
    public static NetworkClient getInstance()
    {
        if (networkClientInstance == null)
            networkClientInstance = new NetworkClient();

        return networkClientInstance;
    }


    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
