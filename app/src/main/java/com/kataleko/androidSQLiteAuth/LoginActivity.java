package com.kataleko.androidSQLiteAuth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private ViewHolder viewHolder = new ViewHolder();
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        viewHolder.etLogin = findViewById(R.id.etLogin);
        viewHolder.etPassword = findViewById(R.id.etPassword);
        viewHolder.btnLogin = findViewById(R.id.btnLogin);
        viewHolder.tvRegister = findViewById(R.id.tvRegister);

        dbHelper = new DBHelper(this);

        SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean isLoggedIn = userPref.getBoolean("isLoggedIn", false);

        if(isLoggedIn) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        viewHolder.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        viewHolder.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = viewHolder.etLogin.getText().toString().trim();
                String password = viewHolder.etPassword.getText().toString().trim();

                if(login.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor insira o login", Toast.LENGTH_SHORT).show();
                } else if(password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor insira a sua senha", Toast.LENGTH_SHORT).show();
                } else {
                    // buscar no banco se tem esse registo

                    long res = dbHelper.login(login, password);

                    if(res > 0) {

                        SharedPreferences userPref = getSharedPreferences("user", MODE_PRIVATE);
                        SharedPreferences.Editor editor = userPref.edit();
                        editor.putString("id", String.valueOf(res));
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("login", login);
                        intent.putExtra("id", res);
                        startActivity(intent);

                    } else {
                        Toast.makeText(LoginActivity.this, "Login incorreto. Tente de novo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public class ViewHolder {
        EditText etLogin, etPassword;
        Button btnLogin;
        TextView tvRegister;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null && requestCode == 1 && resultCode == 1) { // registo válido
            viewHolder.etLogin.setText(data.getExtras().getString("login"));
            viewHolder.etPassword.setText(data.getExtras().getString("password"));
        }
    }
}