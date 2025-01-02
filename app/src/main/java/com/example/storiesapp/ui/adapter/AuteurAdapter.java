package com.example.storiesapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storiesapp.R;
import com.example.storiesapp.model.Auteur;
import com.example.storiesapp.ui.fragment.ManipulateAuteurDialogFragment;
import com.example.storiesapp.viewmodel.AppViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class AuteurAdapter extends RecyclerView.Adapter<AuteurAdapter.MyViewHolder> {
    private List<Auteur> auteurs;
    private AppViewModel appViewModel;

    public AuteurAdapter(List<Auteur> auteurs , AppViewModel appViewModel) {
        this.auteurs = auteurs;
        this.appViewModel =appViewModel;
    }

    @NonNull
    @Override
    public AuteurAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_template_auteurs_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuteurAdapter.MyViewHolder holder, int position) {
        Auteur auteur = auteurs.get(position);
        holder.tv_nomAuteur.setText(auteur.getNom());
        holder.tv_dateNaissance.setText(auteur.getDateNaissance());
        holder.ib_modifierAuteur.setOnClickListener(view -> {
            ManipulateAuteurDialogFragment manipulateAuteurDialogFragment = ManipulateAuteurDialogFragment.newInstance("Modifier", auteur);
            manipulateAuteurDialogFragment.setCancelable(false);
            manipulateAuteurDialogFragment.show(((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager(), "manipulatePaysDialogFragment");
        });
        holder.ib_supprimerAuteur.setOnClickListener(view -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(holder.itemView.getContext(), R.style.CustomAlertDialog);
            builder.setTitle(R.string.delete_confirmation_title)
                    .setMessage(holder.itemView.getContext().getString(R.string.delete_confirmation_message, auteur.getNom().trim()))
                    .setPositiveButton(R.string.confirm, (dialog, which) -> appViewModel.repository.deleteAuteurs(auteur))
                    .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return auteurs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageButton ib_modifierAuteur, ib_supprimerAuteur;
        TextView tv_nomAuteur, tv_dateNaissance;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ib_modifierAuteur = itemView.findViewById(R.id.ib_modifierAuteur);
            this.ib_supprimerAuteur = itemView.findViewById(R.id.ib_supprimerAuteur);
            this.tv_nomAuteur = itemView.findViewById(R.id.tv_nomAuteur);
            this.tv_dateNaissance = itemView.findViewById(R.id.tv_dateNaissance);
        }
    }
}
