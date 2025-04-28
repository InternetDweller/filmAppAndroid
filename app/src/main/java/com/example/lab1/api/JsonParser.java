package com.example.lab1.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import com.example.lab1.model.Film;

public class JsonParser {
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
            tmpFilm.genres = jsonArrayToString(jsonGenreArray, "genre", "[Список жанров отсутствует]");

            filmsList.add(tmpFilm);
        }

        return filmsList;
    }

    public static ArrayList<Film> parseLongInfo(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonFilmArray = jsonObject.getJSONArray("items");

        ArrayList<Film> filmsList = new ArrayList<>();

        for (int i = 0; i < jsonFilmArray.length(); i++) {
            Film tmpFilm = new Film();
            tmpFilm.id = jsonFilmArray.getJSONObject(i).getInt("kinopoiskId");
            tmpFilm.nameRu = jsonFilmArray.getJSONObject(i).getString("nameRu");
            tmpFilm.nameOrig = jsonFilmArray.getJSONObject(i).getString("nameOriginal");

            String jsonYear = jsonFilmArray.getJSONObject(i).getString("year");
            if (jsonYear.equals("null")) {
                tmpFilm.year = 0;
            } else {
                tmpFilm.year = jsonFilmArray.getJSONObject(i).getInt("year");
            }

            tmpFilm.poster = jsonFilmArray.getJSONObject(i).getString("posterUrl");
            tmpFilm.description = jsonFilmArray.getJSONObject(i).getString("description");

            JSONArray jsonGenreArray = jsonFilmArray.getJSONObject(i).getJSONArray("genres");
            tmpFilm.genres = jsonArrayToString(jsonGenreArray, "genre", "[Список жанров отсутствует]");

            JSONArray jsonCountryArray = jsonFilmArray.getJSONObject(i).getJSONArray("countries");
            tmpFilm.countries = jsonArrayToString(jsonCountryArray, "country", "[Список стран отсутствует]");

            filmsList.add(tmpFilm);
        }

        return filmsList;
    }

    private static String jsonArrayToString(JSONArray jsonArray, String fieldName, String resultDefault) {
        String result = resultDefault;

        try {
            ArrayList<String> arrayList = new ArrayList<>();
            for (int j = 0; j < jsonArray.length(); j++) {
                arrayList.add(jsonArray.getJSONObject(j).getString(fieldName));
            }
            result = String.join(", ", arrayList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
