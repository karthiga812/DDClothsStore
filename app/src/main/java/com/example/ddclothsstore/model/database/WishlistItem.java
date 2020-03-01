package com.example.ddclothsstore.model.database;

public class WishlistItem {

    public static final String TABLE_NAME = "wishlistitem";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCTID = "productId";

    private Integer id;
    private Integer productId;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PRODUCTID + " INTEGER"
                    + ")";

    public WishlistItem(){

    }

    public WishlistItem(int id, int productId) {
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
