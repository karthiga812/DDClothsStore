package com.example.ddclothsstore.model;

import java.util.List;

public class ResourceManager {

    private static ResourceManager resourceManager = null;
    private List<Product> products = null;
    private List<CartItem> cartItems = null;

    // static method to create instance of Singleton class
    public static ResourceManager getInstance()
    {
        if (resourceManager == null)
            resourceManager = new ResourceManager();

        return resourceManager;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

}
