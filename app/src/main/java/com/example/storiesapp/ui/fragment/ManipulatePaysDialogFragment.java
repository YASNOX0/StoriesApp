package com.example.storiesapp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storiesapp.R;
import com.example.storiesapp.model.Pays;
import com.example.storiesapp.viewmodel.AppViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class ManipulatePaysDialogFragment extends DialogFragment {

    private static final String ACTION = "action";
    private static final String UPDATED_PAYS = "updatedPays";

    private String action;
    private Pays updatedPays;
    private Uri fileUri = null;
    private AppViewModel appViewModel;
    private EditText et_NomPays;
    private LinearLayout ll_imagePickerPays;
    private TextView tv_imagePickerPays;
    private ImageView iv_pickedPays;
    private Button btn_manipilatePays, btn_annulerPays;

    // Factory method for creating a new instance of the dialog fragment
    public static ManipulatePaysDialogFragment newInstance(String action, Pays updatedPays) {
        ManipulatePaysDialogFragment fragment = new ManipulatePaysDialogFragment();
        Bundle args = new Bundle();
        args.putString(ACTION, action);
        args.putSerializable(UPDATED_PAYS, updatedPays);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            action = getArguments().getString(ACTION);
            updatedPays = (Pays) getArguments().getSerializable(UPDATED_PAYS);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Use a null-safe approach to set the background drawable
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(R.drawable.rouded_corner_bg);
        }

        // Inflate the layout
        View view = inflater.inflate(R.layout.dialog_fragment_manipulate_pays, container, false);
        initializeViews(view);
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (action.equals("Modifier") && updatedPays != null) {
            populateFieldsForEdit();
        }

        setupListeners();
    }

    private void initializeViews(View view) {
        et_NomPays = view.findViewById(R.id.et_nomPays);
        ll_imagePickerPays = view.findViewById(R.id.ll_imagePicker_pays);
        tv_imagePickerPays = view.findViewById(R.id.tv_imagePicker_pays);
        iv_pickedPays = view.findViewById(R.id.iv_pickedPays);
        btn_manipilatePays = view.findViewById(R.id.btn_manipulatePays);
        btn_annulerPays = view.findViewById(R.id.btn_annulerPays);
    }

    private void populateFieldsForEdit() {
        et_NomPays.setText(updatedPays.getNom());
        tv_imagePickerPays.setText("Modifier l'image");
        iv_pickedPays.setVisibility(View.VISIBLE);
        iv_pickedPays.setImageURI(Uri.parse(updatedPays.getDrapeau()));
        btn_manipilatePays.setText(getString(R.string.modifier));
    }

    private void setupListeners() {
        btn_manipilatePays.setOnClickListener(v -> handleAddOrUpdateAction());
        btn_annulerPays.setOnClickListener(v -> dismiss());
        ll_imagePickerPays.setOnClickListener(v -> openImagePicker());
    }

    private void handleAddOrUpdateAction() {
        String nomPays = et_NomPays.getText().toString();
        if (fileUri == null || nomPays.isBlank()) {
            Toast.makeText(getContext(), "Veuillez sélectionner une image ou remplir le nom du pays.", Toast.LENGTH_SHORT).show();
            return;
        }

        if ("Ajouter".equals(action)) {
            appViewModel.repository.insertPays(new Pays(nomPays, fileUri.toString()));
        } else if ("Modifier".equals(action) && updatedPays != null) {
            updatedPays.setNom(nomPays);
            updatedPays.setDrapeau(fileUri.toString());
            appViewModel.repository.updatePays(updatedPays);
        }
        dismiss();
    }

    private void openImagePicker() {
        ImagePicker.with(this)
                .crop()                 // Crop image
                .compress(1024)         // Compress to 1MB
                .maxResultSize(1080, 1080) // Max resolution
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
                    iv_pickedPays.setVisibility(View.VISIBLE);
                    iv_pickedPays.setImageURI(Uri.parse(fileUri.toString()));
                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(getContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Task Cancelled", Toast.LENGTH_SHORT).show();
                }
            });
}