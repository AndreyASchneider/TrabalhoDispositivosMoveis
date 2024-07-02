package com.example.codigomorse.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.codigomorse.model.*;

public class EnabledLetterDAO {

    private DatabaseHelper dbHelper;

    public EnabledLetterDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addEnabledLetter(EnabledLetter enabledLetter) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_ID_ENABLED_LETTERS, enabledLetter.getUserId());
        values.put(DatabaseHelper.COLUMN_LANGUAGE_ID_ENABLED_LETTERS, enabledLetter.getLanguageId());
        values.put(DatabaseHelper.COLUMN_LETTERS_ENABLED_LETTERS, enabledLetter.getLetters());
        long id = db.insert(DatabaseHelper.TABLE_ENABLED_LETTERS, null, values);
        db.close();
        return id;
    }

    @SuppressLint("Range")
    public EnabledLetter getEnabledLetterById(int enabledLetterId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_ENABLED_LETTERS, new String[]{DatabaseHelper.COLUMN_ENABLED_LETTERS_ID, DatabaseHelper.COLUMN_USER_ID_ENABLED_LETTERS, DatabaseHelper.COLUMN_LANGUAGE_ID_ENABLED_LETTERS, DatabaseHelper.COLUMN_LETTERS_ENABLED_LETTERS},
                DatabaseHelper.COLUMN_ENABLED_LETTERS_ID + "=?", new String[]{String.valueOf(enabledLetterId)}, null, null, null);
        EnabledLetter enabledLetter = null;
        if (cursor != null && cursor.moveToFirst()) {
            enabledLetter = new EnabledLetter(
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ENABLED_LETTERS_ID)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ID_ENABLED_LETTERS)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_LANGUAGE_ID_ENABLED_LETTERS)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LETTERS_ENABLED_LETTERS))
            );
            cursor.close();
        }
        db.close();
        return enabledLetter;
    }

    // New method to get EnabledLetter by userId and languageId
    @SuppressLint("Range")
    public EnabledLetter getEnabledLetterByUserIdAndLanguageId(int userId, int languageId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = DatabaseHelper.COLUMN_USER_ID_ENABLED_LETTERS + "=? AND " + DatabaseHelper.COLUMN_LANGUAGE_ID_ENABLED_LETTERS + "=?";
        String[] selectionArgs = { String.valueOf(userId), String.valueOf(languageId) };
        Cursor cursor = db.query(DatabaseHelper.TABLE_ENABLED_LETTERS,
                new String[]{DatabaseHelper.COLUMN_ENABLED_LETTERS_ID, DatabaseHelper.COLUMN_USER_ID_ENABLED_LETTERS, DatabaseHelper.COLUMN_LANGUAGE_ID_ENABLED_LETTERS, DatabaseHelper.COLUMN_LETTERS_ENABLED_LETTERS},
                selection, selectionArgs, null, null, null);
        EnabledLetter enabledLetter = null;
        if (cursor != null && cursor.moveToFirst()) {
            enabledLetter = new EnabledLetter(
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ENABLED_LETTERS_ID)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ID_ENABLED_LETTERS)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_LANGUAGE_ID_ENABLED_LETTERS)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LETTERS_ENABLED_LETTERS))
            );
            cursor.close();
        }
        db.close();
        return enabledLetter;
    }

    // New method to add EnabledLetter if not exists
    public long addEnabledLetterIfNotExists(EnabledLetter enabledLetter) {
        EnabledLetter existingEnabledLetter = getEnabledLetterByUserIdAndLanguageId(enabledLetter.getUserId(), enabledLetter.getLanguageId());
        if (existingEnabledLetter == null) {
            return addEnabledLetter(enabledLetter);
        } else {
            return -1; // Indicating that the entry already exists
        }
    }

    // New method to update EnabledLetter by userId and languageId
    public int updateEnabledLetterByUserIdAndLanguageId(EnabledLetter enabledLetter) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_LETTERS_ENABLED_LETTERS, enabledLetter.getLetters());
        String selection = DatabaseHelper.COLUMN_USER_ID_ENABLED_LETTERS + "=? AND " + DatabaseHelper.COLUMN_LANGUAGE_ID_ENABLED_LETTERS + "=?";
        String[] selectionArgs = { String.valueOf(enabledLetter.getUserId()), String.valueOf(enabledLetter.getLanguageId()) };
        int count = db.update(DatabaseHelper.TABLE_ENABLED_LETTERS, values, selection, selectionArgs);
        db.close();
        return count;
    }

    // Outros métodos CRUD conforme necessidade
}
