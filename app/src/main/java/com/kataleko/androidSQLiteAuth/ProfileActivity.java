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
import com.kataleko.androidSQLiteAuth.models.User;

public class ProfileActivity extends AppCompatActivity {

    EditText etId, etLogin, etPassword, etCPassword, etName;
    Button btnUpdateProfile, btnDeleteProfile;
    Intent intent;
    DBHelper dbHelper;
    long profileId;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etId = findViewById(R.id.etIdProfile);
        etLogin = findViewById(R.id.etLoginProfile);
        etPassword = findViewById(R.id.etPasswordProfile);
        etCPassword = findViewById(R.id.etCPasswordProfile);
        etName = findViewById(R.id.etNameProfile);

        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        btnDeleteProfile = findViewById(R.id.btnDeleteProfile);
        dbHelper = new DBHelper(this);
        builder = new AlertDialog.Builder(this);

        intent = getIntent();

        SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean isLoggedIn = userPref.getBoolean("isLoggedIn", false);

        if(!isLoggedIn) {
            this.getSharedPreferences("user", 0).edit().clear().apply();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            // check in db
            Auth auth = new Auth(this);
            if(auth.isLogged()) {
                profileId = Long.parseLong(userPref.getString("id", String.valueOf(0)));
            } else {
                this.getSharedPreferences("user", 0).edit().clear().apply();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }

        btnDeleteProfile.setOnClickListener(new View.OnClickListener() {
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

                                long res = dbHelper.deleteUser((int) profileId);
                                if(res > 0) {
                                    Toast.makeText(ProfileActivity.this, "Usuário excluído com sucesso", Toast.LENGTH_SHORT).show();
                                    setResult(2);
                                    finish();
                                } else {
                                    Toast.makeText(ProfileActivity.this, "Houve um erro", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .show();

            }
        });

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etName.getText().toString().trim();
                String login = etLogin.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();
                String cpass = etCPassword.getText().toString().trim();

                if(name.isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Por favor insere o nome", Toast.LENGTH_SHORT).show();
                } else if(login.isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Por favor insere o login", Toast.LENGTH_SHORT).show();
                } else if(pass.isEmpty() || pass.length() < 5) {
                    Toast.makeText(ProfileActivity.this, "Por favor insere uma senha com 5 carateres no mínimo", Toast.LENGTH_SHORT).show();
                } else if(!pass.equals(cpass)) {
                    Toast.makeText(ProfileActivity.this, "Por favor confirme a senha", Toast.LENGTH_SHORT).show();
                } else {
                    // update user in database
                    long res = dbHelper.updateUser(name, login, pass, (int) profileId);
                    if(res > 0) {
                        Toast.makeText(ProfileActivity.this, "Usuário salvo com sucesso", Toast.LENGTH_SHORT).show();
                        setResult(2);
                        finish();
                    } else {
                        Toast.makeText(ProfileActivity.this, "Houve um erro", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        carregarDadosDoUsuarioLogado(profileId);

    }

    private void carregarDadosDoUsuarioLogado(long id) {
        User user = dbHelper.findById((int) id);
        if(user != null) {
            etId.setText(String.valueOf(user.getId()));
            etLogin.setText(user.getLogin());
            etPassword.setText(user.getPass());
            etCPassword.setText(user.getPass());
            etName.setText(user.getName());
        }
    }
}