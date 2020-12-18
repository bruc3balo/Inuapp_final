package com.example.inuapp.ui.reviews;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;


import com.example.inuapp.R;
import com.example.inuapp.models.Products;
import com.example.inuapp.ui.reviews.adapter.ProductReviewAdapter;

import java.util.LinkedList;

public class Reviews extends AppCompatActivity {

    private LinearLayout productRatingLayout, customerReviewLayout, riderReviewLayout;
    private ViewPager2 productRatingViewPager;
    private LinkedList<Products> productsList = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        productRatingLayout = findViewById(R.id.productRatingLayout);
        customerReviewLayout = findViewById(R.id.customerReviewLayout);
        riderReviewLayout = findViewById(R.id.riderReviewLayout);
        productRatingViewPager = findViewById(R.id.productRatingViewPager);


        Button postReviewButton = findViewById(R.id.postReviewButton);
        postReviewButton.setOnClickListener(v -> {

        });

    }

    private void setRiderLayout() {
        riderReviewLayout.setVisibility(View.VISIBLE);
        productRatingLayout.setVisibility(View.GONE);
        customerReviewLayout.setVisibility(View.GONE);
        productRatingViewPager.setVisibility(View.GONE);
    }

    private void setCustomerLayout() {
        riderReviewLayout.setVisibility(View.GONE);
        productRatingLayout.setVisibility(View.VISIBLE);
        customerReviewLayout.setVisibility(View.VISIBLE);
        productRatingViewPager.setVisibility(View.GONE);
    }

    private void setProductLayout() {
        riderReviewLayout.setVisibility(View.GONE);
        productRatingLayout.setVisibility(View.GONE);
        customerReviewLayout.setVisibility(View.GONE);
        productRatingViewPager.setVisibility(View.VISIBLE);

        ProductReviewAdapter productReviewAdapter = new ProductReviewAdapter(this, productsList);
        productRatingViewPager.setAdapter(productReviewAdapter);
        productRatingViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);


        productRatingViewPager.setPadding(50, 10, 50, 10);
        productRatingViewPager.setClipToPadding(false);
        productRatingViewPager.setClipChildren(false);
        productRatingViewPager.setOffscreenPageLimit(3);
        productRatingViewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(60));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleX(0.85f + r * 0.15f);
        });

        productRatingViewPager.setPageTransformer(compositePageTransformer);
        productRatingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });


        ImageButton previousProductButton = findViewById(R.id.previousProductButton);
        previousProductButton.setOnClickListener(v -> {
            if (productRatingViewPager.getCurrentItem() != 0) {
                productRatingViewPager.setCurrentItem(productRatingViewPager.getCurrentItem() - 1);
            } else {
                Toast.makeText(this, "This is the first product", Toast.LENGTH_SHORT).show();
            }
        });
        ImageButton nextProductButton = findViewById(R.id.nextProductButton);
        nextProductButton.setOnClickListener(v -> {
            if (productRatingViewPager.getCurrentItem() != productsList.size()) {
                productRatingViewPager.setCurrentItem(productRatingViewPager.getCurrentItem() + 1);
            } else {
                Toast.makeText(this, "This is the last product", Toast.LENGTH_SHORT).show();
            }
        });
    }
}