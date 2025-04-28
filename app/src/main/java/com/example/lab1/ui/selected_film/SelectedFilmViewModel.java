package com.example.lab1.ui.selected_film;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab1.model.Film;

public class SelectedFilmViewModel extends ViewModel {
    private final MutableLiveData<String> mText;
    private final MutableLiveData<Film> mFilm;

    public SelectedFilmViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Выбранный фильм\n\nЗдесь могла бы быть ваша реклама");

        mFilm = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Film> getFilm() { return mFilm; }

    public void postFilmData(Film film) {
        try {
            mFilm.postValue(film);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}