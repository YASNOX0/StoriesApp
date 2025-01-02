package com.example.storiesapp.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.storiesapp.R;
import com.example.storiesapp.model.Auteur;
import com.example.storiesapp.model.Categorie;
import com.example.storiesapp.model.Pays;

import java.util.List;

public class CustomSpinnerAdapter<T> extends BaseAdapter {
    private Context context;
    private List<T> items;

    public CustomSpinnerAdapter(Context context, List<T> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(context, R.layout.item_custom_spinner_template, null);
        }
        ImageView iv_item = view.findViewById(R.id.iv_item);
        TextView tv_item = view.findViewById(R.id.tv_item);
        if (items.get(i) instanceof Categorie) {
            Categorie categorie = (Categorie) items.get(i);
            iv_item.setImageURI(Uri.parse(categorie.getImage()));
            tv_item.setText(categorie.getNom());
        } else if (items.get(i) instanceof Pays) {
            Pays pays = (Pays) items.get(i);
            iv_item.setImageURI(Uri.parse(pays.getDrapeau()));
            tv_item.setText(pays.getNom());
        } else if (items.get(i) instanceof Auteur) {
            iv_item.setVisibility(View.GONE);
            Auteur auteur = (Auteur) items.get(i);
            tv_item.setText(auteur.getNom());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

}
