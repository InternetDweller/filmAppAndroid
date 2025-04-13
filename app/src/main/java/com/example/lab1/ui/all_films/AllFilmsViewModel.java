package com.example.lab1.ui.all_films;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AllFilmsViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public AllFilmsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Это будет список фильмов");
    }

    public LiveData<String> getText() {
        return mText;
    }
}