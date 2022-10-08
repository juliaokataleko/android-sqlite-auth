package com.kataleko.androidSQLiteAuth.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.kataleko.androidSQLiteAuth.DBHelper;

public class Auth {

    private final Context context;
    private final SharedPreferences sharedPrefs;
    DBHelper dbHelper;

    public Auth(Context context) {
        this.context = context;
        sharedPrefs = context.getSharedPreferences("user", 0);
        dbHelper = new DBHelper(context);
    }

    public boolean isLogged() {
        boolean isLoggedIn = sharedPrefs.getBoolean("isLoggedIn", false);
        if(isLoggedIn) {
            long profileId = Long.parseLong(sharedPrefs.getString("id", String.valueOf(0)));
            Log.e("ID do usuário: ", sharedPrefs.getString("id", String.valueOf(0)));
            long res = dbHelper.checkUser((int) profileId);
            if(res > 0) return true;
        }
        return  false;
    }
}
