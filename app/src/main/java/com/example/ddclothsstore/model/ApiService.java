package com.example.ddclothsstore.model;

import com.example.ddclothsstore.model.database.CartItem;
import com.example.ddclothsstore.model.database.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    public static final String API_KEY = "dd09d278cf-87dd-46b6-b0cb-97248c18b968";

    @GET("/cloths/products")
    Call<List<Product>> getProductData(@Header("X-API-KEY") String api_key);

    @GET("/cloths/cart")
    Call<List<CartItem>> getProductsInCart(@Header("X-API-KEY") String api_key);

    @POST("/cloths/cart")
    Call<CartItem> addProductsToCart(@Header("X-API-KEY") String api_key, @Query("productId") int productID);

    @DELETE("/cloths/cart")
    Call<CartItem> removeProductsFromCart(@Header("X-API-KEY") String api_key, @Query("id") int productID);
}
