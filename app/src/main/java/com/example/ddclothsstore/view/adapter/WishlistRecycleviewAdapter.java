package com.example.ddclothsstore.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ddclothsstore.R;
import com.example.ddclothsstore.model.database.Product;
import com.example.ddclothsstore.view.ui.ClickCallback;

import java.util.List;

public class WishlistRecycleviewAdapter extends RecyclerView.Adapter<WishlistRecycleviewAdapter.ViewHolder> {

    private List<Product> products = null;
    private LayoutInflater layoutInflater = null;
    private ClickCallback clickCallbacks = null;

    public WishlistRecycleviewAdapter(Context context, List<Product> products){
        clickCallbacks = (ClickCallback) context;
        layoutInflater = LayoutInflater.from(context);
        this.products = products;
    }

    @NonNull
    @Override
    public WishlistRecycleviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.wishlist_recycleview_element, parent, false);
        return new WishlistRecycleviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistRecycleviewAdapter.ViewHolder holder, final int position) {
        final Product product = products.get(position);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(product.getPrice());

        holder.btnRemoveFromWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallbacks.onButtonClick(v,product.getId());
            }
        });

        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallbacks.onButtonClick(v,product.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName = null;
        private TextView tvPrice = null;
        private Button btnRemoveFromWishlist = null;
        private Button btnAddToCart = null;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name);
            tvPrice = itemView.findViewById(R.id.price);
            btnRemoveFromWishlist = itemView.findViewById(R.id.removeFromWishlist);
            btnAddToCart = itemView.findViewById(R.id.moveFromWishlistToCart);
        }
    }
}
