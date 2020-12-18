package com.example.inuapp.ui.wishlist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inuapp.R;
import com.example.inuapp.ui.cart.CartViewModel;
import com.example.inuapp.ui.wishlist.adapter.WishlistAdapter;

import java.util.LinkedList;


public class Wishlist extends AppCompatActivity {

    private WishlistAdapter wishlistAdapter;
    private final LinkedList<com.example.inuapp.models.Wishlist> wishlistLinkedList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        RecyclerView wishlistRv = findViewById(R.id.wishlistRv);
        wishlistRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        wishlistAdapter = new WishlistAdapter(this, wishlistLinkedList);
        wishlistRv.setAdapter(wishlistAdapter);

        populateWishlist();
    }

    private void populateWishlist() {
        WishlistViewModel wishlistViewModel = new ViewModelProvider(this).get(WishlistViewModel.class);
        wishlistViewModel.getWishList().observe(this, wishlist -> {
            wishlistLinkedList.add(wishlist);
            wishlistAdapter.notifyDataSetChanged();
        });
    }
}