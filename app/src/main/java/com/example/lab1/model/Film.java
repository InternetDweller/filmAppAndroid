package com.example.lab1.model;

public class Film { // Модель данных для ответа API
    public int id;
    public String nameRu;
    public String nameOrig;
    public String genres;
    public int year;
    public String poster;
    public String countries;
    public String description;

    public String getValidName() {
        if (nameRu.equals("null") && nameOrig.equals("null")) { return "[Название отсутствует]"; }
        if (nameRu.equals("null")) { return nameOrig; }
        if (nameOrig.equals("null")) { return nameRu; }
        return ( nameRu + " (" + nameOrig + ")" );
    }
    public String getYearString() {
        String result = "";
        if (year != 0) { result += year; }
        return (result);
    }
}
