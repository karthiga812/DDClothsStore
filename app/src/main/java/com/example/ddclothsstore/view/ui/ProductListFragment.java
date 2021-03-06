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
import com.example.ddclothsstore.model.database.CartItem;
import com.example.ddclothsstore.model.NetworkClient;
import com.example.ddclothsstore.model.database.Product;
import com.example.ddclothsstore.model.ResourceManager;
import com.example.ddclothsstore.view.adapter.MyRecycleviewAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListFragment extends Fragment  {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getActivity(),"fragment",Toast.LENGTH_LONG).show();

        return inflater.inflate(R.layout.productlistfragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((ProductListActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((ProductListActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name));
        getProductsList();

    }

    public void getProductsList(){


        ApiService service = NetworkClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Product>> call = service.getProductData(ApiService.API_KEY);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

                Log.e(NetworkClient.TAG,"Failed to download products list");
            }
        });

    }

    private void generateDataList(List<Product> productList) {

        ResourceManager resourceManager = ResourceManager.getInstance(getActivity());
        resourceManager.setProducts(productList);
        // set up the RecyclerView
        RecyclerView recyclerView = getActivity().findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MyRecycleviewAdapter adapter = new MyRecycleviewAdapter(getActivity(), productList);
        //Toast.makeText(getActivity(),"list size " + productList.size(),Toast.LENGTH_LONG).show();
        recyclerView.setAdapter(adapter);
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
                }
            }

            @Override
            public void onFailure(Call<CartItem> call, Throwable t) {

                Log.e(NetworkClient.TAG,"Failed to add item to cart");
            }
        });

    }

    public void addItemsToWishlist(final int productId){

        ResourceManager resourceManager = ResourceManager.getInstance(getActivity());
        resourceManager.addProductsToWishlist(productId);
    }


}
