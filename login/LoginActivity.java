package com.example.inuapp.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.inuapp.MainActivity;
import com.example.inuapp.R;
import com.example.inuapp.admin.AdminActivity;
import com.example.inuapp.login.adapter.PageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import static com.example.inuapp.models.User.ROLE;
import static com.example.inuapp.models.User.ROLE_ADMIN;
import static com.example.inuapp.models.User.ROLE_CUSTOMER;
import static com.example.inuapp.models.User.USERS;


public class LoginActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        FirebaseApp.initializeApp(this);

        viewPager = findViewById(R.id.loginViewpager);
        tabLayout = findViewById(R.id.tabs);
        // loginPb = findViewById(R.id.loginPb);

        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    default:
                    case 0:
                        //toolbar.setBackgroundColor(ContextCompat.getColor(Home.this, R.color.colorAccent));
                        tabLayout.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.greenPrimary));
                        tab.setText("Login");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.greenPrimaryVariant));
                        }
                        break;
                    case 1:
                        // toolbar.setBackgroundColor(ContextCompat.getColor(Home.this, R.color.colorHover));
                        tabLayout.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.greenPrimaryVariant));
                        tab.setText("Create Account");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.greenPrimary));
                        }
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setText("");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //sync tabs and viewpager
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager, true);

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.login_);//.setText("New Items");
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.create_24);//.setText("On Sale Items");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Checking user", Toast.LENGTH_SHORT).show();
        FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                logInUser(firebaseAuth.getCurrentUser());
            } else {
                Toast.makeText(LoginActivity.this, "Sign in to continue", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logInUser(FirebaseUser user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(USERS).document(user.getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String role = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).get(ROLE)).toString();
                if (role.equals(ROLE_ADMIN)) {
                    startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                    finish();
                } else if (role.equals(ROLE_CUSTOMER)) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            } else {
                Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Checking user", Toast.LENGTH_SHORT).show();
        FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                logInUser(firebaseAuth.getCurrentUser());
            } else {
                Toast.makeText(LoginActivity.this, "Sign in to continue", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}