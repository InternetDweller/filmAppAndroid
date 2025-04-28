package com.example.lab1.model;

public class User {
    private final String login;
    private final String password;

    public User(String log, String pass) {
        login = log;
        password = pass;
    }

    public String getLogin() { return login; }

    public String getPassword() { return password; }
}
