package com.example.storiesapp.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.storiesapp.R;
import com.example.storiesapp.model.Histoire;
import com.example.storiesapp.ui.adapter.HistoireRVAdapter;
import com.example.storiesapp.viewmodel.AppViewModel;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistroiresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoiresFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView rv_histoires;
    AppViewModel appViewModel;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoiresFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistroiresFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoiresFragment newInstance(String param1, String param2) {
        HistoiresFragment fragment = new HistoiresFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_histoires, container, false);
        rv_histoires = view.findViewById(R.id.rv_histoires);
        rv_histoires.setLayoutManager(new LinearLayoutManager(getContext()));
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        appViewModel.repository.getAllHistoires().observe(requireActivity(), new Observer<List<Histoire>>() {
            @Override
            public void onChanged(List<Histoire> histoires) {
                rv_histoires.setAdapter(new HistoireRVAdapter(requireActivity(), histoires, appViewModel));
            }
        });
        return view;
    }
}