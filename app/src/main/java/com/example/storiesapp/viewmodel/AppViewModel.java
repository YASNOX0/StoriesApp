package com.example.storiesapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.storiesapp.data.repository.Repository;

public class AppViewModel extends AndroidViewModel {

    public Repository repository;

    public AppViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
    }
}
