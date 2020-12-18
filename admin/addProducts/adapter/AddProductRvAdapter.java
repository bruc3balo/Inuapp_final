package com.example.inuapp.admin.addProducts.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.inuapp.R;
import com.example.inuapp.models.Products;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.LinkedList;

import static com.example.inuapp.admin.addNewProduct.AddNewProductActivity.truncate;
import static com.example.inuapp.models.Products.PRODUCTS;
import static com.example.inuapp.models.Products.SHOP;

public class AddProductRvAdapter extends RecyclerView.Adapter<AddProductRvAdapter.ViewHolder> {


    private final LinkedList<Products> notificationList;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private final Context mContext;
    private Products product = new Products();


    public AddProductRvAdapter(Context context, LinkedList<Products> notificationList) {
        this.mInflater = LayoutInflater.from(context);
        this.notificationList = notificationList;
        this.mContext = context;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.add_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.commodityPrice_admin.setText(String.valueOf(notificationList.get(position).getProductSellingPricePerUnit()));
        holder.commodityDescription_admin.setText(notificationList.get(position).getProductDescription());
        holder.commodityName_admin.setText(notificationList.get(position).getProductName());
        holder.commodityNumber_admin.setText(notificationList.get(position).getProductCount());
        Glide.with(mContext).load(notificationList.get(position).getProductImageUrl()).into(holder.commodityImage_admin);

        holder.edit_item.setOnClickListener(v -> {
            Dialog edit_shop_item = new Dialog(mContext);
            edit_shop_item.setContentView(R.layout.edit_shop_item_dialog);

            EditText commodityDescription_admin_dialog, commodityPrice_admin_dialog, commodityName_admin_dialog, commodityNumber_admin_dialog;
            ImageView commodityImage_admin_dialog;
            ImageButton save_edit_admin;

            edit_shop_item.show();

            commodityDescription_admin_dialog = edit_shop_item.findViewById(R.id.commodityDescription_admin_dialog);
            commodityPrice_admin_dialog = edit_shop_item.findViewById(R.id.commodityPrice_admin_dialog);
            commodityName_admin_dialog = edit_shop_item.findViewById(R.id.commodityName_admin_dialog);
            commodityImage_admin_dialog = edit_shop_item.findViewById(R.id.commodityImage_admin_dialog);
            commodityNumber_admin_dialog = edit_shop_item.findViewById(R.id.commodityNumber_admin_dialog);
            save_edit_admin = edit_shop_item.findViewById(R.id.shop_edit_admin_save);

            commodityDescription_admin_dialog.setText(notificationList.get(position).getProductDescription());
            commodityPrice_admin_dialog.setText(String.valueOf(notificationList.get(position).getProductSellingPricePerUnit()));
            commodityName_admin_dialog.setText(notificationList.get(position).getProductName());
            Glide.with(mContext).load(notificationList.get(position).getProductImageUrl()).into(commodityImage_admin_dialog);
            commodityNumber_admin_dialog.setText(notificationList.get(position).getProductCount());

            save_edit_admin.setOnClickListener(v1 -> {

                if (validateUpdate(commodityPrice_admin_dialog, commodityName_admin_dialog, commodityDescription_admin_dialog, commodityNumber_admin_dialog, position)) {
                    updateItem(product, edit_shop_item);
                } else {
                    Toast.makeText(mContext, "Check details", Toast.LENGTH_SHORT).show();
                }
            });

        });

        holder.delete.setOnClickListener(v -> {
            Dialog delete = new Dialog(mContext);
            delete.setContentView(R.layout.confirm_delete);
            delete.show();

            Button no = delete.findViewById(R.id.no);
            Button yes = delete.findViewById(R.id.yes);


            yes.setOnClickListener(v13 -> deleteItem(position,delete));

            no.setOnClickListener(v12 -> delete.dismiss());
        });
    }

    private void deleteItem(int position,Dialog d) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(SHOP).document(notificationList.get(position).getProductId()).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println(notificationList.get(position).getProductName() + " has been deleted");
                if (d.isShowing()) {
                    d.dismiss();
                    notifyDataSetChanged();
                }
            } else {
                System.out.println(notificationList.get(position).getProductName() + " has not been deleted");
            }
        });
    }

    private void updateItem(Products product, Dialog d) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(SHOP).document(product.getProductId()).set(product).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(mContext.getApplicationContext(), "Added to market", Toast.LENGTH_SHORT).show();
                System.out.println(product.getProductName() + " has been updated to the market");
                if (d.isShowing()) {
                    d.dismiss();
                }
            } else {
                System.out.println(product.getProductName() + " has not been updaed to the market");
            }
        });
    }

    private boolean validateUpdate(EditText price, EditText name, EditText desc, EditText number, int position) {
        boolean valid = false;
        product = notificationList.get(position);

        if (price.getText().toString().isEmpty()) {
            price.setError("");
            price.requestFocus();
        } else if (name.getText().toString().isEmpty()) {
            name.setError("");
            name.requestFocus();
        } else if (desc.getText().toString().isEmpty()) {
            desc.setError("");
            desc.requestFocus();
        } else if (number.getText().toString().isEmpty()) {
            number.setError("");
            number.requestFocus();
        } else {
            valid = true;
            product.setProductName(name.getText().toString());
            product.setProductDescription(desc.getText().toString());
            //product.setProductMarketPricePerUnit(Double.parseDouble(price.getText().toString()));
            product.setProductSellingPricePerUnit(Double.parseDouble(price.getText().toString()));
            product.setProductCount(number.getText().toString());
            product.setPostedAt(truncate(Calendar.getInstance().getTime().toString(), 16));
        }
        return valid;
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return notificationList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView commodityName_admin, commodityPrice_admin, commodityDescription_admin, commodityNumber_admin;
        ImageView commodityImage_admin;
        ImageButton edit_item,delete;

        ViewHolder(View itemView) {
            super(itemView);

            commodityName_admin = itemView.findViewById(R.id.commodityName_admin);
            commodityPrice_admin = itemView.findViewById(R.id.commodityPrice_admin);
            commodityDescription_admin = itemView.findViewById(R.id.commodityDescription_admin);
            commodityNumber_admin = itemView.findViewById(R.id.commodityNumber_admin);
            commodityImage_admin = itemView.findViewById(R.id.commodityImage_admin);
            edit_item = itemView.findViewById(R.id.edit_admin);
            delete = itemView.findViewById(R.id.delete_admin);

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