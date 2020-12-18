package com.example.inuapp.admin.addProducts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.inuapp.R;
import com.example.inuapp.admin.AdminActivity;
import com.example.inuapp.admin.addNewProduct.adapter.NewProductRvAdapter;
import com.example.inuapp.admin.addProducts.adapter.AddProductRvAdapter;
import com.example.inuapp.models.Products;
import com.example.inuapp.ui.explore.ExploreViewModel;
import com.google.android.material.snackbar.Snackbar;
import static com.example.inuapp.admin.AdminActivity.currentAdminPage;

import java.util.LinkedList;

import static com.example.inuapp.admin.AdminActivity.fab;


public class AddProductsFragment extends Fragment {

    private LinkedList<Products> add_product_list = new LinkedList<>();

    public AddProductsFragment() {
        // Required empty public constructor
    }

    public static AddProductsFragment newInstance() {

        return new AddProductsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_add_products, container, false);

        currentAdminPage = 1;
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(view -> startActivity(new Intent(requireContext(), AddToMarket.class)));

        //Rv
        RecyclerView recyclerView = v.findViewById(R.id.add_product_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false));


        AddProductRvAdapter addProductRvAdapter = new AddProductRvAdapter(requireContext(),add_product_list);
        recyclerView.setAdapter(addProductRvAdapter);

        populateShop(addProductRvAdapter);

        return v;
    }


    private void populateShop(AddProductRvAdapter newProductFragment) {
        ExploreViewModel exploreViewModel = new ViewModelProvider(this).get(ExploreViewModel.class);
        exploreViewModel.getShopProductsList().observe(getViewLifecycleOwner(), products -> {
            add_product_list.add(products);
            newProductFragment.notifyDataSetChanged();
        });
    }
}