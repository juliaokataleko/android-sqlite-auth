package com.kataleko.androidSQLiteAuth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kataleko.androidSQLiteAuth.helpers.Auth;

public class UpdateUserActivity extends AppCompatActivity {

    EditText etLoginUpdate, etPasswordUpdate, etCPasswordUpdate, etNameUpdate;
    Button btnUserUpdate, btnDeleteUser;
    Intent intent;
    DBHelper dbHelper;
    long profileId;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        builder = new AlertDialog.Builder(this);

        SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean isLoggedIn = userPref.getBoolean("isLoggedIn", false);

        if(!isLoggedIn) {
            this.getSharedPreferences("user", 0).edit().clear().apply();
            Intent intent = new Intent(UpdateUserActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            // check in db
            Auth auth = new Auth(this);
            if(auth.isLogged()) {
                profileId = Long.parseLong(userPref.getString("id", String.valueOf(0)));
            } else {
                this.getSharedPreferences("user", 0).edit().clear().apply();
                Intent intent = new Intent(UpdateUserActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }

        intent = getIntent();
        dbHelper = new DBHelper(this);

        etNameUpdate = findViewById(R.id.etNameUpdate);
        etLoginUpdate = findViewById(R.id.etLoginUpdate);
        etPasswordUpdate = findViewById(R.id.etPasswordUpdate);
        etCPasswordUpdate = findViewById(R.id.etCPasswordUpdate);
        btnUserUpdate = findViewById(R.id.btnUserUpdate);
        btnDeleteUser = findViewById(R.id.btnDeleteUser);

        long userId = Long.parseLong(intent.getExtras().getString("id"));
        String name = intent.getExtras().getString("name");
        String login = intent.getExtras().getString("login");
        String password = intent.getExtras().getString("pass");
        String cpassword = intent.getExtras().getString("pass");

        etNameUpdate.setText(name);
        etLoginUpdate.setText(login);
        etPasswordUpdate.setText(password);
        etCPasswordUpdate.setText(cpassword);

        btnUserUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etNameUpdate.getText().toString().trim();
                String login = etLoginUpdate.getText().toString().trim();
                String pass = etPasswordUpdate.getText().toString().trim();
                String cpass = etCPasswordUpdate.getText().toString().trim();

                if(name.isEmpty()) {
                    Toast.makeText(UpdateUserActivity.this, "Por favor insere o nome", Toast.LENGTH_SHORT).show();
                } else if(login.isEmpty()) {
                    Toast.makeText(UpdateUserActivity.this, "Por favor insere o login", Toast.LENGTH_SHORT).show();
                } else if(pass.isEmpty() || pass.length() < 5) {
                    Toast.makeText(UpdateUserActivity.this, "Por favor insere uma senha com 5 carateres no mínimo", Toast.LENGTH_SHORT).show();
                } else if(!pass.equals(cpass)) {
                    Toast.makeText(UpdateUserActivity.this, "Por favor confirme a senha", Toast.LENGTH_SHORT).show();
                } else {
                    // update user in database
                    long res = dbHelper.updateUser(name, login, pass, (int) userId);
                    if(res > 0) {
                        Toast.makeText(UpdateUserActivity.this, "Usuário salvo com sucesso", Toast.LENGTH_SHORT).show();
                        setResult(2);
                        finish();
                    } else {
                        Toast.makeText(UpdateUserActivity.this, "Houve um erro", Toast.LENGTH_SHORT).show();
                    }
                }

             }
        });

        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder.setTitle("Atencão")
                        .setMessage("Deseja realmente excluir?")
                        .setCancelable(true)
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                long res = dbHelper.deleteUser((int) userId);
                                if(res > 0) {
                                    Toast.makeText(UpdateUserActivity.this, "Usuário excluído com sucesso", Toast.LENGTH_SHORT).show();
                                    setResult(2);
                                    finish();
                                } else {
                                    Toast.makeText(UpdateUserActivity.this, "Houve um erro", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .show();

            }
        });
    }
}