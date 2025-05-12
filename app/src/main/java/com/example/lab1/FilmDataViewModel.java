package com.example.lab1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab1.model.Film;

public class FilmDataViewModel extends ViewModel {
    private final MutableLiveData<Film> mSelectedFilm = new MutableLiveData<>();

    public void setSelectedFilm(Film film) {
        mSelectedFilm.setValue(film);
    }

    public LiveData<Film> getSelectedFilm() {
        return mSelectedFilm;
    }
}
