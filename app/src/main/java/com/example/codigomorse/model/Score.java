package com.example.codigomorse.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Score implements Parcelable {
    private int id;
    private String pontuation;
    private int userId;
    private String foto;
    private String location;
    private int languageId;
    private String dateCreate;

    public Score() {
    }

    public Score(String pontuation, int userId, String foto, String location, int languageId) {
        this.pontuation = pontuation;
        this.userId = userId;
        this.foto = foto;
        this.location = location;
        this.languageId = languageId;
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

    protected Score(Parcel in) {
        id = in.readInt();
        pontuation = in.readString();
        userId = in.readInt();
        foto = in.readString();
        location = in.readString();
        languageId = in.readInt();
        dateCreate = in.readString();
    }

    public static final Creator<Score> CREATOR = new Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(pontuation);
        dest.writeInt(userId);
        dest.writeString(foto);
        dest.writeString(location);
        dest.writeInt(languageId);
        dest.writeString(dateCreate);
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
}
