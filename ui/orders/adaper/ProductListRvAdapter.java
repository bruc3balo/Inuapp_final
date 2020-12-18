package com.example.inuapp.ui.orders.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inuapp.R;
import com.example.inuapp.models.Orders;
import com.example.inuapp.models.Products;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class ProductListRvAdapter extends RecyclerView.Adapter<ProductListRvAdapter.ViewHolder> {


    private LinkedList<Products> productsLinkedList;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private final Context mContext;


    public ProductListRvAdapter(Context context, LinkedList<Products> productsLinkedList) {
        this.mInflater = LayoutInflater.from(context);
        this.productsLinkedList = productsLinkedList;
        this.mContext = context;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.commodityDescription_order.setText(productsLinkedList.get(position).getProductDescription());
        holder.commodityPrice_order.setText(String.valueOf(productsLinkedList.get(position).getProductSellingPricePerUnit()));
        holder.commodityName_order.setText(productsLinkedList.get(position).getProductName());
        Glide.with(mContext).load(productsLinkedList.get(position).getProductImageUrl()).into(holder.commodityImage_order);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return productsLinkedList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView commodityName_order, commodityPrice_order, commodityDescription_order;
        ImageView commodityImage_order;

        ViewHolder(View itemView) {
            super(itemView);

            commodityName_order = itemView.findViewById(R.id.commodityName_order);
            commodityPrice_order = itemView.findViewById(R.id.commodityPrice_order);
            commodityDescription_order = itemView.findViewById(R.id.commodityDescription_order);
            commodityImage_order = itemView.findViewById(R.id.commodityImage_order);

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