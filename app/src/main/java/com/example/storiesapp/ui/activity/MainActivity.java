package com.example.storiesapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.storiesapp.R;
import com.example.storiesapp.model.Histoire;
import com.example.storiesapp.ui.fragment.ManipulateAuteurDialogFragment;
import com.example.storiesapp.ui.fragment.ManipulateCategorieDialogFragment;
import com.example.storiesapp.ui.fragment.ManipulatePaysDialogFragment;
import com.example.storiesapp.ui.fragment.AuteursFragment;
import com.example.storiesapp.ui.fragment.CategoriesFragment;
import com.example.storiesapp.ui.fragment.HistoiresFragment;
import com.example.storiesapp.ui.fragment.PaysFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottom_navigation_view;
    FloatingActionButton fab_ajouterNouveau;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region Change the color of NavigationBar and StatusBar
        getWindow().setNavigationBarColor(getColor(R.color.dark_shadow));
        getWindow().setStatusBarColor(getColor(R.color.app_bg_color));
        //endregion

        bottom_navigation_view = findViewById(R.id.bottom_navigation_view);
        fab_ajouterNouveau = findViewById(R.id.fab_ajouterNouveau);
        bottom_navigation_view.setBackground(null);

        fragment = new HistoiresFragment();
        replaceFragment(fragment);

        fab_ajouterNouveau.setOnClickListener(v -> {
            if (fragment instanceof HistoiresFragment){
                Intent intent = new Intent(MainActivity.this, HistoireManipulationActivity.class);
                intent.putExtra("action", "Ajouter");
                intent.putExtra("histoire", (Histoire) null);
                startActivity(intent);
            }else if (fragment instanceof AuteursFragment){
                showDialog(ManipulateAuteurDialogFragment.newInstance("Ajouter",null), "auteurDialogFragment");
            }else if (fragment instanceof CategoriesFragment){
                showDialog(ManipulateCategorieDialogFragment.newInstance("Ajouter",null), "categorieDialogFragment");
            }else if (fragment instanceof PaysFragment){
                showDialog(ManipulatePaysDialogFragment.newInstance("Ajouter",null), "paysDialogFragment");
            }
        });

        bottom_navigation_view.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.it_histoires){
                    fragment = new HistoiresFragment();
                }else if (item.getItemId()==R.id.it_auteurs){
                    fragment = new AuteursFragment();
                }else if (item.getItemId()==R.id.it_categories){
                    fragment = new CategoriesFragment();
                }else if (item.getItemId()==R.id.it_pays){
                    fragment = new PaysFragment();
                }
                replaceFragment(fragment);
                return false;
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }

    private void showDialog(DialogFragment dialogFragment, String tag) {
        dialogFragment.setCancelable(false);
        dialogFragment.show(getSupportFragmentManager(), tag);
    }
}