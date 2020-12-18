package com.example.inuapp.admin.logs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.inuapp.R;
import com.example.inuapp.admin.logs.adapter.LogsRvAdapter;

import java.util.LinkedList;

import static com.example.inuapp.admin.AdminActivity.fab;
import static com.example.inuapp.admin.AdminActivity.currentAdminPage;


public class LogsFragment extends Fragment {

    private LinkedList<String> logs_list = new LinkedList<>();

    public LogsFragment() {
        // Required empty public constructor
    }

    public static LogsFragment newInstance() {

        return new LogsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_logs, container, false);

        currentAdminPage = 2;
        fab.setVisibility(View.GONE);

        //Rv
        RecyclerView recyclerView = v.findViewById(R.id.logs_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));

        logs_list.add("");
        logs_list.add("");
        logs_list.add("");

        LogsRvAdapter logsRvAdapter = new LogsRvAdapter(requireContext(),logs_list);
        recyclerView.setAdapter(logsRvAdapter);

        return v;
    }

}