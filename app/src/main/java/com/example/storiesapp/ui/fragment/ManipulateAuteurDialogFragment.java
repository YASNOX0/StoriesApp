package com.example.storiesapp.ui.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storiesapp.R;
import com.example.storiesapp.model.Auteur;
import com.example.storiesapp.viewmodel.AppViewModel;

import java.util.Calendar;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManipulateAuteurDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManipulateAuteurDialogFragment extends DialogFragment {

    private static final String ACTION = "action";
    private static final String UPDATED_AUTEUR = "updatedAuteur";

    private String action;
    private Auteur updatedAuteur;
    private AppViewModel appViewModel;
    private EditText et_nomAuteur;
    private TextView tv_dateNaissance;
    private Button btn_manipulateAuteur, btn_annulerAuteur;
    private AppCompatActivity appCompatActivity;

    public ManipulateAuteurDialogFragment() {
        // Required empty public constructor
    }

    public static ManipulateAuteurDialogFragment newInstance(String action, Auteur auteur) {
        ManipulateAuteurDialogFragment fragment = new ManipulateAuteurDialogFragment();
        Bundle args = new Bundle();
        args.putString(ACTION, action);
        args.putSerializable(UPDATED_AUTEUR, auteur);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            updatedAuteur = (Auteur) getArguments().getSerializable(UPDATED_AUTEUR);
            action = getArguments().getString(ACTION);
        }
        appCompatActivity = (AppCompatActivity) requireActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setBackgroundDrawableResource(R.drawable.rouded_corner_bg);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_fragment_manipulate_auteur, container, false);
        initializeViews(view);
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (action.equals("Modifier") && updatedAuteur != null) {
            populateFieldsForEdit();
        }
        setupListeners();
    }

    private void initializeViews(View view) {
        et_nomAuteur = view.findViewById(R.id.et_nomAuteur);
        tv_dateNaissance = view.findViewById(R.id.tv_dateNaissance);
        btn_manipulateAuteur = view.findViewById(R.id.btn_manipulateAuteur);
        btn_annulerAuteur = view.findViewById(R.id.btn_annulerAuteur);
    }

    private void populateFieldsForEdit() {
        et_nomAuteur.setText(updatedAuteur.getNom());
        tv_dateNaissance.setText(updatedAuteur.getDateNaissance());
        btn_manipulateAuteur.setText(getString(R.string.modifier));
    }

    private void setupListeners() {
        btn_manipulateAuteur.setOnClickListener(view -> handleAddOrUpdateAction());
        btn_annulerAuteur.setOnClickListener(view -> dismiss());
        tv_dateNaissance.setOnClickListener(view -> showDatePicker());
    }

    private void handleAddOrUpdateAction() {
        String nomAuteur = et_nomAuteur.getText().toString();
        String dateNaissance = tv_dateNaissance.getText().toString();
        if (dateNaissance.isBlank() || nomAuteur.isBlank()) {
            Toast.makeText(getContext(), "Veuillez sélectionner une image ou remplir le nom du pays.", Toast.LENGTH_SHORT).show();
            return;
        }

        if ("Ajouter".equals(action)) {
            appViewModel.repository.insertAuteurs(new Auteur(nomAuteur, dateNaissance));
        } else if ("Modifier".equals(action) && updatedAuteur != null) {
            updatedAuteur.setNom(nomAuteur);
            updatedAuteur.setDateNaissance(dateNaissance);
            appViewModel.repository.updateAuteurs(updatedAuteur);
        }
        dismiss();
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this.appCompatActivity,
                R.style.Datepicker,
                (datePicker, year, month, day) -> tv_dateNaissance.setText((day<10) ? String.format("0%d/%d/%d", day, month+1, year) : String.format("%d/%d/%d", day, month+1, year)),
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        if(action.equals("Modifier") && updatedAuteur != null){
            String year , month , day;
            year = updatedAuteur.getDateNaissance().split("/")[2];
            month = updatedAuteur.getDateNaissance().split("/")[1];
            day = updatedAuteur.getDateNaissance().split("/")[0];
            datePickerDialog.updateDate(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day));
        }
        datePickerDialog.show();
    }
}