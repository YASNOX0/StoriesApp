package com.example.storiesapp.ui.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
 * Use the {@link CategorieDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategorieDialogFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Uri fileUri = null;
    AppViewModel appViewModel;
    EditText et_nomCategorie;
    TextView tv_imagePickerCategorie;
    Button btn_ajouterCategorie;
    Button btn_annulerCategorie;

    public CategorieDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategorieDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategorieDialogFragment newInstance(String param1, String param2) {
        CategorieDialogFragment fragment = new CategorieDialogFragment();
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
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setBackgroundDrawableResource(R.drawable.rouded_corner_bg);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categorie_dialog, container, false);
        et_nomCategorie = view.findViewById(R.id.et_nomCategorie);
        tv_imagePickerCategorie = view.findViewById(R.id.tv_imagePicker_categorie);
        btn_ajouterCategorie = view.findViewById(R.id.btn_ajouterCategorie);
        btn_annulerCategorie = view.findViewById(R.id.btn_annulerCategorie);
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_ajouterCategorie.setOnClickListener(v -> {
            if (fileUri != null) {
                Toast.makeText(getContext(), "Jaaaaaaaaaa: " + fileUri.toString(), Toast.LENGTH_SHORT).show();
                appViewModel.repository.insertCategorie(new Categorie(et_nomCategorie.getText().toString(), fileUri.toString()));
                this.dismiss();
            }else{
                Toast.makeText(getContext(), "Veuillez sélectionner une image", Toast.LENGTH_SHORT).show();
            }
        });

        btn_annulerCategorie.setOnClickListener(v -> {
            this.dismiss();
        });

        tv_imagePickerCategorie.setOnClickListener(v -> {
            ImagePicker.with(this)
                    .crop()                 // Crop image (Optional), Check Customization for more options
                    .compress(1024)         // Final image size will be less than 1 MB (Optional)
                    .maxResultSize(1080, 1080) // Final image resolution will be less than 1080 x 1080 (Optional)
                    .createIntent(intent -> {
                        startForProfileImageResult.launch(intent);
                        return null;
                    });
        });


    }


    private ActivityResultLauncher<Intent> startForProfileImageResult =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                int resultCode = result.getResultCode();
                Intent data = result.getData();

                if (resultCode == Activity.RESULT_OK) {
                    // Image Uri will not be null for RESULT_OK
                    fileUri = data.getData();
                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(getContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Task Cancelled", Toast.LENGTH_SHORT).show();
                }
            });

}