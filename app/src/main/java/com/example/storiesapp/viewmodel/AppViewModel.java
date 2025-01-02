package com.example.storiesapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.storiesapp.data.repository.Repository;

import java.io.Serializable;

public class AppViewModel extends AndroidViewModel implements Serializable {

    public Repository repository;

    public AppViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
    }
}
