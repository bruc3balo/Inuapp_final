package com.example.inuapp.ui.reviews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.inuapp.R;
import com.example.inuapp.models.Products;

import java.util.LinkedList;


public class ProductReviewAdapter extends RecyclerView.Adapter<ProductReviewAdapter.SliderViewHolder> {

    private Context context;
    private LinkedList<Products> productsList;

    public ProductReviewAdapter(Context context, LinkedList<Products> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_rating, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final SliderViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }


    static class SliderViewHolder extends RecyclerView.ViewHolder {

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

}
