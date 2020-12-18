package com.example.inuapp.admin.addProducts;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inuapp.R;
import com.example.inuapp.admin.addProducts.adapter.ProductPickRv;
import com.example.inuapp.models.Products;
import com.example.inuapp.ui.explore.ExploreViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.LinkedList;

import static com.example.inuapp.admin.addNewProduct.AddNewProductActivity.truncate;
import static com.example.inuapp.models.Products.COMPUTING;
import static com.example.inuapp.models.Products.ELECTRONICS;
import static com.example.inuapp.models.Products.FASHION;
import static com.example.inuapp.models.Products.GAMING;
import static com.example.inuapp.models.Products.HOME;
import static com.example.inuapp.models.Products.SHOP;
import static com.example.inuapp.models.Products.SPORTING;


public class AddToMarket extends AppCompatActivity {

    private final LinkedList<Products> productsList = new LinkedList<>();
    private String categoryS = "";
    private Products products = new Products();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_market);

        RecyclerView productMarketRv = findViewById(R.id.productMarketRv);
        productMarketRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        ProductPickRv productPickRv = new ProductPickRv(this, productsList);
        productMarketRv.setAdapter(productPickRv);


        //Category
        RadioGroup categoryGroup = findViewById(R.id.categoryGroup);
        categoryGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                default:
                    break;
                case R.id.electronicRadio:
                    categoryS = ELECTRONICS;
                    break;

                case R.id.homeRadio:
                    categoryS = HOME;
                    break;

                case R.id.gamingRadio:
                    categoryS = GAMING;
                    break;

                case R.id.fashionRadio:
                    categoryS = FASHION;
                    break;

                case R.id.computingRadio:
                    categoryS = COMPUTING;
                    break;

                case R.id.sportingRadio:
                    categoryS = SPORTING;
                    break;
            }
            Toast.makeText(AddToMarket.this, "" + categoryS, Toast.LENGTH_SHORT).show();
        });

        //Unit
        EditText unitEdit = findViewById(R.id.unitEdit);

        //Price
        EditText priceEdit = findViewById(R.id.priceEdit);

        //Description
        EditText description = findViewById(R.id.descriptionEdit);

        productPickRv.setClickListener((view, position) -> {
            view.setBackgroundColor(Color.CYAN);
            setDetails(unitEdit, priceEdit, description, position);
            new Handler().postDelayed(() -> view.setBackgroundColor(Color.WHITE), 300);
        });

        Button addToMarketButton = findViewById(R.id.addToMarketButton);
        addToMarketButton.setOnClickListener(v -> {
            if (validForm(unitEdit,priceEdit,description)) {
                addProductToShop(addToMarketButton,products);
            }
        });

        populateShop(productPickRv);
    }

    private boolean validForm(EditText unitEdit, EditText priceEdit, EditText description) {
        boolean valid = false;
        if (unitEdit.getText().toString().isEmpty()) {
            unitEdit.setError("Empty");
            unitEdit.requestFocus();
        } else if (priceEdit.getText().toString().isEmpty()) {
            priceEdit.setError("Empty");
            priceEdit.requestFocus();
        } else if (description.getText().toString().isEmpty()) {
            description.setError("Empty");
            description.requestFocus();
        } else {
            products.setProductCount(unitEdit.getText().toString());
            products.setProductSellingPricePerUnit(Double.parseDouble(priceEdit.getText().toString()));
            products.setProductDescription(description.getText().toString());
            products.setPostedAt(truncate(Calendar.getInstance().getTime().toString(),16));
            valid = true;
        }
        return valid;
    }

    private void populateShop(ProductPickRv newProductFragment) {
        ExploreViewModel exploreViewModel = new ViewModelProvider(this).get(ExploreViewModel.class);
        exploreViewModel.getProductsData().observe(this, products -> {
            productsList.add(products);
            newProductFragment.notifyDataSetChanged();
        });
    }

    private void setDetails (EditText unitEdit, EditText priceEdit, EditText descriptionEdit, int position) {
        products = productsList.get(position);
        unitEdit.setError("Fill units of" + products.getProductUnit());
        priceEdit.setText(String.valueOf(products.getProductSellingPricePerUnit()));
        descriptionEdit.setText(products.getProductDescription());
    }

    private void addProductToShop (Button button, Products product) {
        button.setEnabled(false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(SHOP).document(product.getProductId()).set(product).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getApplicationContext(), "Added to market", Toast.LENGTH_SHORT).show();
                System.out.println(product.getProductName() + " has been added to the market");
                finish();
            } else {
                System.out.println(product.getProductName() + " has not been added to the market");
                button.setEnabled(true);
            }
        });
    }

}