package com.example.myapplication.ui.seach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.Adapter_cook_new;
import com.example.myapplication.model.DatatestAll_Cook;

import java.util.List;

public class SeachFragment extends Fragment {

    View v;
    private RecyclerView recyclerView;
    private List<DatatestAll_Cook> listdata;
    public SeachFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_breakfast,container,false);
        recyclerView=(RecyclerView) v.findViewById(R.id.home_recyclerView);
        Adapter_cook_new adapter_home= new Adapter_cook_new(getContext(),listdata);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter_home);


        return v;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}