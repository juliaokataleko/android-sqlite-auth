package com.kataleko.androidSQLiteAuth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.kataleko.androidSQLiteAuth.helpers.Auth;
import com.kataleko.androidSQLiteAuth.models.User;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    Intent intent;
    Button btnGoToHome;
    ListView lvListUsers;
    DBHelper dbHelper;
    private List<User> usersList;
    private ArrayAdapter<User> adapter;
    long profileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean isLoggedIn = userPref.getBoolean("isLoggedIn", false);

        if(!isLoggedIn) {
            this.getSharedPreferences("user", 0).edit().clear().apply();
            Intent intent = new Intent(UsersActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {

            // check in db
            Auth auth = new Auth(this);
            if(auth.isLogged()) {
                profileId = Long.parseLong(userPref.getString("id", String.valueOf(0)));
            } else {
                this.getSharedPreferences("user", 0).edit().clear().apply();
                Intent intent = new Intent(UsersActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

        }

        intent = getIntent();

        btnGoToHome = findViewById(R.id.btnGoToHome);
        lvListUsers = findViewById(R.id.lvUsers);
        dbHelper = new DBHelper(this);
        usersList = new ArrayList<>();

        btnGoToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UsersActivity.this, MainActivity.class);
                i.putExtra("id", intent.getExtras().getLong("id"));
                startActivityForResult(i, 1);
            }
        });

        lvListUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User userItem = usersList.get(i);
                Intent intent2 = new Intent(UsersActivity.this, UpdateUserActivity.class);
                intent2.putExtra("name", userItem.getName());
                intent2.putExtra("login", userItem.getLogin());
                intent2.putExtra("pass", userItem.getPass());
                intent2.putExtra("id",  String.valueOf(userItem.getId()));
                Log.e("ID", String.valueOf(userItem.getId()));
                intent2.putExtra("address", userItem.getAddress());
                startActivityForResult(intent2, 1);
            }
        });

        carregarListaDeUsuarios();

        // tvWellcome = findViewById(R.id.wellcome);
        //tvWellcome.setText(intent.getExtras().getString("login") + " - " + intent.getExtras().getLong("id"));
    }

    private void carregarListaDeUsuarios() {
        Log.e( "U...", "entrou no carregamento...");
        usersList = dbHelper.selectAllUsers();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usersList);
        lvListUsers.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && (resultCode == 1 || resultCode == 2)) {
            carregarListaDeUsuarios();
        }
    }
}