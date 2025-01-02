package com.example.storiesapp.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
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
import com.example.storiesapp.ui.fragment.AjouterAuteurDialogFragment;
import com.example.storiesapp.ui.fragment.AjouterCategorieDialogFragment;
import com.example.storiesapp.ui.fragment.AjouterPaysDialogFragment;
import com.example.storiesapp.viewmodel.AppViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;
import java.util.Calendar;

public class HistoireManipulationActivity extends AppCompatActivity {

    private AppViewModel appViewModel;
    private LinearLayout llImagePicker;
    private ImageView ivPickedHistoire;
    private TextView tvImagePicker, tvDateCreation, tvManipulateHistoire;
    private EditText etTitre, etContenu;
    private Spinner spinnerAuteurs, spinnerCategories, spinnerPays;
    private Button btnManipulateHistoire;
    private Uri fileUri = null;
    private Histoire updatedHistoire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manipulation_histoire);

        setSystemBarColors();
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
        llImagePicker = findViewById(R.id.ll_imagePicker);
        tvImagePicker = findViewById(R.id.tv_imagePicker);
        tvDateCreation = findViewById(R.id.tv_dateCreation);
        etTitre = findViewById(R.id.et_titre);
        etContenu = findViewById(R.id.et_contenu);
        spinnerAuteurs = findViewById(R.id.spinner_auteurs);
        spinnerCategories = findViewById(R.id.spinner_categories);
        spinnerPays = findViewById(R.id.spinner_pays);
        btnManipulateHistoire = findViewById(R.id.btn_manipulateHistoir);
        tvManipulateHistoire = findViewById(R.id.tv_manipulateHistoir);
        ivPickedHistoire = findViewById(R.id.iv_pickedHistoire);

        String action = getIntent().getStringExtra("action");
        tvManipulateHistoire.setText(String.format("%s histoire", action));
        btnManipulateHistoire.setText(action);

        updatedHistoire = (Histoire) getIntent().getSerializableExtra("histoire");
        if (updatedHistoire != null) {
            populateHistoireDetails();
        }
    }

    private void populateHistoireDetails() {
        etTitre.setText(updatedHistoire.getTitre());
        etContenu.setText(updatedHistoire.getContenu());
        tvDateCreation.setText(updatedHistoire.getDate());
        ivPickedHistoire.setVisibility(View.VISIBLE);
        ivPickedHistoire.setImageURI(Uri.parse(updatedHistoire.getImage()));
        tvImagePicker.setText("Modifier l'image");
    }

    private void setupClickListeners() {
        llImagePicker.setOnClickListener(view -> ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start());

        tvDateCreation.setOnClickListener(view -> showDatePickerDialog());
        findViewById(R.id.tv_ajouterAuteur).setOnClickListener(view -> showDialog(new AjouterAuteurDialogFragment(), "auteurDialogFragment"));
        findViewById(R.id.tv_ajouterCategorie).setOnClickListener(view -> showDialog(new AjouterCategorieDialogFragment(), "categorieDialogFragment"));
        findViewById(R.id.tv_ajouterPays).setOnClickListener(view -> showDialog(new AjouterPaysDialogFragment(), "paysDialogFragment"));

        btnManipulateHistoire.setOnClickListener(view -> manipulateHistoire(btnManipulateHistoire.getText().toString()));
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(
                this,
                R.style.datepicker,
                (datePicker, year, month, day) -> tvDateCreation.setText(String.format("%d/%d/%d", day, month + 1, year)),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void manipulateHistoire(String action) {
        String titre = etTitre.getText().toString().trim();
        String contenu = etContenu.getText().toString().trim();
        String dateCreation = tvDateCreation.getText().toString().trim();
        Auteur auteur = (Auteur) spinnerAuteurs.getSelectedItem();
        Categorie categorie = (Categorie) spinnerCategories.getSelectedItem();
        Pays pays = (Pays) spinnerPays.getSelectedItem();

        if (isInputValid(auteur, titre, contenu, dateCreation, categorie, pays)) {
            if ("Ajouter".equals(action)) {
                Histoire newHistoire = createHistoire(titre, contenu, dateCreation, auteur, categorie, pays);
                appViewModel.repository.insertHistoires(newHistoire);
            } else if ("Modifier".equals(action)) {
                updateHistoire(titre, contenu, dateCreation, auteur, categorie, pays);
            }
            finish();
        } else {
            showToast("Veuillez remplir tous les champs");
        }
    }

    private Histoire createHistoire(String titre, String contenu, String dateCreation, Auteur auteur, Categorie categorie, Pays pays) {
        return new Histoire(
                titre,
                fileUri != null ? fileUri.toString() : null,
                contenu,
                dateCreation,
                auteur.getId(),
                pays.getNom(),
                categorie.getNom()
        );
    }

    private void updateHistoire(String titre, String contenu, String dateCreation, Auteur auteur, Categorie categorie, Pays pays) {
        updatedHistoire.setTitre(titre);
        updatedHistoire.setImage(fileUri != null ? fileUri.toString() : updatedHistoire.getImage());
        updatedHistoire.setContenu(contenu);
        updatedHistoire.setDate(dateCreation);
        updatedHistoire.setIdAuteur(auteur.getId());
        updatedHistoire.setNomPays(pays.getNom());
        updatedHistoire.setNomCategorie(categorie.getNom());
        appViewModel.repository.updateHistoires(updatedHistoire);
    }

    private boolean isInputValid(Auteur auteur, String titre, String contenu, String date, Categorie categorie, Pays pays) {
        return auteur != null && titre != null && !titre.isEmpty()
                && contenu != null && !contenu.isEmpty()
                && date != null && !date.isEmpty()
                && categorie != null && pays != null;
    }

    private void showDialog(DialogFragment dialogFragment, String tag) {
        dialogFragment.setCancelable(false);
        dialogFragment.show(getSupportFragmentManager(), tag);
    }

    private void setupSpinnersAdapters() {
        appViewModel.repository.getAllAuteurs().observe(this, auteurs -> {
            spinnerAuteurs.setAdapter(new CustomSpinnerAdapter<>(this, auteurs));
            if (updatedHistoire != null) {
                auteurs.stream()
                        .filter(auteur -> auteur.getId() == updatedHistoire.getIdAuteur())
                        .findFirst()
                        .ifPresent(auteur -> spinnerAuteurs.setSelection(auteurs.indexOf(auteur)));
            }
        });

        appViewModel.repository.getAllPays().observe(this, pays -> {
            spinnerPays.setAdapter(new CustomSpinnerAdapter<>(this, pays));
            if (updatedHistoire != null) {
                pays.stream()
                        .filter(p -> p.getNom().equals(updatedHistoire.getNomPays()))
                        .findFirst()
                        .ifPresent(p -> spinnerPays.setSelection(pays.indexOf(p)));
            }
        });

        appViewModel.repository.getAllCategories().observe(this, categories -> {
            spinnerCategories.setAdapter(new CustomSpinnerAdapter<>(this, categories));
            if (updatedHistoire != null) {
                categories.stream()
                        .filter(categorie -> categorie.getNom().equals(updatedHistoire.getNomCategorie()))
                        .findFirst()
                        .ifPresent(categorie -> spinnerCategories.setSelection(categories.indexOf(categorie)));
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImagePicker.REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                fileUri = data.getData();
                ivPickedHistoire.setVisibility(View.VISIBLE);
                ivPickedHistoire.setImageURI(fileUri);
            } else {
                String errorMessage = resultCode == ImagePicker.RESULT_ERROR
                        ? ImagePicker.getError(data)
                        : "Sélection annulée";
                showToast(errorMessage);
            }
        }
    }
}
