package com.example.storiesapp.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storiesapp.R;
import com.example.storiesapp.model.Histoire;
import com.example.storiesapp.ui.activity.HistoireManipulationActivity;
import com.example.storiesapp.viewmodel.AppViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HistoireRVAdapter extends RecyclerView.Adapter<HistoireRVAdapter.MyViewHolder> implements Serializable {

    private List<Histoire> histoires;
    private List<Histoire> searchedHistoies;
    private final AppViewModel appViewModel;
    private final Context context;

    public HistoireRVAdapter(Context context, List<Histoire> histoires, AppViewModel appViewModel) {
        this.context = context;
        this.histoires = histoires;
        this.searchedHistoies = new ArrayList<>(histoires);
        this.appViewModel = appViewModel;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setHistoires(List<Histoire> histoires) {
        this.histoires = histoires;
        this.searchedHistoies = new ArrayList<>(histoires);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_template_hitoires_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Histoire histoire = this.searchedHistoies.get(position);

        holder.iv_histoire.setImageURI(Uri.parse(histoire.getImage()));
        holder.tv_titre.setText(histoire.getTitre());
        holder.tv_categorie.setText(String.format("Type: %s", histoire.getNomCategorie()));
        holder.tv_dateCreation.setText(String.format("En: %s", histoire.getDate()));

        holder.ib_edite.setOnClickListener(v -> this.navigateToHistoireManipulation(histoire, "Modifier"));
        holder.ib_delete.setOnClickListener(v -> this.deleteHistoire(histoire));
        holder.itemView.setOnClickListener(v -> this.showBottomSheetDialog(histoire));
    }

    @Override
    public int getItemCount() {
        return this.searchedHistoies.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_histoire;
        TextView tv_titre, tv_categorie, tv_dateCreation;
        ImageButton ib_edite, ib_delete;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_histoire = itemView.findViewById(R.id.iv_info_histoire);
            tv_titre = itemView.findViewById(R.id.tv_titre);
            tv_categorie = itemView.findViewById(R.id.tv_categorie);
            tv_dateCreation = itemView.findViewById(R.id.tv_dateCreation);
            ib_edite = itemView.findViewById(R.id.ib_edite);
            ib_delete = itemView.findViewById(R.id.ib_delete);
        }
    }

    private void navigateToHistoireManipulation(Histoire histoire, String action) {
        Intent intent = new Intent(this.context, HistoireManipulationActivity.class);
        intent.putExtra("action", action);
        intent.putExtra("histoire", histoire);
        this.context.startActivity(intent);
    }

    private void deleteHistoire(Histoire histoire) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context, R.style.CustomAlertDialog);
        builder.setTitle(R.string.delete_confirmation_title)
                .setMessage(context.getString(R.string.delete_confirmation_message, histoire.getTitre().trim()))
                .setPositiveButton(R.string.confirm, (dialog, which) -> appViewModel.repository.deleteHistoires(histoire))
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void showBottomSheetDialog(Histoire histoire) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this.context, R.style.RoundedBottomSheetDialog);
        View view = LayoutInflater.from(this.context).inflate(R.layout.bottom_sheet_dialog_histoire, null);

        this.setupBottomSheetView(view, histoire, bottomSheetDialog);

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }

    private void setupBottomSheetView(View view, Histoire histoire, BottomSheetDialog bottomSheetDialog) {
        ImageView iv_info_histoire = view.findViewById(R.id.iv_info_histoire);
        ImageView iv_info_drapeau = view.findViewById(R.id.iv_info_drapeau);
        TextView tv_info_titre = view.findViewById(R.id.tv_info_titre);
        TextView tv_info_categorie = view.findViewById(R.id.tv_info_categorie);
        TextView tv_info_auteur = view.findViewById(R.id.tv_info_auteur);
        TextView tv_info_dateCreation = view.findViewById(R.id.tv_info_dateCreation);
        TextView tv_info_pays = view.findViewById(R.id.tv_info_pays);
        TextView tv_info_contenu = view.findViewById(R.id.tv_info_contenu);
        Button btn_modifier = view.findViewById(R.id.btn_modifier);
        Button btn_supprimer = view.findViewById(R.id.btn_supprimer);

        iv_info_histoire.setImageURI(Uri.parse(histoire.getImage()));
        tv_info_titre.setText(histoire.getTitre());
        tv_info_categorie.setText(String.format("Type: %s", histoire.getNomCategorie()));
        tv_info_dateCreation.setText(String.format("En: %s", histoire.getDate()));
        tv_info_pays.setText(histoire.getNomPays());
        tv_info_contenu.setText(histoire.getContenu());

        if (this.context instanceof LifecycleOwner) {
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.context;

            this.appViewModel.repository.getAuteursByIds((int) histoire.getIdAuteur())
                    .observe(lifecycleOwner, auteurs -> {
                        if (!auteurs.isEmpty()) {
                            tv_info_auteur.setText(String.format("Par: %s", auteurs.get(0).getNom()));
                        }
                    });

            this.appViewModel.repository.getPaysByNames(histoire.getNomPays())
                    .observe(lifecycleOwner, paysList -> {
                        if (!paysList.isEmpty()) {
                            iv_info_drapeau.setImageURI(Uri.parse(paysList.get(0).getDrapeau()));
                        }
                    });
        }

        btn_supprimer.setOnClickListener(v -> {
            this.deleteHistoire(histoire);
            bottomSheetDialog.dismiss();
        });
        btn_modifier.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            this.navigateToHistoireManipulation(histoire, "Modifier");
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void search(String searchElement) {
        this.searchedHistoies.clear();

        if (searchElement.isBlank()) {
            this.searchedHistoies.addAll(this.histoires);
        } else {
            for (Histoire histoire : this.histoires) {
                if (histoire.getTitre().toLowerCase().contains(searchElement.toLowerCase().trim())) {
                    this.searchedHistoies.add(histoire);
                }
            }
        }
        this.notifyDataSetChanged();
    }
}
