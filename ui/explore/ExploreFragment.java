package com.example.inuapp.ui.explore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.inuapp.R;
import com.example.inuapp.models.Products;
import com.example.inuapp.ui.explore.adapters.ExploreRv;

import java.util.LinkedList;

import static com.example.inuapp.models.Products.COMPUTING;
import static com.example.inuapp.models.Products.ELECTRONICS;
import static com.example.inuapp.models.Products.FASHION;
import static com.example.inuapp.models.Products.GAMING;
import static com.example.inuapp.models.Products.HOME;
import static com.example.inuapp.models.Products.SPORTING;

public class ExploreFragment extends Fragment {

    private LinkedList<Products> electronicList, homeList, gamingList, fashionList, computingList, sportingList;
    private ExploreRv electronicsAdapter, homeAdapter, gamingAdapter, fashionAdapter, computingAdapter, sportingAdapter;
    private RecyclerView electronicsRv, homeRv, gamingRv, fashionRv, computingRv, sportingRv;
    private ExploreViewModel exploreViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        exploreViewModel = new ViewModelProvider(this).get(ExploreViewModel.class);
        View root = inflater.inflate(R.layout.fragment_explore, container, false);

        //Electronics
        TextView electronicsTv = root.findViewById(R.id.electronicsTv);
        electronicsTv.setOnClickListener(v -> {
            if (electronicsRv.getVisibility() == View.VISIBLE) {
                hideElectronicRv();
            } else {
                showElectronicRv();
            }
        });
        electronicsRv = root.findViewById(R.id.electronicsRv);
        electronicsRv.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        electronicList = new LinkedList<>();
        electronicsAdapter = new ExploreRv(requireContext(), electronicList);
        electronicsRv.setAdapter(electronicsAdapter);

        //Home
        TextView homeTv = root.findViewById(R.id.homeTv);
        homeTv.setOnClickListener(v -> {
            if (homeRv.getVisibility() == View.VISIBLE) {
                hideHomeRv();
            } else {
                showHomeRv();
            }
        });
        homeRv = root.findViewById(R.id.homeRv);
        homeRv.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        homeList = new LinkedList<>();
        homeAdapter = new ExploreRv(requireContext(), homeList);
        homeRv.setAdapter(homeAdapter);

        //Gaming
        TextView gamingTv = root.findViewById(R.id.gamingTv);
        gamingTv.setOnClickListener(v -> {
            if (gamingRv.getVisibility() == View.VISIBLE) {
                hideGamingRv();
            } else {
                showGamingRv();
            }
        });
        gamingRv = root.findViewById(R.id.gamingRv);
        gamingRv.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        gamingList = new LinkedList<>();
        gamingAdapter = new ExploreRv(requireContext(), gamingList);
        gamingRv.setAdapter(gamingAdapter);

        //Fashion
        TextView fashionTv = root.findViewById(R.id.fashionTv);
        fashionTv.setOnClickListener(v -> {
            if (fashionRv.getVisibility() == View.VISIBLE) {
                hideFashionRv();
            } else {
                showFashionRv();
            }
        });
        fashionRv = root.findViewById(R.id.fashionRv);
        fashionRv.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        fashionList = new LinkedList<>();
        fashionAdapter = new ExploreRv(requireContext(), fashionList);
        fashionRv.setAdapter(fashionAdapter);

        //Computing
        TextView computingTv = root.findViewById(R.id.computingTv);
        computingTv.setOnClickListener(v -> {
            if (computingRv.getVisibility() == View.VISIBLE) {
                hideComputingRv();
            } else {
                showComputingRv();
            }
        });
        computingRv = root.findViewById(R.id.computingRv);
        computingRv.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        computingList = new LinkedList<>();
        computingAdapter = new ExploreRv(requireContext(), computingList);
        computingRv.setAdapter(computingAdapter);

        //Sporting
        TextView sportingTv = root.findViewById(R.id.sportingTv);
        sportingTv.setOnClickListener(v -> {
            if (sportingRv.getVisibility() == View.VISIBLE) {
                hideSportingRv();
            } else {
                showSportingRv();
            }
        });
        sportingRv = root.findViewById(R.id.sportingRv);
        sportingRv.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        sportingList = new LinkedList<>();
        sportingAdapter = new ExploreRv(requireContext(), sportingList);
        sportingRv.setAdapter(sportingAdapter);

        populateShop();

        return root;
    }

    private void populateShop() {
        exploreViewModel.getShopProductsList().observe(getViewLifecycleOwner(), products -> {
            switch (products.getProductCategory()) {
                default:
                    break;
                case ELECTRONICS:
                    electronicList.add(products);
                    electronicsAdapter.notifyDataSetChanged();
                    break;

                case HOME:
                    homeList.add(products);
                    homeAdapter.notifyDataSetChanged();
                    break;

                case GAMING:
                    gamingList.add(products);
                    gamingAdapter.notifyDataSetChanged();
                    break;

                case FASHION:
                    fashionList.add(products);
                    fashionAdapter.notifyDataSetChanged();
                    break;

                case COMPUTING:
                    computingList.add(products);
                    computingAdapter.notifyDataSetChanged();
                    break;

                case SPORTING:
                    sportingList.add(products);
                    sportingAdapter.notifyDataSetChanged();
                    break;
            }
            checkSizes();
        });
    }

    private void checkSizes() {
        sportingAdapter.notifyDataSetChanged();
        if (sportingList.size() == 0) {
            hideSportingRv();
        } else {
            showSportingRv();
        }
        computingAdapter.notifyDataSetChanged();
        if (computingList.size() == 0) {
            hideComputingRv();
        } else {
            showComputingRv();
        }
        fashionAdapter.notifyDataSetChanged();
        if (fashionList.size() == 0) {
            hideFashionRv();
        } else {
            showFashionRv();
        }
        gamingAdapter.notifyDataSetChanged();
        if (gamingList.size() == 0) {
            hideGamingRv();
        } else {
            showGamingRv();
        }
        homeAdapter.notifyDataSetChanged();
        if (homeList.size() == 0) {
            hideHomeRv();
        } else {
            showHomeRv();
        }
        electronicsAdapter.notifyDataSetChanged();
        if (electronicList.size() == 0) {
            hideElectronicRv();
        } else {
            showElectronicRv();
        }
    }

    private void hideElectronicRv() {
        electronicsRv.setVisibility(View.GONE);
    }

    private void showElectronicRv() {
        electronicsRv.setVisibility(View.VISIBLE);
    }

    private void hideSportingRv() {
        sportingRv.setVisibility(View.GONE);
    }

    private void showSportingRv() {
        sportingRv.setVisibility(View.VISIBLE);
    }

    private void hideComputingRv() {
        computingRv.setVisibility(View.GONE);
    }

    private void showComputingRv() {
        computingRv.setVisibility(View.VISIBLE);
    }

    private void hideFashionRv() {
        fashionRv.setVisibility(View.GONE);
    }

    private void showFashionRv() {
        fashionRv.setVisibility(View.VISIBLE);
    }

    private void hideGamingRv() {
        gamingRv.setVisibility(View.GONE);
    }

    private void showGamingRv() {
        homeRv.setVisibility(View.VISIBLE);
    }

    private void hideHomeRv() {
        homeRv.setVisibility(View.GONE);
    }

    private void showHomeRv() {
        homeRv.setVisibility(View.VISIBLE);
    }

}