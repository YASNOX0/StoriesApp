package com.example.storiesapp.ui.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.storiesapp.R;
import com.example.storiesapp.model.Histoire;
import com.example.storiesapp.ui.adapter.HistoireRVAdapter;
import com.example.storiesapp.viewmodel.AppViewModel;

import java.util.List;

public class HistoiresFragment extends Fragment {

    private AppCompatActivity appCompatActivity;
    private HistoireRVAdapter histoireRVAdapter;

    public HistoiresFragment() {
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
        View view = inflater.inflate(R.layout.fragment_histoires, container, false);
        EditText et_rechercheHistoires = view.findViewById(R.id.et_rechercheHistoires);
        ImageButton ib_filtreHistoires = view.findViewById(R.id.ib_filterHistoires);
        RecyclerView rv_histoires = view.findViewById(R.id.rv_histoires);
        rv_histoires.setLayoutManager(new LinearLayoutManager(this.appCompatActivity));
        AppViewModel appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        appViewModel.repository.getAllHistoires().observe(this.appCompatActivity, new Observer<List<Histoire>>() {
            @Override
            public void onChanged(List<Histoire> histoires) {
                histoireRVAdapter = new HistoireRVAdapter(appCompatActivity, histoires, appViewModel);
                rv_histoires.setAdapter(histoireRVAdapter);
            }
        });

        et_rechercheHistoires.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (histoireRVAdapter != null) {
                    histoireRVAdapter.search(editable.toString());
                }
            }
        });

        ib_filtreHistoires.setOnClickListener(view1 -> FilterHistoiresDilalogFragment.newInstance(histoireRVAdapter)
                .show(appCompatActivity.getSupportFragmentManager(), "filter"));
        return view;
    }
}