package com.example.storiesapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.storiesapp.R;
import com.example.storiesapp.ui.fragment.AuteursFragment;
import com.example.storiesapp.ui.fragment.CategoriesFragment;
import com.example.storiesapp.ui.fragment.HistoiresFragment;
import com.example.storiesapp.ui.fragment.PaysFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity2 extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FloatingActionButton fab_ajouterNouveau;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //region making activity full screen
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //endregion

        //region Change the color of NavigationBar and StatusBar
        getWindow().setNavigationBarColor(getColor(R.color.dark_shadow));
        getWindow().setStatusBarColor(getColor(R.color.app_bg_color));
        //endregion

//        EdgeToEdge.enable(this);
////        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
////            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
////            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
////            return insets;
////        });

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        fab_ajouterNouveau = findViewById(R.id.fab_ajouterNouveau);

        fab_ajouterNouveau.setOnClickListener(v -> {
            Toast.makeText(this, "Ajouter histoire", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity2.this, AjouterHistoireActivity.class);
            startActivity(intent);
        });

        bottomNavigationView.setBackground(null);
        replaceFragment(new HistoiresFragment());
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.it_histoires){
                    replaceFragment(new HistoiresFragment());
                }else if (item.getItemId()==R.id.it_auteurs){
                    replaceFragment(new AuteursFragment());
                }else if (item.getItemId()==R.id.it_categories){
                    replaceFragment(new CategoriesFragment());
                }else if (item.getItemId()==R.id.it_pays){
                    replaceFragment(new PaysFragment());
                }
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
}