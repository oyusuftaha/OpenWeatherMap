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
import com.example.openweathermapcase.util.CommonUtils;
import com.example.openweathermapcase.viewmodel.RegisterViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.etEMail)
    EditText etEMail;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.btnGoToLogin)
    Button btnGoTologin;

    RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_register);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Register");

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        btnGoTologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEMail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();


                if (CommonUtils.isEmailValid(email)) {
                    if (password.length()>=6) {
                        registerViewModel.register(email, password);
                    }else {
                        etPassword.setError("Password should be at least 6 character");
                    }
                }else{
                    etEMail.setError("Invalid email");
                }
            }
        });

        registerViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });

        registerViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("Register Failed")
                        .setMessage(s)
                        .setPositiveButton("OK",null)
                        .create()
                        .show();
            }
        });


    }

}