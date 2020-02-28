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
import com.example.ddclothsstore.model.Product;
import com.example.ddclothsstore.view.ui.ClickCallback;

import java.util.List;

public class MyRecycleviewAdapter extends RecyclerView.Adapter<MyRecycleviewAdapter.ViewHolder> {

    private List<Product> products = null;
    private LayoutInflater layoutInflater = null;
    private ViewGroup parent = null;
    private ClickCallback clickCallbacks = null;

    public MyRecycleviewAdapter(Context context, List<Product> products){
        clickCallbacks = (ClickCallback) context;
        layoutInflater = LayoutInflater.from(context);
        this.products = products;
    }

    @NonNull
    @Override
    public MyRecycleviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = layoutInflater.inflate(R.layout.recycleview_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Product product = products.get(position);
        holder.tvName.setText(product.getName());
        holder.tvCategory.setText(product.getCategory());
        holder.tvPrice.setText(product.getPrice());
        holder.tvOldPrice.setText(product.getOldPrice());
        holder.tvStock.setText(String.valueOf(product.getStock()));
        holder.btnAdToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCallbacks.onButtonClick(v,product.getId());
            }
        });
        holder.btnAdToWishlist.setOnClickListener(new View.OnClickListener() {
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
        private TextView tvCategory = null;
        private TextView tvPrice = null;
        private TextView tvOldPrice = null;
        private TextView tvStock = null;
        private Button btnAdToCart = null;
        private Button btnAdToWishlist = null;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name);
            tvCategory = itemView.findViewById(R.id.category);
            tvPrice = itemView.findViewById(R.id.price);
            tvOldPrice = itemView.findViewById(R.id.oldprice);
            tvStock = itemView.findViewById(R.id.stock);
            btnAdToCart = itemView.findViewById(R.id.addToCart);
            btnAdToWishlist = itemView.findViewById(R.id.addToWishlist);
        }
    }


}
