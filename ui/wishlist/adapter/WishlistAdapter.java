package com.example.inuapp.ui.wishlist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inuapp.R;
import com.example.inuapp.models.Cart;
import com.example.inuapp.models.Wishlist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Objects;

import static com.example.inuapp.models.Wishlist.WISHLIST;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {


    private LinkedList<Wishlist> wishLinkedList;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private final Context mContext;


    public WishlistAdapter(Context context, LinkedList<Wishlist> wishLinkedList) {
        this.mInflater = LayoutInflater.from(context);
        this.wishLinkedList = wishLinkedList;
        this.mContext = context;
    }


    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.wishlist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.commodityName_wish.setText(wishLinkedList.get(position).getProductId());
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Added to cart", Toast.LENGTH_SHORT).show();
            }
        });

        holder.remove_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                db.child(WISHLIST).child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child(wishLinkedList.get(position).getProductId()).removeValue().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(mContext, "Removed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "Failed to remove", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //  holder.commodityPrice_wish_cart.setText(String.valueOf(wishLinkedList.get(position).getProductPrice()));
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return wishLinkedList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView commodityName_wish, commodityPrice_wish_cart, numberCart;
        ImageButton remove_wish, addToCart;

        ViewHolder(View itemView) {
            super(itemView);
            commodityName_wish = itemView.findViewById(R.id.commodityName_wish);
            //   commodityPrice_wish_cart = itemView.findViewById(R.id.commodityPrice_wish_cart);
            //  numberCart = itemView.findViewById(R.id.numberCart);
            remove_wish = itemView.findViewById(R.id.remove_wish);
            addToCart = itemView.findViewById(R.id.addCart_wish);
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