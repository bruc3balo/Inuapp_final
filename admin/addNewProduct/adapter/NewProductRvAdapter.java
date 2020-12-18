package com.example.inuapp.admin.addNewProduct.adapter;

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

public class NewProductRvAdapter extends RecyclerView.Adapter<NewProductRvAdapter.ViewHolder> {


    private LinkedList<Products> productList;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private final Context mContext;
    private Products product = new Products();


    public NewProductRvAdapter(Context context, LinkedList<Products> productList) {
        this.mInflater = LayoutInflater.from(context);
        this.productList = productList;
        this.mContext = context;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.new_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.commodityDescription_admin.setText(productList.get(position).getProductDescription());
        holder.commodityPrice_admin.setText(String.valueOf(productList.get(position).getProductSellingPricePerUnit()));
        holder.commodityName_admin.setText(productList.get(position).getProductName());
        Glide.with(mContext).load(productList.get(position).getProductImageUrl()).into(holder.commodityImage_admin);

        holder.edit_admin.setOnClickListener(v -> {
            Dialog editD = new Dialog(mContext);
            editD.setContentView(R.layout.edit_meta_dialog);
            EditText commodityDescription_admin_dialog, commodityPrice_admin_dialog, commodityName_admin_dialog;
            ImageView commodityImage_admin_dialog;
            ImageButton save_edit_admin;

            editD.show();

            commodityDescription_admin_dialog = editD.findViewById(R.id.commodityDescription_admin_dialog);
            commodityPrice_admin_dialog = editD.findViewById(R.id.commodityPrice_admin_dialog);
            commodityName_admin_dialog = editD.findViewById(R.id.commodityName_admin_dialog);
            commodityImage_admin_dialog = editD.findViewById(R.id.commodityImage_admin_dialog);
            save_edit_admin = editD.findViewById(R.id.save_edit_admin);

            commodityDescription_admin_dialog.setText(productList.get(position).getProductDescription());
            commodityPrice_admin_dialog.setText(String.valueOf(productList.get(position).getProductSellingPricePerUnit()));
            commodityName_admin_dialog.setText(productList.get(position).getProductName());
            Glide.with(mContext).load(productList.get(position).getProductImageUrl()).into(commodityImage_admin_dialog);

            save_edit_admin.setOnClickListener(v1 -> {

                if (validateUpdate(commodityPrice_admin_dialog, commodityName_admin_dialog, commodityDescription_admin_dialog, position)) {
                    updateItem(product, editD);

                } else {
                    Toast.makeText(mContext, "Check details", Toast.LENGTH_SHORT).show();
                }
            });

        });

        holder.delete_admin.setOnClickListener(v -> {
            Dialog delete = new Dialog(mContext);
            delete.setContentView(R.layout.confirm_delete);
            delete.show();

            Button no = delete.findViewById(R.id.no);
            Button yes = delete.findViewById(R.id.yes);


            yes.setOnClickListener(v13 -> deleteItem(position, delete));

            no.setOnClickListener(v12 -> delete.dismiss());
        });
    }

    private void deleteItem(int position, Dialog d) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(PRODUCTS).document(productList.get(position).getProductId()).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println(productList.get(position).getProductName() + " has been deleted");
                try {
                    db.collection(SHOP).document(productList.get(position).getProductId()).delete().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            System.out.println(productList.get(position).getProductName() + " has been deleted from shop");
                            if (d.isShowing()) {
                                d.dismiss();
                                notifyDataSetChanged();
                            }
                        } else {
                            System.out.println(productList.get(position).getProductName() + " has not been deleted");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (d.isShowing()) {
                    d.dismiss();
                    notifyDataSetChanged();
                }
            } else {
                System.out.println(productList.get(position).getProductName() + " has not been deleted");
            }
        });
    }

    private boolean validateUpdate(EditText price, EditText name, EditText desc, int position) {
        boolean valid = false;
        product = productList.get(position);

        if (price.getText().toString().isEmpty()) {
            price.setError("");
            price.requestFocus();
        } else if (name.getText().toString().isEmpty()) {
            name.setError("");
            name.requestFocus();
        } else if (desc.getText().toString().isEmpty()) {
            desc.setError("");
            desc.requestFocus();
        } else {
            valid = true;
            product.setProductName(name.getText().toString());
            product.setProductDescription(desc.getText().toString());
            product.setProductMarketPricePerUnit(Double.parseDouble(price.getText().toString()));
            product.setProductSellingPricePerUnit(Double.parseDouble(price.getText().toString()));
            product.setPostedAt(truncate(Calendar.getInstance().getTime().toString(), 16));
        }
        return valid;
    }

    private void updateItem(Products product, Dialog d) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(PRODUCTS).document(product.getProductId()).set(product).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println(product.getProductName() + " has been updated");
                if (d.isShowing()) {
                    d.dismiss();
                    notifyDataSetChanged();
                }
            } else {
                System.out.println(product.getProductName() + " has not been updated");
            }
        });
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return productList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView commodityName_admin, commodityPrice_admin, commodityDescription_admin, commodityNumber_admin;
        ImageView commodityImage_admin;
        ImageButton edit_admin, delete_admin;

        ViewHolder(View itemView) {
            super(itemView);
            commodityName_admin = itemView.findViewById(R.id.commodityName_admin);
            commodityPrice_admin = itemView.findViewById(R.id.commodityPrice_admin);
            commodityDescription_admin = itemView.findViewById(R.id.commodityDescription_admin);
            //  commodityNumber_admin = itemView.findViewById(R.id.commodityNumber_admin);
            commodityImage_admin = itemView.findViewById(R.id.commodityImage_admin);
            edit_admin = itemView.findViewById(R.id.edit_admin);
            delete_admin = itemView.findViewById(R.id.delete_admin);
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