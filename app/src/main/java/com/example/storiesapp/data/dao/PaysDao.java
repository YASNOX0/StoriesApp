package com.example.storiesapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.storiesapp.model.Pays;

import java.util.List;

@Dao
public interface PaysDao {
    @Query("SELECT * FROM Pays")
    LiveData<List<Pays>> getAll();

    @Query("SELECT * FROM Pays WHERE nom IN (:nomsPays)")
    LiveData<List<Pays>> loadAllByNames(int... nomsPays);

    @Insert
    void insertAll(Pays... pays);

    @Delete
    void deleteAll(Pays... pays);

    @Update
    void updateAll(Pays... pays);
}
