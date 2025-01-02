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
import com.example.storiesapp.model.Categorie;
import com.example.storiesapp.ui.adapter.PaysCategorieRVAdapter;
import com.example.storiesapp.ui.decorator.RecyclerViewItemDecorator;
import com.example.storiesapp.viewmodel.AppViewModel;

import java.util.List;

public class CategoriesFragment extends Fragment {

    private AppCompatActivity appCompatActivity;

    public CategoriesFragment() {
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
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        RecyclerView rv_categories = view.findViewById(R.id.rv_categories);
        rv_categories.addItemDecoration(new RecyclerViewItemDecorator(-8));
        rv_categories.setLayoutManager(new GridLayoutManager(this.appCompatActivity, 3));
        AppViewModel appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        appViewModel.repository.getAllCategories()
                .observe(appCompatActivity, categories -> rv_categories.setAdapter(new PaysCategorieRVAdapter<>(categories, appViewModel)));
        return view;
    }
}