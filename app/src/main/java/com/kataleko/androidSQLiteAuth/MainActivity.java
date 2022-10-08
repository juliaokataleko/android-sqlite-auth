package com.kataleko.androidSQLiteAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.kataleko.androidSQLiteAuth.helpers.Auth;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    Button btnGoToProfile, btnUsers;
    DBHelper dbHelper;
    long profileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // getSupportActionBar().hide();
        intent = getIntent();
        // profileId = intent.getExtras().getLong("id");

        dbHelper = new DBHelper(this);

        SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean isLoggedIn = userPref.getBoolean("isLoggedIn", false);

        if(!isLoggedIn) {
            this.getSharedPreferences("user", 0).edit().clear().apply();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            // check in db
            Auth auth = new Auth(this);
            if(auth.isLogged()) {
                profileId = Long.parseLong(userPref.getString("id", String.valueOf(0)));
            } else {
                this.getSharedPreferences("user", 0).edit().clear().apply();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                Intent intent1 = new Intent(MainActivity.this, ProfileActivity.class);
                intent1.putExtra("id", profileId);
                startActivity(intent1);
                return true;
            case R.id.users:
                Intent i = new Intent(MainActivity.this, UsersActivity.class);
                i.putExtra("id", profileId);
                startActivity(i);
                return true;
            case R.id.logout:
                this.getSharedPreferences("user", 0).edit().clear().apply();
                Intent logout = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(logout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}