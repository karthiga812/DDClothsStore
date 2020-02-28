package com.example.ddclothsstore.view.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.ddclothsstore.R;
import com.example.ddclothsstore.model.Product;

import java.util.List;

public class ProductListActivity extends AppCompatActivity implements ClickCallback {

    List<Product> productsList = null;
    ProductListFragment productListFragment = null;
    CartListFragment cartListFragment = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
            case R.id.action_cart:
                addCartListFragment();
                return true;

            case R.id.action_wishlist:

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

       transaction.add(R.id.fragment_container, productListFragment);
       transaction.addToBackStack(null);

       transaction.commit();
       Toast.makeText(this,"fragment added ",Toast.LENGTH_LONG).show();
   }

    private void addCartListFragment(){

        if(cartListFragment == null){
            cartListFragment = new CartListFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, cartListFragment);
        transaction.addToBackStack(null);

        transaction.commit();
        Toast.makeText(this,"fragment added ",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void setProductsList(List<Product> productsList) {
        this.productsList = productsList;
    }

    public List<Product> getProductsList() {
        return productsList;
    }

    @Override
    public void onButtonClick(View view, int id) {
        int viewId = view.getId();
        switch (viewId){
            case R.id.addToCart:
                productListFragment.addItemsToCart(id);
                break;
            case R.id.addToWishlist:
                break;
            case R.id.removeFromCart:
                cartListFragment.removeItemsFromCart(id);
                break;

        }

    }

}
