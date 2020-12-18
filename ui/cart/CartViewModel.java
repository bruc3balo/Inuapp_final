package com.example.inuapp.ui.cart;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inuapp.models.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import static com.example.inuapp.models.Cart.CART;
import static com.example.inuapp.models.Cart.NO_OF_ITEMS;
import static com.example.inuapp.models.Cart.PRODUCT_PRICE;
import static com.example.inuapp.models.Products.PRODUCT_ID;
import static com.example.inuapp.models.Products.PRODUCT_IMAGE_URL;
import static com.example.inuapp.models.Products.PRODUCT_NAME;
import static com.example.inuapp.models.Wishlist.DATE_ADDED;

public class CartViewModel extends ViewModel {
    private MutableLiveData<Cart> cartMutableLiveData;

    public CartViewModel() {
    }

    private MutableLiveData<Cart> getMyCartData() {
        cartMutableLiveData = new MutableLiveData<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child(CART).child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String productId = Objects.requireNonNull(ds.child(PRODUCT_ID).getValue()).toString();
                        String dateAdded = Objects.requireNonNull(ds.child(DATE_ADDED).getValue()).toString();
                        Double price = Double.parseDouble(Objects.requireNonNull(ds.child(PRODUCT_PRICE).getValue()).toString());
                        int noOfItems = Integer.parseInt(Objects.requireNonNull(ds.child(NO_OF_ITEMS).getValue()).toString());
                        String img = Objects.requireNonNull(ds.child(PRODUCT_IMAGE_URL).getValue()).toString();
                        String productName = Objects.requireNonNull(ds.child(PRODUCT_NAME).getValue()).toString();

                        Cart cart = new Cart(productId, price, dateAdded, noOfItems, img, productName);
                        cartMutableLiveData.setValue(cart);
                    }

                } else {
                    cartMutableLiveData.setValue(new Cart("fail"));
                    System.out.println("No data");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getMessage());
            }
        });

        return cartMutableLiveData;
    }

    public LiveData<Cart> getCart() {
        return getMyCartData();
    }
}
