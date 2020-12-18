package com.example.inuapp.admin.orders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.inuapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class AdminOrdersRvAdapter extends RecyclerView.Adapter<AdminOrdersRvAdapter.ViewHolder> {


    private LinkedList<String> notificationList;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private final Context mContext;



    public AdminOrdersRvAdapter(Context context, LinkedList<String> notificationList) {
        this.mInflater = LayoutInflater.from(context);
        this.notificationList = notificationList;
        this.mContext = context;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.admin_order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }


    // total number of rows
    @Override
    public int getItemCount() {
        return notificationList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ViewHolder(View itemView) {
            super(itemView);

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