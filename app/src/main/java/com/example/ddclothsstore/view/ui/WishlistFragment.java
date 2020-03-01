package com.example.ddclothsstore.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ddclothsstore.R;
import com.example.ddclothsstore.model.ApiService;
import com.example.ddclothsstore.model.NetworkClient;
import com.example.ddclothsstore.model.ResourceManager;
import com.example.ddclothsstore.model.database.CartItem;
import com.example.ddclothsstore.model.database.Product;
import com.example.ddclothsstore.model.database.WishlistItem;
import com.example.ddclothsstore.view.adapter.WishlistRecycleviewAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistFragment extends Fragment {

    private List<Product> productsInWishlist = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cartlistfragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getWishlistProductsList();
    }

    public void getWishlistProductsList(){

        ResourceManager resourceManager = ResourceManager.getInstance(getActivity());
        resourceManager.setWishlistItems();
        productsInWishlist = resourceManager.getProductsInWishlist();
        generateDataList();
    }


    private void generateDataList() {

        // set up the RecyclerView
        RecyclerView recyclerView = getActivity().findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        WishlistRecycleviewAdapter adapter = null;
        if(productsInWishlist != null){
            adapter= new WishlistRecycleviewAdapter(getActivity(), productsInWishlist);
        }
        recyclerView.setAdapter(adapter);

    }


    public void removeItemsFromWishlist(final int productId){

        int toBeRemovedItem = 0;
        ResourceManager resourceManager = ResourceManager.getInstance(getActivity());
        List<WishlistItem> wishlistItems = resourceManager.getWishlistItems();
        for (WishlistItem wishlistItem : wishlistItems){
            if(wishlistItem.getProductId() == productId){
                toBeRemovedItem = wishlistItem.getId();
                break;
            }
        }
        resourceManager.removeProductsFromWishlist(toBeRemovedItem);
        getWishlistProductsList();

    }

    public void addItemsToCart(final int productId){

        Product product = ResourceManager.getInstance(getActivity()).getProductById(productId);
        if(product != null && product.getStock() <=0){
            Toast.makeText(getActivity(),"Out of Stock product cannot be added to cart",Toast.LENGTH_LONG).show();
            return;
        }

        ApiService service = NetworkClient.getRetrofitInstance().create(ApiService.class);
        Call<CartItem> call = service.addProductsToCart(ApiService.API_KEY, productId);
        Log.i(NetworkClient.TAG, "url = " + call.request().url());
        call.enqueue(new Callback<CartItem>() {
            @Override
            public void onResponse(Call<CartItem> call, Response<CartItem> response) {

                if(response.isSuccessful()) {
                    Log.i(NetworkClient.TAG, "post submitted to API." + response.body().toString());
                    removeItemsFromWishlist(productId);
                }
            }

            @Override
            public void onFailure(Call<CartItem> call, Throwable t) {

                Log.e(NetworkClient.TAG,"Failed to add item to cart");
            }
        });
    }

}
