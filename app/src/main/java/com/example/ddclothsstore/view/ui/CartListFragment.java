package com.example.ddclothsstore.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ddclothsstore.R;
import com.example.ddclothsstore.model.ApiService;
import com.example.ddclothsstore.model.CartItem;
import com.example.ddclothsstore.model.NetworkClient;
import com.example.ddclothsstore.model.Product;
import com.example.ddclothsstore.model.ResourceManager;
import com.example.ddclothsstore.view.adapter.CartRecycleviewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartListFragment extends Fragment {

    private List<Product> productsInCart = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cartlistfragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getCartProductsList();
    }

    public void getCartProductsList(){

        ApiService service = NetworkClient.getRetrofitInstance().create(ApiService.class);
        Call<List<CartItem>> call = service.getProductsInCart(ApiService.API_KEY);

        call.enqueue(new Callback<List<CartItem>>() {
            @Override
            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                getCartItems(response.body());

            }

            @Override
            public void onFailure(Call<List<CartItem>> call, Throwable t) {

                Log.e(NetworkClient.TAG,"Failed to download products list");
            }
        });

    }

    public void getCartItems(List<CartItem> cartItems){

        ResourceManager resourceManager = ResourceManager.getInstance();
        resourceManager.setCartItems(cartItems);

        productsInCart = new ArrayList<Product>(cartItems.size());
        List<Product> productsList = ((ProductListActivity)getActivity()).getProductsList();
        for(CartItem cartItem : cartItems){
            for(Product product:productsList){
                if(product.getId() == cartItem.getProductId()){
                    productsInCart.add(product);
                    break;
                }
            }

        }
        generateDataList();
        calculateAndSetCartTotal();
    }


    private void generateDataList() {
        // set up the RecyclerView
        RecyclerView recyclerView = getActivity().findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CartRecycleviewAdapter adapter = new CartRecycleviewAdapter(getActivity(), productsInCart);
        //Toast.makeText(getActivity(),"list size " + productList.size(),Toast.LENGTH_LONG).show();
        recyclerView.setAdapter(adapter);
    }

    private void calculateAndSetCartTotal(){
        float cartTotal = 0;

        for(Product product:productsInCart){
            cartTotal += Float.parseFloat(product.getPrice());
        }

        TextView tvCartTotal = (TextView) getActivity().findViewById(R.id.cart_total);
        if(cartTotal == 0){
            tvCartTotal.setText(getString(R.string.cart_empty));
            return;
        }
        StringBuffer value = new StringBuffer();
        value.append(getString(R.string.cart_total));
        value.append(" = ");
        value.append(cartTotal);
        tvCartTotal.setText(value.toString());
    }

    public void removeItemsFromCart(final int productId){

        int toBeRemovedItem = 0;
        ResourceManager resourceManager = ResourceManager.getInstance();
        List<CartItem> cartItems = resourceManager.getCartItems();
        for (CartItem cartItem : cartItems){
            if(cartItem.getProductId() == productId){
                toBeRemovedItem = cartItem.getId();
                break;
            }
        }
        ApiService service = NetworkClient.getRetrofitInstance().create(ApiService.class);
        Call<CartItem> call = service.removeProductsFromCart(ApiService.API_KEY, toBeRemovedItem);
        Log.i(NetworkClient.TAG, "url = " + call.request().url());
        call.enqueue(new Callback<CartItem>() {
            @Override
            public void onResponse(Call<CartItem> call, Response<CartItem> response) {

                if(response.isSuccessful()) {
                    Log.i(NetworkClient.TAG, "delete submitted to API." );
                }
                getCartProductsList();
            }

            @Override
            public void onFailure(Call<CartItem> call, Throwable t) {

                Log.e(NetworkClient.TAG,"Failed to remove item from cart");
            }
        });

    }

}
