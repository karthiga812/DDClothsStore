package com.example.ddclothsstore.model.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartItem {

    public static final String TABLE_NAME = "cartitem";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCTID = "productId";

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("productId")
    @Expose
    private Integer productId;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_PRODUCTID + " INTEGER"
                    + ")";

    public CartItem(){

    }

    public CartItem(int id, int productId) {
        super();
        this.id = id;
        this.productId = productId;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

}