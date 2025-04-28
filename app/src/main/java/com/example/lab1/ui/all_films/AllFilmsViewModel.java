package com.example.lab1.ui.all_films;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab1.model.Film;

import java.util.ArrayList;

public class AllFilmsViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Film>> mFilms;

    public AllFilmsViewModel() {
        mFilms = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Film>> getFilms() { return mFilms; }

    public void postFilmsData(ArrayList<Film> films) {
        try {
            mFilms.postValue(films); //async; use .setValue() for synced update

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}