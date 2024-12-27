package com.example.storiesapp.ui.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.storiesapp.R;
import com.example.storiesapp.model.Auteur;
import com.example.storiesapp.viewmodel.AppViewModel;

import java.util.Calendar;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuteurDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuteurDialogFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    AppViewModel appViewModel;
    EditText et_nomAuteur;
    TextView tv_dateNaissance;
    Button btn_ajouterAuteur;
    Button btn_annulerAuteur;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AuteurDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AuteurDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AuteurDialogFragment newInstance(String param1, String param2) {
        AuteurDialogFragment fragment = new AuteurDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setBackgroundDrawableResource(R.drawable.rouded_corner_bg);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_auteur_dialog, container, false);
        et_nomAuteur = view.findViewById(R.id.et_nomAuteur);
        tv_dateNaissance = view.findViewById(R.id.tv_dateNaissance);
        btn_ajouterAuteur = view.findViewById(R.id.btn_ajouterAuteur);
        btn_annulerAuteur = view.findViewById(R.id.btn_annulerAuteur);
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_ajouterAuteur.setOnClickListener(v -> {
            appViewModel.repository.insertAuteur(new Auteur(et_nomAuteur.getText().toString(),tv_dateNaissance.getText().toString()));
            this.dismiss();
        });

        btn_annulerAuteur.setOnClickListener(v -> {
            this.dismiss();
        });

        tv_dateNaissance.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                    //Showing the picked value in the textView
                    tv_dateNaissance.setText(String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year));

                }
            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();
        });
    }
}