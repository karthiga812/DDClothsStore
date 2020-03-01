package com.example.ddclothsstore.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "store_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Product.CREATE_TABLE);
        db.execSQL(CartItem.CREATE_TABLE);
        db.execSQL(WishlistItem.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void insertProductList(List<Product> products) {

        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        db.delete(Product.TABLE_NAME,null,null);
        try {
            ContentValues values = new ContentValues();
            for (Product product : products) {
                values.put(Product.COLUMN_ID, product.getId());
                values.put(Product.COLUMN_NAME, product.getName());
                values.put(Product.COLUMN_CATEGORY, product.getCategory());
                values.put(Product.COLUMN_PRICE, product.getPrice());
                values.put(Product.COLUMN_OLDPRICE, product.getOldPrice());
                values.put(Product.COLUMN_STOCK, product.getStock());

                db.insert(Product.TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

    }


    public void insertCartItems(List<CartItem> cartItems) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();
        db.delete(CartItem.TABLE_NAME,null,null);
        try {
            ContentValues values = new ContentValues();
            for (CartItem cartItem : cartItems) {
                values.put(CartItem.COLUMN_ID, cartItem.getId());
                values.put(CartItem.COLUMN_PRODUCTID, cartItem.getProductId());
                db.insert(CartItem.TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

    }

    public long addProductsToWishlist(int productId) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WishlistItem.COLUMN_PRODUCTID, productId);
        // insert row
        long id = db.insert(WishlistItem.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Product.TABLE_NAME + " ORDER BY " +
                Product.COLUMN_NAME + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndex(Product.COLUMN_ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(Product.COLUMN_NAME)));
                product.setCategory(cursor.getString(cursor.getColumnIndex(Product.COLUMN_CATEGORY)));
                product.setPrice(cursor.getString(cursor.getColumnIndex(Product.COLUMN_PRICE)));
                product.setOldPrice(cursor.getString(cursor.getColumnIndex(Product.COLUMN_OLDPRICE)));
                product.setStock(Integer.valueOf(cursor.getString(cursor.getColumnIndex(Product.COLUMN_STOCK))));

                products.add(product);
            } while (cursor.moveToNext());
        }

        db.close();
        return products;
    }

    public List<Product> getProductsInCart(){

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables("Product product JOIN CartItem cart ON product.id=cart.productId");
        Cursor cursor = qb.query(getReadableDatabase(), new String[] { "product.id",
                                "product.name", "product.category", "product.price", "product.oldPrice","product.stock" },
                null, null, null,
                        null, null);
        return getProductsFromCursor(cursor);

    }

    private List<Product> getProductsFromCursor(Cursor cursor){

        List<Product> productsInCart = null;

        if(cursor == null){
            return productsInCart;
        }
        if (cursor.moveToFirst()) {
            productsInCart = new ArrayList<>(cursor.getCount());
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndex(Product.COLUMN_ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(Product.COLUMN_NAME)));
                product.setCategory(cursor.getString(cursor.getColumnIndex(Product.COLUMN_CATEGORY)));
                product.setPrice(cursor.getString(cursor.getColumnIndex(Product.COLUMN_PRICE)));
                product.setOldPrice(cursor.getString(cursor.getColumnIndex(Product.COLUMN_OLDPRICE)));
                product.setStock(Integer.valueOf(cursor.getString(cursor.getColumnIndex(Product.COLUMN_STOCK))));

                productsInCart.add(product);
            } while (cursor.moveToNext());
        }

        return productsInCart;
    }


    public List<Product> getProductsInWishlist(){

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables("Product product JOIN WishlistItem wishlist ON product.id=wishlist.productId");
        Cursor cursor = qb.query(getReadableDatabase(), new String[] { "product.id",
                        "product.name", "product.category", "product.price", "product.oldPrice","product.stock" },
                null, null, null,
                null, null);
        return getProductsFromCursor(cursor);

    }

    public void deleteCartItem(CartItem cartItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CartItem.TABLE_NAME, CartItem.COLUMN_ID + " = ?",
                new String[]{String.valueOf(cartItem.getId())});
        db.close();
    }

    public void deleteWishlistItem(int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(WishlistItem.TABLE_NAME, WishlistItem.COLUMN_ID + " = ?",
                new String[]{String.valueOf(productId)});
        db.close();
    }

    public List<WishlistItem> getAllWishListItems() {
        List<WishlistItem> wishlistItems = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + WishlistItem.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                WishlistItem wishlistItem = new WishlistItem();
                wishlistItem.setId(cursor.getInt(cursor.getColumnIndex(WishlistItem.COLUMN_ID)));
                wishlistItem.setProductId(cursor.getInt(cursor.getColumnIndex(WishlistItem.COLUMN_PRODUCTID)));

                wishlistItems.add(wishlistItem);
            } while (cursor.moveToNext());
        }

        db.close();
        return wishlistItems;
    }

    public Product getProduct(int productId) {
        Product product = null;

        String selectQuery = "SELECT  * FROM " + Product.TABLE_NAME + " WHERE " +
                Product.COLUMN_ID + " = " + productId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

                product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndex(Product.COLUMN_ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(Product.COLUMN_NAME)));
                product.setCategory(cursor.getString(cursor.getColumnIndex(Product.COLUMN_CATEGORY)));
                product.setPrice(cursor.getString(cursor.getColumnIndex(Product.COLUMN_PRICE)));
                product.setOldPrice(cursor.getString(cursor.getColumnIndex(Product.COLUMN_OLDPRICE)));
                product.setStock(Integer.valueOf(cursor.getString(cursor.getColumnIndex(Product.COLUMN_STOCK))));


        }

        db.close();
        return product;
    }
}
