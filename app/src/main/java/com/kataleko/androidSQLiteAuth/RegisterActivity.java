package com.kataleko.androidSQLiteAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private ViewHolder viewHolder = new ViewHolder();
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        viewHolder.etLoginRegister = findViewById(R.id.etLoginRegister);
        viewHolder.etPasswordRegister = findViewById(R.id.etPasswordRegister);
        viewHolder.etCPasswordRegister = findViewById(R.id.etCPasswordRegister);
        viewHolder.btnRegister = findViewById(R.id.btnRegister);
        dbHelper = new DBHelper(this);

        SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean isLoggedIn = userPref.getBoolean("isLoggedIn", false);

        if(isLoggedIn) {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        viewHolder.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = viewHolder.etLoginRegister.getText().toString().trim();
                String password = viewHolder.etPasswordRegister.getText().toString().trim();
                String cpassword = viewHolder.etCPasswordRegister.getText().toString().trim();

                if(login.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Por favor informe o login e senha", Toast.LENGTH_SHORT).show();
                } else if(password.length() < 5) {
                    Toast.makeText(RegisterActivity.this, "A senha deve ter no mínimo 5 carateres.", Toast.LENGTH_SHORT).show();
                } else if(!password.equals(cpassword)) {
                    Toast.makeText(RegisterActivity.this, "Precisa confirmar a senha", Toast.LENGTH_SHORT).show();
                } else {
                    // registo válido
                    // buscar no banco
                    long res = dbHelper.insertUser(login, password);

                    if(res > 0) {
                        Toast.makeText(RegisterActivity.this, "Utilizador registado com sucesso.", Toast.LENGTH_SHORT).show();
                        Intent i = getIntent();
                        i.putExtra("login", login);
                        i.putExtra("password", password);
                        setResult(1, i);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Ocorreu um erro ao regista o o usuário...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public  class ViewHolder {
        EditText etLoginRegister, etPasswordRegister, etCPasswordRegister;
        Button btnRegister;
    }
}