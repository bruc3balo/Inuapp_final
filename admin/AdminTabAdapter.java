package com.example.inuapp.admin;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.inuapp.admin.addNewProduct.NewProductFragment;
import com.example.inuapp.admin.addProducts.AddProductsFragment;
import com.example.inuapp.admin.logs.LogsFragment;
import com.example.inuapp.admin.orders.AdminOrdersFragment;
import com.example.inuapp.admin.users.UsersFragment;


public class AdminTabAdapter extends FragmentPagerAdapter {

    public AdminTabAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            default:
            case 0:
                return new NewProductFragment();
            case 1:
                return new AddProductsFragment();
            case 2:
                return new AdminOrdersFragment();
            case 4:
                return new LogsFragment();
            case 5:
                return new UsersFragment();
        }
    }


    @Override
    public int getCount() {
        return 7;
    }
}

