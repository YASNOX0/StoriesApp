package com.example.storiesapp.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.storiesapp.data.dao.AuteurDao;
import com.example.storiesapp.data.dao.CategorieDao;
import com.example.storiesapp.data.dao.HistoireDao;
import com.example.storiesapp.data.dao.PaysDao;
import com.example.storiesapp.model.Auteur;
import com.example.storiesapp.model.Categorie;
import com.example.storiesapp.model.Histoire;
import com.example.storiesapp.model.Pays;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Histoire.class, Auteur.class, Categorie.class, Pays.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HistoireDao histoireDao();
    public abstract AuteurDao auteurDao();
    public abstract CategorieDao categorieDao();
    public abstract PaysDao paysDao();
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
