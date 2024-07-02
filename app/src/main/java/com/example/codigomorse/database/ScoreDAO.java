package com.example.codigomorse.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.codigomorse.model.*;

import java.util.ArrayList;
import java.util.List;

public class ScoreDAO {

    private DatabaseHelper dbHelper;

    public ScoreDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addScore(Score score) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_PONTUATION, score.getPontuation());
        values.put(DatabaseHelper.COLUMN_USER_ID_SCORE, score.getUserId());
        values.put(DatabaseHelper.COLUMN_FOTO, score.getFoto());
        values.put(DatabaseHelper.COLUMN_LOCATION, score.getLocation());
        values.put(DatabaseHelper.COLUMN_LANGUAGE_ID_SCORE, score.getLanguageId());
        values.put(DatabaseHelper.COLUMN_DATE_CREATE_SCORE, score.getDateCreate());
        long id = db.insert(DatabaseHelper.TABLE_SCORES, null, values);
        db.close();
        return id;
    }

    @SuppressLint("Range")
    public Score getScoreById(int scoreId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_SCORES, new String[]{DatabaseHelper.COLUMN_SCORE_ID, DatabaseHelper.COLUMN_PONTUATION, DatabaseHelper.COLUMN_USER_ID_SCORE, DatabaseHelper.COLUMN_FOTO, DatabaseHelper.COLUMN_LOCATION},
                DatabaseHelper.COLUMN_SCORE_ID + "=?", new String[]{String.valueOf(scoreId)}, null, null, null);
        Score score = null;
        if (cursor != null && cursor.moveToFirst()) {
            score = new Score(
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_SCORE_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PONTUATION)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ID_SCORE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOTO)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOCATION)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_LANGUAGE_ID_SCORE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE_CREATE_SCORE))
            );
            cursor.close();
        }
        db.close();
        return score;
    }

    // New method to get a list of scores by userId and languageId
    @SuppressLint("Range")
    public List<Score> getScoresByUserIdAndLanguageId(int userId, int languageId) {
        List<Score> scores = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_SCORES,
                new String[]{DatabaseHelper.COLUMN_SCORE_ID, DatabaseHelper.COLUMN_PONTUATION, DatabaseHelper.COLUMN_USER_ID_SCORE, DatabaseHelper.COLUMN_FOTO, DatabaseHelper.COLUMN_LOCATION, DatabaseHelper.COLUMN_LANGUAGE_ID_SCORE, DatabaseHelper.COLUMN_DATE_CREATE_SCORE},
                DatabaseHelper.COLUMN_USER_ID_SCORE + "=? AND " + DatabaseHelper.COLUMN_LANGUAGE_ID_SCORE + "=?",
                new String[]{String.valueOf(userId), String.valueOf(languageId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Score score = new Score(
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_SCORE_ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PONTUATION)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ID_SCORE)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOTO)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOCATION)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_LANGUAGE_ID_SCORE)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE_CREATE_SCORE))
                );
                scores.add(score);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return scores;
    }

    // New method to get all scores with no filter
    @SuppressLint("Range")
    public List<Score> getAllScores() {
        List<Score> scores = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_SCORES,
                new String[]{DatabaseHelper.COLUMN_SCORE_ID, DatabaseHelper.COLUMN_PONTUATION, DatabaseHelper.COLUMN_USER_ID_SCORE, DatabaseHelper.COLUMN_FOTO, DatabaseHelper.COLUMN_LOCATION, DatabaseHelper.COLUMN_LANGUAGE_ID_SCORE, DatabaseHelper.COLUMN_DATE_CREATE_SCORE},
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Score score = new Score(
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_SCORE_ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PONTUATION)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ID_SCORE)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOTO)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOCATION)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_LANGUAGE_ID_SCORE)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE_CREATE_SCORE))
                );
                scores.add(score);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return scores;
    }

    // Outros métodos CRUD conforme necessidade
}
