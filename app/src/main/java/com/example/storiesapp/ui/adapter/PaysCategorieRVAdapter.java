package com.example.storiesapp.ui.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storiesapp.R;
import com.example.storiesapp.model.Categorie;
import com.example.storiesapp.model.Pays;
import com.example.storiesapp.ui.fragment.ManipulateCategorieDialogFragment;
import com.example.storiesapp.ui.fragment.ManipulatePaysDialogFragment;
import com.example.storiesapp.viewmodel.AppViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class PaysCategorieRVAdapter<T> extends RecyclerView.Adapter<PaysCategorieRVAdapter.MyViewHolder> {
    private final AppViewModel appViewModel;
    private final List<T> items;

    public PaysCategorieRVAdapter(List<T> items, AppViewModel appViewModel) {
        this.appViewModel = appViewModel;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_template_pays_categories_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        T item = items.get(position);

        if (item instanceof Pays) {
            bindPays(holder, (Pays) item);
        } else if (item instanceof Categorie) {
            bindCategorie(holder, (Categorie) item);
        }

        holder.ib_modifierItem.setOnClickListener(v -> showModifyDialog(holder, item));
        holder.ib_supprimerItem.setOnClickListener(v -> showDeleteConfirmationDialog(holder, item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void bindPays(@NonNull MyViewHolder holder, @NonNull Pays pays) {
        Uri drapeauUri = Uri.parse(pays.getDrapeau());
        holder.iv_item.setImageURI(drapeauUri != null ? drapeauUri : Uri.EMPTY);
        holder.tv_item.setText(pays.getNom());
    }

    private void bindCategorie(@NonNull MyViewHolder holder, @NonNull Categorie categorie) {
        Uri imageUri = Uri.parse(categorie.getImage());
        holder.iv_item.setImageURI(imageUri != null ? imageUri : Uri.EMPTY);
        holder.tv_item.setText(categorie.getNom());
    }

    private void showModifyDialog(@NonNull MyViewHolder holder, @NonNull T item) {
        AppCompatActivity activity = (AppCompatActivity) holder.itemView.getContext();

        if (item instanceof Pays) {
            ManipulatePaysDialogFragment dialogFragment =
                    ManipulatePaysDialogFragment.newInstance("Modifier", (Pays) item);
            dialogFragment.setCancelable(false);
            dialogFragment.show(activity.getSupportFragmentManager(), "manipulatePaysDialogFragment");
        } else if (item instanceof Categorie) {
            ManipulateCategorieDialogFragment dialogFragment =
                    ManipulateCategorieDialogFragment.newInstance("Modifier", (Categorie) item);
            dialogFragment.setCancelable(false);
            dialogFragment.show(activity.getSupportFragmentManager(), "manipulateCategorieDialogFragment");
        }
    }

    private void showDeleteConfirmationDialog(@NonNull MyViewHolder holder, @NonNull T item) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(holder.itemView.getContext(), R.style.CustomAlertDialog);

        if (item instanceof Pays) {
            Pays pays = (Pays) item;
            builder.setTitle(R.string.delete_confirmation_title)
                    .setMessage(holder.itemView.getContext().getString(R.string.delete_confirmation_message, pays.getNom().trim()))
                    .setPositiveButton(R.string.confirm, (dialog, which) -> appViewModel.repository.deletePays(pays))
                    .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else if (item instanceof Categorie) {
            Categorie categorie = (Categorie) item;
            builder.setTitle(R.string.delete_confirmation_title)
                    .setMessage(holder.itemView.getContext().getString(R.string.delete_confirmation_message, categorie.getNom().trim()))
                    .setPositiveButton(R.string.confirm, (dialog, which) -> appViewModel.repository.deleteCategories(categorie))
                    .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_item;
        TextView tv_item;
        ImageButton ib_modifierItem;
        ImageButton ib_supprimerItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_item = itemView.findViewById(R.id.iv_item);
            tv_item = itemView.findViewById(R.id.tv_item);
            ib_modifierItem = itemView.findViewById(R.id.ib_modifierItem);
            ib_supprimerItem = itemView.findViewById(R.id.ib_supprimerItem);
        }
    }
}
