package com.example.ddclothsstore.model;

import android.util.Log;

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

    public List<Product> getProductsList(){


        ApiService service = NetworkClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Product>> call = service.getProductData(ApiService.API_KEY);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                productList = response.body();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

                Log.e(NetworkClient.TAG,"Failed to download products list");
            }
        });

        return  productList;
    }

    /*public List<Product> getProductsList(){

        List<Product> productList = null;
        ProductListResponse respond = null;
        *//*Create handle for the RetrofitInstance interface*//*
        ApiService service = NetworkClient.getRetrofitInstance().create(ApiService.class);

        Call<ProductListResponse> callSync = service.getProductData(ApiService.API_KEY);

        try {
            Response<ProductListResponse> response = callSync.execute();
            respond = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        productList = respond.items;

        return productList;
    }

    public class ProductListResponse {

        private List<Product> items;
    }*/

}
