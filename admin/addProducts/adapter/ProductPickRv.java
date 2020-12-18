package com.example.inuapp.admin.addProducts.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inuapp.R;
import com.example.inuapp.models.Products;
import com.example.inuapp.ui.cart.Cart;
import com.example.inuapp.ui.wishlist.Wishlist;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class ProductPickRv extends RecyclerView.Adapter<ProductPickRv.ViewHolder> {


    private LinkedList<Products> productsList;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private final Context mContext;


    public ProductPickRv(Context context, LinkedList<Products> productsList) {
        this.mInflater = LayoutInflater.from(context);
        this.productsList = productsList;
        this.mContext = context;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.product_chooser_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(productsList.get(position).getProductName());
        holder.postedAtTv.setText(productsList.get(position).getPostedAt());
        holder.description.setText(productsList.get(position).getProductDescription());
        holder.price.setText(String.valueOf(productsList.get(position).getProductMarketPricePerUnit()));
        Glide.with(mContext).load(productsList.get(position).getProductImageUrl()).into(holder.preview);
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return productsList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView preview;
        TextView name,price,description,postedAtTv;

        ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.productPrice);
            description = itemView.findViewById(R.id.productDescription);

            postedAtTv = itemView.findViewById(R.id.postedAtTv);
            preview = itemView.findViewById(R.id.productImage);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}