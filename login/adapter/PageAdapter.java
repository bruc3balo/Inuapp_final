package com.example.inuapp.login.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.inuapp.login.create.CreatePage;
import com.example.inuapp.login.loginPage.LoginPage;


public class PageAdapter extends FragmentPagerAdapter {


    public PageAdapter(FragmentManager fm) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            default:
            case 0:
                return new LoginPage();
            case 1:
                return new CreatePage();
        }
    }


        @Override
        public int getCount () {
            return 2;
        }
    }

