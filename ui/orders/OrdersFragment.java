package com.example.inuapp.ui.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.inuapp.R;
import com.example.inuapp.models.Orders;
import com.example.inuapp.ui.orders.adaper.OrdersRvAdapter;

import java.util.LinkedList;

public class OrdersFragment extends Fragment {

    private OrdersSlideshowViewModel ordersSlideshowViewModel;
    private final LinkedList<Orders> ordersList = new LinkedList<>();
    private OrdersRvAdapter ordersRvAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ordersSlideshowViewModel = new ViewModelProvider(this).get(OrdersSlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_orders, container, false);

        RecyclerView userNotificationRv = root.findViewById(R.id.ordersRv);
        userNotificationRv.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        ordersRvAdapter = new OrdersRvAdapter(requireContext(), ordersList);
        userNotificationRv.setAdapter(ordersRvAdapter);

        getData();

        return root;
    }

    private void getData() {
        ordersSlideshowViewModel.getOrdersList().observe(getViewLifecycleOwner(), orders -> {
            ordersList.add(orders);
            Toast.makeText(requireContext(), orders.toString(), Toast.LENGTH_SHORT).show();
            ordersRvAdapter.notifyDataSetChanged();
        });
    }
}