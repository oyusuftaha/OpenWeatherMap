package com.example.openweathermapcase.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.openweathermapcase.R;
import com.example.openweathermapcase.model.User;
import com.example.openweathermapcase.viewmodel.LoginViewModel;
import com.example.openweathermapcase.viewmodel.RegisterViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.etEMail)
    EditText etEMail;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.btnGoToRegister)
    Button btnGoToRegister;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    private LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Login");

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEMail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.length()==0){
                    etEMail.setError("Type your email");
                    return;
                }
                if (password.length()==0){
                    etPassword.setError("Type your password");
                    return;
                }
                loginViewModel.login(email,password);
            }
        });

        btnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        loginViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {

                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        loginViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Login Failed")
                        .setMessage(s)
                        .setPositiveButton("OK",null)
                        .create()
                        .show();
            }
        });



    }
}