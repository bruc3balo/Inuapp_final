package com.example.inuapp.ui.explore.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.inuapp.R;
import com.example.inuapp.models.Cart;
import com.example.inuapp.models.Products;
import com.example.inuapp.models.Wishlist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Objects;

import static com.example.inuapp.admin.addNewProduct.AddNewProductActivity.truncate;
import static com.example.inuapp.models.Cart.CART;
import static com.example.inuapp.models.Wishlist.WISHLIST;

public class ExploreRv extends RecyclerView.Adapter<ExploreRv.ViewHolder> {


    private LinkedList<Products> productsList;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private final Context mContext;


    public ExploreRv(Context context, LinkedList<Products> productsList) {
        this.mInflater = LayoutInflater.from(context);
        this.productsList = productsList;
        this.mContext = context;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(productsList.get(position).getProductName());
        holder.postedAtTv.setText(productsList.get(position).getPostedAt());
        holder.description.setText(productsList.get(position).getProductDescription());

        holder.price.setText(String.valueOf(productsList.get(position).getProductMarketPricePerUnit()));
        Glide.with(mContext).load(productsList.get(position).getProductImageUrl()).into(holder.preview);
        holder.addToWishlistButton.setOnClickListener(v -> addToWishlist(position));

        holder.addToCartButton.setOnClickListener(v -> addToCart(position));
    }

    private void addToWishlist(int pos) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child(WISHLIST).child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child(productsList.get(pos).getProductId()).setValue(new Wishlist(productsList.get(pos).getProductId(), truncate(Calendar.getInstance().getTime().toString(), 16))).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(mContext.getApplicationContext(), "Added to wishlist", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext.getApplicationContext(), "failed to add to wishlist", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToCart(int pos) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        Cart cart = new Cart(productsList.get(pos).getProductId(), productsList.get(pos).getProductSellingPricePerUnit(), truncate(Calendar.getInstance().getTime().toString(), 16), 1, productsList.get(pos).getProductImageUrl(), productsList.get(pos).getProductName());
        db.child(CART).child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child(productsList.get(pos).getProductId()).setValue(cart).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(mContext.getApplicationContext(), "Added "+cart.getProductName() + "to cart ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext.getApplicationContext(), "failed to add to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return productsList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button addToCartButton, addToWishlistButton;
        ImageView preview;
        TextView name, price, description, postedAtTv;

        ViewHolder(View itemView) {
            super(itemView);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
            addToWishlistButton = itemView.findViewById(R.id.addToWishlistButton);

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