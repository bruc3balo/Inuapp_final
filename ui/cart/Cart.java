package com.example.inuapp.ui.cart;

import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inuapp.R;
import com.example.inuapp.models.Orders;
import com.example.inuapp.models.Products;
import com.example.inuapp.ui.cart.adapter.CartAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

import static com.example.inuapp.admin.addNewProduct.AddNewProductActivity.truncate;
import static com.example.inuapp.models.Orders.ORDERS;
import static com.example.inuapp.models.Orders.ORDER_SUR;


public class Cart extends AppCompatActivity {

    private CartAdapter cartAdapter;
    private final LinkedList<com.example.inuapp.models.Cart> cartLinkedList = new LinkedList<>();
    private LinkedList<Double> totals = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        RecyclerView cartRv = findViewById(R.id.cartRv);
        cartRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        cartAdapter = new CartAdapter(this, cartLinkedList);
        cartRv.setAdapter(cartAdapter);

        Button confirm_cart_b = findViewById(R.id.confirm_cart_b);
        confirm_cart_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog total = new Dialog(Cart.this);
                total.setContentView(R.layout.total_layout);
                total.show();
                Button orderB = total.findViewById(R.id.orderB);
                orderB.setOnClickListener(v1 -> placeOrder());
                TextView totalCost = total.findViewById(R.id.totalCost);
                totalCost.setText(String.valueOf(getTotal()));
            }
        });

        populateCart();
    }

    private void populateCart() {
        CartViewModel cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getCart().observe(this, cart -> {
            cartLinkedList.addAll(Collections.singleton(cart));
            cartAdapter.notifyDataSetChanged();
        });
    }

    private void placeOrder() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String date = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            date = truncate(Calendar.getInstance().getTime().toString(), 16);
        }
        String orderNo = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            orderNo = date.trim() + "-" + ORDER_SUR;
        }

        LinkedList<Products> productsList = new LinkedList<>();
        for (int i = 0; i <= cartLinkedList.size() - 1; i++) {
            Products products = new Products(cartLinkedList.get(i).getProductId(), cartLinkedList.get(i).getProductName(), cartLinkedList.get(i).getProductImageUrl(), cartLinkedList.get(i).getProductPrice(), String.valueOf(cartLinkedList.get(i).getNumberOfItems()));
            productsList.add(products);
        }

        Orders orders = new Orders(orderNo, productsList, String.valueOf(getTotal()), date, Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        db.collection(ORDERS).document(orders.getOrderNo()).set(orders).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(Cart.this, "Order placed", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(Cart.this, "Failed to place order", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private double getTotal() {
        double total = 0;
        for (int i = 0; i <= cartLinkedList.size() - 1; i++) {
            Double price = cartLinkedList.get(i).getProductPrice();
            int number = cartAdapter.getItems()[i];
            double amount = number * price;
            System.out.println("Am"+amount);
            total = total + amount;
            System.out.println("Tt"+total);

        }
        return total;
    }
}