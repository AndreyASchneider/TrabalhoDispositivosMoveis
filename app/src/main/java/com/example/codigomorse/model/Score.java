package com.example.codigomorse.model;

public class Score {
    private int id;
    private String pontuation;
    private int userId;
    private String foto;
    private String location;

    public Score() {
    }

    public Score(String pontuation, int userId, String foto, String location) {
        this.pontuation = pontuation;
        this.userId = userId;
        this.foto = foto;
        this.location = location;
    }

    public Score(int id, String pontuation, int userId, String foto, String location) {
        this.id = id;
        this.pontuation = pontuation;
        this.userId = userId;
        this.foto = foto;
        this.location = location;
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
}
