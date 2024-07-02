package com.example.codigomorse.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.codigomorse.model.*;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Informações do banco de dados
    private static final String DATABASE_NAME = "my_app_database.db";
    private static final int DATABASE_VERSION = 1;

    // Tabelas e colunas da tabela users
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_DATE_CREATE = "date_create";
    public static final String COLUMN_DATE_UPDATE = "date_update";

    // Tabelas e colunas da tabela scores
    public static final String TABLE_SCORES = "scores";
    public static final String COLUMN_SCORE_ID = "id";
    public static final String COLUMN_PONTUATION = "pontuation";
    public static final String COLUMN_USER_ID_SCORE = "user_id";
    public static final String COLUMN_FOTO = "foto";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_LANGUAGE_ID_SCORE = "language_id";
    public static final String COLUMN_DATE_CREATE_SCORE = "date_create";

    // Tabelas e colunas da tabela languages
    public static final String TABLE_LANGUAGES = "languages";
    public static final String COLUMN_LANGUAGE_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LETTERS = "letters";
    public static final String COLUMN_TRANSLATIONS = "translations";

    // Tabelas e colunas da tabela enabled_letters
    public static final String TABLE_ENABLED_LETTERS = "enabled_letters";
    public static final String COLUMN_ENABLED_LETTERS_ID = "id";
    public static final String COLUMN_USER_ID_ENABLED_LETTERS = "user_id";
    public static final String COLUMN_LANGUAGE_ID_ENABLED_LETTERS = "language_id";
    public static final String COLUMN_LETTERS_ENABLED_LETTERS = "letters";

    // Comandos SQL de criação das tabelas
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMAIL + " TEXT UNIQUE,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_DATE_CREATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COLUMN_DATE_UPDATE + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ")";

    private static final String CREATE_TABLE_SCORES = "CREATE TABLE " + TABLE_SCORES + "("
            + COLUMN_SCORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_PONTUATION + " TEXT,"
            + COLUMN_USER_ID_SCORE + " INTEGER,"
            + COLUMN_FOTO + " TEXT,"
            + COLUMN_LOCATION + " TEXT,"
            + COLUMN_LANGUAGE_ID_SCORE + " INTEGER,"
            + COLUMN_DATE_CREATE_SCORE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + "FOREIGN KEY(" + COLUMN_USER_ID_SCORE + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "),"
            + "FOREIGN KEY(" + COLUMN_LANGUAGE_ID_SCORE + ") REFERENCES " + TABLE_LANGUAGES + "(" + COLUMN_LANGUAGE_ID + ")"
            + ")";

    private static final String CREATE_TABLE_LANGUAGES = "CREATE TABLE " + TABLE_LANGUAGES + "("
            + COLUMN_LANGUAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_LETTERS + " TEXT,"
            + COLUMN_TRANSLATIONS + " TEXT"
            + ")";

    private static final String CREATE_TABLE_ENABLED_LETTERS = "CREATE TABLE " + TABLE_ENABLED_LETTERS + "("
            + COLUMN_ENABLED_LETTERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_ID_ENABLED_LETTERS + " INTEGER,"
            + COLUMN_LANGUAGE_ID_ENABLED_LETTERS + " INTEGER,"
            + COLUMN_LETTERS_ENABLED_LETTERS + " TEXT,"
            + "FOREIGN KEY(" + COLUMN_USER_ID_ENABLED_LETTERS + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "),"
            + "FOREIGN KEY(" + COLUMN_LANGUAGE_ID_ENABLED_LETTERS + ") REFERENCES " + TABLE_LANGUAGES + "(" + COLUMN_LANGUAGE_ID + ")"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criar tabelas no banco de dados
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_SCORES);
        db.execSQL(CREATE_TABLE_LANGUAGES);
        db.execSQL(CREATE_TABLE_ENABLED_LETTERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Atualizações futuras do banco de dados
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LANGUAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENABLED_LETTERS);
        onCreate(db);
    }
}
