package com.kataleko.androidSQLiteAuth;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.kataleko.androidSQLiteAuth.models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private  static String nomeDb = "projecto.db";
    private  static int versao = 1;

    public DBHelper(@Nullable Context context) {
        super(context, nomeDb, null, versao);
    }

    private String[] sql = {
            "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT , login TEXT UNIQUE, pass TEXT NOT NULL, name TEXT, mother_name TEXT, father_name TEXT, birth_place TEXT, email TEXT, phone TEXT, photo TEXT, address TEXT, gender TEXT, birthday TEXT, status INTEGER DEFAULT 1, user_type INTEGER DEFAULT 1, created_at TEXT, updated_at TEXT);"
    };

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        for (int i = 0; i < sql.length; i++) {
            sqLiteDatabase.execSQL(sql[i]);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // ======================Users==============================
    // ====================== Insert User =======================
    public long insertUser(String login, String passowrd) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();

        // now date
        String now = String.valueOf(SimpleDateFormat.getDateInstance());
        values.put("login", login);
        values.put("name", login);
        values.put("pass", passowrd);
        values.put("created_at", now);
        return database.insert("users", null, values);
    }

    // ====================== Update User =======================
    public long updateUser(String name, String login, String password, int id) {
        Log.e("updateUser: " , String.valueOf(id));
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("login", login);
        cv.put("pass", password);
        return database.update("users", cv, "id=?", new String[]{String.valueOf(id)});
    }

    // ====================== Check user exists ================
    public long checkUser(int id) {
        SQLiteDatabase database = getWritableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM users WHERE id=?", new String[]{String.valueOf(id)});

        c.moveToFirst();
        if(c.getCount() == 1) {
            return 1;
        }

        return 0;
    }

    // ====================== Delete User =======================
    public long deleteUser(int id) {
        SQLiteDatabase database = getWritableDatabase();
        return database.delete("users", "id=?", new String[]{String.valueOf(id)});
    }

    // ====================== Select all users ==================
    public List<User> selectAllUsers() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM users", null);

        List<User>  usersList = new ArrayList<>();

        c.moveToFirst();
        if(c.getCount() > 0) {
            do {
                @SuppressLint("Range") String id = c.getString(c.getColumnIndex("id"));
                @SuppressLint("Range") String login = c.getString(c.getColumnIndex("login"));
                @SuppressLint("Range") String name = c.getString(c.getColumnIndex("name"));
                @SuppressLint("Range") String email = c.getString(c.getColumnIndex("email"));
                @SuppressLint("Range") String phone = c.getString(c.getColumnIndex("phone"));
                @SuppressLint("Range") String address = c.getString(c.getColumnIndex("address"));
                @SuppressLint("Range") String gender = c.getString(c.getColumnIndex("gender"));
                @SuppressLint("Range") String birthday = c.getString(c.getColumnIndex("birthday"));
                @SuppressLint("Range") String password = c.getString(c.getColumnIndex("pass"));
                User userItem = new User();
                userItem.setId(Integer.parseInt(id));
                userItem.setName(name);
                userItem.setEmail(email);
                userItem.setLogin(login);
                userItem.setGender(gender);
                userItem.setBirthday(birthday);
                userItem.setName(name);
                userItem.setPhone(phone);
                userItem.setAddress(address);
                userItem.setPass(password);

                usersList.add(userItem);

            } while (c.moveToNext());
        }
        return usersList;
    }

    //========================= FIND ONE ========================
    @SuppressLint("Range")
    public User findById(int id) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM users WHERE id=?", new String[]{String.valueOf(id)});

        c.moveToFirst();
        if(c.getCount() == 1) {
            do {
                @SuppressLint("Range") String login = c.getString(c.getColumnIndex("login"));
                @SuppressLint("Range") String name = c.getString(c.getColumnIndex("name"));
                @SuppressLint("Range") String email = c.getString(c.getColumnIndex("email"));
                @SuppressLint("Range") String phone = c.getString(c.getColumnIndex("phone"));
                @SuppressLint("Range") String address = c.getString(c.getColumnIndex("address"));
                @SuppressLint("Range") String gender = c.getString(c.getColumnIndex("gender"));
                @SuppressLint("Range") String birthday = c.getString(c.getColumnIndex("birthday"));
                @SuppressLint("Range") String password = c.getString(c.getColumnIndex("pass"));

                User userItem = new User();
                userItem.setId(id);
                userItem.setName(name);
                userItem.setEmail(email);
                userItem.setLogin(login);
                userItem.setGender(gender);
                userItem.setBirthday(birthday);
                userItem.setName(name);
                userItem.setPhone(phone);
                userItem.setAddress(address);
                userItem.setPass(password);

                return userItem;

            } while (c.moveToNext());
        }

        return null;
    }

    // ===================== LOGIN ==============================
    @SuppressLint("Range")
    public long login(String login, String password) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM users WHERE login=? AND pass=?", new String[]{login, password});
        c.moveToFirst();

        if(c.getCount() == 1) {
            return c.getInt(c.getColumnIndex("id"));
        }

        return -1;
    }
}
