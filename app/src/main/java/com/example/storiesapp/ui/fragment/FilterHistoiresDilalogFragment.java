package com.example.storiesapp.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.storiesapp.R;
import com.example.storiesapp.model.Auteur;
import com.example.storiesapp.model.Categorie;
import com.example.storiesapp.model.Pays;
import com.example.storiesapp.ui.adapter.CustomSpinnerAdapter;
import com.example.storiesapp.ui.adapter.HistoireRVAdapter;
import com.example.storiesapp.viewmodel.AppViewModel;

import java.util.Objects;

/**
 * A DialogFragment for filtering stories.
 */
public class FilterHistoiresDilalogFragment extends DialogFragment {

    private static final String HITOIRE_RV_ADAPTER = "histoireRVAdapter";

    private Spinner spinner_filterParNomAuteur, spinner_filterParCategorie, spinner_filterParPays;
    private HistoireRVAdapter histoireRVAdapter;
    private AppViewModel appViewModel;

    public FilterHistoiresDilalogFragment() {
        // Required empty public constructor
    }

    public static FilterHistoiresDilalogFragment newInstance(HistoireRVAdapter histoireRVAdapter) {
        FilterHistoiresDilalogFragment fragment = new FilterHistoiresDilalogFragment();
        Bundle args = new Bundle();
        args.putSerializable(HITOIRE_RV_ADAPTER, histoireRVAdapter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            histoireRVAdapter = (HistoireRVAdapter) getArguments().getSerializable(HITOIRE_RV_ADAPTER);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            Objects.requireNonNull(getDialog().getWindow()).setBackgroundDrawableResource(R.drawable.rouded_corner_bg);
        }

        View view = inflater.inflate(R.layout.dilalog_fragment_filter_histoires, container, false);

        spinner_filterParNomAuteur = view.findViewById(R.id.spinner_filterParNomAuteur);
        spinner_filterParCategorie = view.findViewById(R.id.spinner_filterParCategorie);
        spinner_filterParPays = view.findViewById(R.id.spinner_filterParPays);

        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupSpinnersAdapters();
        setUpSpinnerDropdownOffset();

        spinner_filterParNomAuteur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private boolean isFirstSelection = true;

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (isFirstSelection) {
                    isFirstSelection = false;
                    return;
                }

                Auteur auteur = (Auteur) spinner_filterParNomAuteur.getItemAtPosition(i);
                appViewModel.repository
                        .getHistoiresByNomAuteur(auteur.getNom())
                        .observe(getViewLifecycleOwner(), histoires -> histoireRVAdapter.setHistoires(histoires));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner_filterParCategorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private boolean isFirstSelection = true;

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (isFirstSelection) {
                    isFirstSelection = false;
                    return;
                }

                Categorie categorie = (Categorie) spinner_filterParCategorie.getItemAtPosition(i);
                appViewModel.repository
                        .getHistoiresByCategorie(categorie.getNom())
                        .observe(getViewLifecycleOwner(), histoires -> histoireRVAdapter.setHistoires(histoires));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner_filterParPays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private boolean isFirstSelection = true;

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (isFirstSelection) {
                    isFirstSelection = false;
                    return;
                }

                Pays pays = (Pays) spinner_filterParPays.getItemAtPosition(i);
                appViewModel.repository
                        .getHistoiresByPays(pays.getNom())
                        .observe(getViewLifecycleOwner(), histoires -> histoireRVAdapter.setHistoires(histoires));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void setupSpinnersAdapters() {
        appViewModel.repository.getAllAuteurs().observe(getViewLifecycleOwner(), auteurs ->
                spinner_filterParNomAuteur.setAdapter(new CustomSpinnerAdapter<>(requireActivity(), auteurs)));

        appViewModel.repository.getAllPays().observe(getViewLifecycleOwner(), pays ->
                spinner_filterParPays.setAdapter(new CustomSpinnerAdapter<>(requireActivity(), pays)));

        appViewModel.repository.getAllCategories().observe(getViewLifecycleOwner(), categories ->
                spinner_filterParCategorie.setAdapter(new CustomSpinnerAdapter<>(requireActivity(), categories)));
    }

    private void setUpSpinnerDropdownOffset() {
        int offset = 100;
        spinner_filterParNomAuteur.setDropDownVerticalOffset(offset);
        spinner_filterParCategorie.setDropDownVerticalOffset(offset);
        spinner_filterParPays.setDropDownVerticalOffset(offset);
    }
}
