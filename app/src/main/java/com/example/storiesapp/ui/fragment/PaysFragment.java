package com.example.storiesapp.ui.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.storiesapp.R;
import com.example.storiesapp.model.Pays;
import com.example.storiesapp.ui.adapter.PaysCategorieRVAdapter;
import com.example.storiesapp.ui.decorator.RecyclerViewItemDecorator;
import com.example.storiesapp.viewmodel.AppViewModel;

import java.util.List;


public class PaysFragment extends Fragment {

    private AppCompatActivity appCompatActivity;

    public PaysFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.appCompatActivity = (AppCompatActivity) requireActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pays, container, false);
        RecyclerView rv_pays = view.findViewById(R.id.rv_pays);
        rv_pays.addItemDecoration(new RecyclerViewItemDecorator(-8));
        rv_pays.setLayoutManager(new GridLayoutManager(this.appCompatActivity, 3));
        AppViewModel appViewModel = new ViewModelProvider(this.appCompatActivity).get(AppViewModel.class);
        appViewModel.repository.getAllPays().observe(this.appCompatActivity, new Observer<List<Pays>>() {
            @Override
            public void onChanged(List<Pays> pays) {
                rv_pays.setAdapter(new PaysCategorieRVAdapter<>(pays,appViewModel));
            }
        });
        return view;
    }
}