package com.example.lab1.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser { // Для исп-я требуется создание инстанса
    public static class Film { // Модель данных для ответа API
        public String name;
        public ArrayList<String> genres;
        public int year;
    }

    public static ArrayList<Film> parseShortInfo(String jsonString) throws JSONException { // Превращает JSON в массив объектов. Для списка фильмов
        JSONObject jsonObject = new JSONObject(jsonString);
        // ;-; How was I supposed to know THIS was the error??
        //JSONArray jsonFilmArray = new JSONArray(jsonObject.getJSONArray("items"));
        JSONArray jsonFilmArray = jsonObject.getJSONArray("items"); //"items" - массив в получаемом JSON-е

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
