package com.example.ddclothsstore.view.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.ddclothsstore.R;
import com.example.ddclothsstore.model.database.Product;

import java.util.List;

public class ProductListActivity extends AppCompatActivity implements ClickCallback{

    List<Product> productsList = null;
    ProductListFragment productListFragment = null;
    CartListFragment cartListFragment = null;
    WishlistFragment wishlistFragment = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addProductListFragment();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                addProductListFragment();
                return true;
            case R.id.action_cart:
                addCartListFragment();
                return true;

            case R.id.action_wishlist:
                addWishListFragment();
                return true;

            default:
                   return super.onOptionsItemSelected(item);

        }
    }


   private void addProductListFragment(){

        if(productListFragment == null){
            productListFragment = new ProductListFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

       transaction.replace(R.id.fragment_container, productListFragment);
       transaction.addToBackStack(null);

       transaction.commit();

       getSupportActionBar().setDisplayHomeAsUpEnabled(false);
       getSupportActionBar().setTitle(getString(R.string.app_name));

   }

    private void addCartListFragment(){

        if(cartListFragment == null){
            cartListFragment = new CartListFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, cartListFragment);
        transaction.addToBackStack(null);

        transaction.commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.action_cart));

    }

    private void addWishListFragment(){

        if(wishlistFragment == null){
            wishlistFragment = new WishlistFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, wishlistFragment);
        transaction.addToBackStack(null);

        transaction.commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.action_wishlist));

    }


    @Override
    public void onButtonClick(View view, int id) {
        int viewId = view.getId();
        switch (viewId){
            case R.id.addToCart:
                productListFragment.addItemsToCart(id);
                break;
            case R.id.addToWishlist:
                productListFragment.addItemsToWishlist(id);
                break;
            case R.id.removeFromCart:
                cartListFragment.removeItemsFromCart(id);
                break;
            case R.id.removeFromWishlist:
                wishlistFragment.removeItemsFromWishlist(id);
                break;
            case R.id.moveFromWishlistToCart:
                wishlistFragment.addItemsToCart(id);
                break;
        }

    }

}
