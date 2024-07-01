package com.example.codigomorse.model;

public class Language {
    private int id;
    private String name;
    private String letters;
    private String translations;

    public Language() {
    }

    public Language(String name, String letters, String translations) {
        this.name = name;
        this.letters = letters;
        this.translations = translations;
    }

    public Language(int id, String name, String letters, String translations) {
        this.id = id;
        this.name = name;
        this.letters = letters;
        this.translations = translations;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    public String getTranslations() {
        return translations;
    }

    public void setTranslations(String translations) {
        this.translations = translations;
    }
}
