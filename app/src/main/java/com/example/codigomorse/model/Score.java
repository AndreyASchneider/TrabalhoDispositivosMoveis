package com.example.codigomorse.model;

public class Score {
    private int id;
    private String pontuation;
    private int userId;
    private String foto;
    private String location;
    private int languageId;
    private String dateCreate;

    public Score() {
    }

    public Score(String pontuation, int userId, String foto, String location, int languageId, String dateCreate) {
        this.pontuation = pontuation;
        this.userId = userId;
        this.foto = foto;
        this.location = location;
        this.languageId = languageId;
        this.dateCreate = dateCreate;
    }

    public Score(int id, String pontuation, int userId, String foto, String location, int languageId, String dateCreate) {
        this.id = id;
        this.pontuation = pontuation;
        this.userId = userId;
        this.foto = foto;
        this.location = location;
        this.languageId = languageId;
        this.dateCreate = dateCreate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPontuation() {
        return pontuation;
    }

    public void setPontuation(String pontuation) {
        this.pontuation = pontuation;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }
}
