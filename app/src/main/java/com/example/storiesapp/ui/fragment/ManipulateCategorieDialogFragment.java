package com.example.storiesapp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.example.storiesapp.model.Categorie;
import com.example.storiesapp.viewmodel.AppViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManipulateCategorieDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManipulateCategorieDialogFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ACTION = "action";
    private static final String UPDATED_CATEGORIE = "updatedCategorie";


    private String action;
    private Categorie updatedCategorie;
    private Uri fileUri = null;
    private AppViewModel appViewModel;
    private EditText et_nomCategorie;
    private TextView tv_imagePickerCategorie;
    private Button btn_ajouterCategorie,btn_annulerCategorie;
    private AppCompatActivity appCompatActivity;

    public ManipulateCategorieDialogFragment() {
        // Required empty public constructor
    }

    public static ManipulateCategorieDialogFragment newInstance(String action, Categorie updatedCategorie) {
        ManipulateCategorieDialogFragment fragment = new ManipulateCategorieDialogFragment();
        Bundle args = new Bundle();
        args.putString(ACTION, action);
        args.putSerializable(UPDATED_CATEGORIE, updatedCategorie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.action = getArguments().getString(ACTION);
            this.updatedCategorie = (Categorie) getArguments().getSerializable(UPDATED_CATEGORIE);
        }
        this.appCompatActivity = (AppCompatActivity) requireActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setBackgroundDrawableResource(R.drawable.rouded_corner_bg);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_fragment_manipulate_categorie, container, false);
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (action.equals("Modifier") && updatedCategorie != null) {
            populateFieldsForEdit();
        }
        setupListeners();
    }

    private void initViews(View view){
        et_nomCategorie = view.findViewById(R.id.et_nomCategorie);
        tv_imagePickerCategorie = view.findViewById(R.id.tv_imagePicker_categorie);
        btn_ajouterCategorie = view.findViewById(R.id.btn_manipulateCategorie);
        btn_annulerCategorie = view.findViewById(R.id.btn_annulerCategorie);
    }
    private void populateFieldsForEdit() {
        et_nomCategorie.setText(updatedCategorie.getNom());
        tv_imagePickerCategorie.setText("Modifier l'image");
        btn_ajouterCategorie.setText(getString(R.string.modifier));
    }

    private void setupListeners() {
        btn_ajouterCategorie.setOnClickListener(view -> handleAddOrUpdateAction());
        btn_annulerCategorie.setOnClickListener(view -> dismiss());
        tv_imagePickerCategorie.setOnClickListener(view -> openImagePicker());
    }

    private void handleAddOrUpdateAction() {
        String nomCategorie = et_nomCategorie.getText().toString();
        if (fileUri == null || nomCategorie.isBlank()) {
            Toast.makeText(getContext(), "Veuillez sélectionner une image ou remplir le nom de la catégorie.", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("Ajouter".equals(action)) {
            appViewModel.repository.insertCategories(new Categorie(nomCategorie, fileUri.toString()));
        } else if ("Modifier".equals(action) && updatedCategorie != null) {
            updatedCategorie.setNom(nomCategorie);
            updatedCategorie.setImage(fileUri.toString());
            appViewModel.repository.updateCategories(updatedCategorie);
        }
        this.dismiss();
    }

    private void openImagePicker() {
        ImagePicker.with(this)
                .crop()                 // Crop image (Optional), Check Customization for more options
                .compress(1024)         // Final image size will be less than 1 MB (Optional)
                .maxResultSize(1080, 1080) // Final image resolution will be less than 1080 x 1080 (Optional)
                .createIntent(intent -> {
                    startForProfileImageResult.launch(intent);
                    return null;
                });
    }


    private final ActivityResultLauncher<Intent> startForProfileImageResult =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                int resultCode = result.getResultCode();
                Intent data = result.getData();

                if (resultCode == Activity.RESULT_OK) {
                    // Image Uri will not be null for RESULT_OK
                    assert data != null;
                    fileUri = data.getData();
                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(getContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Task Cancelled", Toast.LENGTH_SHORT).show();
                }
            });

}