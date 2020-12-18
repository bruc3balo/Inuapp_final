package com.example.inuapp.admin.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.inuapp.R;
import com.example.inuapp.admin.orders.adapter.AdminOrdersRvAdapter;

import java.util.LinkedList;

import static com.example.inuapp.admin.AdminActivity.currentAdminPage;
import static com.example.inuapp.admin.AdminActivity.fab;


public class AdminOrdersFragment extends Fragment {

    private LinkedList<String> orders_list = new LinkedList<>();

    public AdminOrdersFragment() {
        // Required empty public constructor
    }

    public static AdminOrdersFragment newInstance() {

        return new AdminOrdersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_admin_orders, container, false);

        currentAdminPage = 3;
        fab.setVisibility(View.GONE);

        //Rv
        RecyclerView recyclerView = v.findViewById(R.id.orders_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false));

        orders_list.add("");
        orders_list.add("");
        orders_list.add("");

        AdminOrdersRvAdapter adminOrdersRvAdapter = new AdminOrdersRvAdapter(requireContext(),orders_list);
        recyclerView.setAdapter(adminOrdersRvAdapter);
        return v;
    }
}