package com.example.lab1.ui.all_films;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab1.api.FilmsAPI;
import com.example.lab1.api.JsonParser;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AllFilmsViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<JsonParser.Film>> mFilms;

    public AllFilmsViewModel() {
        mFilms = new MutableLiveData<>();
    }

    public LiveData<ArrayList<JsonParser.Film>> getFilms() { return mFilms; }

    public void fetchDataFromApi(Context context) {
        FilmsAPI.Requests.getTopFilms(context, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    ArrayList<JsonParser.Film> filmsParsed = JsonParser.parseShortInfo(response.body().string());
                    mFilms.postValue(filmsParsed); //async; use .setValue() for synced update

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}