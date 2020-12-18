package com.example.inuapp.ui.wishlist;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inuapp.models.Cart;
import com.example.inuapp.models.Wishlist;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import static com.example.inuapp.models.Cart.CART;
import static com.example.inuapp.models.Cart.PRODUCT_PRICE;
import static com.example.inuapp.models.Products.PRODUCT_ID;
import static com.example.inuapp.models.Wishlist.DATE_ADDED;
import static com.example.inuapp.models.Wishlist.WISHLIST;

public class WishlistViewModel extends ViewModel {
    private MutableLiveData<Wishlist> wishlistMutableLiveData;

    public WishlistViewModel() {
    }

    private MutableLiveData<Wishlist> getMyWishlistData() {
        wishlistMutableLiveData = new MutableLiveData<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child(WISHLIST).child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String productId = Objects.requireNonNull(ds.child(PRODUCT_ID).getValue()).toString();
                        String dateAdded = Objects.requireNonNull(ds.child(DATE_ADDED).getValue()).toString();
                        Wishlist wishlist = new Wishlist(productId, dateAdded);
                        wishlistMutableLiveData.setValue(wishlist);
                    }
                } else {
                    wishlistMutableLiveData.setValue(new Wishlist("fail", "fail"));
                    System.out.println("No data");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getMessage());
            }
        });

        return wishlistMutableLiveData;
    }

    public LiveData<Wishlist> getWishList() {
        return getMyWishlistData();
    }
}
