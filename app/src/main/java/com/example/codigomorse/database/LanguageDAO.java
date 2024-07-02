package com.example.codigomorse.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.codigomorse.model.*;

import java.util.ArrayList;
import java.util.List;

public class LanguageDAO {

    private DatabaseHelper dbHelper;

    public LanguageDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addLanguage(Language language) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, language.getName());
        values.put(DatabaseHelper.COLUMN_LETTERS, language.getLetters());
        values.put(DatabaseHelper.COLUMN_TRANSLATIONS, language.getTranslations());
        long id = db.insert(DatabaseHelper.TABLE_LANGUAGES, null, values);
        db.close();
        return id;
    }

    @SuppressLint("Range")
    public Language getLanguageById(int languageId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_LANGUAGES, new String[]{DatabaseHelper.COLUMN_LANGUAGE_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_LETTERS, DatabaseHelper.COLUMN_TRANSLATIONS},
                DatabaseHelper.COLUMN_LANGUAGE_ID + "=?", new String[]{String.valueOf(languageId)}, null, null, null);
        Language language = null;
        if (cursor != null && cursor.moveToFirst()) {
            language = new Language(
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_LANGUAGE_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LETTERS)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TRANSLATIONS))
            );
            cursor.close();
        }
        db.close();
        return language;
    }

    public List<Language> getAllLanguages() {
        List<Language> languages = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_LANGUAGES,
                new String[]{DatabaseHelper.COLUMN_LANGUAGE_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_LETTERS, DatabaseHelper.COLUMN_TRANSLATIONS},
                null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Language language = new Language(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LANGUAGE_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LETTERS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TRANSLATIONS))
                );
                languages.add(language);
            }
            cursor.close();
        }
        db.close();
        return languages;
    }

    public Language getLanguageByName(String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_LANGUAGES,
                new String[]{DatabaseHelper.COLUMN_LANGUAGE_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_LETTERS, DatabaseHelper.COLUMN_TRANSLATIONS},
                DatabaseHelper.COLUMN_NAME + "=?", new String[]{name}, null, null, null);
        Language language = null;
        if (cursor != null && cursor.moveToFirst()) {
            language = new Language(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LANGUAGE_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LETTERS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TRANSLATIONS))
            );
            cursor.close();
        }
        db.close();
        return language;
    }

    public long addLanguageIfNotExists(Language language) {
        Language existingLanguage = getLanguageByName(language.getName());
        if (existingLanguage == null) {
            return addLanguage(language);
        } else {
            return -1; // Indicates that the language already exists
        }
    }

    public void triggerOnUpgrade(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(db, 1, 2);
    }

    public boolean deleteLanguageByName(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int affectedRows = db.delete(DatabaseHelper.TABLE_LANGUAGES, DatabaseHelper.COLUMN_NAME + "=?", new String[]{name});
        db.close();
        return affectedRows > 0;
    }

    // Outros métodos CRUD conforme necessidade
}
