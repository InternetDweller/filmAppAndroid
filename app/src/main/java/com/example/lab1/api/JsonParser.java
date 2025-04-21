package com.example.lab1.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {
    public static class Film { // Модель данных для ответа API
        private String nameRu;
        private String nameOrig;
        private ArrayList<String> genres;
        private int year;

        public String getValidName() {
            if (nameRu.equals("null") && nameOrig.equals("null")) { return "[Название отсутствует]"; }
            if (nameRu.equals("null")) { return nameOrig; }
            if (nameOrig.equals("null")) { return nameRu; }
            return ( nameRu + " (" + nameOrig + ")" );
        }
        public String getGenresString() { return String.join(", ", genres);}
        public String getYearString() { return ("" + year); }
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
            tmpFilm.nameRu = jsonFilmArray.getJSONObject(i).getString("nameRu");
            tmpFilm.nameOrig = jsonFilmArray.getJSONObject(i).getString("nameOriginal");
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
