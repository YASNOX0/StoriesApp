package com.example.storiesapp.ui.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storiesapp.R;
import com.example.storiesapp.model.Auteur;
import com.example.storiesapp.model.Histoire;
import com.example.storiesapp.viewmodel.AppViewModel;

import java.util.List;

public class HistoireRVAdapter extends RecyclerView.Adapter<HistoireRVAdapter.MyViewHolder> {
    private LifecycleOwner lifecycleOwner;
    private List<Histoire> histoires;
    private AppViewModel appViewModel;
    public HistoireRVAdapter(LifecycleOwner lifecycleOwner, List<Histoire> histoires , AppViewModel appViewModel) {
        this.lifecycleOwner = lifecycleOwner;
        this.histoires = histoires;
        this.appViewModel = appViewModel;
    }
    @NonNull
    @Override
    public HistoireRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hitoires_recycler_view_template, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoireRVAdapter.MyViewHolder holder, int position) {
        holder.iv_histoire.setImageURI(Uri.parse(this.histoires.get(position).getImage()));
        holder.tv_titre.setText(this.histoires.get(position).getTitre());
        this.appViewModel.repository.getAuteursByIds(Integer.parseInt(histoires.get(position).getIdAuteur()+"")).observe(this.lifecycleOwner, new Observer<List<Auteur>>() {
            @Override
            public void onChanged(List<Auteur> auteurs) {
                holder.tv_auteur.setText(auteurs.get(auteurs.size()-1).getNom());
            }
        });

        holder.tv_dateCreation.setText(histoires.get(position).getDate());

        holder.ib_edite.setOnClickListener(view -> {
            Toast.makeText(view.getContext(), "edite", Toast.LENGTH_SHORT).show();
        });
        holder.ib_delete.setOnClickListener(view -> {
            Toast.makeText(view.getContext(), "delete", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return histoires.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_histoire;
        TextView tv_titre, tv_auteur, tv_dateCreation;
        ImageButton ib_edite, ib_delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_histoire = itemView.findViewById(R.id.iv_histoire);
            tv_titre = itemView.findViewById(R.id.tv_titre);
            tv_auteur = itemView.findViewById(R.id.tv_auteur);
            tv_dateCreation = itemView.findViewById(R.id.tv_dateCreation);
            ib_edite = itemView.findViewById(R.id.ib_edite);
            ib_delete = itemView.findViewById(R.id.ib_delete);
        }
    }
}
