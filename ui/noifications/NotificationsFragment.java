package com.example.inuapp.ui.noifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.inuapp.R;
import com.example.inuapp.ui.noifications.adapter.NotificationRvAdapter;

import java.util.LinkedList;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private LinkedList<String> notificationList = new LinkedList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        RecyclerView userNotificationRv = root.findViewById(R.id.userNotificationRv);
        userNotificationRv.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));

        notificationList.add("");
        notificationList.add("");
        notificationList.add("");

        NotificationRvAdapter notificationRvAdapter = new NotificationRvAdapter(requireContext(), notificationList);
        userNotificationRv.setAdapter(notificationRvAdapter);

        return root;
    }
}