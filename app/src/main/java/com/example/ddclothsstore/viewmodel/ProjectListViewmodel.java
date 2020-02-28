package com.example.ddclothsstore.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.ddclothsstore.model.NetworkClient;
import com.example.ddclothsstore.model.Product;

import java.util.List;

public class ProjectListViewmodel extends AndroidViewModel {

    private List<Product> products = null;

    public ProjectListViewmodel(@NonNull Application application) {
        super(application);

    }

    public List<Product> getProductsList(){
        products = NetworkClient.getInstance().getProductsList();
        return products;
    }
}
