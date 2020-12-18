package com.example.inuapp.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.inuapp.MainActivity;
import com.example.inuapp.R;
import com.example.inuapp.admin.addNewProduct.AddNewProductActivity;
import com.example.inuapp.admin.addProducts.AddToMarket;
import com.example.inuapp.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static int currentAdminPage;
    public static FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fabAdmin);

        switch (currentAdminPage) {
            default: break;

            case 0:
                fab.setVisibility(View.VISIBLE);
                fab.setOnClickListener(view -> startActivity(new Intent(AdminActivity.this, AddNewProductActivity.class)));
                break;

            case 1:
                fab.setVisibility(View.VISIBLE);
                fab.setOnClickListener(view -> startActivity(new Intent(AdminActivity.this, AddToMarket.class)));
                break;

            case 2:
                fab.setVisibility(View.GONE);
            case 3:
                fab.setVisibility(View.GONE);

                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout_admin);
        NavigationView navigationView = findViewById(R.id.nav_view_admin);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_add_new_products, R.id.nav_add_products, R.id.nav_orders, R.id.nav_logs).setDrawerLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_admin);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.logout_admin) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(AdminActivity.this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_admin);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}