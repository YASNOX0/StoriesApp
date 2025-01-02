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
import android.widget.Toast;

import com.example.storiesapp.R;
import com.example.storiesapp.model.Auteur;
import com.example.storiesapp.model.Pays;
import com.example.storiesapp.ui.activity.MainActivity;
import com.example.storiesapp.ui.adapter.AuteurAdapter;
import com.example.storiesapp.ui.adapter.PaysCategorieRVAdapter;
import com.example.storiesapp.ui.decorator.RecyclerViewItemDecorator;
import com.example.storiesapp.viewmodel.AppViewModel;

import java.util.List;

public class AuteursFragment extends Fragment {

    private AppCompatActivity appCompatActivity;

    public AuteursFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appCompatActivity = (AppCompatActivity) requireActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_auteurs, container, false);
        RecyclerView rv_auteurs = view.findViewById(R.id.rv_auteurs);
        rv_auteurs.addItemDecoration(new RecyclerViewItemDecorator(-8));
        rv_auteurs.setLayoutManager(new GridLayoutManager(appCompatActivity, 3));
        AppViewModel appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        appViewModel.repository.getAllAuteurs().observe(appCompatActivity, new Observer<List<Auteur>>() {
            @Override
            public void onChanged(List<Auteur> auteurs) {
                rv_auteurs.setAdapter(new AuteurAdapter(auteurs,appViewModel));
            }
        });
        return view;
    }
}