package com.example.lab1.ui.all_films;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab1.R;
import com.example.lab1.databinding.FragmentAllFilmsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class AllFilmsFragment extends Fragment {

    public class Film { // Модель данных для ответа API
        String name;
        ArrayList<String> genres;
        int year;
    }

    public class JsonParser { // Для исп-я требуется создание инстанса
        public ArrayList<Film> parseShortInfo(String jsonString) throws JSONException { // Превращает JSON в массив объектов. Для списка фильмов
            JSONObject jsonObject = new JSONObject(jsonString);
            // ;-; How was I supposed to know THIS was the error??
            //JSONArray jsonFilmArray = new JSONArray(jsonObject.getJSONArray("items")); //"items" - массив в получаемом JSON-е
            JSONArray jsonFilmArray = jsonObject.getJSONArray("items");

            // Список, который выдаётся на выход парсера
            ArrayList<Film> filmsList = new ArrayList<>();

            for (int i = 0; i < jsonFilmArray.length(); i++) {
                Film tmpFilm = new Film();
                tmpFilm.name = jsonFilmArray.getJSONObject(i).getString("nameRu");
                tmpFilm.year = jsonFilmArray.getJSONObject(i).getInt("year");

                JSONArray jsonGenreArray = jsonFilmArray.getJSONObject(i).getJSONArray("genres");
                ArrayList<String> genresList = new ArrayList<>();
                for (int j = 0; j < jsonGenreArray.length(); j++) {
                    genresList.add(jsonGenreArray.getJSONObject(j).getString("genre"));
                }
                tmpFilm.genres = genresList;

                filmsList.add(tmpFilm);
            }

            return filmsList;
        }
    }

    private FragmentAllFilmsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AllFilmsViewModel allFilmsViewModel =
                new ViewModelProvider(this).get(AllFilmsViewModel.class);

        binding = FragmentAllFilmsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAllFilms;
        allFilmsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        final OkHttpClient okhttpclient = new OkHttpClient();
        Request requestGetFilms = new Request.Builder()
                .url("https://kinopoiskapiunofficial.tech/api/v2.2/films/collections")
                .header("X-API-KEY", getResources().getString(R.string.API_KEY))
                .build();

        okhttpclient.newCall(requestGetFilms).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                System.out.println("response here");
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Request failed: " + response.code() + " " + response.message());
                    }

                    JsonParser jsonParser = new JsonParser();
                    ArrayList<Film> filmsParsed = jsonParser.parseShortInfo(responseBody.string());
                    System.out.println(filmsParsed.get(0).name);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}