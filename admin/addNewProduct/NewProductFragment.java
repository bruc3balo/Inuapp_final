package com.example.inuapp.admin.addNewProduct;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inuapp.R;
import com.example.inuapp.admin.AdminActivity;
import com.example.inuapp.admin.addNewProduct.adapter.NewProductRvAdapter;
import com.example.inuapp.models.Products;
import com.example.inuapp.ui.explore.ExploreViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.LinkedList;

import static com.example.inuapp.admin.AdminActivity.currentAdminPage;
import static com.example.inuapp.admin.AdminActivity.fab;


public class NewProductFragment extends Fragment {

    private LinkedList<Products> new_product_list = new LinkedList<>();

    public NewProductFragment() {
        // Required empty public constructor

    }


    public static NewProductFragment newInstance() {

        return new NewProductFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_new_product, container, false);

        currentAdminPage = 0;
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(view -> startActivity(new Intent(requireContext(), AddNewProductActivity.class)));

        //Rv
        RecyclerView recyclerView = v.findViewById(R.id.new_product_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false));
        NewProductRvAdapter newProductFragment = new NewProductRvAdapter(requireContext(),new_product_list);
        recyclerView.setAdapter(newProductFragment);

        populateShop(newProductFragment);

        return v;
    }

    private void populateShop(NewProductRvAdapter newProductFragment) {
        ExploreViewModel exploreViewModel = new ViewModelProvider(this).get(ExploreViewModel.class);
        exploreViewModel.getProductsData().observe(getViewLifecycleOwner(), products -> {
            new_product_list.add(products);
            newProductFragment.notifyDataSetChanged();
        });
    }
}