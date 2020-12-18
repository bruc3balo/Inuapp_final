package com.example.inuapp.ui.orders.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.inuapp.R;
import com.example.inuapp.models.Orders;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class OrdersRvAdapter extends RecyclerView.Adapter<OrdersRvAdapter.ViewHolder> {


    private LinkedList<Orders> ordersLinkedList;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private final Context mContext;


    public OrdersRvAdapter(Context context, LinkedList<Orders> ordersLinkedList) {
        this.mInflater = LayoutInflater.from(context);
        this.ordersLinkedList = ordersLinkedList;
        this.mContext = context;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.totalProductsTv.setText(ordersLinkedList.get(position).getOrderAmount());
        holder.userNameTv.setText(ordersLinkedList.get(position).getUserId());
        holder.dateOrderTv.setText(ordersLinkedList.get(position).getDeliveryDate());
        holder.orderProductsRv.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
        //ProductListRvAdapter productListRvAdapter = new ProductListRvAdapter(mContext,ordersLinkedList.get(position).getProductsOrdered());
       // holder.orderProductsRv.setAdapter(productListRvAdapter);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return ordersLinkedList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView dateOrderTv, userNameTv, totalProductsTv;
        RecyclerView orderProductsRv;

        ViewHolder(View itemView) {
            super(itemView);
            dateOrderTv = itemView.findViewById(R.id.dateOrderTv);
            userNameTv = itemView.findViewById(R.id.userNameTv);
            totalProductsTv = itemView.findViewById(R.id.totalProductsTv);
            orderProductsRv = itemView.findViewById(R.id.orderProductsRv);
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