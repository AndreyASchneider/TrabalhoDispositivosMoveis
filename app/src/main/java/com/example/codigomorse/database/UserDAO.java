package com.example.codigomorse.database;

import static com.example.codigomorse.database.DatabaseHelper.*;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.example.codigomorse.model.*;

public class UserDAO {

    private DatabaseHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (checkEmailExists(db, user.getEmail())) {
            return -1;
        }

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EMAIL, user.getEmail());
        values.put(DatabaseHelper.COLUMN_PASSWORD, user.getPassword());
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    @SuppressLint("Range")
    public User getUserById(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_USER_ID, DatabaseHelper.COLUMN_EMAIL, DatabaseHelper.COLUMN_PASSWORD},
                COLUMN_USER_ID + "=?", new String[]{String.valueOf(userId)}, null, null, null);
        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE_CREATE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE_UPDATE))
            );
            cursor.close();
        }
        db.close();
        return user;
    }

    @SuppressLint("Range")
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD)));
                user.setDateCreate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE_CREATE)));
                user.setDateUpdate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE_UPDATE)));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    public int updateUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EMAIL, user.getEmail());
        values.put(DatabaseHelper.COLUMN_PASSWORD, user.getPassword());
        int rowsAffected = db.update(TABLE_USERS, values, COLUMN_USER_ID + "=?",
                new String[]{String.valueOf(user.getId())});
        db.close();
        return rowsAffected;
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_USERS, COLUMN_USER_ID + "=?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public User getUserByEmailAndPassword(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                COLUMN_USER_ID,
                DatabaseHelper.COLUMN_EMAIL,
                DatabaseHelper.COLUMN_PASSWORD
        };
        String selection = DatabaseHelper.COLUMN_EMAIL + " = ? AND " + DatabaseHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = { email, password };
        Cursor cursor = db.query(
                TABLE_USERS,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") User user = new User(
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD))
            );
            cursor.close();
            db.close();
            return user;
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return null;
    }

    private boolean checkEmailExists(SQLiteDatabase db, String email) {
        String[] projection = {
                COLUMN_USER_ID
        };

        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(
                TABLE_USERS,   // Nome da tabela
                projection,                         // Colunas para retornar
                selection,                          // Colunas para a cláusula WHERE
                selectionArgs,                      // Valores para a cláusula WHERE
                null,                               // Não agrupar as linhas
                null,                               // Não filtrar por grupo de linhas
                null                                // Ordem de classificação
        );

        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
}
