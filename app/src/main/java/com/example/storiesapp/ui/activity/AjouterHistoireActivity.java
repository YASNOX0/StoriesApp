package com.example.storiesapp.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.storiesapp.R;
import com.example.storiesapp.model.Auteur;
import com.example.storiesapp.model.Categorie;
import com.example.storiesapp.model.Histoire;
import com.example.storiesapp.model.Pays;
import com.example.storiesapp.ui.adapter.CustomSpinnerAdapter;
import com.example.storiesapp.ui.fragment.AuteurDialogFragment;
import com.example.storiesapp.ui.fragment.CategorieDialogFragment;
import com.example.storiesapp.ui.fragment.PaysDialogFragment;
import com.example.storiesapp.viewmodel.AppViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.Calendar;

public class AjouterHistoireActivity extends AppCompatActivity {

    private AppViewModel appViewModel;
    private TextView tv_imagePicker, tv_ajouterAuteur, tv_ajouterCategorie, tv_ajouterPays, tv_dateCreation;
    private EditText et_titre, et_contenu;
    private Spinner spinner_auteurs, spinner_categories, spinner_pays;
    private Uri fileUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_histoire);

        // Set navigation and status bar colors
        setSystemBarColors();
        // Initialize ViewModel and Views
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        initViews();
        setupClickListeners();
        setupSpinnersAdapters();
    }

    private void setSystemBarColors() {
        getWindow().setNavigationBarColor(getColor(R.color.dark_shadow));
        getWindow().setStatusBarColor(getColor(R.color.app_bg_color));
    }

    private void initViews() {
        tv_imagePicker = findViewById(R.id.tv_imagePicker);
        tv_dateCreation = findViewById(R.id.tv_dateCreation);
        tv_ajouterAuteur = findViewById(R.id.tv_ajouterAuteur);
        tv_ajouterCategorie = findViewById(R.id.tv_ajouterCategorie);
        tv_ajouterPays = findViewById(R.id.tv_ajouterPays);
        et_titre = findViewById(R.id.et_titre);
        et_contenu = findViewById(R.id.et_contenu);
        spinner_auteurs = findViewById(R.id.spinner_auteurs);
        spinner_categories = findViewById(R.id.spinner_categories);
        spinner_pays = findViewById(R.id.spinner_pays);
    }

    private void setupClickListeners() {
        tv_imagePicker.setOnClickListener(view -> ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start());

        tv_dateCreation.setOnClickListener(view -> showDatePickerDialog());
        tv_ajouterAuteur.setOnClickListener(view -> showDialog(new AuteurDialogFragment(), "auteurDialogFragment"));
        tv_ajouterCategorie.setOnClickListener(view -> showDialog(new CategorieDialogFragment(), "categorieDialogFragment"));
        tv_ajouterPays.setOnClickListener(view -> showDialog(new PaysDialogFragment(), "paysDialogFragment"));

        findViewById(R.id.btn_ajouterHistoir).setOnClickListener(view -> insertHistoire());
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(
                this,
                R.style.datepicker,
                (datePicker, year, month, day) -> tv_dateCreation.setText(String.format("%d/%d/%d", day, month + 1, year)),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void insertHistoire() {
        String titre = et_titre.getText().toString().trim();
        String contenu = et_contenu.getText().toString().trim();
        String dateCreation = tv_dateCreation.getText().toString().trim();
        Auteur auteur = spinner_auteurs.getSelectedItem() != null ? (Auteur) spinner_auteurs.getSelectedItem() : null;
        Categorie categorie = spinner_categories.getSelectedItem() != null ? (Categorie) spinner_categories.getSelectedItem() : null;
        Pays pays = spinner_pays.getSelectedItem() != null ? (Pays) spinner_pays.getSelectedItem() : null;

        if (isInputValid(auteur.getId(),titre, contenu, dateCreation, categorie.getNom(), pays.getNom())) {
            Histoire histoire = new Histoire(
                    titre,
                    fileUri != null ? fileUri.toString() : null,
                    contenu,
                    dateCreation,
                    auteur.getId(),
                    pays.getNom(),
                    categorie.getNom()
            );
            appViewModel.repository.insertHistoire(histoire);
            Toast.makeText(this, "Histoire ajoutée avec succès", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isInputValid(Long auteurId, String... inputs) {
        for (String input : inputs) {
            if (input == null || input.isEmpty()) return false;
        }
        return fileUri != null && auteurId != null;
    }

    private void showDialog(DialogFragment dialogFragment, String tag) {
        dialogFragment.setCancelable(false);
        dialogFragment.show(getSupportFragmentManager(), tag);
    }

    private void setupSpinnersAdapters() {
        appViewModel.repository.getAllAuteurs().observe(this, auteurs ->
                spinner_auteurs.setAdapter(new CustomSpinnerAdapter<>(this, auteurs))
        );

        appViewModel.repository.getAllPays().observe(this, pays ->
                spinner_pays.setAdapter(new CustomSpinnerAdapter<>(this, pays))
        );

        appViewModel.repository.getAllCategories().observe(this, categories ->
                spinner_categories.setAdapter(new CustomSpinnerAdapter<>(this, categories))
        );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImagePicker.REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                fileUri = data.getData();
                Toast.makeText(this, "Image sélectionnée avec succès", Toast.LENGTH_SHORT).show();
            } else {
                String errorMessage = resultCode == ImagePicker.RESULT_ERROR
                        ? ImagePicker.getError(data)
                        : "Sélection annulée";
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
