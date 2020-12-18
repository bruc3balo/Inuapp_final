package com.example.inuapp.ui.cart.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
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
import org.w3c.dom.Node;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.inuapp.admin.addNewProduct.AddNewProductActivity.truncate;
import static com.example.inuapp.models.Cart.CART;
import static com.example.inuapp.models.Cart.NO_OF_ITEMS;
import static com.example.inuapp.models.Wishlist.WISHLIST;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {


    private LinkedList<Cart> cartLinkedList;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private final Context mContext;
    private final int[] items = new int[]{};


    public CartAdapter(Context context, LinkedList<Cart> cartLinkedList) {
        this.mInflater = LayoutInflater.from(context);
        this.cartLinkedList = cartLinkedList;
        this.mContext = context;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.commodityName_wish_cart.setText(cartLinkedList.get(position).getProductName());
        holder.commodityPrice_wish_cart.setText(String.valueOf(cartLinkedList.get(position).getProductPrice()));
        Glide.with(mContext).load(cartLinkedList.get(position).getProductImageUrl()).into(holder.productImage);

        items[position] = cartLinkedList.get(position).getNumberOfItems();

        holder.numberCart.setText(String.valueOf(cartLinkedList.get(position).getNumberOfItems()));
        holder.minus.setOnClickListener(v -> {
            holder.minus.setImageTintList(ColorStateList.valueOf(Color.RED));
            int current = cartLinkedList.get(position).getNumberOfItems();
            current--;

            if (current < 0) {
                current = 0;
            }
            /*DatabaseReference db = FirebaseDatabase.getInstance().getReference();
            db.child(CART).child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child(cartLinkedList.get(position).getProductId()).child(NO_OF_ITEMS).setValue(current).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        notifyDataSetChanged();
                        Toast.makeText(mContext, ""+ finalCurrent, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "Failed to minus", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
            items[position] = current;
            holder.numberCart.setText(current);
            new Handler().postDelayed(() -> holder.minus.setImageTintList(ColorStateList.valueOf(Color.BLACK)), 300);
            notifyDataSetChanged();
        });
        holder.add.setOnClickListener(v -> {
            holder.add.setImageTintList(ColorStateList.valueOf(Color.GREEN));
            int current = cartLinkedList.get(position).getNumberOfItems();
            current++;
            holder.numberCart.setText(current);
            items[position] = current;
            /*DatabaseReference db = FirebaseDatabase.getInstance().getReference();
            db.child(CART).child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child(cartLinkedList.get(position).getProductId()).child(NO_OF_ITEMS).setValue(current + 1).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        notifyDataSetChanged();
                        Toast.makeText(mContext, ""+ current, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "Failed to add", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
            new Handler().postDelayed(() -> holder.add.setImageTintList(ColorStateList.valueOf(Color.BLACK)), 300);
            notifyDataSetChanged();
        });

        holder.delete.setOnClickListener(v -> {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference();
            db.child(CART).child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child(cartLinkedList.get(position).getProductId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(mContext, "Deleted item", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "Failed to delete", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
           cartLinkedList = (LinkedList<Cart>) cartLinkedList.stream().distinct().collect(Collectors.toList());
        }*/

    }


    // total number of rows
    @Override
    public int getItemCount() {
        return cartLinkedList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView commodityName_wish_cart, commodityPrice_wish_cart;
        EditText numberCart;
        ImageView productImage;
        ImageButton minus, add, delete;

        ViewHolder(View itemView) {
            super(itemView);
            commodityName_wish_cart = itemView.findViewById(R.id.commodityName_wish_cart);
            commodityPrice_wish_cart = itemView.findViewById(R.id.commodityPrice_wish_cart);
            numberCart = itemView.findViewById(R.id.numberCart);
            productImage = itemView.findViewById(R.id.commodityImage_wish_cart);
            add = itemView.findViewById(R.id.cartAdd);
            minus = itemView.findViewById(R.id.cartMinus);
            delete = itemView.findViewById(R.id.remove_wish_cart);

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

    public int[] getItems(){
        return items;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}