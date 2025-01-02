package com.example.storiesapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.storiesapp.model.Auteur;

import java.util.List;

@Dao
public interface AuteurDao {
    @Query("SELECT * FROM Auteur")
    LiveData<List<Auteur>> getAll();

    @Query("SELECT * FROM Auteur WHERE id IN (:idsAuteurs)")
    LiveData<List<Auteur>> loadAllByIds(long... idsAuteurs);

    @Insert
    void insertAll(Auteur... auteur);

    @Delete
    void deleteAll(Auteur... auteur);

    @Update
    void updateAll(Auteur... auteur);
}
