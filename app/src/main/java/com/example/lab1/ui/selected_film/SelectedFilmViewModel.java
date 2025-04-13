package com.example.lab1.ui.selected_film;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SelectedFilmViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public SelectedFilmViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Выбранный фильм\n\nЗдесь могла бы быть ваша реклама");
    }

    public LiveData<String> getText() {
        return mText;
    }
}