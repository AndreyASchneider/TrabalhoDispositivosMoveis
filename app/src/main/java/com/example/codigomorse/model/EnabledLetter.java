package com.example.codigomorse.model;

public class EnabledLetter {
    private int id;
    private int userId;
    private int languageId;
    private String letters;

    public EnabledLetter() {
    }

    public EnabledLetter(int userId, int languageId, String letters) {
        this.userId = userId;
        this.languageId = languageId;
        this.letters = letters;
    }

    public EnabledLetter(int id, int userId, int languageId, String letters) {
        this.id = id;
        this.userId = userId;
        this.languageId = languageId;
        this.letters = letters;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }
}
