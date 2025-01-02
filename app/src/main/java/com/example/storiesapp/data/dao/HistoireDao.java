package com.example.storiesapp.data.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.storiesapp.model.Histoire;

import java.util.List;

@Dao
public interface HistoireDao {
    @Query("SELECT * FROM histoire")
    LiveData<List<Histoire>> getAll();

    @Query("SELECT * FROM histoire WHERE id IN (:idsHistoires)")
    LiveData<List<Histoire>> loadAllByIds(int... idsHistoires);

    @Query("SELECT * FROM histoire WHERE nomPays LIKE :nomPays")
    LiveData<List<Histoire>> findByPays(String nomPays);

    @Query("SELECT * FROM histoire WHERE nomCategorie LIKE :nomCategorie")
    LiveData<List<Histoire>> findByCategories(String nomCategorie);

    @Query("SELECT h.* FROM histoire AS h , auteur AS a WHERE h.idAuteur = a.id AND a.nom LIKE :nomAuteur")
    LiveData<List<Histoire>> findByNomAuteur(String nomAuteur);

    @Insert
    void insertAll(Histoire... histoire);

    @Delete
    void deleteAll(Histoire... histoire);

    @Update
    void updateAll(Histoire... histoire);
}
