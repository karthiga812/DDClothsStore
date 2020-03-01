package com.example.ddclothsstore.model;

import android.content.Context;

import com.example.ddclothsstore.model.database.CartItem;
import com.example.ddclothsstore.model.database.DatabaseHelper;
import com.example.ddclothsstore.model.database.Product;
import com.example.ddclothsstore.model.database.WishlistItem;

import java.util.List;

public class ResourceManager {

    private static ResourceManager resourceManager = null;
    private List<Product> products = null;
    private List<CartItem> cartItems = null;
    private List<WishlistItem> wishlistItems = null;
    private static DatabaseHelper db;

    // static method to create instance of Singleton class
    public static ResourceManager getInstance(Context context)
    {
        if (resourceManager == null)
            resourceManager = new ResourceManager();

        if(db == null){
            db = new DatabaseHelper(context);
        }

        return resourceManager;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        db.insertCartItems(cartItems);
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        db.insertProductList(products);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setWishlistItems() {
        this.wishlistItems = db.getAllWishListItems();
    }

    public List<WishlistItem> getWishlistItems() {
        return wishlistItems;
    }

    public List<Product> getProductsInCart(){
        return db.getProductsInCart();
    }

    public List<Product> getProductsInWishlist() {
        return db.getProductsInWishlist();
    }

    public long addProductsToWishlist(int productId) {
        return db.addProductsToWishlist(productId);
    }

    public void removeProductsFromWishlist(int productId) {
        db.deleteWishlistItem(productId);
    }

    public Product getProductById(int productId){
        return db.getProduct(productId);
    }
}
